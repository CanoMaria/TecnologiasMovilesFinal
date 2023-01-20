package com.example.canomariaayelenfinal.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.example.canomariaayelenfinal.R;
import com.example.canomariaayelenfinal.databinding.ActivityMainBinding;
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
import java.util.concurrent.ExecutionException;
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


        /*//Cargamos data del RecyclerView
        GetData getData1= new GetData();
        getData1.execute("https://api.themoviedb.org/3/movie/popular?api_key=32032564978a1c288fa5874397c2a0bf&language=es-ES");


        //Cargamos data del GridView
        if( getData1.getStatus() == AsyncTask.Status.FINISHED){
            GetData getData2= new GetData();
            getData2.execute("https://api.themoviedb.org/3/movie/top_rated?api_key=32032564978a1c288fa5874397c2a0bf&language=es_Es");
        }*/
        Executor executor = Executors.newSingleThreadExecutor();
        recyclerList = new ArrayList<>();
        GetData task1 = new GetData();
        task1.executeOnExecutor(executor,"https://api.themoviedb.org/3/movie/popular?api_key=32032564978a1c288fa5874397c2a0bf&language=es-ES");

        gridList = new ArrayList<>();
        GetData task2 = new GetData();
        task2.executeOnExecutor(executor,"https://api.themoviedb.org/3/movie/top_rated?api_key=32032564978a1c288fa5874397c2a0bf&language=es_Es");
    }

    /*
        binding.gridFilms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this,"tu clikeaste "+ filmList.get(i).getTitle(),Toast.LENGTH_SHORT ).show();
            }
        });*/
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
        rvPopularFilms=findViewById(R.id.rv_news);
        //definimos que el recycles se vea horizontalmente
        rvPopularFilms.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvAdapter= new RecyclerViewAdapter(filmList,this);
        rvPopularFilms.setAdapter(rvAdapter);
    }
    private void putDataIntoGridView(List<Films> filmList){
        //GridViewAdapter gridViewAdapter = new GridViewAdapter(filmList,this);
        //binding.gridFilms.setAdapter(gridViewAdapter);
        //inicializamos el rv
        gvRecomendedFilms=findViewById(R.id.grid_films);

        gvAdapter= new GridViewAdapter(filmList,this);
        gvRecomendedFilms.setAdapter(gvAdapter);
    }
}