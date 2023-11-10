package com.example.apipokemon.pokedex.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.apipokemon.pokedex.database.pokeinfo.tables.Ability;
import com.example.apipokemon.pokedex.database.pokeinfo.tables.PokeInfo;
import com.example.apipokemon.pokedex.database.pokeinfo.tables.PokeInfo_Ability;
import com.example.apipokemon.pokedex.database.pokeinfo.tables.PokeInfo_Type;
import com.example.apipokemon.pokedex.database.pokeinfo.tables.Pokemon_Favorite;
import com.example.apipokemon.pokedex.database.pokeinfo.tables.Type;
import com.example.apipokemon.pokedex.entities.Pokemon;

import java.util.List;

public class PokedexViewModel extends AndroidViewModel {
    PokedexDao dao;

    public PokedexViewModel(@NonNull Application application) {
        super(application);
        PokedexDatabase database = PokedexDatabase.getInstance(application.getApplicationContext());
        dao = database.obtenerPokedexDao();
    }

    //Pokemon
    public List<Pokemon> obtenerPokemonsDeLaBadeDeDatos() {
        return dao.obtenerPokemonsDeLaBaseDeDatos();
    }

    public void agregarTodosLosPokemonsALaBD(List<Pokemon> pokemons) {
        dao.agregarTodosLosPokemons(pokemons);
    }

    public List<Pokemon> buscarPokemonsFavoritos(String pokemon) {
        return dao.buscarPokemonsEnLaBaseDeDatos(pokemon);
    }

    public LiveData<List<Pokemon>> obtenerPokemonsFavoritos() {
        return dao.obtenerPokemonsFavoritosDeLaBaseDeDatos();
    }

    //PokeInfo
    public void agregarPokemonInfoALaBD(PokeInfo pokeInfo) {
        dao.agregarPokeInfo(pokeInfo);
    }

    public PokeInfo obtenerPokemonInfoDeLaBD(int id) {
        return dao.obtenerPokeInfoDeLaBaseDeDatos(id);
    }

    //Type
    public void agregarTipoALaBaseDeDatos(Type tipo) {
        dao.agregarTipo(tipo);
    }

    public LiveData<List<Type>> obtenerTiposDePokemonDeLaBaseDeDatos(int idPokemon) {
        return dao.obtenerTiposDeLaBaseDeDatos(idPokemon);
    }

    //PokeInfo_Type
    public void agregarRelacionPokeInfoTipo(PokeInfo_Type pokeInfo_type) {
        dao.agregarRelacionPokeInfoTipo(pokeInfo_type);
    }


    public void agregarHabilidadALaBD(Ability ability) {
        dao.agregarHabilidad(ability);
    }

    public LiveData<List<Ability>> obtenerHabilidadesDePokemonDeLaBaseDeDatos(int idPokemon) {
        return dao.obtenerHabilidadesDeLaBD(idPokemon);
    }


    public void agregarRelacionPokeInfoHabilidad(PokeInfo_Ability pokeInfo_ability) {
        dao.agregarRelacionPokeInfoHabilidad(pokeInfo_ability);
    }


    public void agregarRelacionPokemonFavorito(Pokemon_Favorite pokemonFavorite) {
        dao.agregarRelacionPokemonFavorito(pokemonFavorite);
    }

    public int esPokemonFavorito(int id) {
        return dao.esPokemonFavorito(id);
    }

    public LiveData<List<Pokemon>> buscarPokemonsFavoritosEnLaBD(String busca) {
        return dao.buscarPokemonsFavoritosEnLaBD(busca);
    }
}
