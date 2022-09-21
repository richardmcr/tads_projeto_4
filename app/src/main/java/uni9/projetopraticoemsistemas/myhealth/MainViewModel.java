package uni9.projetopraticoemsistemas.myhealth;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import uni9.projetopraticoemsistemas.myhealth.login.model.Usuario;
import uni9.projetopraticoemsistemas.myhealth.login.repositories.UsuarioRepository;

@HiltViewModel
public class MainViewModel extends AndroidViewModel {

    private final MutableLiveData<Usuario> usuarioLiveData;

    @Inject
    public MainViewModel(@NonNull Application application,
                         UsuarioRepository usuarioRepository) {
        super(application);
        this.usuarioLiveData = usuarioRepository.getUsuarioLiveData();
    }

    public MutableLiveData<Usuario> getUsuarioLiveData() {
        return usuarioLiveData;
    }

}
