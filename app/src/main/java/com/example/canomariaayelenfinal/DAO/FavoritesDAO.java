package com.example.canomariaayelenfinal.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.fragment.app.FragmentActivity;

import com.example.canomariaayelenfinal.model.Films;

import java.util.ArrayList;
import java.util.List;


public class FavoritesDAO {
    Context context;
    Films film;
    SQLiteDatabase sql;
    String db = "FavoritesDB";
    int user_id;

    public FavoritesDAO(FragmentActivity context,int user_id) {
        this.context = context;
        this.user_id=user_id;

        //comando para abrir o crear bd
        sql = context.openOrCreateDatabase(db, context.MODE_PRIVATE, null);
        //sql.execSQL("DROP TABLE IF EXISTS favorites");
        sql.execSQL("create table if not exists favorites (id integer,title text,image_link text, description text,is_favorite boolean default 0,user_id integer )");
        film = new Films();
    }
    public boolean isFavorite(int id){
        String s = "SELECT COUNT(*) FROM favorites WHERE id = ? and user_id= ?";
        String[] params = { String.valueOf(id),String.valueOf(user_id) };
        Cursor cursor = sql.rawQuery(s, params);
        cursor.moveToFirst(); // mover el cursor a la primera fila
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

    public boolean insertFavoriteFilm(Films film) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", film.getId());
        contentValues.put("title", film.getTitle());
        contentValues.put("image_link", film.getImageUrl());
        contentValues.put("description", film.getSynopsis());
        contentValues.put("is_favorite", 1);
        contentValues.put("user_id", user_id);

        return (sql.insert("favorites", null, contentValues) > 0);
    }
    public boolean removeFavoriteFilm(int id) {
        return (sql.delete("favorites", "id=? and user_id=?", new String[] { String.valueOf(id),String.valueOf(user_id) }) > 0);
    }
    @SuppressLint("Range")
    public List<Films> getAllFilms(){
        Cursor cursor = sql.rawQuery("SELECT * FROM favorites where user_id=?", new String[] { String.valueOf(user_id) });
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
}
