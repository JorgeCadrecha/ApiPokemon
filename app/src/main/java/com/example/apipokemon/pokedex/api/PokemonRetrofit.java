package com.example.apipokemon.pokedex.api;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.example.apipokemon.pokedex.callback.PokemonCallBack;

import com.example.apipokemon.recyclerview.adapter.ListaPokemonAdapter;
import com.example.apipokemon.util.ConstantUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonRetrofit {
    private static Retrofit retrofit;
    private static PokedexApiService apiService;

    public void createRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void getListaDaAPI(int offset, ListaPokemonAdapter adapter) {
        apiService = retrofit.create(PokedexApiService.class);
        Call<PokemonCallBack> callback = apiService.getListaPokemons(20, offset);
        callback.enqueue(new Callback<PokemonCallBack>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<PokemonCallBack> call, Response<PokemonCallBack> response) {
                if (response.isSuccessful()) //busca lista da API e insere no adapter
                    PokedexRetrofitResponse.setOnSuccessResponse(response, adapter);
                else  //busca lista do banco de dados e insere no adapter
                    PokedexRetrofitResponse.setOnErrorResponse(adapter, ConstantUtil.Error_De_Carga);
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<PokemonCallBack> call, Throwable t) { //busca lista do banco de dados e insere no adapter
                PokedexRetrofitResponse.setOnErrorResponse(adapter, ConstantUtil.Sin_conexion);
            }
        });
    }

    public void getBusca(int offset, ListaPokemonAdapter adapter, String busca, TextView arrowBack) {
        apiService = retrofit.create(PokedexApiService.class);
        Call<PokemonCallBack> callback = apiService.getListaPokemons(15000, offset);
        callback.enqueue(new Callback<PokemonCallBack>() {
            @Override
            public void onResponse(Call<PokemonCallBack> call, Response<PokemonCallBack> response) {
                if (response.isSuccessful()) {
                    PokedexRetrofitResponse.setOnSuccessSearchResponse(response, busca, adapter, arrowBack);
                } else {
                    PokedexRetrofitResponse.setOnErrorSearchResponse(busca, adapter, arrowBack);
                }
            }
            @Override
            public void onFailure(Call<PokemonCallBack> call, Throwable t) {
                PokedexRetrofitResponse.setOnErrorSearchResponse(busca, adapter, arrowBack);
            }
        });
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public boolean isConnected() {
        return PokedexRetrofitResponse.isResponseState();
    }
}