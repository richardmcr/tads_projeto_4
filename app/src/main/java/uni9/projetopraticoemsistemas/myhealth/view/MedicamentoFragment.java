package uni9.projetopraticoemsistemas.myhealth.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import uni9.projetopraticoemsistemas.myhealth.R;
import uni9.projetopraticoemsistemas.myhealth.databinding.FragmentMedicamentoBinding;
import uni9.projetopraticoemsistemas.myhealth.eventos.Eventos;
import uni9.projetopraticoemsistemas.myhealth.viewmodel.MedicamentoViewModel;

public class MedicamentoFragment extends Fragment {

    private MedicamentoViewModel viewModel;
    private FragmentMedicamentoBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMedicamentoBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(MedicamentoViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MedicamentoFragmentArgs medicamentoFragmentArgs = MedicamentoFragmentArgs.fromBundle(getArguments());

        viewModel.init();
        viewModel.getMedicamentoLiveData().observe(getViewLifecycleOwner(), medicamento -> {
            if (medicamento != null) {
                binding.textViewNomeComercial.setText(medicamento.getNomeComercial());
                binding.textViewRazaoSocial.setText(getString(R.string.razao_social_cnpj, medicamento.getRazaoSocial(), medicamento.getCnpj()));

                if (Objects.isNull(medicamento.getPrincipioAtivo()) || medicamento.getPrincipioAtivo().isEmpty()) {
                    binding.linearLayoutPrincipioAtivo.setVisibility(View.GONE);
                } else {
                    binding.textViewPrincipioAtivo.setText(medicamento.getPrincipioAtivo());
                }
                if (Objects.isNull(medicamento.getMedicamentoReferencia()) || medicamento.getMedicamentoReferencia().isEmpty()) {
                    binding.linearLayoutMedicamentoReferencia.setVisibility(View.GONE);
                } else {
                    binding.textViewMedicamentoReferencia.setText(medicamento.getMedicamentoReferencia());
                }
                if (Objects.isNull(medicamento.getClassesTerapeuticas()) || medicamento.getClassesTerapeuticas().isEmpty()) {
                    binding.linearLayoutClasseTerapeutica.setVisibility(View.GONE);
                } else {
                    binding.textViewClasseTerapeutica.setText(medicamento.getClassesTerapeuticas());
                }

                binding.floatingActionButtonAvancar.setOnClickListener(v -> Navigation.findNavController(v).navigate(MedicamentoFragmentDirections.actionMedicamentoFragmentToLembreteFragment(medicamento.getId(), 0L)));

                viewModel.getLoadingLiveData().postValue(Boolean.FALSE);
            }
        });

        viewModel.getLoadingLiveData().observe(getViewLifecycleOwner(),
                isLoading -> {
                    if (isLoading) {
                        binding.contentLoadingProgressBar.setVisibility(View.VISIBLE);
                        binding.cardViewMedicamento.setVisibility(View.GONE);
                    } else {
                        binding.contentLoadingProgressBar.setVisibility(View.GONE);
                        binding.cardViewMedicamento.setVisibility(View.VISIBLE);
                    }
                });

        viewModel.obterMedicamento(medicamentoFragmentArgs.getIdMedicamento(), medicamentoFragmentArgs.getProcesso());

        viewModel.getEventosMutableLiveData().observe(getViewLifecycleOwner(),
                evento -> {
                    if (!Objects.isNull(evento)) {
                        if (evento instanceof Eventos.MensagemErro) {
                            String mensagem = ((Eventos.MensagemErro) evento).getData();
                            switch (mensagem) {
                                case "erro_conexao":
                                    viewModel.getLoadingLiveData().postValue(Boolean.FALSE);
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
