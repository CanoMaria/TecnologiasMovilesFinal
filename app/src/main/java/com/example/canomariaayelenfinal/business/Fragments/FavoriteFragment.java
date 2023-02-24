package com.example.canomariaayelenfinal.business.Fragments;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.example.canomariaayelenfinal.DAO.UserDAO;
import com.example.canomariaayelenfinal.R;
import com.example.canomariaayelenfinal.DAO.FavoritesDAO;
import com.example.canomariaayelenfinal.model.DesciptionActivity;
import com.example.canomariaayelenfinal.model.Films;
import com.example.canomariaayelenfinal.adapter.GridViewAdapter;

import java.util.List;

public class FavoriteFragment extends Fragment {
    List<Films> favoriteList;
    private GridView gvFavoritesFilms;
    private GridViewAdapter gvAdapter;
    FavoritesDAO favoritesDAO;
    private View view;
    SharedPreferences sharedPreferences;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorites, container, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        int  user_id=sharedPreferences.getInt("user_id",0);
        Log.d(TAG, "USER_ID: "+ user_id);
        favoritesDAO = new FavoritesDAO(getActivity(),user_id);

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
        intent.putExtra("FilmDescription",films);
        startActivity(intent);
    }
}
