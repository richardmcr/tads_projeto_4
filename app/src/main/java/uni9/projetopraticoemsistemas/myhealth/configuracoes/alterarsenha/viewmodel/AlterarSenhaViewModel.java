package uni9.projetopraticoemsistemas.myhealth.configuracoes.alterarsenha.viewmodel;


import android.app.Application;

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
public class AlterarSenhaViewModel extends AndroidViewModel {

    private final UsuarioRepository usuarioRepository;
    private final MutableLiveData<String> senhaAtualLiveData;
    private final MutableLiveData<String> novaSenhaLiveData;
    private final MutableLiveData<String> reNovaSenhaLiveData;
    private final MutableLiveData<Usuario> usuarioLiveData;
    private final MutableLiveData<Long> idUsuarioLiveData;
    private final MutableLiveData<Eventos<?>> eventoLiveData;
    private final Observer<Usuario> usuarioObserver;
    private final Observer<Long> idUsuarioObserver;

    @Inject
    public AlterarSenhaViewModel(@NonNull Application application,
                                 UsuarioRepository usuarioRepository) {
        super(application);
        this.usuarioRepository = usuarioRepository;

        this.senhaAtualLiveData = new MutableLiveData<>();
        this.novaSenhaLiveData = new MutableLiveData<>();
        this.reNovaSenhaLiveData = new MutableLiveData<>();
        this.usuarioLiveData = usuarioRepository.getUsuarioLiveData();
        this.idUsuarioLiveData = usuarioRepository.getNovoUsuarioLiveData();
        this.eventoLiveData = new MutableLiveData<>();
        this.usuarioObserver = usuario -> {
            if (!Objects.isNull(usuario)) {
                if (!Objects.equals(this.senhaAtualLiveData.getValue(), usuario.getSenha())) {
                    onErroDeValidacao("senha_errada");
                } else {
                    usuario.setSenha(this.novaSenhaLiveData.getValue());
                    this.usuarioRepository.alterarSenha(usuario);
                }
            }
        };
        this.usuarioLiveData.observeForever(this.usuarioObserver);
        this.idUsuarioObserver = idUsuario -> {
            if (!Objects.isNull(idUsuario)) {
                this.eventoLiveData.postValue(new Eventos.SenhaAlterada<>());
            }
        };
        this.idUsuarioLiveData.observeForever(this.idUsuarioObserver);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        usuarioLiveData.removeObserver(usuarioObserver);
        idUsuarioLiveData.removeObserver(idUsuarioObserver);
    }

    public void init() {
        usuarioLiveData.setValue(null);
        idUsuarioLiveData.setValue(null);
        eventoLiveData.setValue(null);
    }

    public void alterarSenha() {
        if (Objects.isNull(senhaAtualLiveData.getValue())) {
            onErroDeValidacao("senha_atual");
        } else if (Objects.isNull(novaSenhaLiveData.getValue())) {
            onErroDeValidacao("nova_senha");
        } else if (Objects.requireNonNull(novaSenhaLiveData.getValue()).length() != 6) {
            onErroDeValidacao("nova_senha_curta");
        } else if (Objects.isNull(reNovaSenhaLiveData.getValue())) {
            onErroDeValidacao("re_nova_senha");
        } else if (!Objects.requireNonNull(novaSenhaLiveData.getValue()).equals(Objects.requireNonNull(reNovaSenhaLiveData.getValue()))) {
            onErroDeValidacao("senhas_diferentes");
        } else {
            usuarioRepository.obterUsuarioLogado();
        }
    }

    private void onErroDeValidacao(String erro) {
        eventoLiveData.postValue(new Eventos.ErroValidacao(erro));
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtualLiveData.postValue(senhaAtual);
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenhaLiveData.postValue(novaSenha);
    }

    public void setReNovaSenha(String reNovaSenha) {
        this.reNovaSenhaLiveData.postValue(reNovaSenha);
    }

    public MutableLiveData<Eventos<?>> getEventoLiveData() {
        return eventoLiveData;
    }

}
