package com.example.canomariaayelenfinal.rest;

public class Constantes {

    public static final String URL_BASE = "https://api.themoviedb.org/3/";
    public static final String API_KEY = "?api_key=32032564978a1c288fa5874397c2a0bf";
    public static final String LENGUAGE = "&language=es-ES";

    public static final String URL_POPULAR = URL_BASE + "movie/popular" + API_KEY + LENGUAGE;
    public static final String URL_TOP_RATED = URL_BASE + "movie/top_rated" + API_KEY + LENGUAGE;
    public static final String URL_FILTER = URL_BASE + "discover/movie" + API_KEY + LENGUAGE;
    public static final String URL_GENRES = URL_BASE + "genre/movie/list" + API_KEY + LENGUAGE;

}
