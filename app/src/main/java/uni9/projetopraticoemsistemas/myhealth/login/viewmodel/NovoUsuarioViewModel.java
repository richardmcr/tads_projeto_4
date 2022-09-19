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
import uni9.projetopraticoemsistemas.myhealth.login.UsuarioRepository;
import uni9.projetopraticoemsistemas.myhealth.login.model.Usuario;

@HiltViewModel
public class NovoUsuarioViewModel extends AndroidViewModel {

    private final Long EMAIL_EXISTENTE = -1L;
    private final Long USUARIO_NAO_ENCONTRADO = 0L;

    private final UsuarioRepository usuarioRepository;
    private final MutableLiveData<String> nomeLiveData;
    private final MutableLiveData<String> emailLiveData;
    private final MutableLiveData<String> senhaLiveData;
    private final MutableLiveData<String> reSenhaLiveData;
    private final MutableLiveData<Long> usuarioLiveData;
    private final MutableLiveData<Eventos<?>> eventoLiveData;
    private final Observer<Long> usuarioObserver;

    @Inject
    public NovoUsuarioViewModel(@NonNull Application application,
                                UsuarioRepository usuarioRepository) {
        super(application);
        this.usuarioRepository = usuarioRepository;

        this.nomeLiveData = new MutableLiveData<>();
        this.emailLiveData = new MutableLiveData<>();
        this.senhaLiveData = new MutableLiveData<>();
        this.reSenhaLiveData = new MutableLiveData<>();
        this.usuarioLiveData = usuarioRepository.getNovoUsuarioLiveData();
        this.eventoLiveData = new MutableLiveData<>();
        this.usuarioObserver = usuarioId -> {
            if (!Objects.isNull(usuarioId)) {
                if (Objects.equals(usuarioId, this.EMAIL_EXISTENTE)) {
                    onErroDeValidacao("email_existente");
                } else if (Objects.equals(usuarioId, this.USUARIO_NAO_ENCONTRADO)) {
                    Usuario usuario = new Usuario();
                    usuario.setNome(this.nomeLiveData.getValue());
                    usuario.setEmail(this.emailLiveData.getValue());
                    usuario.setSenha(this.senhaLiveData.getValue());

                    this.usuarioRepository.inserirUsuario(usuario);
                } else {
                    this.eventoLiveData.postValue(new Eventos.UsuarioSalvo<>());
                }
            }
        };
        this.usuarioLiveData.observeForever(this.usuarioObserver);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        usuarioLiveData.removeObserver(usuarioObserver);
    }

    public void init() {
        usuarioLiveData.setValue(null);
        eventoLiveData.setValue(null);
    }

    public void inserirUsuario() {
        if (Objects.isNull(nomeLiveData.getValue()) || Objects.requireNonNull(nomeLiveData.getValue()).isEmpty()) {
            onErroDeValidacao("nome");
        } else if (Objects.isNull(emailLiveData.getValue())) {
            onErroDeValidacao("email");
        } else if (!Objects.requireNonNull(emailLiveData.getValue()).matches(Patterns.EMAIL_ADDRESS.pattern())) {
            onErroDeValidacao("email_invalido");
        } else if (Objects.isNull(senhaLiveData.getValue())) {
            onErroDeValidacao("senha");
        } else if (Objects.requireNonNull(senhaLiveData.getValue()).length() != 6) {
            onErroDeValidacao("senha_curta");
        } else if (Objects.isNull(reSenhaLiveData.getValue())) {
            onErroDeValidacao("resenha");
        } else if (!Objects.requireNonNull(senhaLiveData.getValue()).equals(Objects.requireNonNull(reSenhaLiveData.getValue()))) {
            onErroDeValidacao("senhas_diferentes");
        } else {
            usuarioRepository.existeUsuario(emailLiveData.getValue());
        }
    }

    private void onErroDeValidacao(String erro) {
        eventoLiveData.postValue(new Eventos.ErroValidacao(erro));
    }

    public void setEmail(String email) {
        this.emailLiveData.postValue(email);
    }

    public void setNome(String nome) {
        this.nomeLiveData.postValue(nome);
    }

    public void setSenha(String senha) {
        this.senhaLiveData.postValue(senha);
    }

    public void setReSenha(String reSenha) {
        this.reSenhaLiveData.postValue(reSenha);
    }

    public MutableLiveData<Eventos<?>> getEventoLiveData() {
        return eventoLiveData;
    }

}
