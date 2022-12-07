package uni9.projetopraticoemsistemas.myhealth.home.agua.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.Objects;

import uni9.projetopraticoemsistemas.myhealth.R;
import uni9.projetopraticoemsistemas.myhealth.databinding.FragmentAguaBinding;
import uni9.projetopraticoemsistemas.myhealth.home.agua.viewmodel.AguaViewModel;

public class AguaFragment extends Fragment {

    private AguaViewModel viewModel;
    private FragmentAguaBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAguaBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(AguaViewModel.class);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.init();

        viewModel.getLembreteAguaLiveData().observe(getViewLifecycleOwner(), lembreteAgua -> {
            if (Objects.isNull(lembreteAgua)) {
                Navigation.findNavController(requireView()).navigate(AguaFragmentDirections.actionAguaFragmentToConfiguracaoAguaFragment());
            }
        });

        viewModel.getConsumoDiarioLiveData().observe(getViewLifecycleOwner(), consumoDiario -> {
            if (!Objects.isNull(consumoDiario)) {
                binding.textViewMetaDiaria.setText(getString(R.string.meta_diaria_args, consumoDiario.getMeta()));
                binding.textViewRestante.setText(getString(R.string.consumo_restante, consumoDiario.getRestante()));
            }
        });

        viewModel.obterConsumoDia();

    }

}
