package com.example.apipokemon.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.apipokemon.R;
import com.example.apipokemon.pokedex.api.PokedexRetrofitResponse;
import com.example.apipokemon.pokedex.api.PokemonRetrofit;
import com.example.apipokemon.pokedex.database.PokedexViewModel;
import com.example.apipokemon.recyclerview.adapter.ListaPokemonAdapter;
import com.example.apipokemon.util.Constantes;
import com.example.apipokemon.util.animation.Animacion;


public class ListaPokemonFragment extends Fragment {
    private View vista;
    private RecyclerView listaPokemonsRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private PokemonRetrofit retrofit;
    private ListaPokemonAdapter adapter;
    private PokedexViewModel viewModel;
    private boolean seDesplazoAlFinal;
    private boolean buscandoPokemons = false;
    private int offset;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String ultimaBusca = "";
    private TextView flechaAtras;

    public ListaPokemonFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_lista_pokemon, container, false);
        crearAnimacionCargando();
        crearRetrofit();
        crearRecyclerView();
        crearListenerDeDesplazamiento();
        crearRefreshLayout();
        return vista;
    }

    private void crearAnimacionCargando() {
        Animacion.createAnimation(vista);
    }

    private void crearRefreshLayout() {
        swipeRefreshLayout = vista.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            listaPokemonsRecyclerView.setVisibility(View.INVISIBLE);
            scrollUp();
            crearAnimacionCargando();
            adapter.limpiarLista();
            listaPokemonsRecyclerView.setVisibility(View.VISIBLE);
            offset = 0;
            if (buscandoPokemons)
                buscarPokemon(ultimaBusca);
            else
                obtenerInformacion(offset);
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void crearRetrofit() {
        adapter = new ListaPokemonAdapter(getActivity());
        viewModel = ViewModelProviders.of(this).get(PokedexViewModel.class);
        retrofit = new PokemonRetrofit();
        retrofit.createRetrofit();
        new PokedexRetrofitResponse(viewModel, getActivity());
        seDesplazoAlFinal = true;
        offset = 0;
        obtenerInformacion(offset);
    }

    private void crearRecyclerView() {
        listaPokemonsRecyclerView = vista.findViewById(R.id.pokemon_recyclerview);
        listaPokemonsRecyclerView.setAdapter(adapter);
        listaPokemonsRecyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        listaPokemonsRecyclerView.setLayoutManager(gridLayoutManager);
    }

    private void crearListenerDeDesplazamiento() {
        listaPokemonsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int itemVisivelCount = gridLayoutManager.getChildCount();
                    int totalItemCount = gridLayoutManager.getItemCount();
                    int ultimoItemVisivel = gridLayoutManager.findFirstVisibleItemPosition();
                    if ((itemVisivelCount + ultimoItemVisivel) >= totalItemCount) {
                        seDesplazoAlFinal = false;
                        if (!buscandoPokemons && retrofit.isConnected()) {
                            offset += 20;
                            obtenerInformacion(offset);
                        }
                    }
                }
            }
        });
    }

    private void obtenerInformacion(int offset) {
        seDesplazoAlFinal = true;
        retrofit.getListaDaAPI(offset, adapter);
        buscandoPokemons = false;
    }

    public void buscarPokemon(String busca) {
        crearAnimacionCargando();
        ultimaBusca = busca;
        retrofit.getBusca(offset, adapter, busca, flechaAtras);
        buscandoPokemons = true;
    }

    public void retornaAListaPrincipal() {
        adapter.limpiarLista();
        offset = 0;
        obtenerInformacion(offset);
        ultimaBusca = "";
        Toast.makeText(getContext(), Constantes.Volver, Toast.LENGTH_SHORT).show();
    }

    public void scrollUp() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) listaPokemonsRecyclerView.getLayoutManager();
        layoutManager.smoothScrollToPosition(listaPokemonsRecyclerView, null, 0);
    }

    public boolean buscandoPokemons() {
        return buscandoPokemons;
    }

    public void setFlechaAtras(TextView flechaAtras) {
        this.flechaAtras = flechaAtras;
    }
}