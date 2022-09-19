package uni9.projetopraticoemsistemas.myhealth.lembretes.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import uni9.projetopraticoemsistemas.myhealth.databinding.MedicamentoItemBinding;
import uni9.projetopraticoemsistemas.myhealth.lembretes.model.Medicamento;

public class MedicamentoAdapter extends ListAdapter<Medicamento, MedicamentoAdapter.MedicamentoHolder> {

    private final OnItemClickListener listener;

    public MedicamentoAdapter(OnItemClickListener listener) {
        super(new DiffLembreteCallBack());
        this.listener = listener;
    }

    @NonNull
    @Override
    public MedicamentoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MedicamentoItemBinding binding = MedicamentoItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MedicamentoHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicamentoHolder holder, int position) {
        Medicamento medicamento = getItem(position);
        holder.bind(medicamento);
    }

    public class MedicamentoHolder extends RecyclerView.ViewHolder {
        private final MedicamentoItemBinding binding;

        public MedicamentoHolder(MedicamentoItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            this.binding.getRoot().setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    Medicamento medicamento = getItem(position);
                    listener.onItemClick(medicamento);
                }
            });
        }

        public void bind(final Medicamento medicamento) {
            binding.textViewNomeMedicamento.setText(medicamento.getNome());
            binding.textViewNomeEmpresa.setText(medicamento.getRazaoSocial());
        }
    }
    public static class DiffLembreteCallBack extends DiffUtil.ItemCallback<Medicamento>{
        public DiffLembreteCallBack() {
        }

        @Override
        public boolean areItemsTheSame(@NonNull Medicamento oldItem, @NonNull Medicamento newItem) {
            return Objects.equals(oldItem.getId(), newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Medicamento oldItem, @NonNull Medicamento newItem) {
            return oldItem.equals(newItem);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Medicamento item);
    }
}
