package com.example.canomariaayelenfinal.business.Users;

import java.util.ArrayList;

public class Users {
    int id;
    String nombre;
    String apellido;
    String usuario;
    String password;

    String email;

    public Users() {

    }


    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", usuario='" + usuario + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public Users(String nombre, String apellido, String usuario, String password, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuario = usuario;
        this.password = password;
        this.email = email;
    }

    public void isAllNull(String nombre, String apellido, String usuario, String password, String email) throws Exception {
        if (nombre.isEmpty() && apellido.isEmpty() && usuario.isEmpty() && password.isEmpty() && email.isEmpty()) {
            throw new Exception("Todos los campos deben estar llenos");
        }
    }

    public ArrayList<String> validateFields( String nombre, String apellido, String usuario, String password,String email) {
        String[] fields = { "nombre", "apellido", "usuario", "password","email"};
        String[] values = {nombre, apellido, usuario, password,email};
        ArrayList<String> returnList = new ArrayList<String>();
        for (int i = 0; i < fields.length; i++) {
            if (values[i] == null || values[i].trim().length() == 0) {
                returnList.add(fields[i]);
            }
        }
        return returnList;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
