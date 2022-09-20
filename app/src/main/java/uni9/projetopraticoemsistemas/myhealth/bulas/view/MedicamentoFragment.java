package uni9.projetopraticoemsistemas.myhealth.bulas.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.Objects;

import uni9.projetopraticoemsistemas.myhealth.DownloadService;
import uni9.projetopraticoemsistemas.myhealth.R;
import uni9.projetopraticoemsistemas.myhealth.bulas.viewmodel.MedicamentoViewModel;
import uni9.projetopraticoemsistemas.myhealth.databinding.FragmentMedicamentoBinding;
import uni9.projetopraticoemsistemas.myhealth.eventos.Eventos;
import uni9.projetopraticoemsistemas.myhealth.lembretes.view.MedicamentoFragmentArgs;

public class MedicamentoFragment extends Fragment {

    private MedicamentoViewModel viewModel;
    private FragmentMedicamentoBinding binding;
    private ProgressDialog mProgressDialog;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMedicamentoBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(MedicamentoViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK ||
                            result.getResultCode() == Activity.RESULT_CANCELED) {
                        Navigation.findNavController(requireView()).popBackStack(R.id.homeFragment, false);
                    }
                });

        MedicamentoFragmentArgs medicamentoFragmentArgs = MedicamentoFragmentArgs.fromBundle(getArguments());

        mProgressDialog = new ProgressDialog(requireContext());
        mProgressDialog.setMessage(getString(R.string.baixando_bula));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);

        viewModel.init();

        viewModel.getMedicamentoLiveData().observe(getViewLifecycleOwner(), medicamento -> {
            if (!Objects.isNull(medicamento)) {
                binding.textViewNomeComercial.setText(medicamento.getNomeComercial());
                binding.textViewRazaoSocial.setText(getString(R.string.razao_social_cnpj, medicamento.getRazaoSocial(), medicamento.getCnpj()));

                if (Objects.isNull(medicamento.getPrincipioAtivo()) || medicamento.getPrincipioAtivo().isEmpty()) {
                    binding.linearLayoutPrincipioAtivo.setVisibility(View.GONE);
                } else {
                    binding.textViewPrincipioAtivo.setText(medicamento.getPrincipioAtivo());
                }
                if (Objects.isNull(medicamento.getMedicamentoReferencia()) || medicamento.getMedicamentoReferencia().isEmpty()) {
                    binding.linearLayoutMedicamentoReferencia.setVisibility(View.GONE);
                } else {
                    binding.textViewMedicamentoReferencia.setText(medicamento.getMedicamentoReferencia());
                }
                if (Objects.isNull(medicamento.getClassesTerapeuticas()) || medicamento.getClassesTerapeuticas().isEmpty()) {
                    binding.linearLayoutClasseTerapeutica.setVisibility(View.GONE);
                } else {
                    binding.textViewClasseTerapeutica.setText(medicamento.getClassesTerapeuticas());
                }

                binding.floatingActionButtonAvancar.setOnClickListener(v -> {
                    String arquivo = medicamento.getId() + ".pdf";
                    File pdf = new File(DownloadService.folder, arquivo);
                    if (pdf.exists()) {
                        abrirBula(medicamento.getId() + ".pdf");
                    } else {
                        mProgressDialog.show();
                        Intent intent = new Intent(requireContext(), DownloadService.class);
                        intent.putExtra("name", arquivo);
                        intent.putExtra("url", String.format("https://bula.vercel.app/pdf?id=%s", medicamento.getIdBulaPacienteProtegido()));
                        intent.putExtra("receiver", viewModel.getDownloadReceiver());
                        requireActivity().startService(intent);
                    }
                });

                viewModel.getLoadingLiveData().postValue(Boolean.FALSE);

                viewModel.getProgressoLiveData().observe(getViewLifecycleOwner(),
                        progresso -> {
                            mProgressDialog.setProgress(progresso);
                            if (progresso == 100) {
                                mProgressDialog.dismiss();
                                abrirBula(medicamento.getId() + ".pdf");
                            }
                        });
            }
        });


        viewModel.getLoadingLiveData().observe(getViewLifecycleOwner(),
                isLoading -> {
                    if (isLoading) {
                        binding.contentLoadingProgressBar.setVisibility(View.VISIBLE);
                        binding.cardViewMedicamento.setVisibility(View.GONE);
                    } else {
                        binding.contentLoadingProgressBar.setVisibility(View.GONE);
                        binding.cardViewMedicamento.setVisibility(View.VISIBLE);
                    }
                });

        viewModel.obterMedicamento(medicamentoFragmentArgs.getIdMedicamento(), medicamentoFragmentArgs.getProcesso());

        viewModel.getEventosMutableLiveData().observe(getViewLifecycleOwner(),
                evento -> {
                    if (!Objects.isNull(evento)) {
                        if (evento instanceof Eventos.MensagemErro) {
                            String mensagem = ((Eventos.MensagemErro) evento).getData();
                            switch (mensagem) {
                                case "erro_conexao":
                                    viewModel.getLoadingLiveData().postValue(Boolean.FALSE);
                                    Snackbar.make(requireView(), getString(R.string.erro_conexao), Snackbar.LENGTH_LONG).show();
                                    break;
                                case "resultados_offline":
                                    Snackbar.make(requireView(), getString(R.string.resultados_offline), Snackbar.LENGTH_LONG).show();
                                    break;
                                default:
                                    Snackbar.make(requireView(), mensagem, Snackbar.LENGTH_LONG).show();
                            }
                        }
                        viewModel.getEventosMutableLiveData().setValue(null);
                    }
                }
        );
    }

    private void abrirBula(String arquivo) {
        File file = new File(DownloadService.folder, arquivo);
        Uri uriPdfPath = FileProvider.getUriForFile(requireContext(), requireContext().getPackageName() + ".provider", file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setClipData(ClipData.newRawUri("", uriPdfPath));
        intent.setDataAndType(uriPdfPath, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        activityResultLauncher.launch(intent);
    }
}
