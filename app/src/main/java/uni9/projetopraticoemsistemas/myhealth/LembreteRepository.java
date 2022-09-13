package uni9.projetopraticoemsistemas.myhealth;

import static uni9.projetopraticoemsistemas.myhealth.helper.DateFormatter.stringDateTimeTolong;

import android.util.Log;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import uni9.projetopraticoemsistemas.myhealth.apis.LembreteDao;
import uni9.projetopraticoemsistemas.myhealth.mappers.LembreteMapper;
import uni9.projetopraticoemsistemas.myhealth.model.Lembrete;
import uni9.projetopraticoemsistemas.myhealth.model.Ordenacao;
import uni9.projetopraticoemsistemas.myhealth.model.QueryParams;
import uni9.projetopraticoemsistemas.myhealth.model.entity.LembreteEntity;
import uni9.projetopraticoemsistemas.myhealth.model.entity.LembreteJoinMedicamento;


public class LembreteRepository {

    private final LembreteDao lembreteDao;
    private final LembreteMapper lembreteMapper;
    private final MutableLiveData<Long> usuarioLiveData;
    private final MutableLiveData<String> medicamentoLiveData;
    private final MutableLiveData<Ordenacao> ordenacaoLiveData;
    private final MutableLiveData<Boolean> ocultarCompletosLiveData;
    private final MediatorLiveData<QueryParams> liveDataMediator;
    private final MutableLiveData<Lembrete> lembreteLiveData;
    private final MutableLiveData<List<Lembrete>> lembreteListLiveData;
    private final QueryParams queryParams;

    @Inject
    public LembreteRepository(LembreteDao lembreteDao, LembreteMapper lembreteMapper) {
        this.lembreteDao = lembreteDao;
        this.lembreteMapper = lembreteMapper;
        this.queryParams = new QueryParams();
        this.lembreteLiveData = new MutableLiveData<>();
        this.lembreteListLiveData = new MutableLiveData<>();
        this.usuarioLiveData = new MutableLiveData<>(1L);
        this.medicamentoLiveData = new MutableLiveData<>("");
        this.ordenacaoLiveData = new MutableLiveData<>();
        this.ocultarCompletosLiveData = new MutableLiveData<>();
        this.liveDataMediator = new MediatorLiveData<>();

        this.liveDataMediator.observeForever(params -> listarLembretes());

        this.liveDataMediator.addSource(this.medicamentoLiveData, value -> {
            this.queryParams.setBusca(value);
            this.liveDataMediator.postValue(this.queryParams);
        });
        this.liveDataMediator.addSource(this.ordenacaoLiveData, value -> {
            this.queryParams.setOrdenacao(value);
            this.liveDataMediator.postValue(this.queryParams);
        });
        this.liveDataMediator.addSource(this.ocultarCompletosLiveData, value -> {
            this.queryParams.setOcultarCompletos(value);
            this.liveDataMediator.postValue(this.queryParams);
        });
    }

    public void listarLembretes() {
        MyHealthDatabase.databaseWriteExecutor.execute(this::getLembretes);
    }

    private void getLembretes() {
        List<LembreteJoinMedicamento> lembreteEntityList;
        if (ordenacaoLiveData.getValue() == Ordenacao.BY_DATA) {
            if (Boolean.TRUE.equals(ocultarCompletosLiveData.getValue())) {
                Log.d("CONSULTA", "findAllLembretesCompletosByIdUsuarioAndMedicamentoSortedByProximaDose");
                lembreteEntityList = lembreteDao.findAllLembretesCompletosByIdUsuarioAndMedicamentoSortedByProximaDose(usuarioLiveData.getValue(), medicamentoLiveData.getValue(), System.currentTimeMillis());
            } else {
                Log.d("CONSULTA", "findAllLembretesByIdUsuarioAndMedicamentoSortedByProximaDose");
                lembreteEntityList = lembreteDao.findAllLembretesByIdUsuarioAndMedicamentoSortedByProximaDose(usuarioLiveData.getValue(), medicamentoLiveData.getValue(), System.currentTimeMillis());
            }
        } else {
            if (Boolean.TRUE.equals(ocultarCompletosLiveData.getValue())) {
                Log.d("CONSULTA", "findAllLembretesCompletosByIdUsuarioAndMedicamentoSortedByMedicamentoNomeProduto");
                lembreteEntityList = lembreteDao.findAllLembretesCompletosByIdUsuarioAndMedicamentoSortedByMedicamentoNomeProduto(usuarioLiveData.getValue(), medicamentoLiveData.getValue(), System.currentTimeMillis());
            } else {
                Log.d("CONSULTA", "findAllLembretesByIdUsuarioAndMedicamentoSortedByMedicamentoNomeProduto");
                lembreteEntityList = lembreteDao.findAllLembretesByIdUsuarioAndMedicamentoSortedByMedicamentoNomeProduto(usuarioLiveData.getValue(), medicamentoLiveData.getValue(), System.currentTimeMillis());
            }
        }
        lembreteListLiveData.postValue(lembreteMapper.lembreteJoinMedicamentoToLembretes(lembreteEntityList));
    }

    public void getLembrete(Long id) {
        MyHealthDatabase.databaseWriteExecutor.execute(() -> {
            LembreteJoinMedicamento lembreteJoinMedicamento = lembreteDao.findLembreteById(id, System.currentTimeMillis());
            lembreteLiveData.postValue(lembreteMapper.lembreteJoinMedicamentoToLembrete(lembreteJoinMedicamento));
        });
    }

    public void removerLembrete(Lembrete lembrete) {
        MyHealthDatabase.databaseWriteExecutor.execute(() -> {
            LembreteEntity entity = lembreteMapper.lembreteToLembreteEntity(lembrete);
            lembreteDao.delete(entity);
            getLembretes();
        });
    }

    public void inserirLembrete(Lembrete lembrete) {
        MyHealthDatabase.databaseWriteExecutor.execute(() -> {
            lembreteDao.insert(1L, lembrete.getMedicamento().getId(), lembrete.getDetalhes(),
                    stringDateTimeTolong(lembrete.getDataInicio() + " " + lembrete.getHoraInicio()), lembrete.getDuracao(),
                    lembrete.getIntervalo(), lembrete.getAlertas());
            getLembretes();
        });
    }

    public void removerLembretesCompletos() {
        MyHealthDatabase.databaseWriteExecutor.execute(() -> {
            lembreteDao.deleteAllLembretesCompletos(System.currentTimeMillis());
            getLembretes();
        });
    }

    public MutableLiveData<List<Lembrete>> getLembreteListLiveData() {
        return lembreteListLiveData;
    }

    public MutableLiveData<Lembrete> getLembreteLiveData() {
        return lembreteLiveData;
    }

    public MutableLiveData<String> getMedicamentoLiveData() {
        return medicamentoLiveData;
    }

    public MutableLiveData<Ordenacao> getOrdenacaoLiveData() {
        return ordenacaoLiveData;
    }

    public MutableLiveData<Boolean> getOcultarCompletosLiveData() {
        return ocultarCompletosLiveData;
    }
}
