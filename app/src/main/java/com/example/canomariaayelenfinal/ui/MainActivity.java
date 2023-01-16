package com.example.canomariaayelenfinal.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.canomariaayelenfinal.R;
import com.example.canomariaayelenfinal.databinding.ActivityMainBinding;
import com.example.canomariaayelenfinal.ui.Films.GridViewAdapter;
import com.example.canomariaayelenfinal.ui.Films.Items;
import com.example.canomariaayelenfinal.ui.Films.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvNewFilms;
    private RecyclerViewAdapter rvAdapter;
    private List<Items> items;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initRecyclerView();
        initGridView();

    }
    private void initRecyclerView(){
        rvNewFilms=findViewById(R.id.rv_news);
        //definimos que el recycles se vea horizontalmente
        rvNewFilms.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        items = getItems();
        rvAdapter= new RecyclerViewAdapter(items);
        rvNewFilms.setAdapter(rvAdapter);
    }

    private List<Items> getItems(){
        List<Items> itemsList= new ArrayList<>();
        itemsList.add(new Items("Mulan",R.drawable.palomitas3_0));
        itemsList.add(new Items("Spidermar regreso a casa",R.drawable.palomitas3_0));
        itemsList.add(new Items("Mulan",R.drawable.palomitas3_0));
        itemsList.add(new Items("Mulan",R.drawable.palomitas3_0));

        return itemsList;
    }
    private void initGridView(){
        //Iniciaslizamos la grid
        String [] filmsName={"mulan","el rey leon","spiderman","toy story"};
        int [] filmsImage={R.drawable.palomitas3_0,R.drawable.palomitas3_0,R.drawable.palomitas3_0,R.drawable.palomitas3_0};
        GridViewAdapter gridViewAdapter = new GridViewAdapter(MainActivity.this,filmsName,filmsImage);

        binding.gridFilms.setAdapter(gridViewAdapter);
        binding.gridFilms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this,"tu clikeaste "+ filmsName[i],Toast.LENGTH_SHORT ).show();
            }
        });
    }
}