package com.example.apipokemon.ui.activity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import com.example.apipokemon.R;
import com.example.apipokemon.pokedex.api.PokedexApiService;
import com.example.apipokemon.pokedex.api.PokedexRetrofitResponse;
import com.example.apipokemon.pokedex.api.PokemonRetrofit;
import com.example.apipokemon.pokedex.database.PokedexViewModel;
import com.example.apipokemon.pokedex.database.pokeinfo.tables.Ability;
import com.example.apipokemon.pokedex.database.pokeinfo.tables.PokeInfo;
import com.example.apipokemon.pokedex.database.pokeinfo.tables.Pokemon_Favorite;
import com.example.apipokemon.pokedex.database.pokeinfo.tables.Type;
import com.example.apipokemon.pokedex.entities.Pokemon;
import com.example.apipokemon.pokedex.entities.PokemonInfo;
import com.example.apipokemon.recyclerview.adapter.PokemonAbilityAdapter;
import com.example.apipokemon.recyclerview.adapter.PokemonTypeAdapter;
import com.example.apipokemon.util.ColorUtil;
import com.example.apipokemon.util.ConstantUtil;
import com.example.apipokemon.util.FormatUtil;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoPokemonActivity extends AppCompatActivity {

    private static final String APP_TITLE_INFO = "Sobre el pokémon";
    private ProgressBar spinner;
    private TextView nombrePokemon, pesoAlturaPokemon, noConnection;
    private RecyclerView habilidadesRecyclerview, tiposRecyclerview;
    private ImageView imagemPokemon, backgroundTheme;
    private ToggleButton iconoFavorito;
    private PokemonRetrofit retrofit;
    private int pokemonId;
    private PokemonAbilityAdapter adaptadorHabilidad;
    private PokemonTypeAdapter adaptadorTipo;
    private PokedexViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pokemon);
        setTitle(APP_TITLE_INFO);
        crearAnimacionDeCarga();
        getViews();
        getRetrofit();
        getPokemon();
    }

    private void crearAnimacionDeCarga() {
        spinner = findViewById(R.id.spinner);
        spinner.setVisibility(View.VISIBLE);
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> spinner.setVisibility(View.GONE), 300);
    }

    private void getRetrofit() {
        viewModel = new PokedexViewModel(getApplication());
        retrofit = new PokemonRetrofit();
        new PokedexRetrofitResponse(this);
        retrofit.createRetrofit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_item_arrow_back) finish();
        return super.onOptionsItemSelected(item);
    }

    private void getPokemon() {
        if (getIntent().getSerializableExtra("pokemons_extras") != null) {
            Pokemon pokemon = (Pokemon) getIntent().getSerializableExtra("pokemons_extras");
            nombrePokemon.setText(pokemon.getName().toUpperCase());
            pokemonId = pokemon.getNumber();
            estadoFavorito();
            setFotoPokemon();
            getInfoApi();
            toggleFavoriteIcon();
        } else
            Toast.makeText(this, ConstantUtil.Pokemon_no_encontrado, Toast.LENGTH_SHORT).show();
    }

    private void establecerFondo(int numero) {
        int backgroundColor = ColorUtil.getBackgroundColor(numero);
        backgroundTheme.getBackground().setColorFilter(backgroundColor, PorterDuff.Mode.DARKEN);
    }

    private void estadoFavorito() {
        try {
            if (viewModel.getIsPokemonFavorite(pokemonId) == 1)
                iconoFavorito.setChecked(true);
        } catch (IndexOutOfBoundsException ie) {
            viewModel.addRelationPokemonFavorite(new Pokemon_Favorite(pokemonId, 0));
            iconoFavorito.setChecked(false);
        }
    }

    private void setFotoPokemon() {
        Glide.with(this)
                .load(ConstantUtil.IMAGE_BASE_URL + pokemonId + ".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imagemPokemon);
    }

    private void getViews() {
        backgroundTheme = findViewById(R.id.info_background_theme);

        noConnection = findViewById(R.id.info_sem_conexao);
        noConnection.setVisibility(View.GONE);

        nombrePokemon = findViewById(R.id.placeholder_nome_pokemon);
        pesoAlturaPokemon = findViewById(R.id.info_peso_altura);
        imagemPokemon = findViewById(R.id.info_imagem_pokemon);

        iconoFavorito = findViewById(R.id.info_favorito);
        createRecyclerViews();
    }

    private void createRecyclerViews() {
        LinearLayoutManager linearLayoutManager;
        GridLayoutManager gridLayoutManager;

        adaptadorHabilidad = new PokemonAbilityAdapter(this);
        habilidadesRecyclerview = findViewById(R.id.info_recyclerview_ability);
        habilidadesRecyclerview.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        habilidadesRecyclerview.setLayoutManager(linearLayoutManager);
        habilidadesRecyclerview.setAdapter(adaptadorHabilidad);

        adaptadorTipo = new PokemonTypeAdapter();
        tiposRecyclerview = findViewById(R.id.info_pokemon_type);
        tiposRecyclerview.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(this, 2);
        tiposRecyclerview.setLayoutManager(gridLayoutManager);
        tiposRecyclerview.setAdapter(adaptadorTipo);
    }

    private void getInfoApi() {
        PokedexApiService apiService = retrofit.getRetrofit().create(PokedexApiService.class);
        Call<PokemonInfo> callback = apiService.getInfoPokemon(pokemonId);
        callback.enqueue(new Callback<PokemonInfo>() {
            @Override
            public void onResponse(Call<PokemonInfo> call, Response<PokemonInfo> response) {
                if (response.isSuccessful()) {
                    PokemonInfo body = setPokemonInfo(response);
                    PokedexRetrofitResponse.setInfoResponseSuccess(body);
                    establecerFondo(body.getTypes().get(0).getType().getNumber());

                } else {
                    PokeInfo info = getPokeInfo();
                    setInfoFromDatabase(info);
                }
            }

            @Override
            public void onFailure(Call<PokemonInfo> call, Throwable t) {
                PokeInfo info = getPokeInfo();
                setInfoFromDatabase(info);
            }
        });
        spinner.setVisibility(View.GONE);
    }

    private PokeInfo getPokeInfo() {
        Toast.makeText(InfoPokemonActivity.this, ConstantUtil.Sin_conexion, Toast.LENGTH_SHORT).show();
        noConnection.setVisibility(View.VISIBLE);
        return viewModel.getPokeInfoFromDatabase(pokemonId);
    }

    @NonNull
    private PokemonInfo setPokemonInfo(Response<PokemonInfo> response) {
        PokemonInfo body = response.body();
        String pesoAltura = "Peso: " + FormatUtil.formatoInfo(body.getWeight(), "kg")
                + " - Altura: " + FormatUtil.formatoInfo(body.getAltura(), "m");
        adaptadorHabilidad.agregarListaHabilidades((ArrayList<PokemonInfo.Ability>) body.getAbilities());
        adaptadorTipo.agregarLista((ArrayList<PokemonInfo.Types>) body.getTypes());
        pesoAlturaPokemon.setText(pesoAltura);
        return body;
    }

    private void setInfoFromDatabase(PokeInfo info) {
        try {
            String pesoAltura = "Peso: " + FormatUtil.formatoInfo(info.getWeight(), "kg")
                    + " - Altura: " + FormatUtil.formatoInfo(info.getHeight(), "m");
            pesoAlturaPokemon.setText(pesoAltura);

            viewModel.getPokemonTypesFromDatabase(info.getId()).observe(this, lista -> {
                adaptadorTipo.agregarListaDeBaseDeDatos((ArrayList<Type>) lista);
                establecerFondo(lista.get(0).getIdTipo());
            });

            viewModel.getPokemonAbilitiesFromDatabase(info.getId()).observe(this, lista -> {
                adaptadorHabilidad.agregarListaDeBaseDeDatos((ArrayList<Ability>) lista);
            });
        } catch (NullPointerException nu) {
            Log.i("Pokedex Null", "setInfoPesoAltura: no exite el pokemon en la base de datos" + nombrePokemon.getText());
            Toast.makeText(this, ConstantUtil.Error_De_Carga, Toast.LENGTH_SHORT).show();
        }
    }

    public void toggleFavoriteIcon() {
        iconoFavorito.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                viewModel.addRelationPokemonFavorite(new Pokemon_Favorite(pokemonId, 1));
            } else {
                viewModel.addRelationPokemonFavorite(new Pokemon_Favorite(pokemonId, 0));
            }
        });
    }

    public void finishBtn(View view) {
        finish();
    }
}