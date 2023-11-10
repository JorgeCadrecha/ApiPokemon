package com.example.apipokemon.recyclerview.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.apipokemon.R;
import com.example.apipokemon.pokedex.database.pokeinfo.tables.Ability;
import com.example.apipokemon.pokedex.entities.PokemonInfo;
import com.example.apipokemon.util.Constantes;

import java.util.ArrayList;

public class PokemonAbilityAdapter extends RecyclerView.Adapter<PokemonAbilityAdapter.ViewHolderAbility> {
    private ArrayList<PokemonInfo.Ability> listaDeHabilidades;
    private ArrayList<Ability> listaDeHabilidadesBD;
    private Activity activity;
    private Context contexto;

    public PokemonAbilityAdapter(Activity activity) {
        this.activity = activity;
        this.contexto = activity.getApplicationContext();
        this.listaDeHabilidades = new ArrayList<>();
        this.listaDeHabilidadesBD = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolderAbility onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_info, parent, false);
        return new ViewHolderAbility(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAbility holder, int position) {
        if (listaDeHabilidades.isEmpty())
            holder.abilityTextView.setText(listaDeHabilidadesBD.get(position).getNomeAbility().toUpperCase());
        else
            holder.abilityTextView.setText(listaDeHabilidades.get(position).getAbilityInfo().getName().toUpperCase());
    }

    @Override
    public int getItemCount() {
        if (listaDeHabilidades.isEmpty())
            return listaDeHabilidadesBD.size();
        return listaDeHabilidades.size();
    }

    public void agregarListaHabilidades(ArrayList<PokemonInfo.Ability> lista) {
        if (lista != null) {
            this.listaDeHabilidades.addAll(lista);
            notifyDataSetChanged();
        } else {
            Log.i(Constantes.Error_pokedex, lista.toString());
        }
    }

    public void agregarListaDeBaseDeDatos(ArrayList<Ability> lista) {
        if (lista != null) {
            this.listaDeHabilidadesBD.addAll(lista);
            notifyDataSetChanged();
        } else {
            Log.i(Constantes.Error_pokedex, lista.toString());
        }
    }

    class ViewHolderAbility extends RecyclerView.ViewHolder {
        private TextView abilityTextView;

        public ViewHolderAbility(View view) {
            super(view);
            abilityTextView = view.findViewById(R.id.item_topic_texto);
        }
    }
}