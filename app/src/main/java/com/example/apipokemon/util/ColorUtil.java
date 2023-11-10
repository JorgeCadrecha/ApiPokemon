package com.example.apipokemon.util;

import android.graphics.Color;

public class ColorUtil {

    public static int getBackgroundColor(int type) {
        switch (type) {
            case 1:
                return Color.parseColor(ConstantUtil.NORMAL);
            case 2:
                return Color.parseColor(ConstantUtil.Lucha);
            case 3:
                return Color.parseColor(ConstantUtil.Volador);
            case 4:
                return Color.parseColor(ConstantUtil.Veneno);
            case 5:
                return Color.parseColor(ConstantUtil.Tierra);
            case 6:
                return Color.parseColor(ConstantUtil.Roca);
            case 7:
                return Color.parseColor(ConstantUtil.Bicho);
            case 8:
                return Color.parseColor(ConstantUtil.Fantasma);
            case 9:
                return Color.parseColor(ConstantUtil.Acero);
            case 10:
                return Color.parseColor(ConstantUtil.Fuego);
            case 11:
                return Color.parseColor(ConstantUtil.Agua);
            case 12:
                return Color.parseColor(ConstantUtil.GRASS);
            case 13:
                return Color.parseColor(ConstantUtil.Electrico);
            case 14:
                return Color.parseColor(ConstantUtil.Psyquico);
            case 15:
                return Color.parseColor(ConstantUtil.hielo);
            case 16:
                return Color.parseColor(ConstantUtil.dragon);
            case 17:
                return Color.parseColor(ConstantUtil.Oscuridad);
            case 18:
                return Color.parseColor(ConstantUtil.Hada);
            case 10001:
                return Color.parseColor(ConstantUtil.ni_idea);
            case 10002:
                return Color.parseColor(ConstantUtil.sombra);
            default:
        }
        return Color.CYAN;
    }

    public static int getForegroundViewColor(int type) {
        if (type == 1 || type == 7 || type == 9 || type == 12 || type == 13 || type == 15) {
            return Color.BLACK;
        }
        return Color.WHITE;
    }
}
