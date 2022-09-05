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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import uni9.projetopraticoemsistemas.myhealth.R;
import uni9.projetopraticoemsistemas.myhealth.viewmodel.MedicamentoViewModel;

public class MedicamentoFragment extends Fragment {

    private MedicamentoViewModel viewModel;
    private MedicamentoFragmentArgs medicamentoFragmentArgs;

    private TextView ttvNomeComercial, ttvRazaoSocialCnpj, ttvPrincipioAtivo, ttvMedicamentoReferencia, ttvClasseTerapeutica;
    private Button btnAvancar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        medicamentoFragmentArgs = MedicamentoFragmentArgs.fromBundle(getArguments());

        viewModel = new ViewModelProvider(this).get(MedicamentoViewModel.class);
        viewModel.init();
        viewModel.getMedicamentoEntityLiveData().observe(this, medicamentoEntity -> {
            if (medicamentoEntity != null) {
                ttvNomeComercial.setText(medicamentoEntity.getNomeComercial());
                ttvRazaoSocialCnpj.setText(medicamentoEntity.getRazaoSocial()); //todo
                ttvPrincipioAtivo.setText(medicamentoEntity.getPrincipioAtivo());
                ttvMedicamentoReferencia.setText(medicamentoEntity.getMedicamentoReferencia());
                ttvClasseTerapeutica.setText(medicamentoEntity.getClassesTerapeuticas());
            }
        });
        viewModel.obterMedicamento(medicamentoFragmentArgs.getIdProduto());
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

        btnAvancar.setOnClickListener(v ->  Navigation.findNavController(v).navigate(MedicamentoFragmentDirections.actionMedicamentoFragmentToNovoLembreteFragment()));

        return view;
    }

}
