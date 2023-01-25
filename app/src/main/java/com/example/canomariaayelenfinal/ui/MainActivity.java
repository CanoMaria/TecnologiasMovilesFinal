package com.example.canomariaayelenfinal.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.canomariaayelenfinal.R;
import com.example.canomariaayelenfinal.databinding.ActivityMainBinding;
import com.example.canomariaayelenfinal.ui.Films.DesciptionActivity;
import com.example.canomariaayelenfinal.ui.Films.Films;
import com.example.canomariaayelenfinal.ui.Films.GridViewAdapter;
import com.example.canomariaayelenfinal.ui.Films.RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvPopularFilms;
    private GridView gvRecomendedFilms;
    private RecyclerViewAdapter rvAdapter;
    private GridViewAdapter gvAdapter;
    ActivityMainBinding binding;
    List <Films>recyclerList;
    List <Films>gridList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Utiliza el executors para obtener los datos de forma asincrona
        Executor executor = Executors.newSingleThreadExecutor();

        //Creamos una lista para guardar las peliculas populares
        recyclerList = new ArrayList<>();
        GetData popularFilms = new GetData();
        popularFilms.executeOnExecutor(executor,"https://api.themoviedb.org/3/movie/popular?api_key=32032564978a1c288fa5874397c2a0bf&language=es-ES");

        //Creamos una lista para guardar las peliculas recomendadas
        gridList = new ArrayList<>();
        GetData recomendedFilms = new GetData();
        recomendedFilms.executeOnExecutor(executor,"https://api.themoviedb.org/3/movie/top_rated?api_key=32032564978a1c288fa5874397c2a0bf&language=es-Es");
    }

    //Funcion asincrona para obtener las peliculas
    public  class GetData extends AsyncTask<String,String,String> {
        String JSON_URL=null;
        protected String doInBackground(String... params) {
            JSON_URL = params[0]; // obtiene la URL pasada como parámetro
            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while(data != -1){
                        current +=(char) data;
                        data = isr.read();
                    }
                    return current;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(urlConnection != null){
                        urlConnection.disconnect();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;
        }

        protected void onPostExecute(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for (int i = 0 ; i< jsonArray.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    Films movie = new Films();

                    movie.setTitle (jsonObject1.getString("title"));
                    movie.setPoster_path (jsonObject1.getString("poster_path"));
                    movie.setSynopsis(jsonObject1.getString("overview"));

                    Log.d("INFO", movie.getTitle());
                    if (JSON_URL.contains("popular")) {
                        recyclerList.add(movie);
                    } else {
                        gridList.add(movie);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (JSON_URL.contains("popular")) {
                putDataIntoRecyclerView(recyclerList);

            } else {
                putDataIntoGridView(gridList);
            }

        }
    }

    private void putDataIntoRecyclerView(List<Films> filmList){
        //inicializamos el rv
        rvPopularFilms=findViewById(R.id.rv_popular);
        //definimos que el recycles se vea horizontalmente
        rvPopularFilms.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvAdapter= new RecyclerViewAdapter(filmList, this);
        rvAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"tu clickeaste "+
                        filmList.get(rvPopularFilms.getChildAdapterPosition(view)).getTitle(),Toast.LENGTH_SHORT).show();
                moveToDescription(filmList.get(rvPopularFilms.getChildAdapterPosition(view)));
            }
        });
        rvPopularFilms.setAdapter(rvAdapter);
    }

    private void moveToDescription(Films films) {
        Intent intent = new Intent(this, DesciptionActivity.class);
        intent.putExtra("Film",films);
        startActivity(intent);
    }


    private void putDataIntoGridView(List<Films> filmList){
        GridViewAdapter gridViewAdapter = new GridViewAdapter(filmList,this);
        binding.gridFilms.setAdapter(gridViewAdapter);

        binding.gridFilms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this,"tu clikeaste "+ filmList.get(i).getTitle(),Toast.LENGTH_SHORT ).show();
                moveToDescription(filmList.get(i));
            }
        });
    }
}