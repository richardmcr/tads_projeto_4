package uni9.projetopraticoemsistemas.myhealth.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import uni9.projetopraticoemsistemas.myhealth.R;
import uni9.projetopraticoemsistemas.myhealth.model.dto.BuscaResponse;
import uni9.projetopraticoemsistemas.myhealth.viewmodel.NovoLembreteViewModel;

public class NovoLembreteFragment extends Fragment {
    
    private NovoLembreteViewModel viewModel;

    private TextInputEditText tietMedicamento, tietInicioTratamento, tietDuracao, tietIntervalo;
    private Switch swtAlertas;
    private Button btnSalvar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        viewModel = new ViewModelProvider(this).get(NovoLembreteViewModel.class);
        viewModel.init();
        viewModel.getBuscaResponseLiveData().observe(this, new Observer<BuscaResponse>() {
            @Override
            public void onChanged(BuscaResponse buscaResponse) {
                if (buscaResponse != null) {

                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_novo_lembrete, container, false);

        tietMedicamento = view.findViewById(R.id.tiet_medicamento);
        tietInicioTratamento = view.findViewById(R.id.tiet_inicio_tratamento);
        tietDuracao = view.findViewById(R.id.tiet_duracao);
        tietIntervalo = view.findViewById(R.id.tiet_intervalo);
        swtAlertas = view.findViewById(R.id.swt_alertas);
        btnSalvar = view.findViewById(R.id.btn_salvar);

        btnSalvar.setOnClickListener(v -> salvarLembrete());

        return view;
    }

    public void salvarLembrete() {

    }
}
