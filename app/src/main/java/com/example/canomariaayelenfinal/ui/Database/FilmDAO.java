package com.example.canomariaayelenfinal.ui.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import androidx.fragment.app.FragmentActivity;

import com.example.canomariaayelenfinal.ui.Films.Films;

import java.util.ArrayList;
import java.util.List;

public class FilmDAO {
    Context context;
    Films film;
    SQLiteDatabase sql;
    String db = "FilmsDB";

    public FilmDAO(FragmentActivity context) {
        this.context = context;
        //comando para abrir o crear bd
        sql= context.openOrCreateDatabase(db,context.MODE_PRIVATE,null);

        //sql.execSQL("DROP TABLE IF EXISTS films");
        sql.execSQL("create table if not exists films (id integer,title text,image_link text, description text,type text)");
        film = new Films();
    }
    public boolean insertFilm(Films film,String type){

        ContentValues contentValues = new ContentValues();
        contentValues.put("id",film.getId());
        contentValues.put("title",film.getTitle());
        contentValues.put("image_link",film.getImageUrl());
        contentValues.put("description",film.getSynopsis());
        contentValues.put("type",type);

        return (sql.insert("films",null,contentValues)>0);
    }


    public void cleanTable(){
        sql.execSQL("DELETE FROM films");
    }

    @SuppressLint("Range")
    public List<Films> getFilmsByType(String type){
        Cursor cursor = sql.rawQuery("SELECT * FROM films WHERE type = ?", new String[] {type});
        List<Films> filmsList = new ArrayList<>();

        while (cursor.moveToNext()) {
            Films filmTemp= new Films();
            filmTemp.setId(cursor.getInt(cursor.getColumnIndex("id")));
            filmTemp.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            filmTemp.setImageUrl(cursor.getString(cursor.getColumnIndex("image_link")));
            filmTemp.setSynopsis(cursor.getString(cursor.getColumnIndex("description")));
            filmsList.add(filmTemp);
        }
        cursor.close();
        return filmsList;
    }
    @SuppressLint("Range")
    public Films getFilmByName(String name){

        Cursor cursor = sql.rawQuery("SELECT * FROM films WHERE title = ?", new String[] {name});
        Films filmTemp= new Films();
        filmTemp.setId(cursor.getInt(cursor.getColumnIndex("id")));
        filmTemp.setTitle(cursor.getString(cursor.getColumnIndex("title")));
        filmTemp.setImageUrl(cursor.getString(cursor.getColumnIndex("image_link")));
        filmTemp.setSynopsis(cursor.getString(cursor.getColumnIndex("description")));
        cursor.close();
        return filmTemp;
    }
}

