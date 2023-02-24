package com.example.canomariaayelenfinal.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.canomariaayelenfinal.business.Users.Users;

import java.util.ArrayList;

public class UserDAO {
    Context context;
    Users user;
    ArrayList<Users> usersList;
    SQLiteDatabase sql;
    String db = "UserDB";


    public UserDAO(Context context) {
        this.context = context;
        //comando para abrir o crear bd
        sql= context.openOrCreateDatabase(db,context.MODE_PRIVATE,null);
        //sql.execSQL("DROP TABLE IF EXISTS users");
        sql.execSQL("create table if not exists users (id integer primary key autoincrement,email text,username text, password text, name text, surname text)");
        user=new Users();
    }

    public boolean insertUser(Users user){
        if(userAlreadyExist(user.getUsuario())==false){
            ContentValues contentValues = new ContentValues();
            contentValues.put("email",user.getEmail());
            contentValues.put("username",user.getUsuario());
            contentValues.put("password",user.getPassword());
            contentValues.put("name",user.getNombre());
            contentValues.put("surname",user.getApellido());
            return (sql.insert("users",null,contentValues)>0);
        }else{
            return false;
        }
    }

    //Funcion que devuelve true si el usuario ya existe en la BD
    private boolean userAlreadyExist(String username) {
        Cursor cursor = sql.rawQuery("SELECT * FROM users WHERE username = ?", new String[] {username});
        if (cursor.moveToFirst()) {
            // El nombre de usuario se encuentra en la base de datos
            cursor.close();
            return true;
        } else {
            // El nombre de usuario no se encuentra en la base de datos
            cursor.close();
            return false;
        }
    }
    public boolean userAndPasswordExist(String username,String password) {
        Cursor cursor = sql.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[] {username,password});
        if (cursor.moveToFirst()) {
            // El nombre de usuario se encuentra en la base de datos
            cursor.close();
            return true;
        } else {
            // El nombre de usuario no se encuentra en la base de datos
            cursor.close();
            return false;
        }
    }

}
