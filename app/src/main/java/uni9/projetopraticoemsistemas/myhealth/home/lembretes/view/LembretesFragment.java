package uni9.projetopraticoemsistemas.myhealth.home.lembretes.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import uni9.projetopraticoemsistemas.myhealth.R;
import uni9.projetopraticoemsistemas.myhealth.databinding.FragmentLembretesBinding;
import uni9.projetopraticoemsistemas.myhealth.eventos.Eventos;
import uni9.projetopraticoemsistemas.myhealth.home.lembretes.adapters.LembretesAdapter;
import uni9.projetopraticoemsistemas.myhealth.home.lembretes.model.Lembrete;
import uni9.projetopraticoemsistemas.myhealth.home.lembretes.model.Ordenacao;
import uni9.projetopraticoemsistemas.myhealth.home.lembretes.viewmodel.LembretesViewModel;

@AndroidEntryPoint
public class LembretesFragment extends Fragment implements LembretesAdapter.OnItemClickListener, FragmentResultListener {

    private LembretesViewModel viewModel;
    private FragmentLembretesBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLembretesBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(LembretesViewModel.class);

        requireActivity().getSupportFragmentManager().setFragmentResultListener("lembrete_salvo", getViewLifecycleOwner(), this);

        return binding.getRoot();
    }

    @Override
    public void onItemClick(Lembrete lembrete) {
        Navigation.findNavController(requireView()).navigate(LembretesFragmentDirections.actionLembretesFragmentToLembreteFragment(lembrete.getMedicamento().getId(), lembrete.getId()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LembretesAdapter adapter = new LembretesAdapter(this);
        viewModel.init();

        binding.recyclerViewLembretes.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewLembretes.setAdapter(adapter);
        binding.recyclerViewLembretes.setHasFixedSize(true);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Lembrete lembrete = adapter.getCurrentList().get(viewHolder.getAdapterPosition());
                viewModel.onLembreteSwiped(lembrete);
            }
        }).attachToRecyclerView(binding.recyclerViewLembretes);

        binding.floatingActionButtonNovoLembrete.setOnClickListener(v -> viewModel.onNovoLembrete());

        viewModel.getLembreteListLiveData().observe(getViewLifecycleOwner(),
                lembreteList -> {
                    if (Objects.isNull(lembreteList)) {
                        binding.textViewSemLembrete.setVisibility(View.VISIBLE);
                    } else {
                        if (lembreteList.isEmpty()) {
                            binding.textViewSemLembrete.setVisibility(View.VISIBLE);
                        } else {
                            binding.textViewSemLembrete.setVisibility(View.GONE);
                        }
                        adapter.submitList(lembreteList);
                    }
                });

        viewModel.getLembreteEventoLiveData().observe(getViewLifecycleOwner(),
                eventos -> {
                    if (!Objects.isNull(eventos)) {
                        if (eventos instanceof Eventos.LembreteRemovido) {
                            Snackbar.make(requireView(), getString(R.string.lembrete_removido), Snackbar.LENGTH_LONG)
                                    .setAction(getString(R.string.desfazer),
                                            v -> viewModel.onDesfazerLembreteRemovido(((Eventos.LembreteRemovido) eventos).getData())).show();
                        } else if (eventos instanceof Eventos.LembreteRecuperado) {
                            viewModel.eventoCompleto();
                        } else if (eventos instanceof Eventos.NovoLembrete) {
                            Navigation.findNavController(view).navigate(LembretesFragmentDirections.actionLembretesFragmentToBuscaMedicamentoFragment());
                        } else if (eventos instanceof Eventos.LembretesCompletosRemovidos) {
                            Snackbar.make(requireView(), getString(R.string.lembretes_removido), Snackbar.LENGTH_LONG).show();
                        }
                    }
                });

        MenuHost menuHost = requireActivity();
        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_fragment_lembretes, menu);

                MenuItem itemBuscar = menu.findItem(R.id.itemBuscar);
                SearchView searchViewBuscar = (SearchView) itemBuscar.getActionView();

                searchViewBuscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        viewModel.getMedicamentoLiveData().postValue(newText);
                        return true;
                    }
                });

                MenuItem itemOcultarCompletos = menu.findItem(R.id.itemOcultarCompletos);
                itemOcultarCompletos.setChecked(Boolean.TRUE.equals(viewModel.getOcultarCompletosInicial()));

            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.itemBuscar) {
                    return true;
                } else if (itemId == R.id.itemOrdenarMedicamento) {
                    viewModel.getOrdenacaoLiveData().postValue(Ordenacao.BY_MEDICAMENTO);
                    return true;
                } else if (itemId == R.id.itemOrdenarData) {
                    viewModel.getOrdenacaoLiveData().postValue(Ordenacao.BY_DATA);
                    return true;
                } else if (itemId == R.id.itemOcultarCompletos) {
                    menuItem.setChecked(!menuItem.isChecked());
                    viewModel.getOcultarCompletosLiveData().postValue(menuItem.isChecked());
                    return true;
                } else if (itemId == R.id.itemApagarCompletos) {
                    new AlertDialog.Builder(requireContext())
                            .setTitle(getString(R.string.confirmar_exclusao))
                            .setMessage(getString(R.string.mensagem_confirmar_exclusao))
                            .setNegativeButton(getString(R.string.cancelar), null)
                            .setPositiveButton(getString(R.string.sim), (dialog, which) -> viewModel.removerLembretesCompletos())
                            .create()
                            .show();
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    @Override
    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
        int resultado = result.getInt("lembrete_salvo");
        if (resultado == 0) {
            Snackbar.make(requireView(), getString(R.string.novo_lembrete_criado), Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(requireView(), getString(R.string.lembrete_editado), Snackbar.LENGTH_LONG).show();
        }
    }
}
