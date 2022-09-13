package uni9.projetopraticoemsistemas.myhealth.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import uni9.projetopraticoemsistemas.myhealth.LembreteRepository;
import uni9.projetopraticoemsistemas.myhealth.PreferenciasRepository;
import uni9.projetopraticoemsistemas.myhealth.eventos.Eventos;
import uni9.projetopraticoemsistemas.myhealth.model.Lembrete;
import uni9.projetopraticoemsistemas.myhealth.model.Ordenacao;

@HiltViewModel
public class LembretesViewModel extends AndroidViewModel {

    private final MutableLiveData<String> medicamentoLiveData;
    private final MutableLiveData<Ordenacao> ordenacaoLiveData;
    private final MutableLiveData<Boolean> ocultarCompletosLiveData;
    private final LembreteRepository lembreteRepository;
    private final PreferenciasRepository preferenciasRepository;
    private final MutableLiveData<List<Lembrete>> lembreteListLiveData;
    private final MutableLiveData<Eventos<?>> lembreteEventoLiveData;

    @Inject
    public LembretesViewModel(@NonNull Application application, LembreteRepository lembreteRepository,
                              PreferenciasRepository preferenciasRepository) {
        super(application);
        this.lembreteRepository = lembreteRepository;
        this.preferenciasRepository = preferenciasRepository;
        this.lembreteEventoLiveData = new MutableLiveData<>();
        this.medicamentoLiveData = lembreteRepository.getMedicamentoLiveData();
        this.ordenacaoLiveData = lembreteRepository.getOrdenacaoLiveData();
        this.ocultarCompletosLiveData = lembreteRepository.getOcultarCompletosLiveData();

        this.ordenacaoLiveData.observeForever(this.preferenciasRepository::atualizarOrdenacao);
        this.ocultarCompletosLiveData.observeForever(this.preferenciasRepository::atualizarOcultarCompletos);

        this.lembreteListLiveData = lembreteRepository.getLembreteListLiveData();
    }

    public void init() {
        lembreteEventoLiveData.setValue(null);
        ordenacaoLiveData.postValue(this.preferenciasRepository.getOrdenacao());
        ocultarCompletosLiveData.postValue(this.preferenciasRepository.getOcultarCompletos());
        lembreteRepository.listarLembretes();
    }

    public LiveData<List<Lembrete>> getLembreteListLiveData() {
        return lembreteListLiveData;
    }

    public void onLembreteSwiped(Lembrete lembrete) {
        lembreteRepository.removerLembrete(lembrete);
        lembreteEventoLiveData.postValue(new Eventos.LembreteRemovido(lembrete));
    }

    public void onDesfazerLembreteRemovido(Lembrete lembrete) {
        lembreteRepository.inserirLembrete(lembrete);
        lembreteEventoLiveData.postValue(new Eventos.LembreteRecuperado<>());
    }

    public void removerLembretesCompletos() {
        this.lembreteRepository.removerLembretesCompletos();
        lembreteEventoLiveData.postValue(new Eventos.LembretesCompletosRemovidos<>());
    }

    public void onNovoLembrete() {
        lembreteEventoLiveData.postValue(new Eventos.NovoLembrete<>());
    }

    public void eventoCompleto() {
        lembreteEventoLiveData.setValue(null);
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

    public Boolean getOcultarCompletosInicial() {
        return this.preferenciasRepository.getOcultarCompletos();
    }

    public LiveData<Eventos<?>> getLembreteEventoLiveData() {
        return lembreteEventoLiveData;
    }
}
