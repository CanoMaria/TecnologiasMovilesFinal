package com.example.canomariaayelenfinal.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.canomariaayelenfinal.R;
import com.example.canomariaayelenfinal.ui.recomendadosRV.Items;
import com.example.canomariaayelenfinal.ui.recomendadosRV.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvRecomendados;
    private RecyclerViewAdapter adapter;
    private List<Items> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initValues();
    }

    //Inicializa la vista
    private void initViews(){
        rvRecomendados= findViewById(R.id.rv_recomendados);
    }
    //Carga los valores de Recycler
    private void initValues(){
        //Si queremos que se muestre en lista ponemos linearlayaout, si queremos grillas ponemos el gridlayaput
        LinearLayoutManager manager= new LinearLayoutManager(this);
        rvRecomendados.setLayoutManager(manager);
        items = getItems();
        adapter= new RecyclerViewAdapter(items);
        rvRecomendados.setAdapter(adapter);
    }
    private List<Items> getItems(){
        List<Items> itemsList= new ArrayList<>();
        itemsList.add(new Items("Mulan","Fa mulan se ve involucrada en .....",R.drawable.palomitas3_0));
        return itemsList;
    }
}