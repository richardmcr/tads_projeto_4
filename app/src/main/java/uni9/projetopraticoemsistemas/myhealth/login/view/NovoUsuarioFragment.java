package uni9.projetopraticoemsistemas.myhealth.login.view;

import android.os.Bundle;
import android.text.Editable;
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
import uni9.projetopraticoemsistemas.myhealth.databinding.FragmentNovoUsuarioBinding;
import uni9.projetopraticoemsistemas.myhealth.eventos.Eventos;
import uni9.projetopraticoemsistemas.myhealth.helper.view.CustomTextWatcher;
import uni9.projetopraticoemsistemas.myhealth.login.viewmodel.NovoUsuarioViewModel;

public class NovoUsuarioFragment extends Fragment {

    private NovoUsuarioViewModel viewModel;
    private FragmentNovoUsuarioBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNovoUsuarioBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(NovoUsuarioViewModel.class);

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
        binding.textInputEditTextNome.addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                viewModel.setNome(s.toString());
            }
        });
        binding.textInputEditTextSenha.addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                viewModel.setSenha(s.toString());
            }
        });
        binding.textInputEditTextConfirmacaoSenha.addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                viewModel.setReSenha(s.toString());
            }
        });
        binding.floatingActionButtonSalvar.setOnClickListener(v -> viewModel.inserirUsuario());

        viewModel.getEventoLiveData().observe(getViewLifecycleOwner(), evento -> {
            if (!Objects.isNull(evento)) {
                if (evento instanceof Eventos.MensagemErro) {
                    Snackbar.make(requireView(), ((Eventos.MensagemErro) evento).getData(), Snackbar.LENGTH_LONG).show();
                } else if (evento instanceof Eventos.ErroValidacao) {
                    switch (((Eventos.ErroValidacao) evento).getData()) {
                        case "nome":
                            binding.textInputEditTextNome.setError(getString(R.string.erro_campo_vazio, getString(R.string.nome)));
                            Snackbar.make(requireView(), getString(R.string.erro_campo_vazio, getString(R.string.nome)), Snackbar.LENGTH_LONG).show();
                            break;
                        case "email":
                            binding.textInputEditTextEmail.setError(getString(R.string.erro_campo_vazio, getString(R.string.email)));
                            Snackbar.make(requireView(), getString(R.string.erro_campo_vazio, getString(R.string.email)), Snackbar.LENGTH_LONG).show();
                            break;
                        case "email_invalido":
                            binding.textInputEditTextEmail.setError(getString(R.string.erro_email_invalido));
                            Snackbar.make(requireView(), getString(R.string.erro_email_invalido), Snackbar.LENGTH_LONG).show();
                            break;
                        case "email_existente":
                            binding.textInputEditTextEmail.setError(getString(R.string.erro_email_existente));
                            Snackbar.make(requireView(), getString(R.string.erro_email_existente), Snackbar.LENGTH_LONG).show();
                            break;
                        case "senha":
                            binding.textInputEditTextSenha.setError(getString(R.string.erro_campo_vazio, getString(R.string.senha)));
                            Snackbar.make(requireView(), getString(R.string.erro_campo_vazio, getString(R.string.senha)), Snackbar.LENGTH_LONG).show();
                            break;
                        case "senha_curta":
                            binding.textInputEditTextSenha.setError(getString(R.string.erro_senha_curta));
                            Snackbar.make(requireView(), getString(R.string.erro_senha_curta), Snackbar.LENGTH_LONG).show();
                            break;
                        case "resenha":
                            binding.textInputEditTextConfirmacaoSenha.setError(getString(R.string.erro_campo_vazio, getString(R.string.confirmar_senha)));
                            Snackbar.make(requireView(), getString(R.string.erro_campo_vazio, getString(R.string.confirmar_senha)), Snackbar.LENGTH_LONG).show();
                            break;
                        case "senhas_diferentes":
                            binding.textInputEditTextConfirmacaoSenha.setError(getString(R.string.senhas_diferentes));
                            Snackbar.make(requireView(), getString(R.string.senhas_diferentes), Snackbar.LENGTH_LONG).show();
                            break;
                    }
                } else if (evento instanceof Eventos.UsuarioSalvo) {
                    Bundle result = new Bundle();
                    result.putInt("usuario_salvo", 1);
                    requireActivity().getSupportFragmentManager().setFragmentResult("usuario_salvo", result);
                    Navigation.findNavController(view).popBackStack(R.id.homeFragment, false);
                }
                viewModel.getEventoLiveData().setValue(null);
            }
        });
    }
}
