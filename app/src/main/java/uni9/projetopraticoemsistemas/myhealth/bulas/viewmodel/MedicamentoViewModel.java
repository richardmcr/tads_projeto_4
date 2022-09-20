package uni9.projetopraticoemsistemas.myhealth.bulas.viewmodel;


import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import uni9.projetopraticoemsistemas.myhealth.DownloadService;
import uni9.projetopraticoemsistemas.myhealth.eventos.Eventos;
import uni9.projetopraticoemsistemas.myhealth.lembretes.model.Medicamento;
import uni9.projetopraticoemsistemas.myhealth.lembretes.repositories.MedicamentoRepository;

@HiltViewModel
public class MedicamentoViewModel extends AndroidViewModel {

    private final MedicamentoRepository medicamentoRepository;
    private final MutableLiveData<Medicamento> medicamentoLiveData;
    private final MutableLiveData<Boolean> loadingLiveData;
    private final MutableLiveData<Eventos<?>> eventosMutableLiveData;
    private final MutableLiveData<Integer> progressoLiveData;
    private final DownloadReceiver downloadReceiver;

    @Inject
    public MedicamentoViewModel(@NonNull Application application,
                                MedicamentoRepository medicamentoRepository) {
        super(application);
        this.medicamentoRepository = medicamentoRepository;
        this.loadingLiveData = new MutableLiveData<>(Boolean.FALSE);
        this.progressoLiveData = new MutableLiveData<>();
        this.eventosMutableLiveData = medicamentoRepository.getEventosMutableLiveData();
        this.medicamentoLiveData = medicamentoRepository.getMedicamentoLiveData();
        this.downloadReceiver = new DownloadReceiver(new Handler());
    }

    public void init() {
        medicamentoLiveData.setValue(null);
        progressoLiveData.setValue(0);
    }

    public void obterMedicamento(Long idMedicamento, String processo) {
        loadingLiveData.setValue(Boolean.TRUE);
        medicamentoRepository.obterMedicamentoOffline(idMedicamento, processo);
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

    public MutableLiveData<Integer> getProgressoLiveData() {
        return progressoLiveData;
    }

    public DownloadReceiver getDownloadReceiver() {
        return downloadReceiver;
    }

    private class DownloadReceiver extends ResultReceiver {

        public DownloadReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            super.onReceiveResult(resultCode, resultData);

            if (resultCode == DownloadService.UPDATE_PROGRESS) {
                int progress = resultData.getInt("progress");
                progressoLiveData.setValue(progress);
            }
        }
    }
}
