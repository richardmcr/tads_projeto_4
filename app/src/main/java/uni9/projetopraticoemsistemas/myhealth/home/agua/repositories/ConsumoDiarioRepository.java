package uni9.projetopraticoemsistemas.myhealth.home.agua.repositories;

import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import uni9.projetopraticoemsistemas.myhealth.MyHealthDatabase;
import uni9.projetopraticoemsistemas.myhealth.home.agua.apis.ConsumoDiarioDao;
import uni9.projetopraticoemsistemas.myhealth.home.agua.mappers.ConsumoDiarioMapper;
import uni9.projetopraticoemsistemas.myhealth.home.agua.model.ConsumoDiario;
import uni9.projetopraticoemsistemas.myhealth.home.agua.model.entity.ConsumoDiarioEntity;

public class ConsumoDiarioRepository {

    private final ConsumoDiarioDao consumoDiarioDao;

    private final ConsumoDiarioMapper consumoDiarioMapper;

    private final MutableLiveData<ConsumoDiario> consumoDiarioLiveData;
    private final MutableLiveData<Long> novoConsumoDiarioLiveData;

    @Inject
    public ConsumoDiarioRepository(ConsumoDiarioDao consumoDiarioDao, ConsumoDiarioMapper consumoDiarioMapper) {
        this.consumoDiarioDao = consumoDiarioDao;
        this.consumoDiarioMapper = consumoDiarioMapper;
        this.consumoDiarioLiveData = new MutableLiveData<>();
        this.novoConsumoDiarioLiveData = new MutableLiveData<>();
    }

    public void obterConsumoDiario(Long usuarioId, String data) {
        MyHealthDatabase.databaseWriteExecutor.execute(() -> {
            ConsumoDiarioEntity consumoDiarioEntity = consumoDiarioDao.findConsumoDiarioByIdUsuarioAndData(usuarioId, data);
            consumoDiarioLiveData.postValue(consumoDiarioMapper.consumoDiarioEntityToConsumoDiario(consumoDiarioEntity));
        });
    }

//    public void inserirLembreteAgua(LembreteAgua lembreteAgua, Long usuarioId) {
//        MyHealthDatabase.databaseWriteExecutor.execute(() -> {
//            LembreteAguaEntity lembreteAguaEntity = lembreteAguaMapper.lembreteAguaToLembreteAguaEntity(lembreteAgua, usuarioId);
//            long lembreteAguaId = lembreteAguaDao.insert(lembreteAguaEntity);
//            novoLembreteAguaLiveData.postValue(lembreteAguaId);
//        });
//    }

    public MutableLiveData<ConsumoDiario> getConsumoDiarioLiveData() {
        return consumoDiarioLiveData;
    }

    public MutableLiveData<Long> getNovoConsumoDiarioLiveData() {
        return novoConsumoDiarioLiveData;
    }
}
