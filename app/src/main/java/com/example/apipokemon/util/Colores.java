package com.example.apipokemon.util;

import android.graphics.Color;

public class Colores {

    public static int getBackgroundColor(int type) {
        switch (type) {
            case 1:
                return Color.parseColor(Constantes.NORMAL);
            case 2:
                return Color.parseColor(Constantes.Lucha);
            case 3:
                return Color.parseColor(Constantes.Volador);
            case 4:
                return Color.parseColor(Constantes.Veneno);
            case 5:
                return Color.parseColor(Constantes.Tierra);
            case 6:
                return Color.parseColor(Constantes.Roca);
            case 7:
                return Color.parseColor(Constantes.Bicho);
            case 8:
                return Color.parseColor(Constantes.Fantasma);
            case 9:
                return Color.parseColor(Constantes.Acero);
            case 10:
                return Color.parseColor(Constantes.Fuego);
            case 11:
                return Color.parseColor(Constantes.Agua);
            case 12:
                return Color.parseColor(Constantes.GRASS);
            case 13:
                return Color.parseColor(Constantes.Electrico);
            case 14:
                return Color.parseColor(Constantes.Psyquico);
            case 15:
                return Color.parseColor(Constantes.hielo);
            case 16:
                return Color.parseColor(Constantes.dragon);
            case 17:
                return Color.parseColor(Constantes.Oscuridad);
            case 18:
                return Color.parseColor(Constantes.Hada);
            case 10001:
                return Color.parseColor(Constantes.ni_idea);
            case 10002:
                return Color.parseColor(Constantes.sombra);
            default:
        }
        return Color.CYAN;
    }

    public static int getForegroundViewColor(int type) {
        if (type==1){
            return Color.parseColor(Constantes.NORMAL);
        }
        if (type==2){
            return Color.parseColor(Constantes.Lucha);
        }
        if (type==3){
            return Color.parseColor(Constantes.Volador);
        }
        if (type==4){
            return Color.parseColor(Constantes.Veneno);
        }
        if (type==5){
            return Color.parseColor(Constantes.Tierra);
        }
        if (type==6){
            return Color.parseColor(Constantes.Roca);
        }
        if (type==7){
            return Color.parseColor(Constantes.Bicho);
        }
        if (type==8){
            return Color.parseColor(Constantes.Fantasma);
        }
        if (type==9){
            return Color.parseColor(Constantes.Acero);
        }
        if (type==10){
            return Color.parseColor(Constantes.Fuego);
        }
        if (type==11){
            return Color.parseColor(Constantes.Agua);
        }
        if (type==12){
            return Color.parseColor(Constantes.GRASS);
        }
        if (type==13){
            return Color.parseColor(Constantes.Electrico);
        }
        if (type==14){
            return Color.parseColor(Constantes.Psyquico);
        }
        if (type==15){
            return Color.parseColor(Constantes.hielo);
        }
        if (type==16){
            return Color.parseColor(Constantes.dragon);
        }
        if (type==17){
            return Color.parseColor(Constantes.Oscuridad);
        }
        if (type==18){
            return Color.parseColor(Constantes.Hada);
        }
        if (type==10001){
            return Color.parseColor(Constantes.ni_idea);
        }
        if (type==10002){
            return Color.parseColor(Constantes.sombra);
        }

        return Color.TRANSPARENT;
    }
}
