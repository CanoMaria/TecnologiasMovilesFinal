package com.example.canomariaayelenfinal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.canomariaayelenfinal.R;
import com.example.canomariaayelenfinal.model.Films;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    Context context;
    private List<Films> films;

    LayoutInflater inflater;

    public GridViewAdapter(List<Films> films,Context context) {
        this.context = context;
        this.films = films;
    }

    @Override
    public int getCount() {
        return films.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater == null)
            inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(view == null)
            view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_recomendados,viewGroup,false);

        ImageView imageView = view.findViewById(R.id.imageItem);
        TextView textView = view.findViewById(R.id.titleItem);

        //String url = "https://image.tmdb.org/t/p/w500"+ films.get(i).getPoster_path();
        String url = films.get(i).getImageUrl();
        Glide.with(context).load(url).into(imageView);
        films.get(i).setImageUrl(url);
        textView.setText(films.get(i).getTitle());

        return view;
    }
}
