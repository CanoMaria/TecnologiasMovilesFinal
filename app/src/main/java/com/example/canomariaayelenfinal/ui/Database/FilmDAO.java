package com.example.canomariaayelenfinal.ui.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.canomariaayelenfinal.ui.Films.Films;
import com.example.canomariaayelenfinal.ui.Users.Users;

import java.util.ArrayList;
import java.util.List;

public class FilmDAO {
    Context context;
    Films film;
    SQLiteDatabase sql;
    String db = "FilmsDB";

    public FilmDAO(Context context) {
        this.context = context;
        //comando para abrir o crear bd
        sql= context.openOrCreateDatabase(db,context.MODE_PRIVATE,null);
        sql.execSQL("create table if not exists films (id integer primary key autoincrement,title text,image_link text, description text,type text)");
        film = new Films();
    }
    public boolean insertFilm(Films film,String type){

        ContentValues contentValues = new ContentValues();
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
    public List<Films> getFilms(String type){
        Cursor cursor = sql.rawQuery("SELECT * FROM films WHERE type = ?", new String[] {type});
        List<Films> filmsList = new ArrayList<>();

        while (cursor.moveToNext()) {
            Films filmTemp= new Films();
            filmTemp.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            filmTemp.setImageUrl(cursor.getString(cursor.getColumnIndex("image_link")));
            filmTemp.setSynopsis(cursor.getString(cursor.getColumnIndex("description")));
            filmsList.add(filmTemp);
        }
        return filmsList;
    }
}

