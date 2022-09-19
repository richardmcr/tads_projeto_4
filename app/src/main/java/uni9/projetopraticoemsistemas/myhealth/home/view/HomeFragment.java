package uni9.projetopraticoemsistemas.myhealth.home.view;

import android.os.Bundle;
import android.util.Log;
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

public class HomeFragment extends Fragment {

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

        final NavController navController = Navigation.findNavController(view);
        viewModel.getUsuarioIdLiveData().observe(getViewLifecycleOwner(), usuarioId -> {
            Log.d("LOGIN", "usuario: " + usuarioId);
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
                    Snackbar.make(requireView(), getString(R.string.bem_vindo, usuario.getNome()), Snackbar.LENGTH_LONG).show();
                }
                viewModel.getEventoLiveData().setValue(null);
            }
        });
    }
}
