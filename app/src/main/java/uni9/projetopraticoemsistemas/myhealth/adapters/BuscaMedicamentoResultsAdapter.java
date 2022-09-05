package uni9.projetopraticoemsistemas.myhealth.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import uni9.projetopraticoemsistemas.myhealth.R;
import uni9.projetopraticoemsistemas.myhealth.model.dto.ContentResponse;

public class BuscaMedicamentoResultsAdapter extends RecyclerView.Adapter<BuscaMedicamentoResultsAdapter.BuscaMedicamentoResultHolder> {

    private List<ContentResponse> results = new ArrayList<>();
    private final OnItemClickListener listener;

    public BuscaMedicamentoResultsAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public BuscaMedicamentoResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicamento_item, parent, false);

        return new BuscaMedicamentoResultHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BuscaMedicamentoResultHolder holder, int position) {
        ContentResponse contentResponse = results.get(position);
        holder.bind(contentResponse, listener);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setResults(List<ContentResponse> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    static class BuscaMedicamentoResultHolder extends RecyclerView.ViewHolder {
        private final TextView tvMedicamentoNome;
        private final TextView tvMedicamentoEmpresa;

        public BuscaMedicamentoResultHolder(@NonNull View itemView) {
            super(itemView);
            tvMedicamentoNome = itemView.findViewById(R.id.tv_medicamento_nome);
            tvMedicamentoEmpresa = itemView.findViewById(R.id.tv_medicamento_empresa);
        }

        public void bind(final ContentResponse contentResponse, final OnItemClickListener listener) {
            tvMedicamentoNome.setText(contentResponse.getNomeProduto());
            tvMedicamentoEmpresa.setText(contentResponse.getRazaoSocial());

            itemView.setOnClickListener(v -> listener.onItemClick(contentResponse));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(ContentResponse item);
    }
}
