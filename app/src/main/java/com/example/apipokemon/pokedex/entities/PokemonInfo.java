package com.example.apipokemon.pokedex.entities;

import com.example.apipokemon.util.FormatUtil;

import java.util.List;

public class PokemonInfo {
    private int altura;
    private List<Ability> abilities;
    private int id;
    private int weight;
    private List<Types> types;
    private String name;

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<Types> getTypes() {
        return types;
    }

    public void setTypes(List<Types> types) {
        this.types = types;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public class Types {
        private int slot;
        private Type type;

        public Type getType() {
            return type;
        }

        public class Type {
            private String name;
            private String url;

            public String getName() {
                return name;
            }

            public int getNumber() {
                return FormatUtil.getUrlAcortada(url);
            }
        }
    }

    public class Ability {
        private int slot;
        private boolean is_hidden;
        private AbilityInfo ability;

        public AbilityInfo getAbilityInfo() {
            return ability;
        }

        public class AbilityInfo {
            private String name;
            private String url;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getNumber() {
                return FormatUtil.getUrlAcortada(url);
            }
        }
    }
}