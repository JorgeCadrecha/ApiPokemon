package com.example.apipokemon.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import com.example.apipokemon.R;
import com.example.apipokemon.recyclerview.adapter.viewPager.ViewPagerAdapter;
import com.example.apipokemon.ui.fragment.ListaFavoritosFragment;
import com.example.apipokemon.ui.fragment.ListaPokemonFragment;
import com.example.apipokemon.util.ConstantUtil;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private EditText pesquisa;
    private TextView arrowBack;
    private ListaPokemonFragment listaPokemonFragment;
    private ListaFavoritosFragment listaFavoritosFragment;
    private int tabIcons[] = {R.drawable.pokeball, R.drawable.ic_favorite};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Pokédex");
        setViews();
        setLayoutManager();
        setArrowBackToFragments();
    }

    private void setViews() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.main_view_pager);
        listaPokemonFragment = new ListaPokemonFragment();
        listaFavoritosFragment = new ListaFavoritosFragment();
    }

    private void setLayoutManager() {
        setupViewPagerAdapter();
        setupTabIcons();
    }

    private void setupViewPagerAdapter() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPagerAdapter.adicionaFragmento(listaPokemonFragment, "Pokémons");
        viewPagerAdapter.adicionaFragmento(listaFavoritosFragment, "Favoritos");
        viewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) ->
                tab.setText(viewPagerAdapter.agregarFragmento(position))).attach();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    private void setArrowBackToFragments() {
        arrowBack = findViewById(R.id.back_to_list);
        listaPokemonFragment.setFlechaAtras(arrowBack);
        listaFavoritosFragment.setFlechaAtras(arrowBack);
    }


    public void buscaPokemon(View view) {
        pesquisa = findViewById(R.id.editTextPesquisaNomePokemon);
        String busca = pesquisa.getText().toString();
        if (!busca.isEmpty()) {
            buscaPokemonporFragmentos(busca);
            return;
        }
        Toast.makeText(this, ConstantUtil.Pokemon_no_encontrado, Toast.LENGTH_SHORT).show();
    }

    private void buscaPokemonporFragmentos(String busca) {
        switch (viewPager.getCurrentItem()) {
            case 0:
                listaPokemonFragment.buscarPokemon(busca);
                break;
            case 1:
                listaFavoritosFragment.buscaLosFavoritos(busca);
                break;
            default:
                Toast.makeText(this, ConstantUtil.Pokemon_no_encontrado, Toast.LENGTH_SHORT);
                arrowBack.setVisibility(View.GONE);
        }
    }

    public void scrollUp(View view) {
        switch (viewPager.getCurrentItem()) {
            case 0:
                listaPokemonFragment.scrollUp();
                break;
            case 1:
                listaFavoritosFragment.scrollUp();
                break;
            default:
                Toast.makeText(this, ConstantUtil.Error_De_Carga, Toast.LENGTH_SHORT);
        }
    }

    public void retornaAListaPrincipal(View view) {
        arrowBack.setVisibility(View.GONE);
        pesquisa.setText("");
        retornaListaPrincipalFragment();
        Toast.makeText(this, ConstantUtil.Volver, Toast.LENGTH_SHORT).show();
    }

    private void retornaListaPrincipalFragment() {
        if (listaPokemonFragment.buscandoPokemons())
            listaPokemonFragment.retornaAListaPrincipal();
        if (listaFavoritosFragment.buscandoPokemons())
            listaFavoritosFragment.retornaAListaPrincipal();
    }
}