package uni9.projetopraticoemsistemas.myhealth.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import uni9.projetopraticoemsistemas.myhealth.R;
import uni9.projetopraticoemsistemas.myhealth.adapters.BuscaMedicamentoResultsAdapter;
import uni9.projetopraticoemsistemas.myhealth.model.dto.BuscaResponse;
import uni9.projetopraticoemsistemas.myhealth.model.dto.ContentResponse;
import uni9.projetopraticoemsistemas.myhealth.viewmodel.BuscaMedicamentoViewModel;

public class BuscaMedicamentosFragment extends Fragment {
    
    private BuscaMedicamentoViewModel viewModel;
    private BuscaMedicamentoResultsAdapter adapter;

    private TextInputEditText tietBusca;
    private Button btnBuscar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new BuscaMedicamentoResultsAdapter(new BuscaMedicamentoResultsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ContentResponse item) {
                Log.d("LOG_BuscaMedicamentos", item.toString());
                salvarMedicamento(item);
                obterMedicamento(item.getNumProcesso());
            }
        });

        viewModel = new ViewModelProvider(this).get(BuscaMedicamentoViewModel.class);
        viewModel.init();
        viewModel.getBuscaResponseLiveData().observe(this, new Observer<BuscaResponse>() {
            @Override
            public void onChanged(BuscaResponse buscaResponse) {
                if (buscaResponse != null) {
                    adapter.setResults(buscaResponse.getContentResponse());
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_busca_medicamento, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_medicamentos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        tietBusca = view.findViewById(R.id.tiet_busca);
        btnBuscar = view.findViewById(R.id.btn_buscar);

        btnBuscar.setOnClickListener(v -> buscarMedicamentos());

        return view;
    }

    public void buscarMedicamentos() {
        String nome = tietBusca.getEditableText().toString();

        viewModel.buscarMedicamentos(nome);
    }

    public void obterMedicamento(String numProcesso) {
        viewModel.obterMedicamento(numProcesso);
    }

    public void salvarMedicamento(ContentResponse item){
        viewModel.salvarMedicamento(item);
    }

}
