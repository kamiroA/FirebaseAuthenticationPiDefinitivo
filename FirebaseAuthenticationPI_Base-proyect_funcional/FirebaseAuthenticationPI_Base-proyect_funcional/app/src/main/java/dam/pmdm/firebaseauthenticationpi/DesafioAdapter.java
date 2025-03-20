package dam.pmdm.firebaseauthenticationpi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DesafioAdapter extends RecyclerView.Adapter<DesafioAdapter.DesafioViewHolder> {

    private List<Desafio> desafios;
    private List<String> experienciasDelUsuario; // Lista de experiencias del usuario
    private OnDesafioClickListener onDesafioClickListener;

    public DesafioAdapter(List<Desafio> desafios, List<String> experienciasDelUsuario, OnDesafioClickListener onDesafioClickListener) {
        this.desafios = desafios;
        this.experienciasDelUsuario = experienciasDelUsuario; // Inicializa la lista de experiencias del usuario
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

        // Verificar si hay nuevas experiencias
        if (hayNuevasExperiencias(desafio)) {
            holder.tvNombreDesafio.append(" (Nuevas)");
        }

        // Configurar el clic en el elemento
        holder.itemView.setOnClickListener(v -> onDesafioClickListener.onDesafioClick(desafio));
    }

    private boolean hayNuevasExperiencias(Desafio desafio) {
        // Verificar si la lista de experiencias es null
        if (desafio.getExperiencias() == null) {
            return false; // No hay nuevas experiencias si la lista es null
        }

        // Contar el número de experiencias en el desafío
        int totalExperienciasDesafio = desafio.getExperiencias().size();

        // Contar el número de experiencias que tiene el usuario
        int totalExperienciasUsuario = experienciasDelUsuario.size();

        // Comparar las cantidades
        return totalExperienciasDesafio > totalExperienciasUsuario; // Hay nuevas experiencias si el desafío tiene más
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