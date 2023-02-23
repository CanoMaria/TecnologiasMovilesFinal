package com.example.canomariaayelenfinal.ui.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canomariaayelenfinal.R;
import com.example.canomariaayelenfinal.ui.Database.FilmDAO;
import com.example.canomariaayelenfinal.ui.Films.DesciptionActivity;
import com.example.canomariaayelenfinal.ui.Films.Films;
import com.example.canomariaayelenfinal.ui.Films.GridViewAdapter;
import com.example.canomariaayelenfinal.ui.Films.RecyclerViewAdapter;
import com.example.canomariaayelenfinal.ui.URLDataFetcher;

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


public class HomeFragment extends Fragment {
    List <Films>recyclerList;
    List <Films>gridList;
    private RecyclerView rvPopularFilms;
    private RecyclerViewAdapter rvAdapter;

    private GridView gvRecomendedFilms;
    private GridViewAdapter gvAdapter;
    FilmDAO filmDAO;
    private View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        filmDAO = new FilmDAO(getActivity());
        //Cargamos las peliculas
        loadDataFromDB();
        return view;
    }
    private void loadDataFromDB() {
        //Levantamos los datos de la BD
        List<Films> recyclerListFromDB= filmDAO.getFilmsByType("popular");
        if (!recyclerListFromDB.isEmpty())
            putDataIntoRecyclerView(recyclerListFromDB);

        List <Films>gridListFromDB= filmDAO.getFilmsByType("recomendados");
        if (!gridListFromDB.isEmpty())
            putDataIntoGridView(gridListFromDB);

        if (!isOnline(getActivity())) {
            showNoInternetDialog(getActivity());
        }else{
            filmDAO.cleanTable();
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
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static void showNoInternetDialog(Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Sin conexión a Internet");
        builder.setMessage("No tienes conexión a Internet");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ((Activity) context).finish();
            }
        });
        builder.show();

    }


    //Funcion asincrona para obtener las peliculas
    public  class GetData extends AsyncTask<String,String,String> {
        String JSON_URL=null;

        protected String doInBackground(String... params) {
            JSON_URL = params[0]; // Obtiene la URL pasada como parámetro
            URLDataFetcher urlDataFetcher = new URLDataFetcher();
            return urlDataFetcher.fetchData(JSON_URL);
        }

        protected void onPostExecute(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                //limpiamos la bd para cargar los nuevos resultados

                for (int i = 0 ; i< jsonArray.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    Films movie = new Films();

                    movie.setId(Integer.parseInt(jsonObject1.getString("id")));
                    movie.setTitle (jsonObject1.getString("title"));
                    movie.setPoster_path (jsonObject1.getString("poster_path"));
                    movie.setImageUrl("https://image.tmdb.org/t/p/w500"+movie.getPoster_path());
                    movie.setSynopsis(jsonObject1.getString("overview"));
                    movie.setIs_favourite(false);

                    if (JSON_URL.contains("popular")) {
                        filmDAO.insertFilm(movie,"popular");
                        recyclerList.add(movie);
                    } else {
                        filmDAO.insertFilm(movie,"recomendados");
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
        rvPopularFilms= view.findViewById(R.id.rv_popular);
        //definimos que el recycles se vea horizontalmente
        rvPopularFilms.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvAdapter= new RecyclerViewAdapter(filmList, getActivity());
        rvAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToDescription(filmList.get(rvPopularFilms.getChildAdapterPosition(view)));
            }
        });
        rvPopularFilms.setAdapter(rvAdapter);
    }

    private void moveToDescription(Films films) {
        Intent intent = new Intent(getActivity(), DesciptionActivity.class);
        intent.putExtra("Film",films);
        startActivity(intent);
    }


    private void putDataIntoGridView(List<Films> filmList){
        GridViewAdapter gridViewAdapter = new GridViewAdapter(filmList,getActivity());
        gvRecomendedFilms= view.findViewById(R.id.grid_films);
        gvRecomendedFilms.setAdapter(gridViewAdapter);
        gvRecomendedFilms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                moveToDescription(filmList.get(i));
            }
        });
    }
}