package uni9.projetopraticoemsistemas.myhealth.configuracoes.alterarsenha.view;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import uni9.projetopraticoemsistemas.myhealth.R;
import uni9.projetopraticoemsistemas.myhealth.configuracoes.alterarsenha.viewmodel.AlterarSenhaViewModel;
import uni9.projetopraticoemsistemas.myhealth.databinding.FragmentAlterarSenhaBinding;
import uni9.projetopraticoemsistemas.myhealth.eventos.Eventos;
import uni9.projetopraticoemsistemas.myhealth.helper.view.CustomTextWatcher;

public class AlterarSenhaFragment extends Fragment {

    private AlterarSenhaViewModel viewModel;
    private FragmentAlterarSenhaBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAlterarSenhaBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(AlterarSenhaViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.init();

        binding.textInputEditTextSenhaAtual.addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                viewModel.setSenhaAtual(s.toString());
            }
        });
        binding.textInputEditTextNovaSenha.addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                viewModel.setNovaSenha(s.toString());
            }
        });
        binding.textInputEditTextReNovaSenha.addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                viewModel.setReNovaSenha(s.toString());
            }
        });
        binding.floatingActionButtonSalvar.setOnClickListener(v -> viewModel.alterarSenha());

        viewModel.getEventoLiveData().observe(getViewLifecycleOwner(), evento -> {
            if (!Objects.isNull(evento)) {
                if (evento instanceof Eventos.ErroValidacao) {
                    switch (((Eventos.ErroValidacao) evento).getData()) {
                        case "senha_atual":
                            binding.textInputEditTextSenhaAtual.setError(getString(R.string.erro_campo_vazio, getString(R.string.senha_atual)));
                            Snackbar.make(requireView(), getString(R.string.erro_campo_vazio, getString(R.string.senha_atual)), Snackbar.LENGTH_LONG).show();
                            break;
                        case "nova_senha":
                            binding.textInputEditTextNovaSenha.setError(getString(R.string.erro_campo_vazio, getString(R.string.nova_senha)));
                            Snackbar.make(requireView(), getString(R.string.erro_campo_vazio, getString(R.string.nova_senha)), Snackbar.LENGTH_LONG).show();
                            break;
                        case "nova_senha_curta":
                            binding.textInputEditTextNovaSenha.setError(getString(R.string.erro_nova_senha_curta));
                            Snackbar.make(requireView(), getString(R.string.erro_nova_senha_curta), Snackbar.LENGTH_LONG).show();
                            break;
                        case "re_nova_senha":
                            binding.textInputLayoutReNovaSenha.setError(getString(R.string.erro_campo_vazio, getString(R.string.confirmar_senha)));
                            Snackbar.make(requireView(), getString(R.string.erro_campo_vazio, getString(R.string.confirmar_senha)), Snackbar.LENGTH_LONG).show();
                            break;
                        case "senhas_diferentes":
                            binding.textInputLayoutReNovaSenha.setError(getString(R.string.senhas_diferentes));
                            Snackbar.make(requireView(), getString(R.string.senhas_diferentes), Snackbar.LENGTH_LONG).show();
                            break;
                        case "senha_errada":
                            binding.textInputEditTextSenhaAtual.setError(getString(R.string.senha_atual_errada));
                            Snackbar.make(requireView(), getString(R.string.senha_atual_errada), Snackbar.LENGTH_LONG).show();
                            break;
                    }
                } else if (evento instanceof Eventos.SenhaAlterada) {
                    binding.textInputEditTextSenhaAtual.setText(null);
                    binding.textInputEditTextNovaSenha.setText(null);
                    binding.textInputEditTextReNovaSenha.setText(null);

                    View v = requireActivity().getCurrentFocus();
                    if (v != null) {
                        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }

                    Snackbar.make(requireView(), getString(R.string.senha_alterada_sucesso), Snackbar.LENGTH_LONG).show();
                }
                viewModel.getEventoLiveData().setValue(null);
            }
        });
    }
}
