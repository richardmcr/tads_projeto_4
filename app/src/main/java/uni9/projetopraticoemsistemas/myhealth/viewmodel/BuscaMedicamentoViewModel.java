package uni9.projetopraticoemsistemas.myhealth.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import uni9.projetopraticoemsistemas.myhealth.MedicamentoRepository;
import uni9.projetopraticoemsistemas.myhealth.model.dto.BuscaResponse;
import uni9.projetopraticoemsistemas.myhealth.model.dto.ContentResponse;
import uni9.projetopraticoemsistemas.myhealth.model.dto.MedicamentoResponse;

public class BuscaMedicamentoViewModel extends AndroidViewModel {

    private final MedicamentoRepository medicamentoRepository;
    private LiveData<BuscaResponse> buscaResponseLiveData;
    private LiveData<MedicamentoResponse> medicamentoResponseLiveData;

    public BuscaMedicamentoViewModel(@NonNull Application application) {
        super(application);
        medicamentoRepository = new MedicamentoRepository(application);
    }

    public void init() {
        buscaResponseLiveData = medicamentoRepository.getBuscaResponseLiveData();
        medicamentoResponseLiveData = medicamentoRepository.getMedicamentoResponseLiveData();
    }

    public void buscarMedicamentos(String nome) {
        medicamentoRepository.buscarMedicamentos(nome, 1);
    }

    public void obterMedicamento(String numProcesso) {
        medicamentoRepository.obterMedicamento(numProcesso);
    }

    public void salvarMedicamento(ContentResponse contentResponse) {
        medicamentoRepository.insert(contentResponse);
    }

    public void atualizarMedicamento(MedicamentoResponse medicamentoResponse) {
        medicamentoRepository.update(medicamentoResponse);
    }

    public LiveData<BuscaResponse> getBuscaResponseLiveData() {
        return buscaResponseLiveData;
    }

    public LiveData<MedicamentoResponse> getMedicamentoResponseLiveData() {
        return medicamentoResponseLiveData;
    }
}
