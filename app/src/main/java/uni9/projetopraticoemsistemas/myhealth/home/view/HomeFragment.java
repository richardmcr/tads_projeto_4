package uni9.projetopraticoemsistemas.myhealth.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import uni9.projetopraticoemsistemas.myhealth.R;
import uni9.projetopraticoemsistemas.myhealth.databinding.FragmentHomeBinding;
import uni9.projetopraticoemsistemas.myhealth.eventos.Eventos;
import uni9.projetopraticoemsistemas.myhealth.home.viewmodel.HomeViewModel;
import uni9.projetopraticoemsistemas.myhealth.login.model.Usuario;

public class HomeFragment extends Fragment implements FragmentResultListener {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.init();

        MenuHost menuHost = requireActivity();
        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_home, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.itemSair) {
                    viewModel.onSair();
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        binding.extendedFloatingActionButtonLembretes.setOnClickListener(v -> Navigation.findNavController(v).navigate(HomeFragmentDirections.actionHomeFragmentToLembretesFragment()));

        requireActivity().getSupportFragmentManager().setFragmentResultListener("usuario_logado", getViewLifecycleOwner(), this);

        final NavController navController = Navigation.findNavController(view);
        viewModel.getUsuarioIdLiveData().observe(getViewLifecycleOwner(), usuarioId -> {
            if (usuarioId == 0L) {
                navController.navigate(R.id.loginFragment);
            } else {
                viewModel.onLogin(usuarioId);
            }
        });

        viewModel.getEventoLiveData().observe(getViewLifecycleOwner(), evento -> {
            if (!Objects.isNull(evento)) {
                if (evento instanceof Eventos.Logout) {
                    Bundle result = new Bundle();
                    result.putInt("logout", 1);
                    requireActivity().getSupportFragmentManager().setFragmentResult("logout", result);

                    navController.navigate(R.id.loginFragment);
                } else if (evento instanceof Eventos.UsuarioLogado) {
                    Usuario usuario = ((Eventos.UsuarioLogado) evento).getData();
                }
                viewModel.getEventoLiveData().setValue(null);
            }
        });
    }

    @Override
    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
        String usuarioLogado = result.getString("usuario_logado");
        if (!Objects.isNull(usuarioLogado)) {
            Snackbar.make(requireView(), getString(R.string.bem_vindo, usuarioLogado), Snackbar.LENGTH_LONG).show();
        }
    }
}
