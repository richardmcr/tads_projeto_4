package uni9.projetopraticoemsistemas.myhealth.home.home.viewmodel;


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
public class HomeViewModel extends AndroidViewModel {

    private final UsuarioRepository usuarioRepository;
    private final MutableLiveData<Long> usuarioIdLiveData;
    private final MutableLiveData<Usuario> usuarioLiveData;
    private final MutableLiveData<Eventos<?>> eventoLiveData;
    private final Observer<Usuario> usuarioObserver;

    @Inject
    public HomeViewModel(@NonNull Application application,
                         UsuarioRepository usuarioRepository) {
        super(application);
        this.usuarioRepository = usuarioRepository;
        this.usuarioIdLiveData = new MutableLiveData<>();
        this.usuarioLiveData = usuarioRepository.getUsuarioLiveData();
        this.eventoLiveData = new MutableLiveData<>();
        this.usuarioObserver = usuario -> {
            if (!Objects.isNull(usuario)) {
                onUsuarioLogado(usuario);
            }
        };
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        usuarioLiveData.removeObserver(usuarioObserver);
    }

    public void init() {
        usuarioLiveData.setValue(null);
        usuarioLiveData.observeForever(usuarioObserver);
        usuarioIdLiveData.setValue(usuarioRepository.getIdUsuarioLogado());
    }

    public void onLogin(Long id) {
        usuarioRepository.obterUsuario(id);
    }

    public void onSair() {
        usuarioRepository.sair();
        eventoLiveData.postValue(new Eventos.Logout<>());
    }

    private void onUsuarioLogado(Usuario usuario) {
        eventoLiveData.postValue(new Eventos.UsuarioLogado(usuario));
    }

    public MutableLiveData<Eventos<?>> getEventoLiveData() {
        return eventoLiveData;
    }

    public MutableLiveData<Long> getUsuarioIdLiveData() {
        return usuarioIdLiveData;
    }

}
