package com.example.apipokemon.pokedex.api;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apipokemon.pokedex.callback.PokemonCallBack;
import com.example.apipokemon.pokedex.database.PokedexViewModel;
import com.example.apipokemon.pokedex.database.pokeinfo.tables.Ability;
import com.example.apipokemon.pokedex.database.pokeinfo.tables.PokeInfo;
import com.example.apipokemon.pokedex.database.pokeinfo.tables.PokeInfo_Ability;
import com.example.apipokemon.pokedex.database.pokeinfo.tables.PokeInfo_Type;
import com.example.apipokemon.pokedex.database.pokeinfo.tables.Type;
import com.example.apipokemon.pokedex.entities.Pokemon;
import com.example.apipokemon.pokedex.entities.PokemonInfo;
import com.example.apipokemon.recyclerview.adapter.ListaPokemonAdapter;
import com.example.apipokemon.util.ConstantUtil;
import java.util.ArrayList;

import retrofit2.Response;


public class PokedexRetrofitResponse {
    private static boolean responseState;
    private static PokedexViewModel viewModel;
    private static Context context;
    private Activity activity;

    public PokedexRetrofitResponse(PokedexViewModel viewModel, Activity activity) {
        this.viewModel = viewModel;
        this.activity = activity;
        this.context = activity.getApplicationContext();
    }

    public PokedexRetrofitResponse(Activity activity) { //Ao mudar de activity, mudamos sua referencia trabalhada no PRR.
        //Não é necessário instanciar outra referência do mesmo viewModel.
        this.activity = activity;
        this.context = activity.getApplicationContext();
    }

    public static void setOnErrorResponse(ListaPokemonAdapter adapter, String noConnection) {
        responseState = false;
        Toast.makeText(context, noConnection, Toast.LENGTH_LONG).show();
        adapter.agregarLista((ArrayList<Pokemon>) viewModel.obtenerPokemonsDeLaBadeDeDatos());
    }

    public static void setOnSuccessResponse(Response<PokemonCallBack> response, ListaPokemonAdapter adapter) {
        responseState = true;
        PokemonCallBack body = response.body();
        ArrayList<Pokemon> result = body.getResults();
        adapter.agregarLista(result);
        viewModel.agregarTodosLosPokemonsALaBD(result);
    }

    public static void setOnSuccessSearchResponse(Response<PokemonCallBack> response, String busca, ListaPokemonAdapter adapter, TextView arrowBack) {
        responseState = true;
        PokemonCallBack body = response.body();
        ArrayList<Pokemon> result = body.getResults();
        ArrayList<Pokemon> searchResult = new ArrayList<>();
        for (Pokemon p : result) {
            if (p.getName().contains(busca.toLowerCase()))
                searchResult.add(p);
        }
        if (searchResult.size() > 0) {
            adapter.agregarListaDeBusqueda(searchResult);
            arrowBack.setVisibility(View.VISIBLE);
            viewModel.agregarTodosLosPokemonsALaBD(searchResult);
        } else {
            Toast.makeText(context, ConstantUtil.Pokemon_no_encontrado, Toast.LENGTH_SHORT).show();
            arrowBack.setVisibility(View.GONE);
        }
    }

    public static void setInfoResponseSuccess(PokemonInfo body) {
        //salva info no database
        viewModel.agregarPokemonInfoALaBD(new PokeInfo(body.getId(), body.getAltura(), body.getWeight(), body.getName()));
        //salva tipos e associação info/tipo no database
        for (PokemonInfo.Types type : body.getTypes()) {
            viewModel.agregarTipoALaBaseDeDatos(new Type(type.getType().getNumber(), type.getType().getName()));
            viewModel.agregarRelacionPokeInfoTipo(new PokeInfo_Type(body.getId(), type.getType().getNumber()));
        }
        //salva habilidades e  associação info/habilidade no database
        for (PokemonInfo.Ability ability : body.getAbilities()) {
            viewModel.agregarHabilidadALaBD(new Ability(ability.getAbilityInfo().getNumber(), ability.getAbilityInfo().getName()));
            viewModel.agregarRelacionPokeInfoHabilidad(new PokeInfo_Ability(body.getId(), ability.getAbilityInfo().getNumber()));
        }
    }

    private static void verificaBuscaRetornadaDoBancoDeDados(ArrayList<Pokemon> result, ListaPokemonAdapter adapter, TextView arrowBack) {
        if (result.size() > 0) {
            adapter.agregarListaDeBusqueda(result);
            arrowBack.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(context, ConstantUtil.Pokemon_no_encontrado, Toast.LENGTH_SHORT).show();
            arrowBack.setVisibility(View.GONE);
        }
    }

    public static void setOnErrorSearchResponse(String busca, ListaPokemonAdapter adapter, TextView arrowBack) {
        responseState = false;
        Toast.makeText(context, ConstantUtil.Sin_conexion, Toast.LENGTH_SHORT).show();
        ArrayList<Pokemon> result = (ArrayList<Pokemon>) viewModel.buscarPokemonsFavoritos(busca);
        verificaBuscaRetornadaDoBancoDeDados(result, adapter, arrowBack);
    }

    public static boolean isResponseState() {
        return responseState;
    }
}