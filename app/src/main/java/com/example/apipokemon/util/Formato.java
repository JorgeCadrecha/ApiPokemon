package com.example.apipokemon.util;

import com.example.apipokemon.pokedex.entities.Pokemon;

public class Formato {

    public static String formatoInfo(int atributo, String info) {
        String format = "" + atributo;
        if (format.length() == 1)
            return "0," + atributo + info;
        return format.substring(0, format.length() - 1) + "," + format.charAt(format.length() - 1) + info;
    }

    public static int getUrlAcortada(String url) {
        String[] urlAcortada = url.split("/");
        return Integer.parseInt(urlAcortada[urlAcortada.length - 1]);
    }

    public static String getNombrePokemonFormato(Pokemon pokemon) {
        String nombrePokemon = pokemon.getName();
        nombrePokemon = nombrePokemon.substring(0, 1).toUpperCase() + nombrePokemon.substring(1);
        return nombrePokemon;
    }
}
