package uni9.projetopraticoemsistemas.myhealth.home.agua.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import uni9.projetopraticoemsistemas.myhealth.eventos.Eventos;
import uni9.projetopraticoemsistemas.myhealth.helper.DateFormatter;
import uni9.projetopraticoemsistemas.myhealth.home.agua.model.ConsumoDiario;
import uni9.projetopraticoemsistemas.myhealth.home.agua.model.LembreteAgua;
import uni9.projetopraticoemsistemas.myhealth.home.agua.repositories.ConsumoDiarioRepository;
import uni9.projetopraticoemsistemas.myhealth.home.agua.repositories.LembreteAguaRepository;
import uni9.projetopraticoemsistemas.myhealth.login.repositories.UsuarioRepository;

@HiltViewModel
public class AguaViewModel extends AndroidViewModel {

    public static final Integer LITROS = 0;
    public static final Integer MILILITROS = 1;

    private final LembreteAguaRepository lembreteAguaRepository;
    private final ConsumoDiarioRepository consumoDiarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final MutableLiveData<ConsumoDiario> consumoDiarioLiveData;
    private final MutableLiveData<LembreteAgua> lembreteAguaLiveData;
    private final MutableLiveData<Eventos<?>> eventoLiveData;

    @Inject
    public AguaViewModel(@NonNull Application application,
                         LembreteAguaRepository lembreteAguaRepository,
                         ConsumoDiarioRepository consumoDiarioRepository,
                         UsuarioRepository usuarioRepository) {
        super(application);
        this.lembreteAguaRepository = lembreteAguaRepository;
        this.consumoDiarioRepository = consumoDiarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.lembreteAguaLiveData = lembreteAguaRepository.getLembreteAguaLiveData();
        this.consumoDiarioLiveData = consumoDiarioRepository.getConsumoDiarioLiveData();
        this.eventoLiveData = new MutableLiveData<>();
    }

    public void init(){
        lembreteAguaRepository.obterLembreteAgua(usuarioRepository.getIdUsuarioLogado());
        consumoDiarioLiveData.setValue(null);
    }

    public void obterConsumoDia(){
        consumoDiarioRepository.obterConsumoDiario(usuarioRepository.getIdUsuarioLogado(), DateFormatter.getDataHoje());
    }

    public MutableLiveData<LembreteAgua> getLembreteAguaLiveData() {
        return lembreteAguaLiveData;
    }

    public MutableLiveData<ConsumoDiario> getConsumoDiarioLiveData() {
        return consumoDiarioLiveData;
    }

    public MutableLiveData<Eventos<?>> getEventoLiveData() {
        return eventoLiveData;
    }
}
