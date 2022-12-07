package uni9.projetopraticoemsistemas.myhealth.home.agua.view;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;

import uni9.projetopraticoemsistemas.myhealth.R;
import uni9.projetopraticoemsistemas.myhealth.databinding.FragmentConfiguracaoAguaBinding;
import uni9.projetopraticoemsistemas.myhealth.helper.view.CustomTextWatcher;
import uni9.projetopraticoemsistemas.myhealth.home.agua.viewmodel.ConfiguracaoAguaViewModel;

public class ConfiguracaoAguaFragment extends Fragment {

    private ConfiguracaoAguaViewModel viewModel;
    private FragmentConfiguracaoAguaBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentConfiguracaoAguaBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(ConfiguracaoAguaViewModel.class);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.init();

        binding.textInputEditTextMetaDiaria.addTextChangedListener(new CustomTextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {
                if (!Objects.isNull(s.toString()) && !s.toString().isEmpty())
                    viewModel.setMeta(Long.valueOf(s.toString()));
            }
        });
        binding.radioGroupUnidade.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId == R.id.radioButtonLitros){
                viewModel.setUnidade(ConfiguracaoAguaViewModel.LITROS);
            } else if (checkedId == R.id.radioButtonMililitros){
                viewModel.setUnidade(ConfiguracaoAguaViewModel.MILILITROS);
            }
        });
        binding.textInputEditTextHoraInicioLembrete.addTextChangedListener(new CustomTextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {
                viewModel.setHoraInicio(s.toString());
            }
        });
        binding.textInputEditTextHoraFimLembrete.addTextChangedListener(new CustomTextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {
                viewModel.setHoraFinal(s.toString());
            }
        });
        binding.switchAlertas.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.setAlertas(isChecked));
    }

}
