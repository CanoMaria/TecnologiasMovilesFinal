package com.example.canomariaayelenfinal.ui.Fragments;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import com.example.canomariaayelenfinal.R;
import com.example.canomariaayelenfinal.ui.Database.FavoritesDAO;
import com.example.canomariaayelenfinal.ui.Films.DesciptionActivity;
import com.example.canomariaayelenfinal.ui.Films.Films;
import com.example.canomariaayelenfinal.ui.Films.GridViewAdapter;

import java.util.List;

public class FavoriteFragment extends Fragment {
    List<Films> favoriteList;
    private GridView gvFavoritesFilms;
    private GridViewAdapter gvAdapter;

    FavoritesDAO favoritesDAO;
    private View view;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorites, container, false);
        favoritesDAO = new FavoritesDAO(getActivity());

        //Obtenemos una lista de todos los favoritos
        favoriteList = favoritesDAO.getAllFilms();

        //Cargamos las peliculas
        putDataIntoGridView(favoriteList);

        return view;
    }
    private void putDataIntoGridView(List<Films> filmList){
        //inicializamos el rv
        gvFavoritesFilms= view.findViewById(R.id.gv_favorites);
        gvAdapter=  new GridViewAdapter(filmList,getActivity());
        gvFavoritesFilms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                moveToDescription(filmList.get(i));
            }
        });
        gvFavoritesFilms.setAdapter(gvAdapter);
    }
    private void moveToDescription(Films films) {
        Intent intent = new Intent(getActivity(), DesciptionActivity.class);
        intent.putExtra("Film",films);
        startActivity(intent);
    }
}
