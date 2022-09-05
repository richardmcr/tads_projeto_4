package uni9.projetopraticoemsistemas.myhealth;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import uni9.projetopraticoemsistemas.myhealth.apis.BuscaMedicamentoService;
import uni9.projetopraticoemsistemas.myhealth.apis.MedicamentoDao;
import uni9.projetopraticoemsistemas.myhealth.mappers.MedicamentoMapper;
import uni9.projetopraticoemsistemas.myhealth.mappers.MedicamentoMapperImpl;
import uni9.projetopraticoemsistemas.myhealth.model.dto.BuscaResponse;
import uni9.projetopraticoemsistemas.myhealth.model.dto.ContentResponse;
import uni9.projetopraticoemsistemas.myhealth.model.dto.MedicamentoResponse;
import uni9.projetopraticoemsistemas.myhealth.model.entity.MedicamentoEntity;

public class MedicamentoRepository {

    private static final String BASE_URL = "https://bula.vercel.app/";

    private final MedicamentoMapper medicamentoMapper;

    private final BuscaMedicamentoService buscaMedicamentoService;
    private final MutableLiveData<MedicamentoEntity> medicamentoEntityLiveData;
    private final MutableLiveData<BuscaResponse> buscaResponseLiveData;
    private final MutableLiveData<MedicamentoResponse> medicamentoResponseLiveData;

    private final MedicamentoDao medicamentoDao;

    public MedicamentoRepository(Application application) {
        MyHealthDatabase database = MyHealthDatabase.getInstance(application);
        medicamentoDao = database.medicamentoDao();

        medicamentoMapper = new MedicamentoMapperImpl();

        medicamentoEntityLiveData = new MutableLiveData<>();
        buscaResponseLiveData = new MutableLiveData<>();
        medicamentoResponseLiveData = new MutableLiveData<>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        buscaMedicamentoService = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BuscaMedicamentoService.class);
    }

    public void findMedicamentoById(Long id) {
        MyHealthDatabase.databaseWriteExecutor.execute(() -> {
            MedicamentoEntity medicamentoEntity = medicamentoDao.FindMedicamentoById(id);
            medicamentoEntityLiveData.postValue(medicamentoEntity);
        });
    }

    public void insert(ContentResponse contentResponse) {
        MedicamentoEntity medicamentoEntity = medicamentoMapper.medicamentoDtoToEntity(contentResponse);
        MyHealthDatabase.databaseWriteExecutor.execute(() -> medicamentoDao.Insert(medicamentoEntity));
    }

    public void update(MedicamentoResponse medicamentoResponse) {
        MyHealthDatabase.databaseWriteExecutor.execute(() -> {
            MedicamentoEntity medicamentoEntity = medicamentoDao.FindMedicamentoById(medicamentoResponse.getCodigoProduto());
            medicamentoMapper.updateMedicamentoEntityFromDto(medicamentoResponse, medicamentoEntity);
            medicamentoDao.Update(medicamentoEntity);
            medicamentoResponseLiveData.postValue(null);
        });
    }

    public void buscarMedicamentos(String nome, Integer pagina) {
        buscaMedicamentoService.buscarMedicamentos(nome, pagina)
                .enqueue(new Callback<BuscaResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<BuscaResponse> call, @NonNull Response<BuscaResponse> response) {
                        if (response.body() != null) {
                            buscaResponseLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<BuscaResponse> call, @NonNull Throwable t) {
                        buscaResponseLiveData.postValue(null);
                    }
                });
    }

    public void obterMedicamento(String numProcesso) {
        buscaMedicamentoService.obterMedicamento(numProcesso)
                .enqueue(new Callback<MedicamentoResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MedicamentoResponse> call, @NonNull Response<MedicamentoResponse> response) {
                        if (response.body() != null) {
                            medicamentoResponseLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MedicamentoResponse> call, @NonNull Throwable t) {
                        medicamentoResponseLiveData.postValue(null);
                    }
                });
    }

    public LiveData<MedicamentoEntity> getMedicamentoEntityLiveData() {
        return medicamentoEntityLiveData;
    }

    public LiveData<BuscaResponse> getBuscaResponseLiveData() {
        return buscaResponseLiveData;
    }

    public LiveData<MedicamentoResponse> getMedicamentoResponseLiveData() {
        return medicamentoResponseLiveData;
    }
}
