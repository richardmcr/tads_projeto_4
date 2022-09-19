package uni9.projetopraticoemsistemas.myhealth.lembretes.repositories;

import static uni9.projetopraticoemsistemas.myhealth.helper.DateFormatter.longDateToStringDate;
import static uni9.projetopraticoemsistemas.myhealth.helper.DateFormatter.longDateToStringTime;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import uni9.projetopraticoemsistemas.myhealth.MyHealthDatabase;
import uni9.projetopraticoemsistemas.myhealth.lembretes.apis.BuscaMedicamentoService;
import uni9.projetopraticoemsistemas.myhealth.lembretes.apis.MedicamentoDao;
import uni9.projetopraticoemsistemas.myhealth.eventos.Eventos;
import uni9.projetopraticoemsistemas.myhealth.lembretes.mappers.MedicamentoMapper;
import uni9.projetopraticoemsistemas.myhealth.lembretes.model.Lembrete;
import uni9.projetopraticoemsistemas.myhealth.lembretes.model.Medicamento;
import uni9.projetopraticoemsistemas.myhealth.lembretes.model.dto.BuscaResponse;
import uni9.projetopraticoemsistemas.myhealth.lembretes.model.dto.MedicamentoResponse;
import uni9.projetopraticoemsistemas.myhealth.lembretes.model.entity.MedicamentoEntity;

public class MedicamentoRepository {

    private final MedicamentoMapper medicamentoMapper;

    private final BuscaMedicamentoService buscaMedicamentoService;
    private final MedicamentoDao medicamentoDao;

    private final MutableLiveData<List<Medicamento>> medicamentoListLiveData;
    private final MutableLiveData<Medicamento> medicamentoLiveData;
    private final MutableLiveData<Lembrete> lembreteLiveData;
    private final MutableLiveData<Eventos<?>> eventosMutableLiveData;

    @Inject
    public MedicamentoRepository(MedicamentoDao medicamentoDao,
                                 MedicamentoMapper medicamentoMapper) {
        this.medicamentoDao = medicamentoDao;
        this.medicamentoMapper = medicamentoMapper;

        this.medicamentoListLiveData = new MutableLiveData<>();
        this.medicamentoLiveData = new MutableLiveData<>();
        this.lembreteLiveData = new MutableLiveData<>();
        this.eventosMutableLiveData = new MutableLiveData<>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        this.buscaMedicamentoService = new retrofit2.Retrofit.Builder()
                .baseUrl("https://bula.vercel.app/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BuscaMedicamentoService.class);
    }

    public void buscarMedicamentos(String nome, Integer pagina) {
        buscaMedicamentoService.buscarMedicamentos(nome, pagina)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<BuscaResponse> call, @NonNull Response<BuscaResponse> response) {
                        if (!Objects.isNull(response.body())) {
                            medicamentoListLiveData.postValue(medicamentoMapper.contentResponseToMedicamentos(Objects.requireNonNull(response.body()).getContentResponse()));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<BuscaResponse> call, @NonNull Throwable t) {
                        eventosMutableLiveData.postValue(new Eventos.MensagemErro("erro_conexao"));
                        buscarMedicamentosOffline(nome);
                    }
                });
    }

    public void buscarMedicamentosOffline(String nome) {
        MyHealthDatabase.databaseWriteExecutor.execute(() -> {
            List<MedicamentoEntity> medicamentoEntityList = medicamentoDao.findMedicamentoByNome(nome);
            medicamentoListLiveData.postValue(medicamentoMapper.medicamentoEntityToMedicamentos(medicamentoEntityList));
            eventosMutableLiveData.postValue(new Eventos.MensagemErro("resultados_offline"));
        });
    }

    public void salvarMedicamento(Medicamento medicamento) {
        MyHealthDatabase.databaseWriteExecutor.execute(() -> {
            MedicamentoEntity medicamentoEntity = medicamentoMapper.medicamentoToMedicamentoEntity(medicamento);
            medicamentoDao.insert(medicamentoEntity);
            medicamentoEntity = medicamentoDao.findMedicamentoById(medicamento.getId());
            medicamentoLiveData.postValue(medicamentoMapper.medicamentoEntityToMedicamento(medicamentoEntity));
        });
    }

    public void obterMedicamento(Long idMedicamento, String processo) {
        buscaMedicamentoService.obterMedicamento(processo)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<MedicamentoResponse> call, @NonNull Response<MedicamentoResponse> response) {
                        if (!Objects.isNull(response.body())) {
                            MyHealthDatabase.databaseWriteExecutor.execute(() -> {
                                MedicamentoEntity medicamentoEntity = medicamentoDao.findMedicamentoById(idMedicamento);
                                medicamentoMapper.updateMedicamentoEntityFromMedicamentoResponse(response.body(), medicamentoEntity);
                                medicamentoDao.insert(medicamentoEntity);

                                Medicamento medicamento = medicamentoMapper.medicamentoEntityToMedicamento(medicamentoEntity);
                                medicamentoLiveData.postValue(medicamento);
                            });
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MedicamentoResponse> call, @NonNull Throwable t) {
                        medicamentoLiveData.postValue(null);
                        eventosMutableLiveData.postValue(new Eventos.MensagemErro("erro_conexao"));
                    }
                });
    }

    public void obterMedicamentoOffline(Long idMedicamento, String processo) {
        MyHealthDatabase.databaseWriteExecutor.execute(() -> {
            MedicamentoEntity medicamentoEntity = medicamentoDao.findMedicamentoById(idMedicamento);
            if (Objects.isNull(medicamentoEntity)
                    || Objects.isNull(medicamentoEntity.getNomeComercial())) {
                obterMedicamento(idMedicamento, processo);
            } else {
                medicamentoLiveData.postValue(medicamentoMapper.medicamentoEntityToMedicamento(medicamentoEntity));
            }
        });
    }

    public void criarLembreteComMedicamento(Long id) {
        MyHealthDatabase.databaseWriteExecutor.execute(() -> {
            MedicamentoEntity medicamentoEntity = medicamentoDao.findMedicamentoById(id);
            Lembrete lembrete = new Lembrete();
            lembrete.setId(0L);
            lembrete.setMedicamento(medicamentoMapper.medicamentoEntityToMedicamento(medicamentoEntity));
            lembrete.setDataInicio(longDateToStringDate(System.currentTimeMillis()));
            lembrete.setHoraInicio(longDateToStringTime(System.currentTimeMillis()));
            lembrete.setAlertas(Boolean.TRUE);
            lembreteLiveData.postValue(lembrete);
        });
    }

    public MutableLiveData<List<Medicamento>> getMedicamentoListLiveData() {
        return medicamentoListLiveData;
    }

    public MutableLiveData<Medicamento> getMedicamentoLiveData() {
        return medicamentoLiveData;
    }

    public MutableLiveData<Lembrete> getLembreteLiveData() {
        return lembreteLiveData;
    }

    public MutableLiveData<Eventos<?>> getEventosMutableLiveData() {
        return eventosMutableLiveData;
    }
}
