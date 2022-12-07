package uni9.projetopraticoemsistemas.myhealth.home.agua.repositories;

import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import uni9.projetopraticoemsistemas.myhealth.MyHealthDatabase;
import uni9.projetopraticoemsistemas.myhealth.home.agua.apis.LembreteAguaDao;
import uni9.projetopraticoemsistemas.myhealth.home.agua.mappers.LembreteAguaMapper;
import uni9.projetopraticoemsistemas.myhealth.home.agua.model.LembreteAgua;
import uni9.projetopraticoemsistemas.myhealth.home.agua.model.entity.LembreteAguaEntity;

public class LembreteAguaRepository {

    private final LembreteAguaDao lembreteAguaDao;

    private final LembreteAguaMapper lembreteAguaMapper;

    private final MutableLiveData<LembreteAgua> lembreteAguaLiveData;
    private final MutableLiveData<Long> novoLembreteAguaLiveData;

    @Inject
    public LembreteAguaRepository(LembreteAguaDao lembreteAguaDao, LembreteAguaMapper lembreteAguaMapper) {
        this.lembreteAguaDao = lembreteAguaDao;
        this.lembreteAguaMapper = lembreteAguaMapper;
        this.lembreteAguaLiveData = new MutableLiveData<>();
        this.novoLembreteAguaLiveData = new MutableLiveData<>();
    }

    public void obterLembreteAgua(Long usuarioId) {
        MyHealthDatabase.databaseWriteExecutor.execute(() -> {
            LembreteAguaEntity lembreteAguaEntity = lembreteAguaDao.getLembreteAgua(usuarioId);
            lembreteAguaLiveData.postValue(lembreteAguaMapper.lembreteAguaEntityToLembreteAgua(lembreteAguaEntity));
        });
    }

    public void inserirLembreteAgua(LembreteAgua lembreteAgua, Long usuarioId) {
        MyHealthDatabase.databaseWriteExecutor.execute(() -> {
            LembreteAguaEntity lembreteAguaEntity = lembreteAguaMapper.lembreteAguaToLembreteAguaEntity(lembreteAgua, usuarioId);
            long lembreteAguaId = lembreteAguaDao.insert(lembreteAguaEntity);
            novoLembreteAguaLiveData.postValue(lembreteAguaId);
        });
    }

    public MutableLiveData<LembreteAgua> getLembreteAguaLiveData() {
        return lembreteAguaLiveData;
    }

    public MutableLiveData<Long> getNovoLembreteAguaLiveData() {
        return novoLembreteAguaLiveData;
    }
}
