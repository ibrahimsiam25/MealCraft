package com.siam.mealcraft.network;

import android.content.Context;


import com.siam.mealcraft.data.datasource.remote.MealService;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Network {

    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static Network instance = null;
    private final MealService mealService;

    private Network(Context ctx) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        mealService = retrofit.create(MealService.class);
    }

    public static Network getInstance(Context ctx) {
        if (instance == null) {
            instance = new Network(ctx);
        }
        return instance;
    }

    public MealService getHomeService() {
        return mealService;
    }
}
