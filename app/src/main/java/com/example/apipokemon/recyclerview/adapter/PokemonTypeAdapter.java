package com.example.apipokemon.recyclerview.adapter;


import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.apipokemon.R;
import com.example.apipokemon.pokedex.database.pokeinfo.tables.Type;
import com.example.apipokemon.pokedex.entities.PokemonInfo;
import com.example.apipokemon.util.Colores;
import com.example.apipokemon.util.Constantes;

import java.util.ArrayList;

public class PokemonTypeAdapter extends RecyclerView.Adapter<com.example.apipokemon.recyclerview.adapter.PokemonTypeAdapter.ViewHolder> {
    private ArrayList<PokemonInfo.Types> listaTipo;
    private ArrayList<Type> listaTipoBD;

    public PokemonTypeAdapter() {
        this.listaTipo = new ArrayList<>();
        this.listaTipoBD = new ArrayList<>();
    }

    @NonNull
    @Override
    public PokemonTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_info, parent, false);
        return new PokemonTypeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (listaTipo.isEmpty() || listaTipo == null) {
            obtenerListaDeBaseDeDatos(holder, position);
        } else {
            holder.typeTextView.setText(listaTipo.get(position).getType().getName().toUpperCase());
            int type = listaTipo.get(position).getType().getNumber();
            setColorTipo(holder, type);
        }
    }

    private void obtenerListaDeBaseDeDatos(ViewHolder holder, int posicion) {
        holder.typeTextView.setText(listaTipoBD.get(posicion).getNomeTipo().toUpperCase());
        int type = listaTipoBD.get(posicion).getIdTipo();
        setColorTipo(holder, type);
    }

    @Override
    public int getItemCount() {
        if (listaTipo.isEmpty())
            return listaTipoBD.size();
        return listaTipo.size();
    }

    private void setColorTipo(ViewHolder holder, int tipo) {
        Drawable fondo = holder.typeContainerImageView.getBackground();
        Drawable pokeball = holder.logoImageView.getBackground();
        int viewColor = Colores.getForegroundViewColor(tipo);

        fondo.setColorFilter(Colores.getForegroundViewColor(tipo), PorterDuff.Mode.MULTIPLY);
        holder.typeTextView.setTextColor(viewColor);
        pokeball.setColorFilter(viewColor, PorterDuff.Mode.SRC_IN);
    }

    public void agregarLista(ArrayList<PokemonInfo.Types> lista) {
        if (lista != null) {
            this.listaTipo.addAll(lista);
            notifyDataSetChanged();
        } else {
            Log.i(Constantes.Error_pokedex, lista.toString());
        }
    }

    public void agregarListaDeBaseDeDatos(ArrayList<Type> lista) {
        if (lista != null) {
            this.listaTipoBD.addAll(lista);
            notifyDataSetChanged();
        } else {
            Log.i(Constantes.Error_pokedex, lista.toString());
        }
    }

    public int getTipo() {
        if (listaTipo.isEmpty() && listaTipoBD.isEmpty())
            return 0;
        if (listaTipo.isEmpty())
            return listaTipoBD.get(0).getIdTipo();
        return listaTipo.get(0).getType().getNumber();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView logoImageView, typeContainerImageView;
        private TextView typeTextView;

        public ViewHolder(View view) {
            super(view);
            logoImageView = view.findViewById(R.id.item_topic_vector);
            typeTextView = view.findViewById(R.id.item_topic_texto);
            typeContainerImageView = view.findViewById(R.id.item_topic_container);
        }
    }
}