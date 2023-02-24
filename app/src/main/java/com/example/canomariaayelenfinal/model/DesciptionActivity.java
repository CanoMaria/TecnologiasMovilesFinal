package com.example.canomariaayelenfinal.model;


import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.canomariaayelenfinal.R;
import com.example.canomariaayelenfinal.DAO.FavoritesDAO;
import com.example.canomariaayelenfinal.MainActivity;

public class DesciptionActivity extends AppCompatActivity {
    TextView nameFilm;
    ImageView imageFilm;
    TextView descriptionFilm;
    Films film;
    FavoritesDAO favoritesDAO;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        favoritesDAO = new FavoritesDAO(this);
        context = this;


        film = getIntent().getParcelableExtra("FilmDescription");

        Log.d(TAG, "FILM: "+ film);
        Log.d(TAG, "ID: "+ film.getId());



        //Cargamos la pagina
        loadMovieDetails();

        //Definimos un boton para agregar a favoritos
        loadFavouriteButtom();

    }

    private void loadFavouriteButtom(){
        Button addToFavourites_btn = findViewById(R.id.addToFavourites_btn);
        Log.d(TAG, "FAVORITE: ."+ film.getIs_favourite());
        if(favoritesDAO.isFavorite(film.getId())){
            addToFavourites_btn.setText(R.string.remove_to_favorites);
            addToFavourites_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    film.setIs_favourite(false);
                    if(favoritesDAO.removeFavoriteFilm(film.getId())){
                        addToFavourites_btn.setText(R.string.add_to_favourites);
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("fragment_to_load", "favorites");
                        startActivity(intent);

                    }
                }
            });
        }else{
            addToFavourites_btn.setText(R.string.add_to_favourites);
            addToFavourites_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    film.setIs_favourite(true);
                    if(favoritesDAO.insertFavoriteFilm(film)){
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("fragment_to_load", "favorites");
                        startActivity(intent);
                    }
                }
            });
        }
    }

    private void loadMovieDetails(){
        nameFilm=findViewById(R.id.titleMovieDetail);
        imageFilm=findViewById(R.id.imageMovieDetail);
        descriptionFilm=findViewById(R.id.descriptionMovieDetail);

        //Seteamos los valores
        nameFilm.setText(film.getTitle());
        Glide.with(this).load(film.getImageUrl()).into(imageFilm);
        descriptionFilm.setText(film.getSynopsis());
    }
}
