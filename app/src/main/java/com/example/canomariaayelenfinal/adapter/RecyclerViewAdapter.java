package com.example.canomariaayelenfinal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.canomariaayelenfinal.R;
import com.example.canomariaayelenfinal.model.Films;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerHolder>
                                 implements View.OnClickListener{
    private List<Films> films;
    private Context mContext;

    private View.OnClickListener listener;


    public RecyclerViewAdapter(List<Films> films, FragmentActivity mContext) {
        this.films = films;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_recomendados,parent,false);
        view.setOnClickListener(this);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        holder.title.setText(films.get(position).getTitle());
        //String url = "https://image.tmdb.org/t/p/w500"+ films.get(position).getPoster_path();
        String url = films.get(position).getImageUrl();
        films.get(position).setImageUrl(url);
        Glide.with(mContext).load(url).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
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
