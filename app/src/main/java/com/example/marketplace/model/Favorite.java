package com.example.marketplace.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.marketplace.FavoriteActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Favorite {


    private static Favorite favorite;
    private List<Product> favorites;



    public static Favorite getInstance(){
        if(favorite == null)
            favorite = new Favorite();
        return favorite;
    }
    public Favorite(){
        favorites = new ArrayList<>();
    }

    public List<Product> getFavorites() {
        return favorites;
    }

    public void addFavorites(Product product) {
        if(!favorites.contains(product))
            favorites.add(product);
    }
    public void removeFavorites(Product product) {
        if (favorites.contains(product))
            favorites.remove(product);
    }




}
