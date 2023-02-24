package com.example.canomariaayelenfinal.business;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.canomariaayelenfinal.R;
import com.example.canomariaayelenfinal.DAO.UserDAO;
import com.example.canomariaayelenfinal.business.Users.Users;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText name,email,pass,surname,username;
    TextInputLayout nameLayaout,surnameLayout,emailLayout,usernameLayout,passLayout;
    Button button;

    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        nameLayaout = findViewById(R.id.nameRegister);
        name= nameLayaout.getEditText();

        surnameLayout = findViewById(R.id.surnameRegister);
        surname = surnameLayout.getEditText();

        emailLayout = findViewById(R.id.emailRegister);
        email = emailLayout.getEditText();

        usernameLayout = findViewById(R.id.userRegister);
        username = usernameLayout.getEditText();

        passLayout = findViewById(R.id.passRegister);
        pass = passLayout.getEditText();


        button = findViewById(R.id.registerButton);
        button.setOnClickListener(this);

        userDAO = new UserDAO(this);
    }

    @Override
    public void onClick(View view) {
        Users user = new Users();
        user.setNombre(name.getText().toString());
        user.setApellido(surname.getText().toString());
        user.setEmail(email.getText().toString());
        user.setUsuario(username.getText().toString());
        user.setPassword(pass.getText().toString());

        //inicializamos todos
        nameLayaout.setError(null);
        surnameLayout.setError(null);
        emailLayout.setError(null);
        usernameLayout.setError(null);
        passLayout.setError(null);

        if(isFieldNull(user)==false && userDAO.insertUser(user)){
            Toast.makeText(this,"Registro Exitoso",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else {
            usernameLayout.setError("El ususario ingresado ya existe.");
        }
    }


    public boolean isFieldNull(Users user){

        try {
            user.isAllNull(user.getNombre(),user.getApellido(),user.getUsuario(),user.getPassword(),user.getEmail());
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_SHORT).show();
            nameLayaout.setError("Este campo no puede estar vacio");
            surnameLayout.setError("Este campo no puede estar vacio");
            emailLayout.setError("Este campo no puede estar vacio");
            usernameLayout.setError("Este campo no puede estar vacio");
            passLayout.setError("Este campo no puede estar vacio");

        }
        ArrayList<String> nullFields= user.validateFields(user.getNombre(),user.getApellido(),user.getUsuario(),user.getPassword(),user.getEmail());
        if (!nullFields.isEmpty()){
            for (int i = 0; i < nullFields.size(); i++) {
                switch(nullFields.get(i)) {
                    case "nombre":
                        nameLayaout.setError("El campo no puede estar vacio");
                        return true;
                    case "apellido":
                        surnameLayout.setError("El campo no puede estar vacio");
                        return true;
                    case "usuario":
                        usernameLayout.setError("El campo no puede estar vacio");
                        return true;
                    case "password":
                        passLayout.setError("El campo no puede estar vacio");
                        return true;
                    case "email":
                        emailLayout.setError("El campo no puede estar vacio");
                        return true;
                }

            }
        }
        return false;
    }

}