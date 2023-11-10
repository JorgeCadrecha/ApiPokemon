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
import com.example.apipokemon.pokedex.database.PokedexViewModel;
import com.example.apipokemon.pokedex.entities.Pokemon;
import com.example.apipokemon.recyclerview.adapter.ListaPokemonAdapter;
import com.example.apipokemon.util.ConstantUtil;
import com.example.apipokemon.util.animation.LoadingAnimation;


import java.util.ArrayList;

public class ListaFavoritosFragment extends Fragment {
    private View vista;
    private RecyclerView listaPokemonsRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private ListaPokemonAdapter adapter;
    private PokedexViewModel viewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean buscandoPokemons = false;
    private String ultimaBusca = "";
    private TextView flechaAtras;

    public ListaFavoritosFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_lista_pokemon, container, false);
        adapter = new ListaPokemonAdapter(getActivity());
        viewModel = ViewModelProviders.of(this).get(PokedexViewModel.class);

        crearAnimacionCargando();
        buscarListaFavoritos();
        crearRecyclerView();
        crearRefreshLayout();
        return vista;
    }

    @Override
    public void onResume() {
        super.onResume();
        buscarListaFavoritos();
    }

    private void crearRefreshLayout() {
        swipeRefreshLayout = vista.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            listaPokemonsRecyclerView.setVisibility(View.INVISIBLE);
            scrollUp();
            crearAnimacionCargando();
            listaPokemonsRecyclerView.setVisibility(View.VISIBLE);
            if (buscandoPokemons)
                buscaLosFavoritos(ultimaBusca);
            else
                buscarListaFavoritos();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void crearAnimacionCargando() {
        LoadingAnimation.createAnimation(vista);
    }

    private void buscarListaFavoritos() {
        buscandoPokemons = false;
        adapter.limpiarLista();
        viewModel.getFavoritePokemons().observe(getActivity(), lista -> {
            adapter.agregarLista((ArrayList<Pokemon>) lista);
        });
    }

    private void crearRecyclerView() {
        listaPokemonsRecyclerView = vista.findViewById(R.id.pokemon_recyclerview);
        listaPokemonsRecyclerView.setAdapter(adapter);
        listaPokemonsRecyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        listaPokemonsRecyclerView.setLayoutManager(gridLayoutManager);
    }

    public void buscaLosFavoritos(String busca) {
        ultimaBusca = busca;
        viewModel.searchFavoritePokemons(busca).observe(getActivity(), lista -> {
            adapter.limpiarLista();
            buscandoPokemons = true;
            if (lista.size() > 0) {
                adapter.agregarListaDeBusqueda((ArrayList<Pokemon>) lista);
                flechaAtras.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(getActivity(), ConstantUtil.Pokemon_no_encontrado, Toast.LENGTH_SHORT).show();
                flechaAtras.setVisibility(View.GONE);
            }
        });
    }

    public void retornaAListaPrincipal() {
        adapter.limpiarLista();
        buscarListaFavoritos();
        ultimaBusca = "";
        Toast.makeText(getContext(), ConstantUtil.Volver, Toast.LENGTH_SHORT).show();
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