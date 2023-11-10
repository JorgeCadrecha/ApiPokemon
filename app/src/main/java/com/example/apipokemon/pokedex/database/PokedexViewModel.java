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
        return dao.getPokemonsDoDatabase();
    }

    public void agregarTodosLosPokemonsALaBD(List<Pokemon> pokemons) {
        dao.addAllPokemons(pokemons);
    }

    public List<Pokemon> buscarPokemonsFavoritos(String pokemon) {
        return dao.searchPokemonsFromDatabase(pokemon);
    }

    public LiveData<List<Pokemon>> obtenerPokemonsFavoritos() {
        return dao.getFavoritePokemonsDoDatabase();
    }

    //PokeInfo
    public void agregarPokemonInfoALaBD(PokeInfo pokeInfo) {
        dao.addPokeInfo(pokeInfo);
    }

    public PokeInfo obtenerPokemonInfoDeLaBD(int id) {
        return dao.getPokeInfoFromDatabase(id);
    }

    //Type
    public void agregarTipoALaBaseDeDatos(Type tipo) {
        dao.addType(tipo);
    }

    public LiveData<List<Type>> obtenerTiposDePokemonDeLaBaseDeDatos(int idPokemon) {
        return dao.getTypesFromDatabase(idPokemon);
    }

    //PokeInfo_Type
    public void agregarRelacionPokeInfoTipo(PokeInfo_Type pokeInfo_type) {
        dao.addRelationPokeInfoType(pokeInfo_type);
    }


    public void agregarHabilidadALaBD(Ability ability) {
        dao.addAbility(ability);
    }

    public LiveData<List<Ability>> obtenerHabilidadesDePokemonDeLaBaseDeDatos(int idPokemon) {
        return dao.getAbilitiesFromDatabase(idPokemon);
    }


    public void agregarRelacionPokeInfoHabilidad(PokeInfo_Ability pokeInfo_ability) {
        dao.addRelationPokeInfoAbility(pokeInfo_ability);
    }


    public void agregarRelacionPokemonFavorito(Pokemon_Favorite pokemonFavorite) {
        dao.addRelationPokemonFavorite(pokemonFavorite);
    }

    public int esPokemonFavorito(int id) {
        return dao.getIsPokemonFavorite(id);
    }

    public LiveData<List<Pokemon>> buscarPokemonsFavoritosEnLaBD(String busca) {
        return dao.searchFavoritePokemonsDoDatabase(busca);
    }
}
