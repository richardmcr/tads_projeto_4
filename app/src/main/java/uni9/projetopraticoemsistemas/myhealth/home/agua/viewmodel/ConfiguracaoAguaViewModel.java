package uni9.projetopraticoemsistemas.myhealth.home.agua.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import uni9.projetopraticoemsistemas.myhealth.eventos.Eventos;
import uni9.projetopraticoemsistemas.myhealth.home.agua.model.LembreteAgua;
import uni9.projetopraticoemsistemas.myhealth.home.agua.repositories.LembreteAguaRepository;
import uni9.projetopraticoemsistemas.myhealth.login.repositories.UsuarioRepository;

@HiltViewModel
public class ConfiguracaoAguaViewModel extends AndroidViewModel {

    public static final Integer LITROS = 0;
    public static final Integer MILILITROS = 1;

    private final LembreteAguaRepository lembreteAguaRepository;
    private final UsuarioRepository usuarioRepository;
    private final MutableLiveData<Long> metaDiariaLivaData;
    private final MutableLiveData<Integer> unidadeLiveData;
    private final MutableLiveData<String> horaInicioLiveData;
    private final MutableLiveData<String> horaFinalLiveData;
    private final MutableLiveData<Boolean> alertasLiveData;
    private final MutableLiveData<LembreteAgua> lembreteAguaLiveData;
    private final MutableLiveData<Long> novoLembreteAguaLiveData;
    private final MutableLiveData<Eventos<?>> eventoLiveData;

    @Inject
    public ConfiguracaoAguaViewModel(@NonNull Application application,
                                     LembreteAguaRepository lembreteAguaRepository,
                                     UsuarioRepository usuarioRepository) {
        super(application);
        this.lembreteAguaRepository = lembreteAguaRepository;
        this.usuarioRepository = usuarioRepository;
        this.lembreteAguaLiveData = lembreteAguaRepository.getLembreteAguaLiveData();
        this.novoLembreteAguaLiveData = lembreteAguaRepository.getNovoLembreteAguaLiveData();
        this.metaDiariaLivaData = new MutableLiveData<>(2L);
        this.unidadeLiveData = new MutableLiveData<>(LITROS);
        this.horaInicioLiveData = new MutableLiveData<>("08:00");
        this.horaFinalLiveData = new MutableLiveData<>("22:00");
        this.alertasLiveData = new MutableLiveData<>(Boolean.TRUE);
        this.eventoLiveData = new MutableLiveData<>();
    }

    public void init() {
    }

    public void setMeta(Long meta) {
        this.metaDiariaLivaData.postValue(meta);
    }

    public void setUnidade(Integer unidade) {
        this.unidadeLiveData.postValue(unidade);
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicioLiveData.postValue(horaInicio);
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinalLiveData.postValue(horaFinal);
    }

    public void setAlertas(Boolean alertas) {
        this.alertasLiveData.postValue(alertas);
    }

    public MutableLiveData<Long> getMetaDiariaLivaData() {
        return metaDiariaLivaData;
    }

    public MutableLiveData<Integer> getUnidadeLiveData() {
        return unidadeLiveData;
    }

    public MutableLiveData<String> getHoraInicioLiveData() {
        return horaInicioLiveData;
    }

    public MutableLiveData<String> getHoraFinalLiveData() {
        return horaFinalLiveData;
    }

    public MutableLiveData<Boolean> getAlertasLiveData() {
        return alertasLiveData;
    }
}
