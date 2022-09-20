package uni9.projetopraticoemsistemas.myhealth.perfil.repositories;

import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import uni9.projetopraticoemsistemas.myhealth.MyHealthDatabase;
import uni9.projetopraticoemsistemas.myhealth.perfil.apis.PerfilDao;
import uni9.projetopraticoemsistemas.myhealth.perfil.mappers.PerfilMapper;
import uni9.projetopraticoemsistemas.myhealth.perfil.model.Perfil;
import uni9.projetopraticoemsistemas.myhealth.perfil.model.entity.PerfilEntity;
import uni9.projetopraticoemsistemas.myhealth.perfil.model.entity.UsuarioJoinPerfil;

public class PerfilRepository {


    private final PerfilMapper perfilMapper;

    private final PerfilDao perfilDao;

    private final MutableLiveData<Perfil> perfilUsuarioLiveData;
    private final MutableLiveData<Long> novoPerfilUsuarioLiveData;

    @Inject
    public PerfilRepository(PerfilDao perfilDao,
                            PerfilMapper perfilMapper) {
        this.perfilDao = perfilDao;
        this.perfilMapper = perfilMapper;
        this.perfilUsuarioLiveData = new MutableLiveData<>();
        this.novoPerfilUsuarioLiveData = new MutableLiveData<>();
    }

    public void obterPerfilUsuario(Long idUsuario) {
        MyHealthDatabase.databaseWriteExecutor.execute(() -> {
            UsuarioJoinPerfil usuarioJoinPerfil = perfilDao.getPerfilByIdUsuario(idUsuario);
            perfilUsuarioLiveData.postValue(perfilMapper.perfilEntityToPerfil(usuarioJoinPerfil));
        });
    }

    public void inserirPerfilUsuario(Perfil perfil, Long idUsuario) {
        MyHealthDatabase.databaseWriteExecutor.execute(() -> {
            PerfilEntity perfilEntity = perfilMapper.perfilToPerfilEntity(perfil, idUsuario);
            long usuarioJoinPerfil = perfilDao.insert(perfilEntity);
            novoPerfilUsuarioLiveData.postValue(usuarioJoinPerfil);
        });
    }

    public MutableLiveData<Perfil> getPerfilUsuarioLiveData() {
        return perfilUsuarioLiveData;
    }

    public MutableLiveData<Long> getNovoPerfilUsuarioLiveData() {
        return novoPerfilUsuarioLiveData;
    }
}
