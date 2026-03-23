package com.siam.mealcraft.data.repo;


import android.content.Context;

import com.siam.mealcraft.data.datasource.local.MealLocalDataSource;
import com.siam.mealcraft.data.datasource.remote.MealRemoteDataSource;
import com.siam.mealcraft.data.models.category.CategoryDto;
import com.siam.mealcraft.data.models.meal.MealDto;

import java.util.List;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsRepo {

    private final MealRemoteDataSource remoteDataSource;
    private final MealLocalDataSource localDataSource;

    public MealsRepo(Context ctx) {
        this.remoteDataSource = new MealRemoteDataSource(ctx);
        this.localDataSource = new MealLocalDataSource(ctx);
    }

    public Single<List<MealDto>> getRandomMeal() {
        return remoteDataSource.getRandomMeal()
                .subscribeOn(Schedulers.io())
                .map(response -> response.meals);
    }

    public Single<List<CategoryDto>> getCategories() {
        return remoteDataSource.getCategories()
                .subscribeOn(Schedulers.io())
                .map(response -> response.categories);
    }
    

}
