package pt.pepedevils.tme.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;

import pt.pepedevils.tme.model.Jogador;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by dezvezesdez on 26/05/2017.
 */

public class Helper {

    public static void setListOnSharedPreferences(Activity a, ArrayList<Jogador> jogadores_){
        SharedPreferences mPrefs = a.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();

        for (int i = 0; i < jogadores_.size(); i++) {
            Jogador jog = jogadores_.get(i);
            Gson gson = new Gson();
            String json = gson.toJson(jog);
            prefsEditor.putString("jogador_" + i, json);
        }
        prefsEditor.apply();
    }


    public static ArrayList<Jogador> getListOnSharedPreferences(Activity a){
        ArrayList<Jogador> res = new ArrayList<>();
        SharedPreferences mPrefs = a.getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        for (int i = 0; i < 10; i++) {
            String json = mPrefs.getString("jogador_" + i, "");
            Jogador obj = gson.fromJson(json, Jogador.class);
            res.add(obj);
        }

        return res;
    }


}
