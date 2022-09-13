package uni9.projetopraticoemsistemas.myhealth.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import uni9.projetopraticoemsistemas.myhealth.MedicamentoRepository;
import uni9.projetopraticoemsistemas.myhealth.eventos.Eventos;
import uni9.projetopraticoemsistemas.myhealth.model.Medicamento;

@HiltViewModel
public class MedicamentoViewModel extends AndroidViewModel {

    private final MedicamentoRepository medicamentoRepository;
    private final MutableLiveData<Medicamento> medicamentoLiveData;
    private final MutableLiveData<Boolean> loadingLiveData;
    private final MutableLiveData<Eventos<?>> eventosMutableLiveData;

    @Inject
    public MedicamentoViewModel(@NonNull Application application,
                                MedicamentoRepository medicamentoRepository) {
        super(application);
        this.medicamentoRepository = medicamentoRepository;
        this.loadingLiveData = new MutableLiveData<>(Boolean.FALSE);
        this.eventosMutableLiveData = medicamentoRepository.getEventosMutableLiveData();
        this.medicamentoLiveData = medicamentoRepository.getMedicamentoLiveData();
    }

    public void init() {
        medicamentoLiveData.setValue(null);
    }

    public void obterMedicamento(Long idMedicamento, String processo) {
        loadingLiveData.setValue(Boolean.TRUE);
        medicamentoRepository.obterMedicamento(idMedicamento, processo);
    }

    public MutableLiveData<Medicamento> getMedicamentoLiveData() {
        return medicamentoLiveData;
    }

    public MutableLiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public MutableLiveData<Eventos<?>> getEventosMutableLiveData() {
        return eventosMutableLiveData;
    }
}
