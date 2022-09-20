package uni9.projetopraticoemsistemas.myhealth.login.view;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import uni9.projetopraticoemsistemas.myhealth.R;
import uni9.projetopraticoemsistemas.myhealth.databinding.FragmentLoginBinding;
import uni9.projetopraticoemsistemas.myhealth.eventos.Eventos;
import uni9.projetopraticoemsistemas.myhealth.helper.view.CustomTextWatcher;
import uni9.projetopraticoemsistemas.myhealth.login.model.Usuario;
import uni9.projetopraticoemsistemas.myhealth.login.viewmodel.LoginViewModel;

public class LoginFragment extends Fragment implements FragmentResultListener {

    private LoginViewModel viewModel;
    private FragmentLoginBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true ) {
            @Override
            public void handleOnBackPressed() {
                Toast.makeText(getContext(), "Voltar pra onde?", Toast.LENGTH_SHORT).show();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.init();

        binding.textInputEditTextEmail.addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                viewModel.setEmail(s.toString());
            }
        });
        binding.textInputEditTextSenha.addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                viewModel.setSenha(s.toString());
            }
        });
        binding.buttonLogin.setOnClickListener(v -> viewModel.logar());
        binding.textViewNovoUsuario.setOnClickListener(v -> Navigation.findNavController(v).navigate(LoginFragmentDirections.actionLoginFragmentToNovoUsuarioFragment()));

        requireActivity().getSupportFragmentManager().setFragmentResultListener("usuario_salvo", getViewLifecycleOwner(), this);
        requireActivity().getSupportFragmentManager().setFragmentResultListener("logout", getViewLifecycleOwner(), this);

        viewModel.getEventoLiveData().observe(getViewLifecycleOwner(), evento -> {
            if (!Objects.isNull(evento)) {
                if (evento instanceof Eventos.ErroValidacao) {
                    switch (((Eventos.ErroValidacao) evento).getData()) {
                        case "email":
                            binding.textInputEditTextEmail.setError(getString(R.string.erro_campo_vazio, getString(R.string.email)));
                            Snackbar.make(requireView(), getString(R.string.erro_campo_vazio, getString(R.string.email)), Snackbar.LENGTH_LONG).show();
                            break;
                        case "email_invalido":
                            binding.textInputEditTextEmail.setError(getString(R.string.erro_email_invalido));
                            Snackbar.make(requireView(), getString(R.string.erro_email_invalido), Snackbar.LENGTH_LONG).show();
                            break;
                        case "senha":
                            binding.textInputEditTextSenha.setError(getString(R.string.erro_campo_vazio, getString(R.string.senha)));
                            Snackbar.make(requireView(), getString(R.string.erro_campo_vazio, getString(R.string.senha)), Snackbar.LENGTH_LONG).show();
                            break;
                        case "usuario_nao_encontrado":
                            Snackbar.make(requireView(), getString(R.string.usuario_nao_encontrado), Snackbar.LENGTH_LONG).show();
                            break;
                        case "senha_errada":
                            Snackbar.make(requireView(), getString(R.string.senha_errada), Snackbar.LENGTH_LONG).show();
                            break;
                    }
                } else if (evento instanceof Eventos.UsuarioLogado) {
                    Usuario usuario = ((Eventos.UsuarioLogado) evento).getData();
                    Bundle result = new Bundle();
                    result.putString("usuario_logado", usuario.getNome());
                    requireActivity().getSupportFragmentManager().setFragmentResult("usuario_logado", result);

                    NavHostFragment.findNavController(this).popBackStack();
                }
                viewModel.getEventoLiveData().setValue(null);
            }
        });
    }

    @Override
    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
        int usuarioSalvo = result.getInt("usuario_salvo");
        if (usuarioSalvo == 1) {
            Snackbar.make(requireView(), getString(R.string.novo_usuario_criado), Snackbar.LENGTH_LONG).show();
        }

        int logout = result.getInt("logout");
        if (logout == 1) {
            Snackbar.make(requireView(), getString(R.string.logout_sucesso), Snackbar.LENGTH_LONG).show();
        }
    }
}
