package uni9.projetopraticoemsistemas.myhealth.home.lembretes.view;

import static uni9.projetopraticoemsistemas.myhealth.helper.DateFormatter.longDateToStringDate;
import static uni9.projetopraticoemsistemas.myhealth.helper.DateFormatter.longDateToStringTime;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import uni9.projetopraticoemsistemas.myhealth.R;
import uni9.projetopraticoemsistemas.myhealth.databinding.FragmentLembreteBinding;
import uni9.projetopraticoemsistemas.myhealth.eventos.Eventos;
import uni9.projetopraticoemsistemas.myhealth.helper.view.CustomTextWatcher;
import uni9.projetopraticoemsistemas.myhealth.home.lembretes.viewmodel.LembreteViewModel;

public class LembreteFragment extends Fragment {

    private LembreteViewModel viewModel;
    private FragmentLembreteBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLembreteBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(LembreteViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LembreteFragmentArgs lembreteFragmentArgs = LembreteFragmentArgs.fromBundle(getArguments());

        viewModel.init();

        binding.textInputEditTextDetalhes.addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                viewModel.setDetalhes(s.toString());
            }
        });
        binding.textInputEditTextDuracaoTratamento.addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (!Objects.isNull(s.toString()) && !s.toString().isEmpty())
                    viewModel.setDuracao(Long.valueOf(s.toString()));
            }
        });
        binding.textInputEditTextIntervalo.addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (!Objects.isNull(s.toString()) && !s.toString().isEmpty())
                    viewModel.setIntervalo(Long.valueOf(s.toString()));
            }
        });
        binding.switchAlertas.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.setAlertas(isChecked));

        viewModel.getLembreteLiveData().observe(getViewLifecycleOwner(), lembrete -> {
            if ((!Objects.isNull(lembrete))) {
                if (lembrete.getId() == 0L) {
                    binding.imageButtonDataInicioTratamento.setOnClickListener(v -> {
                        final Calendar c = Calendar.getInstance();
                        if (!Objects.isNull(viewModel.getInicioLiveData().getValue())) {
                            c.setTime(new Date(Objects.requireNonNull(viewModel.getInicioLiveData().getValue())));
                        }
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                                (t, year, monthOfYear, dayOfMonth) -> {
                                    c.set(Calendar.YEAR, year);
                                    c.set(Calendar.MONTH, monthOfYear);
                                    c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                    viewModel.setInicio(c.getTime().getTime());
                                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                        datePickerDialog.show();
                    });
                    binding.imageButtonHoraInicioTratamento.setOnClickListener(v -> {
                        final Calendar c = Calendar.getInstance();
                        if (!Objects.isNull(viewModel.getInicioLiveData().getValue())) {
                            c.setTime(new Date(Objects.requireNonNull(viewModel.getInicioLiveData().getValue())));
                        }
                        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                                (t, hourOfDay, minute) -> {
                                    c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                    c.set(Calendar.MINUTE, minute);
                                    viewModel.setInicio(c.getTime().getTime());
                                }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
                        timePickerDialog.show();
                    });
                    binding.floatingActionButtonSalvar.setOnClickListener(v -> viewModel.inserirLembrete());
                } else {
                    binding.textInputEditTextDetalhes.setEnabled(Boolean.FALSE);
                    binding.textInputEditTextDuracaoTratamento.setEnabled(Boolean.FALSE);
                    binding.textInputEditTextIntervalo.setEnabled(Boolean.FALSE);
                    binding.switchAlertas.setEnabled(Boolean.FALSE);
                    binding.floatingActionButtonSalvar.setOnClickListener(v -> Navigation.findNavController(view).popBackStack(R.id.lembretesFragment, false));
                }
                binding.textInputEditTextMedicamento.setText(lembrete.getMedicamento().getNome());
                binding.textInputEditTextDetalhes.setText(lembrete.getDetalhes());
                binding.textInputEditTextDataInicioTratamento.setText(lembrete.getDataInicio());
                binding.textInputEditTextHoraInicioTratamento.setText(lembrete.getHoraInicio());
                binding.textInputEditTextDuracaoTratamento.setText(Objects.isNull(lembrete.getDuracao()) ? "" : String.valueOf(lembrete.getDuracao()));
                binding.textInputEditTextIntervalo.setText(Objects.isNull(lembrete.getIntervalo()) ? "" : String.valueOf(lembrete.getIntervalo()));
                binding.switchAlertas.setChecked(Objects.nonNull(lembrete.getAlertas()) && lembrete.getAlertas());

                viewModel.setDetalhes(lembrete.getDetalhes());
                viewModel.setDuracao(lembrete.getDuracao());
                viewModel.setIntervalo(lembrete.getIntervalo());
                viewModel.setAlertas(Objects.nonNull(lembrete.getAlertas()) && lembrete.getAlertas());
            }
        });

        viewModel.obterLembrete(lembreteFragmentArgs.getIdLembrete(), lembreteFragmentArgs.getIdMedicamento());

        viewModel.getInicioLiveData().observe(getViewLifecycleOwner(), data -> {
            if (!Objects.isNull(data)) {
                binding.textInputEditTextDataInicioTratamento.setText(longDateToStringDate(data));
                binding.textInputEditTextHoraInicioTratamento.setText(longDateToStringTime(data));
            }

            viewModel.setInicio(data);
        });

        viewModel.getEventoLiveData().observe(getViewLifecycleOwner(), evento -> {
            if (!Objects.isNull(evento)) {
                if (evento instanceof Eventos.MensagemErro) {
                    Snackbar.make(requireView(), ((Eventos.MensagemErro) evento).getData(), Snackbar.LENGTH_LONG).show();
                } else if (evento instanceof Eventos.ErroValidacao) {
                    switch (((Eventos.ErroValidacao) evento).getData()) {
                        case "duracao":
                            binding.textInputEditTextDuracaoTratamento.setError(getString(R.string.erro_campo_vazio, getString(R.string.duracao)));
                            Snackbar.make(requireView(), getString(R.string.erro_campo_vazio, getString(R.string.duracao)), Snackbar.LENGTH_LONG).show();
                            break;
                        case "duracao_negativa":
                            binding.textInputEditTextDuracaoTratamento.setError(getString(R.string.erro_campo_negativo, getString(R.string.duracao)));
                            Snackbar.make(requireView(), getString(R.string.erro_campo_negativo, getString(R.string.duracao)), Snackbar.LENGTH_LONG).show();
                            break;
                        case "intervalo":
                            binding.textInputEditTextIntervalo.setError(getString(R.string.erro_campo_vazio, getString(R.string.intervalo)));
                            Snackbar.make(requireView(), getString(R.string.erro_campo_vazio, getString(R.string.intervalo)), Snackbar.LENGTH_LONG).show();
                            break;
                        case "intervalo_negativo":
                            binding.textInputEditTextIntervalo.setError(getString(R.string.erro_campo_negativo, getString(R.string.intervalo)));
                            Snackbar.make(requireView(), getString(R.string.erro_campo_negativo, getString(R.string.intervalo)), Snackbar.LENGTH_LONG).show();
                            break;
                    }
                } else if (evento instanceof Eventos.LembreteSalvo) {
                    Bundle result = new Bundle();
                    result.putInt("lembrete_salvo", ((Eventos.LembreteSalvo) evento).getData());
                    requireActivity().getSupportFragmentManager().setFragmentResult("lembrete_salvo", result);
                    Navigation.findNavController(view).popBackStack(R.id.lembretesFragment, false);
                }
            }
        });
    }

}
