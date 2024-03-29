package com.example.canomariaayelenfinal.business;


import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.canomariaayelenfinal.MainActivity;
import com.example.canomariaayelenfinal.R;
import com.example.canomariaayelenfinal.DAO.UserDAO;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;


    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin= findViewById(R.id.loginButton);
        UserDAO userDAO = new UserDAO(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();




        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Obtenemos los layaout
                TextInputLayout usernameLayout = findViewById(R.id.username);
                TextInputLayout passLayout = findViewById(R.id.password);


                //Seteamos en nulo
                usernameLayout.setError(null);
                passLayout.setError(null);

                //Obtenemos el editText de los layaout
                EditText user=usernameLayout.getEditText();
                EditText pass=passLayout.getEditText();



                //En caso de que los campos esten vacios llevamos a registro
                if(user.getText().toString().trim().isEmpty() && pass.getText().toString().trim().isEmpty()){
                    Intent intent = new Intent(view.getContext(),RegisterActivity.class);
                    startActivity(intent);
                }else if (userDAO.userAndPasswordExist(user.getText().toString(),pass.getText().toString()) == true){
                //En caso de que se escribio algo verificamos que exista el usuario y la pass
                    //Agregamos el user al shared preference
                    int user_id=userDAO.getUseIdrByUsername(user.getText().toString().trim());
                    editor.putInt("user_id",user_id);
                    editor.apply();
                    Log.d(TAG, "USER_ID: "+ sharedPreferences.getInt("user_id",0));
                    openMainActivity();
                    Toast.makeText(getApplicationContext(),"Bienvenido",Toast.LENGTH_LONG).show();
                }else{
                    usernameLayout.setError("Los datos ingresados no son validos");
                    passLayout.setError("Los datos ingresados no son validos");
                }
            }

        });
    }



    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
