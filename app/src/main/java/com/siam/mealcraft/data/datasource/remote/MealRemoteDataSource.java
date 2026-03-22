package com.siam.mealcraft.data.datasource.remote;


import android.content.Context;
import com.siam.mealcraft.data.models.category.CategoriesResponse;
import com.siam.mealcraft.data.models.meal.MealsResponse;
import com.siam.mealcraft.network.Network;
import io.reactivex.rxjava3.core.Single;

public class MealRemoteDataSource {

    private final MealService mealService;


    public MealRemoteDataSource(Context ctx) {
        this.mealService = Network.getInstance(ctx).getHomeService();
    }

    public Single<MealsResponse> getRandomMeal() {
        return mealService.getRandomMeal();
    }

    public Single<CategoriesResponse> getCategories() {
        return mealService.getCategories();
    }


}
