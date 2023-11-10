package com.example.apipokemon.recyclerview.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.apipokemon.R;
import com.example.apipokemon.pokedex.entities.Pokemon;
import com.example.apipokemon.ui.activity.InfoPokemonActivity;
import com.example.apipokemon.util.ConstantUtil;
import com.example.apipokemon.util.FormatUtil;


import java.util.ArrayList;
import java.util.List;

public class ListaPokemonAdapter extends RecyclerView.Adapter<ListaPokemonAdapter.ViewHolder> {

    private List<Pokemon> listaDePokemons;
    private Context context;
    private Activity activity;

    public ListaPokemonAdapter(Activity activity) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.listaDePokemons = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pokemon pokemon = listaDePokemons.get(position);
        holder.nomeView.setText(FormatUtil.getNombrePokemonFormato(pokemon));
        obtenerImagenDesdeApi(holder, pokemon);
        holder.container.setOnClickListener(view -> {
            iniciarActividadInfoPokemon(pokemon);
        });
    }

    private void iniciarActividadInfoPokemon(Pokemon pokemon) {
        Intent intent = new Intent(activity, InfoPokemonActivity.class);
        intent.putExtra(ConstantUtil.Pokemon_extras, pokemon);
        activity.startActivity(intent);
    }

    private void obtenerImagenDesdeApi(@NonNull ViewHolder holder, Pokemon pokemon) {
        Glide.with(context)
                .load(ConstantUtil.IMAGE_BASE_URL + pokemon.getNumber() + ".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imagemView);
    }

    @Override
    public int getItemCount() {
        return listaDePokemons.size();
    }

    public void agregarLista(ArrayList<Pokemon> lista) {
        if (lista != null) {
            this.listaDePokemons.addAll(lista);
            notifyDataSetChanged();
        } else {
            Log.i(ConstantUtil.Error_pokedex, lista.toString());
        }
    }

    public void agregarListaDeBusqueda(ArrayList<Pokemon> lista) {
        limpiarLista();
        agregarLista(lista);
    }

    public void limpiarLista() {
        this.listaDePokemons.removeAll(listaDePokemons);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagemView, container;
        private TextView nomeView;

        public ViewHolder(View view) {
            super(view);
            imagemView = view.findViewById(R.id.item_imagem_pokemon);
            nomeView = view.findViewById(R.id.placeholder_nome_pokemon);
            container = view.findViewById(R.id.item_container_pokemon);
        }
    }
}