package com.example.apipokemon.util.animation;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;

import com.example.apipokemon.R;

public class Animacion {
    private static ProgressBar spinner;

    public static void createAnimation(View vi) {
        spinner = vi.findViewById(R.id.spinner_lista_pokemon);
        spinner.setVisibility(View.VISIBLE);

        final Handler handler = new Handler(Looper.getMainLooper());
        ProgressBar finalSpinner = spinner;
        handler.postDelayed(() -> finalSpinner.setVisibility(View.GONE), 300);
    }
}
