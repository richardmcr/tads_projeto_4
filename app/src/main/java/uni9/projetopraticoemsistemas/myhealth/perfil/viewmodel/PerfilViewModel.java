package uni9.projetopraticoemsistemas.myhealth.perfil.viewmodel;


import static uni9.projetopraticoemsistemas.myhealth.helper.DateFormatter.longDateToStringDate;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import uni9.projetopraticoemsistemas.myhealth.eventos.Eventos;
import uni9.projetopraticoemsistemas.myhealth.login.repositories.UsuarioRepository;
import uni9.projetopraticoemsistemas.myhealth.perfil.model.Perfil;
import uni9.projetopraticoemsistemas.myhealth.perfil.repositories.PerfilRepository;

@HiltViewModel
public class PerfilViewModel extends AndroidViewModel {

    private final PerfilRepository perfilRepository;
    private final UsuarioRepository usuarioRepository;
    private final MutableLiveData<Long> dataNascimentoLiveData;
    private final MutableLiveData<Long> pesoLiveData;
    private final MutableLiveData<Float> alturaLiveData;
    private final MutableLiveData<String> generoLiveData;
    private final MutableLiveData<String> tipoSanguineoLiveData;
    private final MutableLiveData<Long> novoPerfilUsuarioLiveData;
    private final MutableLiveData<Perfil> perfilUsuarioLiveData;
    private final MutableLiveData<Eventos<?>> eventoLiveData;
    private final Observer<Long> perfilUsuarioObserver;

    @Inject
    public PerfilViewModel(@NonNull Application application,
                           PerfilRepository perfilRepository,
                           UsuarioRepository usuarioRepository) {
        super(application);
        this.perfilRepository = perfilRepository;
        this.usuarioRepository = usuarioRepository;
        this.perfilUsuarioLiveData = perfilRepository.getPerfilUsuarioLiveData();
        this.novoPerfilUsuarioLiveData = perfilRepository.getNovoPerfilUsuarioLiveData();
        this.eventoLiveData = new MutableLiveData<>();
        this.dataNascimentoLiveData = new MutableLiveData<>();
        this.pesoLiveData = new MutableLiveData<>();
        this.alturaLiveData = new MutableLiveData<>();
        this.generoLiveData = new MutableLiveData<>();
        this.tipoSanguineoLiveData = new MutableLiveData<>();
        this.perfilUsuarioObserver = perfilUsuarioId -> {
            if (!Objects.isNull(perfilUsuarioId)) {
                this.eventoLiveData.postValue(new Eventos.PerfilUsuarioSalvo<>());
            }
        };
        this.novoPerfilUsuarioLiveData.observeForever(perfilUsuarioObserver);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        novoPerfilUsuarioLiveData.removeObserver(perfilUsuarioObserver);
    }

    public void init() {
        perfilUsuarioLiveData.setValue(null);
    }

    public void onPerfilUsuarioAberto() {
        perfilRepository.obterPerfilUsuario(usuarioRepository.getUsuarioLogado());
    }

    public void inserirPerfilUsuario() {
        if (!Objects.isNull(dataNascimentoLiveData.getValue()) && Objects.requireNonNull(dataNascimentoLiveData.getValue()) >= System.currentTimeMillis()) {
            onErroDeValidacao("data_futura");
        } else if (!Objects.isNull(pesoLiveData.getValue()) && Objects.requireNonNull(pesoLiveData.getValue()) >= 200L) {
            onErroDeValidacao("peso_elevado");
        } else if (!Objects.isNull(pesoLiveData.getValue()) && Objects.requireNonNull(pesoLiveData.getValue()) < 0L) {
            onErroDeValidacao("peso_negativo");
        } else if (!Objects.isNull(pesoLiveData.getValue()) && Objects.requireNonNull(pesoLiveData.getValue()).equals(0L)) {
            onErroDeValidacao("peso_zerado");
        } else if (!Objects.isNull(alturaLiveData.getValue()) && Objects.requireNonNull(alturaLiveData.getValue()) >= 2.2) {
            onErroDeValidacao("altura_elevada");
        } else if (!Objects.isNull(alturaLiveData.getValue()) && Objects.requireNonNull(alturaLiveData.getValue()) < 0.0) {
            onErroDeValidacao("altura_negativa");
        } else if (!Objects.isNull(alturaLiveData.getValue()) && Objects.requireNonNull(alturaLiveData.getValue()) == 0.0) {
            onErroDeValidacao("altura_zerada");
        } else {
            Perfil perfil = new Perfil();
            perfil.setDataNascimento(longDateToStringDate(dataNascimentoLiveData.getValue()));
            perfil.setPeso(pesoLiveData.getValue());
            perfil.setAltura(alturaLiveData.getValue());
            perfil.setGenero(generoLiveData.getValue());
            perfil.setTipoSanguineo(tipoSanguineoLiveData.getValue());
            perfilRepository.inserirPerfilUsuario(perfil, usuarioRepository.getUsuarioLogado());
        }
    }

    private void onErroDeValidacao(String erro) {
        eventoLiveData.postValue(new Eventos.ErroValidacao(erro));
    }

    public void setDataNascimento(Long dataNascimento) {
        this.dataNascimentoLiveData.postValue(dataNascimento);
    }

    public void setPeso(Long peso) {
        this.pesoLiveData.postValue(peso);
    }

    public void setAltura(Float altura) {
        this.alturaLiveData.postValue(altura);
    }

    public void setGenero(String genero) {
        this.generoLiveData.postValue(genero);
    }

    public void setTipoSanguineo(String tipoSanguineo) {
        this.tipoSanguineoLiveData.postValue(tipoSanguineo);
    }

    public MutableLiveData<Long> getDataNascimentoLiveData() {
        return dataNascimentoLiveData;
    }

    public MutableLiveData<Eventos<?>> getEventoLiveData() {
        return eventoLiveData;
    }

    public MutableLiveData<Perfil> getPerfilUsuarioLiveData() {
        return perfilUsuarioLiveData;
    }

}
