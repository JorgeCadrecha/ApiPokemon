package com.example.apipokemon.pokedex.api;


import com.example.apipokemon.pokedex.callback.PokemonCallBack;
import com.example.apipokemon.pokedex.entities.PokemonInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokedexApiService {

    @GET("pokemon")
    Call<PokemonCallBack> getListaPokemons(@Query("limit") int limit, @Query("offset") int offset);

    @GET("pokemon/{url}")
    Call<PokemonInfo> getInfoPokemon(@Path("url") int url);
}