package com.example.canomariaayelenfinal.ui.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.canomariaayelenfinal.R;
import com.example.canomariaayelenfinal.ui.Films.Genres;
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

public class SettingsFragment extends Fragment {
    List<Genres> genresOptions;
    String genreSelected;
    String orderSelected;
    View view;
    Spinner spnGenero;
    Spinner spnParametro;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        spnGenero = view.findViewById(R.id.spnGenero);
        spnParametro = view.findViewById(R.id.spnParametro);
        TextView tvBuscar = view.findViewById(R.id.tvBuscar);
        Button seach_btn = view.findViewById(R.id.search_btn);

        tvBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spnGenero.getVisibility() == View.VISIBLE && spnParametro.getVisibility() == View.VISIBLE && seach_btn.getVisibility() == View.VISIBLE) {
                    spnGenero.setVisibility(View.GONE);
                    spnParametro.setVisibility(View.GONE);
                    seach_btn.setVisibility(View.GONE);
                } else {
                    initGenerSpinner();
                    initOrderSpinner(spnParametro);
                    spnGenero.setVisibility(View.VISIBLE);
                    spnParametro.setVisibility(View.VISIBLE);
                    seach_btn.setVisibility(View.VISIBLE);
                }
            }
        });



        //Cuando se aprita el boton buscar
        seach_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!spnGenero.equals(getString(R.string.select_genres)) && !spnParametro.equals(getString(R.string.select_order)) ){
                    loadFilms();
                }
            }
        });


        return view;
    }

    private void loadFilms() {
    }

    public static int getIdFromTitle(List<Genres> genresList, String title) {
        for (Genres genre : genresList) {
            if (genre.getName().equals(title)) {
                return genre.getId();
            }
        }
        // Si no se encuentra el título en la lista, se podría devolver -1 u otro valor que indique que no se encontró.
        return -1;
    }


    private void initGenerSpinner() {
        GetGeneres recomendedFilms = new GetGeneres();
        Executor executor = Executors.newSingleThreadExecutor();
        recomendedFilms.executeOnExecutor(executor,"https://api.themoviedb.org/3/genre/movie/list?api_key=32032564978a1c288fa5874397c2a0bf&language=es-ES");
    }

    public  class GetGeneres extends AsyncTask<String,String,String> {
        String JSON_URL=null;

       protected String doInBackground(String... params) {
           String JSON_URL = params[0]; // Obtiene la URL pasada como parámetro
           URLDataFetcher urlDataFetcher = new URLDataFetcher();
           return urlDataFetcher.fetchData(JSON_URL);
       }

        protected void onPostExecute(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("genres");
                List<String> genresNames = new ArrayList<>();
                genresOptions=new ArrayList<>();
                genresNames.add(getString(R.string.select_genres));

                for (int i = 0 ; i< jsonArray.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    Genres genres = new Genres();
                    genres.setId(Integer.parseInt(jsonObject1.getString("id")));
                    genres.setName(jsonObject1.getString("name"));

                    genresOptions.add(genres);
                    genresNames.add(genres.getName());
                }
                putGeneresIntoSpinner(genresNames);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void putGeneresIntoSpinner(List<String> genresNames) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, genresNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGenero.setAdapter(adapter);

        spnGenero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Obtener la opción seleccionada y almacenarla en una variable miembro
                genreSelected = (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // No se ha seleccionado nada
            }
        });
    }

    private void initOrderSpinner(Spinner orden) {
        String[] opciones = {getString(R.string.select_order), getString(R.string.most_popular), getString(R.string.most_recent)};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orden.setAdapter(adapter);

        spnParametro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Obtener la opción seleccionada y almacenarla en una variable miembro
                orderSelected = (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // No se ha seleccionado nada
            }
        });
    }

}
