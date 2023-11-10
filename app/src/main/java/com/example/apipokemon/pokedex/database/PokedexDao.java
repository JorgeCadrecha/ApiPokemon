package com.example.apipokemon.pokedex.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.apipokemon.pokedex.database.pokeinfo.tables.Ability;
import com.example.apipokemon.pokedex.database.pokeinfo.tables.PokeInfo;
import com.example.apipokemon.pokedex.database.pokeinfo.tables.PokeInfo_Ability;
import com.example.apipokemon.pokedex.database.pokeinfo.tables.PokeInfo_Type;
import com.example.apipokemon.pokedex.database.pokeinfo.tables.Pokemon_Favorite;
import com.example.apipokemon.pokedex.database.pokeinfo.tables.Type;
import com.example.apipokemon.pokedex.entities.Pokemon;

import java.util.List;

@Dao
public interface PokedexDao {

    //Pokemon
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void agregarTodosLosPokemons(List<Pokemon> pokemons);

    @Query("SELECT * FROM tb_pokemon")
    List<Pokemon> obtenerPokemonsDeLaBaseDeDatos();

    @Query("SELECT * FROM tb_pokemon WHERE name LIKE '%' || :pokemon || '%'")
    List<Pokemon> buscarPokemonsEnLaBaseDeDatos(String pokemon);

    @Query("SELECT DISTINCT po.number, po.name, po.url FROM tb_pokemon AS po " +
            "INNER JOIN tb_pokemon_favorite AS pf ON po.number = pf.id " +
            "WHERE pf.favorite = 1")
    LiveData<List<Pokemon>> obtenerPokemonsFavoritosDeLaBaseDeDatos();

    //PokeInfo
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void agregarPokeInfo(PokeInfo pokeInfo);

    @Query("SELECT id, name, height, weight FROM tb_pokeInfo WHERE id = :id ")
    PokeInfo obtenerPokeInfoDeLaBaseDeDatos(int id);

    //Type
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void agregarTipo(Type tipo);

    @Query("SELECT DISTINCT ti.nomeTipo, ti.idTipo FROM tb_type AS ti " +
            "INNER JOIN tb_pokeInfo_Type AS pit ON TI.idTipo = PIT.fk_type " +
            "WHERE fk_pokeInfo = :idPokeInfo")
    LiveData<List<Type>> obtenerTiposDeLaBaseDeDatos(int idPokeInfo);

    //PokeInfo_Type
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void agregarRelacionPokeInfoTipo(PokeInfo_Type pokeInfo_type);

    //Ability
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void agregarHabilidad(Ability ability);

    @Query("SELECT DISTINCT ab.id, ab.nomeAbility FROM tb_ability AS ab " +
            "INNER JOIN tb_pokeInfo_Ability AS pia ON ab.id = pia.fk_ability " +
            "WHERE fk_pokeInfo = :idPokeInfo")
    LiveData<List<Ability>> obtenerHabilidadesDeLaBD(int idPokeInfo);

    //PokeInfo_Ability
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void agregarRelacionPokeInfoHabilidad(PokeInfo_Ability pokeInfo_ability);

    //Pokemon_Favorite
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void agregarRelacionPokemonFavorito(Pokemon_Favorite pokemonFavorite);

    @Query("SELECT pf.favorite FROM tb_pokemon_favorite AS pf WHERE pf.id = :id")
    int esPokemonFavorito(int id);

    @Query("SELECT DISTINCT po.number, po.name, po.url FROM tb_pokemon AS po " +
            "INNER JOIN tb_pokemon_favorite AS pf ON po.number = pf.id " +
            "WHERE pf.favorite = 1 AND po.name LIKE '%' || :busca || '%'")
    LiveData<List<Pokemon>> buscarPokemonsFavoritosEnLaBD(String busca);
}