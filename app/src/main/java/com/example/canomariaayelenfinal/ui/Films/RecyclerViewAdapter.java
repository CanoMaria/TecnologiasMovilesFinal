package com.example.canomariaayelenfinal.ui.Films;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canomariaayelenfinal.R;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerHolder> {
    private List<Items> items;

    public RecyclerViewAdapter(List<Items> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_recomendados,parent,false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        Items item = items.get(position);
        holder.imageItem.setImageResource(item.getImage());
        holder.tvTitulo.setText(item.getTitle());
       // holder.tvDescripcion.setText((item.getDescription()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class RecyclerHolder extends RecyclerView.ViewHolder{
        private ImageView imageItem;
        private TextView tvTitulo;
        private TextView tvDescripcion;

        public RecyclerHolder(@NonNull View itemView){
            super(itemView);
            imageItem=itemView.findViewById(R.id.imageItem);
            tvTitulo= itemView.findViewById(R.id.titleItem);
            //tvDescripcion=itemView.findViewById(R.id.descriptionItem);

        }
    }
}
