package uni9.projetopraticoemsistemas.myhealth.login.viewmodel;


import android.app.Application;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import uni9.projetopraticoemsistemas.myhealth.eventos.Eventos;
import uni9.projetopraticoemsistemas.myhealth.login.model.Usuario;
import uni9.projetopraticoemsistemas.myhealth.login.repositories.UsuarioRepository;

@HiltViewModel
public class LoginViewModel extends AndroidViewModel {

    private final Long USUARIO_NAO_ENCONTRADO = -1L;

    private final UsuarioRepository usuarioRepository;
    private final MutableLiveData<String> emailLiveData;
    private final MutableLiveData<String> senhaLiveData;
    private final MutableLiveData<Usuario> usuarioLiveData;
    private final MutableLiveData<Eventos<?>> eventoLiveData;
    private final Observer<Usuario> usuarioObserver;

    @Inject
    public LoginViewModel(@NonNull Application application,
                          UsuarioRepository usuarioRepository) {
        super(application);
        this.usuarioRepository = usuarioRepository;

        this.emailLiveData = new MutableLiveData<>();
        this.senhaLiveData = new MutableLiveData<>();
        this.usuarioLiveData = usuarioRepository.getUsuarioLiveData();
        this.eventoLiveData = new MutableLiveData<>();
        this.usuarioObserver = usuario -> {
            if (!Objects.isNull(usuario)) {
                if (Objects.equals(usuario.getId(), this.USUARIO_NAO_ENCONTRADO)) {
                    onErroDeValidacao("usuario_nao_encontrado");
                } else if (!usuario.getSenha().equals(this.senhaLiveData.getValue())) {
                    onErroDeValidacao("senha_errada");
                } else {
                    onUsuarioLogado(usuario);
                }
            }
        };
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        usuarioLiveData.removeObserver(usuarioObserver);
    }

    public void init() {
        usuarioLiveData.observeForever(usuarioObserver);
        emailLiveData.setValue(null);
        senhaLiveData.setValue(null);
    }

    public void logar() {
        if (Objects.isNull(emailLiveData.getValue()) || Objects.requireNonNull(emailLiveData.getValue()).isEmpty()) {
            onErroDeValidacao("email");
        } else if (!Objects.requireNonNull(emailLiveData.getValue()).matches(Patterns.EMAIL_ADDRESS.pattern())) {
            onErroDeValidacao("email_invalido");
        } else if (Objects.isNull(senhaLiveData.getValue()) || Objects.requireNonNull(senhaLiveData.getValue()).isEmpty()) {
            onErroDeValidacao("senha");
        } else {
            usuarioRepository.obterUsuario(emailLiveData.getValue());
        }
    }

    private void onErroDeValidacao(String erro) {
        eventoLiveData.postValue(new Eventos.ErroValidacao(erro));
    }

    private void onUsuarioLogado(Usuario usuario) {
        usuarioRepository.atualizarUsuarioLogado(usuario);
        eventoLiveData.postValue(new Eventos.UsuarioLogado(usuario));
    }

    public void setEmail(String email) {
        this.emailLiveData.postValue(email);
    }

    public void setSenha(String senha) {
        this.senhaLiveData.postValue(senha);
    }

    public MutableLiveData<Eventos<?>> getEventoLiveData() {
        return eventoLiveData;
    }

    public MutableLiveData<Usuario> getUsuarioLiveData() {
        return usuarioLiveData;
    }
}
