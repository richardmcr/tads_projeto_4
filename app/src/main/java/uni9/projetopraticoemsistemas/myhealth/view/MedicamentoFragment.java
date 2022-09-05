package uni9.projetopraticoemsistemas.myhealth.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import uni9.projetopraticoemsistemas.myhealth.R;
import uni9.projetopraticoemsistemas.myhealth.model.dto.BuscaResponse;
import uni9.projetopraticoemsistemas.myhealth.model.dto.ContentResponse;
import uni9.projetopraticoemsistemas.myhealth.viewmodel.BuscaMedicamentoViewModel;

public class MedicamentoFragment extends Fragment {
    
    private BuscaMedicamentoViewModel viewModel;

    private TextView ttvNomeComercial, ttvRazaoSocialCnpj, ttvPrincipioAtivo, ttvMedicamentoReferencia, ttvClasseTerapeutica;
    private Button btnAvancar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(BuscaMedicamentoViewModel.class);
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
        View view = inflater.inflate(R.layout.fragment_medicamento, container, false);

        ttvNomeComercial = view.findViewById(R.id.ttv_nome_comercial);
        ttvRazaoSocialCnpj = view.findViewById(R.id.ttv_razao_social_cnpj);
        ttvPrincipioAtivo = view.findViewById(R.id.ttv_principio_ativo);
        ttvMedicamentoReferencia = view.findViewById(R.id.ttv_medicamento_referencia);
        ttvClasseTerapeutica = view.findViewById(R.id.ttv_classe_terapeutica);
        btnAvancar = view.findViewById(R.id.btn_avancar);

        btnAvancar.setOnClickListener(v -> buscarMedicamentos());

        return view;
    }

    public void buscarMedicamentos() {
        String nome = ttvNomeComercial.getEditableText().toString();

        viewModel.buscarMedicamentos(nome);
    }

    public void obterMedicamento(String numProcesso) {
        viewModel.obterMedicamento(numProcesso);
    }

    public void salvarMedicamento(ContentResponse item){
        viewModel.salvarMedicamento(item);
    }

}
