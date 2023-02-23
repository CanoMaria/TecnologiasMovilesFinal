package com.example.canomariaayelenfinal.ui.Films;

import java.io.Serializable;

public class Genres implements Serializable {
    private int id;
    private String name;

    @Override
    public String toString() {
        return "Genres{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
