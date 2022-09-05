package uni9.projetopraticoemsistemas.myhealth.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import uni9.projetopraticoemsistemas.myhealth.MedicamentoRepository;
import uni9.projetopraticoemsistemas.myhealth.model.entity.MedicamentoEntity;

public class MedicamentoViewModel extends AndroidViewModel {

    private final MedicamentoRepository medicamentoRepository;
    private LiveData<MedicamentoEntity> medicamentoEntityLiveData;

    public MedicamentoViewModel(@NonNull Application application) {
        super(application);
        medicamentoRepository = new MedicamentoRepository(application);
    }

    public void init() {
        medicamentoEntityLiveData = medicamentoRepository.getMedicamentoEntityLiveData();
    }

    public void obterMedicamento(Long idProduto) {
        medicamentoRepository.findMedicamentoById(idProduto);
    }

    public LiveData<MedicamentoEntity> getMedicamentoEntityLiveData() {
        return medicamentoEntityLiveData;
    }
}
