package uni9.projetopraticoemsistemas.myhealth.home.bulas.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import uni9.projetopraticoemsistemas.myhealth.eventos.Eventos;
import uni9.projetopraticoemsistemas.myhealth.home.lembretes.model.Medicamento;
import uni9.projetopraticoemsistemas.myhealth.home.lembretes.repositories.MedicamentoRepository;

@HiltViewModel
public class BuscaMedicamentoViewModel extends AndroidViewModel {

    private final MedicamentoRepository medicamentoRepository;
    private final MutableLiveData<List<Medicamento>> medicamentoListLiveData;
    private final MutableLiveData<Medicamento> medicamentoLiveData;
    private final MutableLiveData<Boolean> loadingLiveData;
    private final MutableLiveData<Eventos<?>> eventosMutableLiveData;

    @Inject
    public BuscaMedicamentoViewModel(@NonNull Application application,
                                     MedicamentoRepository medicamentoRepository) {
        super(application);
        this.medicamentoRepository = medicamentoRepository;
        this.loadingLiveData = new MutableLiveData<>(Boolean.FALSE);
        this.eventosMutableLiveData = medicamentoRepository.getEventosMutableLiveData();
        this.medicamentoLiveData = medicamentoRepository.getMedicamentoLiveData();
        this.medicamentoListLiveData = medicamentoRepository.getMedicamentoListLiveData();
    }

    public void init() {
        medicamentoLiveData.setValue(null);
        medicamentoListLiveData.setValue(null);
    }

    public void buscarMedicamentos(String nome) {
        loadingLiveData.setValue(Boolean.TRUE);
        medicamentoRepository.buscarMedicamentos(nome, 1);
    }

    public void onMedicamentoSelecionado(Medicamento medicamento) {
        medicamentoRepository.salvarMedicamento(medicamento);
    }

    public MutableLiveData<Medicamento> getMedicamentoLiveData() {
        return medicamentoLiveData;
    }

    public MutableLiveData<List<Medicamento>> getMedicamentoListLiveData() {
        return medicamentoListLiveData;
    }

    public MutableLiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public MutableLiveData<Eventos<?>> getEventosMutableLiveData() {
        return eventosMutableLiveData;
    }
}
