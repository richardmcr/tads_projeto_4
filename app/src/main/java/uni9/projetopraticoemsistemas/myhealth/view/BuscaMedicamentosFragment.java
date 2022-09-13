package uni9.projetopraticoemsistemas.myhealth.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import uni9.projetopraticoemsistemas.myhealth.R;
import uni9.projetopraticoemsistemas.myhealth.adapters.MedicamentoAdapter;
import uni9.projetopraticoemsistemas.myhealth.databinding.FragmentBuscaMedicamentoBinding;
import uni9.projetopraticoemsistemas.myhealth.eventos.Eventos;
import uni9.projetopraticoemsistemas.myhealth.model.Medicamento;
import uni9.projetopraticoemsistemas.myhealth.viewmodel.BuscaMedicamentoViewModel;

public class BuscaMedicamentosFragment extends Fragment implements MedicamentoAdapter.OnItemClickListener {

    private BuscaMedicamentoViewModel viewModel;
    private FragmentBuscaMedicamentoBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentBuscaMedicamentoBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(BuscaMedicamentoViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onItemClick(Medicamento medicamento) {
        viewModel.onMedicamentoSelecionado(medicamento);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.init();

        MedicamentoAdapter adapter = new MedicamentoAdapter(this);

        binding.recyclerViewMedicamentos.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewMedicamentos.setAdapter(adapter);
        binding.recyclerViewMedicamentos.setHasFixedSize(true);

        binding.floatingActionButtonBuscar.setOnClickListener(v -> {
            String busca = binding.textInputEditTextBusca.getEditableText().toString();
            viewModel.buscarMedicamentos(busca);
        });

        binding.textInputEditTextBusca.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED
                    || actionId == EditorInfo.IME_ACTION_GO
                    || actionId == EditorInfo.IME_ACTION_DONE) {
                String busca = binding.textInputEditTextBusca.getEditableText().toString();
                viewModel.buscarMedicamentos(busca);
                return true;
            }
            return false;
        });

        viewModel.getMedicamentoListLiveData().observe(getViewLifecycleOwner(),
                buscaResponse -> {
                    viewModel.getLoadingLiveData().setValue(Boolean.FALSE);
                    if (buscaResponse != null) {
                        adapter.submitList(buscaResponse);
                    }
                });

        viewModel.getMedicamentoLiveData().observe(getViewLifecycleOwner(),
                medicamento -> {
                    if (medicamento != null) {
                        Navigation.findNavController(requireView())
                                .navigate(BuscaMedicamentosFragmentDirections.actionBuscaMedicamentoFragmentToMedicamentoFragment(medicamento.getId(), medicamento.getProcesso()));
                    }
                });

        viewModel.getLoadingLiveData().observe(getViewLifecycleOwner(),
                isLoading -> {
                    if (isLoading) binding.contentLoadingProgressBar.setVisibility(View.VISIBLE);
                    else binding.contentLoadingProgressBar.setVisibility(View.GONE);
                });

        viewModel.getEventosMutableLiveData().observe(getViewLifecycleOwner(),
                evento -> {
                    if (!Objects.isNull(evento)) {
                        if (evento instanceof Eventos.MensagemErro) {
                            String mensagem = ((Eventos.MensagemErro) evento).getData();
                            switch (mensagem) {
                                case "erro_conexao":
                                    Snackbar.make(requireView(), getString(R.string.erro_conexao), Snackbar.LENGTH_LONG).show();
                                    break;
                                case "resultados_offline":
                                    Snackbar.make(requireView(), getString(R.string.resultados_offline), Snackbar.LENGTH_LONG).show();
                                    break;
                                default:
                                    Snackbar.make(requireView(), mensagem, Snackbar.LENGTH_LONG).show();
                            }
                        }
                        viewModel.getEventosMutableLiveData().setValue(null);
                    }
                }
        );
    }

}
