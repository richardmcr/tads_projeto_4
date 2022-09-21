package uni9.projetopraticoemsistemas.myhealth.home.lembretes.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import uni9.projetopraticoemsistemas.myhealth.R;
import uni9.projetopraticoemsistemas.myhealth.databinding.LembreteItemBinding;
import uni9.projetopraticoemsistemas.myhealth.home.lembretes.model.Lembrete;

public class LembretesAdapter extends ListAdapter<Lembrete, LembretesAdapter.LembreteHolder> {

    private final OnItemClickListener listener;

    public LembretesAdapter(OnItemClickListener listener) {
        super(new DiffLembreteCallBack());
        this.listener = listener;
    }

    @NonNull
    @Override
    public LembreteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LembreteItemBinding binding = LembreteItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new LembreteHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LembreteHolder holder, int position) {
        Lembrete lembrete = getItem(position);
        holder.bind(lembrete);
    }

    public interface OnItemClickListener {
        void onItemClick(Lembrete lembrete);
    }

    public static class DiffLembreteCallBack extends DiffUtil.ItemCallback<Lembrete> {
        public DiffLembreteCallBack() {
        }

        @Override
        public boolean areItemsTheSame(@NonNull Lembrete oldItem, @NonNull Lembrete newItem) {
            return Objects.equals(oldItem.getId(), newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Lembrete oldItem, @NonNull Lembrete newItem) {
            return oldItem.equals(newItem);
        }
    }

    public class LembreteHolder extends RecyclerView.ViewHolder {
        private final LembreteItemBinding binding;

        public LembreteHolder(LembreteItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            this.binding.getRoot().setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Lembrete lembrete = getItem(position);
                    listener.onItemClick(lembrete);
                }
            });
        }

        public void bind(final Lembrete lembrete) {
            binding.textViewNomeMedicamento.setText(lembrete.getMedicamento().getNome());
            if (lembrete.isCompleto()) {
                binding.textViewProximaDose.setText(binding.textViewProximaDose.getContext().getString(R.string.tratamento_completo));
            } else {
                binding.textViewProximaDose.setText(binding.textViewProximaDose.getContext().getString(R.string.proxima_dose, lembrete.getProximaDose()));
            }
        }
    }
}
