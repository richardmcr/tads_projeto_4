package uni9.projetopraticoemsistemas.myhealth.perfil.view;

import static uni9.projetopraticoemsistemas.myhealth.helper.DateFormatter.longDateToStringDate;
import static uni9.projetopraticoemsistemas.myhealth.helper.DateFormatter.stringDateTolong;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import uni9.projetopraticoemsistemas.myhealth.R;
import uni9.projetopraticoemsistemas.myhealth.databinding.FragmentPerfilUsuarioBinding;
import uni9.projetopraticoemsistemas.myhealth.eventos.Eventos;
import uni9.projetopraticoemsistemas.myhealth.helper.view.CustomTextWatcher;
import uni9.projetopraticoemsistemas.myhealth.perfil.viewmodel.PerfilViewModel;

public class PerfilFragment extends Fragment {

    private PerfilViewModel viewModel;
    private FragmentPerfilUsuarioBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPerfilUsuarioBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(PerfilViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.init();

        ArrayAdapter<String> generoAdapter = new ArrayAdapter<>(requireContext(), R.layout.dropdown_menu_popup_item, getResources().getStringArray(R.array.generos));
        ArrayAdapter<String> tipoSanguineoAdapter = new ArrayAdapter<>(requireContext(), R.layout.dropdown_menu_popup_item, getResources().getStringArray(R.array.tipos_sanquineos));

        binding.autoCompleteTextViewGenero.setAdapter(generoAdapter);
        binding.autoCompleteTextViewTipoSanguineo.setAdapter(tipoSanguineoAdapter);

        binding.textInputEditTextPeso.addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (!Objects.isNull(s.toString()) && !s.toString().isEmpty())
                    viewModel.setPeso(Long.valueOf(s.toString()));
            }
        });
        binding.textInputEditTextAltura.addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (!Objects.isNull(s.toString()) && !s.toString().isEmpty())
                    viewModel.setAltura(Float.valueOf(s.toString()));
            }
        });
        binding.autoCompleteTextViewGenero.addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                viewModel.setGenero(s.toString());
            }
        });
        binding.autoCompleteTextViewTipoSanguineo.addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                viewModel.setTipoSanguineo(s.toString());
            }
        });
        binding.imageButtonNascimento.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            if (!Objects.isNull(viewModel.getDataNascimentoLiveData().getValue())) {
                c.setTime(new Date(Objects.requireNonNull(viewModel.getDataNascimentoLiveData().getValue())));
            }
            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                    (t, year, monthOfYear, dayOfMonth) -> {
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, monthOfYear);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        viewModel.setDataNascimento(c.getTime().getTime());
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });
        binding.floatingActionButtonSalvar.setOnClickListener(v -> viewModel.inserirPerfilUsuario());

        viewModel.getPerfilUsuarioLiveData().observe(getViewLifecycleOwner(), perfilUsuario -> {
            if (!Objects.isNull(perfilUsuario)) {
                binding.textInputEditTextNome.setText(perfilUsuario.getNome());
                viewModel.setDataNascimento(stringDateTolong(perfilUsuario.getDataNascimento()));
                binding.textInputEditTextPeso.setText(Objects.toString(perfilUsuario.getPeso(), ""));
                binding.textInputEditTextAltura.setText(Objects.toString(perfilUsuario.getAltura(), ""));
                binding.autoCompleteTextViewGenero.setText(perfilUsuario.getGenero());
                binding.autoCompleteTextViewTipoSanguineo.setText(perfilUsuario.getTipoSanguineo());
            }
        });

        viewModel.getDataNascimentoLiveData().observe(getViewLifecycleOwner(), data -> {
            if (!Objects.isNull(data)) {
                binding.textInputEditTextNascimento.setText(longDateToStringDate(data));
            }
        });

        viewModel.onPerfilUsuarioAberto();

        viewModel.getEventoLiveData().observe(getViewLifecycleOwner(), evento -> {
            if (!Objects.isNull(evento)) {
                if (evento instanceof Eventos.MensagemErro) {
                    Snackbar.make(requireView(), ((Eventos.MensagemErro) evento).getData(), Snackbar.LENGTH_LONG).show();
                } else if (evento instanceof Eventos.ErroValidacao) {
                    switch (((Eventos.ErroValidacao) evento).getData()) {
                        case "data_futura":
                            binding.textInputEditTextNascimento.setError(getString(R.string.data_futura));
                            Snackbar.make(requireView(), getString(R.string.data_futura), Snackbar.LENGTH_LONG).show();
                            break;
                        case "peso_elevado":
                            binding.textInputEditTextPeso.setError(getString(R.string.peso_elevado));
                            Snackbar.make(requireView(), getString(R.string.peso_elevado), Snackbar.LENGTH_LONG).show();
                            break;
                        case "peso_negativo":
                            binding.textInputEditTextPeso.setError(getString(R.string.peso_negativo));
                            Snackbar.make(requireView(), getString(R.string.peso_negativo), Snackbar.LENGTH_LONG).show();
                            break;
                        case "peso_zerado":
                            binding.textInputEditTextPeso.setError(getString(R.string.peso_zerado));
                            Snackbar.make(requireView(), getString(R.string.peso_zerado), Snackbar.LENGTH_LONG).show();
                            break;
                        case "altura_elevada":
                            binding.textInputEditTextAltura.setError(getString(R.string.altura_elevada));
                            Snackbar.make(requireView(), getString(R.string.altura_elevada), Snackbar.LENGTH_LONG).show();
                            break;
                        case "altura_negativa":
                            binding.textInputEditTextAltura.setError(getString(R.string.altura_negativa));
                            Snackbar.make(requireView(), getString(R.string.altura_negativa), Snackbar.LENGTH_LONG).show();
                            break;
                        case "altura_zerada":
                            binding.textInputEditTextAltura.setError(getString(R.string.altura_zerada));
                            Snackbar.make(requireView(), getString(R.string.altura_zerada), Snackbar.LENGTH_LONG).show();
                            break;
                    }
                } else if (evento instanceof Eventos.PerfilUsuarioSalvo) {
                    Snackbar.make(requireView(), getString(R.string.perfil_salvo_sucesso), Snackbar.LENGTH_LONG).show();
                }
                viewModel.getEventoLiveData().setValue(null);
            }
        });
    }
}
