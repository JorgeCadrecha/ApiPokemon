package com.example.apipokemon.pokedex.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.apipokemon.pokedex.database.pokeinfo.tables.Ability;
import com.example.apipokemon.pokedex.database.pokeinfo.tables.PokeInfo;
import com.example.apipokemon.pokedex.database.pokeinfo.tables.PokeInfo_Ability;
import com.example.apipokemon.pokedex.database.pokeinfo.tables.PokeInfo_Type;
import com.example.apipokemon.pokedex.database.pokeinfo.tables.Pokemon_Favorite;
import com.example.apipokemon.pokedex.database.pokeinfo.tables.Type;
import com.example.apipokemon.pokedex.entities.Pokemon;

@Database(entities = {Pokemon.class, PokeInfo.class, Type.class, PokeInfo_Type.class,
        Ability.class, PokeInfo_Ability.class, Pokemon_Favorite.class},
        exportSchema = false, version = 8)
public abstract class PokedexDatabase extends RoomDatabase {

    public abstract PokedexDao getPokedexDao();

    public static PokedexDatabase getInstance(Context contexto) {
        return Room.databaseBuilder(contexto, PokedexDatabase.class, "pokedex.db")
                .allowMainThreadQueries()
//                .fallbackToDestructiveMigration()
                .build();
    }
}