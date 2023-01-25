package com.example.canomariaayelenfinal.ui.Films;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.canomariaayelenfinal.R;

public class DesciptionActivity extends AppCompatActivity {
    TextView nameFilm;
    ImageView imageFilm;
    TextView descriptionFilm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Films film = (Films) getIntent().getSerializableExtra("Film");

        nameFilm=findViewById(R.id.titleMovieDetail);
        imageFilm=findViewById(R.id.imageMovieDetail);
        descriptionFilm=findViewById(R.id.descriptionMovieDetail);

        //Seteamos los valores
        nameFilm.setText(film.getTitle());
        Glide.with(this).load(film.getImageUrl()).into(imageFilm);
        descriptionFilm.setText(film.getSynopsis());
    }
}
