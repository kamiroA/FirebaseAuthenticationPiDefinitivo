package dam.pmdm.firebaseauthenticationpi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dam.pmdm.firebaseauthenticationpi.Desafio;
import dam.pmdm.firebaseauthenticationpi.R;

public class DesafioAdapter extends RecyclerView.Adapter<DesafioAdapter.DesafioViewHolder> {

    private List<Desafio> desafios;
    private OnDesafioClickListener onDesafioClickListener;

    public DesafioAdapter(List<Desafio> desafios, OnDesafioClickListener onDesafioClickListener) {
        this.desafios = desafios;
        this.onDesafioClickListener = onDesafioClickListener;
    }

    @NonNull
    @Override
    public DesafioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_desafio, parent, false);
        return new DesafioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DesafioViewHolder holder, int position) {
        Desafio desafio = desafios.get(position);
        holder.tvNombreDesafio.setText(desafio.getNombre());

        // Configurar el clic en el elemento
        holder.itemView.setOnClickListener(v -> onDesafioClickListener.onDesafioClick(desafio));
    }

    @Override
    public int getItemCount() {
        return desafios.size();
    }

    public static class DesafioViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreDesafio;

        public DesafioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreDesafio = itemView.findViewById(R.id.tvNombreDesafio);
        }
    }

    // Interfaz para manejar el clic en el desafío
    public interface OnDesafioClickListener {
        void onDesafioClick(Desafio desafio);
    }
}