package com.example.canomariaayelenfinal.ui.Films;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.canomariaayelenfinal.R;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerHolder> {
    private List<Films> films;
    private Context mContext;

    public RecyclerViewAdapter(List<Films> films, Context mContext) {
        this.films = films;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_recomendados,parent,false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        holder.title.setText(films.get(position).getTitle());
        String url = "https://image.tmdb.org/t/p/w500"+ films.get(position).getPoster_path();
        Glide.with(mContext).load(url).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return films.size();
    }


    public static class RecyclerHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView image;

        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleItem);
            image = itemView.findViewById(R.id.imageItem);

        }
    }
}
