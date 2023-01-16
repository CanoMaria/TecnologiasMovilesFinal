package com.example.canomariaayelenfinal.ui.Films;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.canomariaayelenfinal.R;

public class GridViewAdapter extends BaseAdapter {
    Context context;
    String [] filmsName;
    int [] image;

    LayoutInflater inflater;
    public GridViewAdapter(Context context, String[] filmsName, int[] image) {
        this.context = context;
        this.filmsName = filmsName;
        this.image = image;
    }

    @Override
    public int getCount() {
        return filmsName.length;
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
            //view= inflater.inflate(R.layout.cardview_recomendados,null);

        ImageView imageView = view.findViewById(R.id.imageItem);
        TextView textView = view.findViewById(R.id.titleItem);

        imageView.setImageResource(image[i]);
        textView.setText(filmsName[i]);
        return view;
    }
}
