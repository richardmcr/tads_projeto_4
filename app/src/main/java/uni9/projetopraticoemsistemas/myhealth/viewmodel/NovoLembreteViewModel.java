package uni9.projetopraticoemsistemas.myhealth.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import uni9.projetopraticoemsistemas.myhealth.MedicamentoRepository;
import uni9.projetopraticoemsistemas.myhealth.model.dto.BuscaResponse;

public class NovoLembreteViewModel extends AndroidViewModel {

    private MedicamentoRepository medicamentoRepository;
    private LiveData<BuscaResponse> buscaResponseLiveData;

    public NovoLembreteViewModel(@NonNull Application application) {
        super(application);
        medicamentoRepository = new MedicamentoRepository(application);
    }

    public void init() {
        buscaResponseLiveData = medicamentoRepository.getBuscaResponseLiveData();
    }

    public void buscarMedicamentos(String nome, Integer pagina) {
        medicamentoRepository.buscarMedicamentos(nome, pagina);
    }

    public LiveData<BuscaResponse> getBuscaResponseLiveData() {
        return buscaResponseLiveData;
    }
}
