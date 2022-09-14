package uni9.projetopraticoemsistemas.myhealth.viewmodel;


import static uni9.projetopraticoemsistemas.myhealth.helper.DateFormatter.longDateToStringDate;
import static uni9.projetopraticoemsistemas.myhealth.helper.DateFormatter.longDateToStringTime;
import static uni9.projetopraticoemsistemas.myhealth.helper.DateFormatter.stringDateTimeTolong;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import uni9.projetopraticoemsistemas.myhealth.LembreteRepository;
import uni9.projetopraticoemsistemas.myhealth.MedicamentoRepository;
import uni9.projetopraticoemsistemas.myhealth.eventos.Eventos;
import uni9.projetopraticoemsistemas.myhealth.model.Lembrete;

@HiltViewModel
public class LembreteViewModel extends AndroidViewModel {

    private final MedicamentoRepository medicamentoRepository;
    private final LembreteRepository lembreteRepository;
    private final MediatorLiveData<Lembrete> lembreteMediator;
    private final MutableLiveData<String> detalhesLiveData;
    private final MutableLiveData<Long> inicioLiveData;
    private final MutableLiveData<Long> duracaoLiveData;
    private final MutableLiveData<Long> intervaloLiveData;
    private final MutableLiveData<Boolean> alertasLiveData;
    private final MutableLiveData<Eventos<?>> eventoLiveData;

    @Inject
    public LembreteViewModel(@NonNull Application application,
                             LembreteRepository lembreteRepository,
                             MedicamentoRepository medicamentoRepository) {
        super(application);
        this.lembreteRepository = lembreteRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.lembreteMediator = new MediatorLiveData<>();
        this.detalhesLiveData = new MutableLiveData<>();
        this.inicioLiveData = new MutableLiveData<>();
        this.duracaoLiveData = new MutableLiveData<>();
        this.intervaloLiveData = new MutableLiveData<>();
        this.alertasLiveData = new MutableLiveData<>();
        this.eventoLiveData = new MutableLiveData<>();

        MutableLiveData<Lembrete> lembreteLiveData = lembreteRepository.getLembreteLiveData();
        MutableLiveData<Lembrete> novoLembreteLiveData = medicamentoRepository.getLembreteLiveData();
        this.lembreteMediator.addSource(lembreteLiveData, lembrete -> {
            this.inicioLiveData.setValue(stringDateTimeTolong(lembrete.getDataInicio() + " " + lembrete.getHoraInicio()));
            this.detalhesLiveData.setValue((lembrete.getDetalhes()));
            this.lembreteMediator.postValue(lembrete);
        });
        this.lembreteMediator.addSource(novoLembreteLiveData, lembrete -> {
            this.inicioLiveData.setValue(stringDateTimeTolong(lembrete.getDataInicio() + " " + lembrete.getHoraInicio()));
            this.detalhesLiveData.setValue((lembrete.getDetalhes()));
            this.lembreteMediator.postValue(lembrete);
        });
    }

    public void init() {
        lembreteMediator.setValue(null);
        eventoLiveData.setValue(null);
    }

    public void obterLembrete(Long idLembrete, Long idMedicamento) {
        if (idLembrete == 0) {
            medicamentoRepository.criarLembreteComMedicamento(idMedicamento);
        } else {
            lembreteRepository.getLembrete(idLembrete);
        }
    }

    public void inserirLembrete() {
        if (Objects.isNull(duracaoLiveData.getValue())) {
            onErroDeValidacao("duracao");
        } else if (Objects.requireNonNull(duracaoLiveData.getValue()) <= 0) {
            onErroDeValidacao("duracao_negativa");
        } else if (Objects.isNull(intervaloLiveData.getValue())) {
            onErroDeValidacao("intervalo");
        } else if (Objects.requireNonNull(intervaloLiveData.getValue()) <= 0) {
            onErroDeValidacao("intervalo_negativo");
        } else {
            Lembrete novoLembrete = lembreteMediator.getValue();
            assert novoLembrete != null;
            // novoLembrete.setUsuario();
            novoLembrete.setDetalhes(detalhesLiveData.getValue());
            novoLembrete.setDataInicio(longDateToStringDate(inicioLiveData.getValue()));
            novoLembrete.setHoraInicio(longDateToStringTime(inicioLiveData.getValue()));
            novoLembrete.setDuracao(duracaoLiveData.getValue());
            novoLembrete.setIntervalo(intervaloLiveData.getValue());
            novoLembrete.setAlertas(alertasLiveData.getValue());
            lembreteRepository.inserirLembrete(novoLembrete);

            if (Objects.isNull(novoLembrete.getId())) {
                eventoLiveData.postValue(new Eventos.LembreteSalvo(0));
            } else {
                eventoLiveData.postValue(new Eventos.LembreteSalvo(1));
            }
        }
    }

    private void onErroDeValidacao(String erro) {
        eventoLiveData.postValue(new Eventos.ErroValidacao(erro));
    }

    public void setDetalhes(String detalhes) {
        this.detalhesLiveData.postValue(detalhes);
    }

    public void setInicio(Long inicio) {
        this.inicioLiveData.postValue(inicio);
    }

    public void setDuracao(Long duracao) {
        this.duracaoLiveData.postValue(duracao);
    }

    public void setIntervalo(Long intervalo) {
        this.intervaloLiveData.postValue(intervalo);
    }

    public void setAlertas(Boolean alertas) {
        this.alertasLiveData.postValue(alertas);
    }

    public MutableLiveData<Lembrete> getLembreteLiveData() {
        return lembreteMediator;
    }

    public MutableLiveData<Long> getInicioLiveData() {
        return inicioLiveData;
    }

    public MutableLiveData<Eventos<?>> getEventoLiveData() {
        return eventoLiveData;
    }
}
