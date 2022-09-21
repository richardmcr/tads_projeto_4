package uni9.projetopraticoemsistemas.myhealth.configuracoes.configuracoes.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import uni9.projetopraticoemsistemas.myhealth.databinding.FragmentConfiguracoesBinding;
import uni9.projetopraticoemsistemas.myhealth.home.home.viewmodel.HomeViewModel;

public class ConfiguracoesFragment extends Fragment {

    private FragmentConfiguracoesBinding binding;
    private HomeViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentConfiguracoesBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.init();

        binding.extendedFloatingActionButtonAlterarSenha.setOnClickListener(v -> Navigation.findNavController(v).navigate(ConfiguracoesFragmentDirections.actionConfiguracoesFragmentToAlterarSenhaFragment()));

    }

}
