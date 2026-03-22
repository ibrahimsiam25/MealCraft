package com.siam.mealcraft.data.datasource.local;

import android.content.Context;


import com.siam.mealcraft.data.models.fav.FavouriteEntity;
import com.siam.mealcraft.data.models.fav.FavouriteWithMeal;
import com.siam.mealcraft.data.models.meal.MealEntity;
import com.siam.mealcraft.db.AppDatabase;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class MealLocalDataSource {
    private final MealDao mealDao;
    private final FavouriteDao favouriteDao;

    public MealLocalDataSource(Context ctx) {
        AppDatabase db = AppDatabase.getInstance(ctx);
        this.mealDao = db.mealDao();
        this.favouriteDao = db.favouriteDao();
    }

    public Completable insertMeal(MealEntity meal) {
        return mealDao.insertMeal(meal);
    }

    public Completable deleteMeal(MealEntity meal) {
        return mealDao.delete(meal);
    }

    public Completable addToFavourites(String mealId) {
        FavouriteEntity entity = new FavouriteEntity(mealId, System.currentTimeMillis());
        return favouriteDao.insert(entity);
    }

    public Completable removeFromFavourites(String mealId) {
        return favouriteDao.deleteByMealId(mealId);
    }

    public Single<Boolean> isFavourite(String mealId) {
        return favouriteDao.isFavourite(mealId);
    }

    public Observable<List<FavouriteWithMeal>> getAllFavourites() {
        return favouriteDao.getAllFavouritesWithMeals();
    }
}