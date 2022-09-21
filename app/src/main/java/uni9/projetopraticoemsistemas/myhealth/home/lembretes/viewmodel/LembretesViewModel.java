package uni9.projetopraticoemsistemas.myhealth.home.lembretes.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import uni9.projetopraticoemsistemas.myhealth.eventos.Eventos;
import uni9.projetopraticoemsistemas.myhealth.home.lembretes.model.Lembrete;
import uni9.projetopraticoemsistemas.myhealth.home.lembretes.model.Ordenacao;
import uni9.projetopraticoemsistemas.myhealth.home.lembretes.repositories.LembreteRepository;
import uni9.projetopraticoemsistemas.myhealth.home.lembretes.repositories.PreferenciasRepository;
import uni9.projetopraticoemsistemas.myhealth.login.repositories.UsuarioRepository;

@HiltViewModel
public class LembretesViewModel extends AndroidViewModel {

    private final MutableLiveData<String> medicamentoLiveData;
    private final MutableLiveData<Ordenacao> ordenacaoLiveData;
    private final MutableLiveData<Boolean> ocultarCompletosLiveData;
    private final LembreteRepository lembreteRepository;
    private final PreferenciasRepository preferenciasRepository;
    private final UsuarioRepository usuarioRepository;
    private final MutableLiveData<Long> usuarioLiveData;
    private final MutableLiveData<List<Lembrete>> lembreteListLiveData;
    private final MutableLiveData<Eventos<?>> lembreteEventoLiveData;
    private final Observer<Ordenacao> observerOdernacao = new Observer<Ordenacao>() {
        @Override
        public void onChanged(Ordenacao ordenacao) {
            preferenciasRepository.atualizarOrdenacao(ordenacao);
        }
    };
    private final Observer<Boolean> observerOcultarCompletos = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean ocultarCompletos) {
            preferenciasRepository.atualizarOcultarCompletos(ocultarCompletos);
        }
    };

    @Inject
    public LembretesViewModel(@NonNull Application application,
                              LembreteRepository lembreteRepository,
                              PreferenciasRepository preferenciasRepository,
                              UsuarioRepository usuarioRepository) {
        super(application);
        this.lembreteRepository = lembreteRepository;
        this.preferenciasRepository = preferenciasRepository;
        this.usuarioRepository = usuarioRepository;
        this.lembreteEventoLiveData = new MutableLiveData<>();
        this.usuarioLiveData = lembreteRepository.getUsuarioLiveData();
        this.medicamentoLiveData = lembreteRepository.getMedicamentoLiveData();
        this.ordenacaoLiveData = lembreteRepository.getOrdenacaoLiveData();
        this.ocultarCompletosLiveData = lembreteRepository.getOcultarCompletosLiveData();
        this.lembreteListLiveData = lembreteRepository.getLembreteListLiveData();
    }

    public void init() {
        ordenacaoLiveData.observeForever(observerOdernacao);
        ocultarCompletosLiveData.observeForever(observerOcultarCompletos);

        usuarioLiveData.setValue(usuarioRepository.getUsuarioLogado());
        lembreteEventoLiveData.setValue(null);
        ordenacaoLiveData.postValue(preferenciasRepository.getOrdenacao());
        ocultarCompletosLiveData.postValue(preferenciasRepository.getOcultarCompletos());
        lembreteRepository.listarLembretes();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        ordenacaoLiveData.removeObserver(observerOdernacao);
        ocultarCompletosLiveData.removeObserver(observerOcultarCompletos);
    }

    public LiveData<List<Lembrete>> getLembreteListLiveData() {
        return lembreteListLiveData;
    }

    public void onLembreteSwiped(Lembrete lembrete) {
        lembreteRepository.removerLembrete(lembrete);
        lembreteEventoLiveData.postValue(new Eventos.LembreteRemovido(lembrete));
    }

    public void onDesfazerLembreteRemovido(Lembrete lembrete) {
        lembreteRepository.inserirLembrete(lembrete, usuarioRepository.getUsuarioLogado());
        lembreteEventoLiveData.postValue(new Eventos.LembreteRecuperado<>());
    }

    public void removerLembretesCompletos() {
        lembreteRepository.removerLembretesCompletos();
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
