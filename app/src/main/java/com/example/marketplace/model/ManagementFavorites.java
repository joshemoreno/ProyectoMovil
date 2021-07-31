package com.example.marketplace.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class ManagementFavorites {

    private static final String FAVORITES_PREFERENCES = "FavoritePreferences";
    private static final String KEY_FAVORITES = "FavoritesKey";
    public static void saveFavorites(Favorite favorite, Context context){
        SharedPreferences preferences = context.getSharedPreferences(FAVORITES_PREFERENCES, Context.MODE_PRIVATE);
        preferences.edit().putString(KEY_FAVORITES,new Gson().toJson(favorite)).commit();
    }

    public static Favorite getFavorite(Context context){
        SharedPreferences jsonPreferences = context.getSharedPreferences(FAVORITES_PREFERENCES, Context.MODE_PRIVATE);
        return jsonPreferences.contains(KEY_FAVORITES) ?
                new Gson().fromJson( jsonPreferences.getString(KEY_FAVORITES,"{}"), Favorite.class) :
                Favorite.getInstance() ;
    }
}
