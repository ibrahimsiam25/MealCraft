package com.siam.mealcraft.data.repo;

import android.content.Context;

import com.siam.mealcraft.data.datasource.local.MealLocalDataSource;
import com.siam.mealcraft.data.datasource.remote.MealRemoteDataSource;
import com.siam.mealcraft.data.models.category.CategoryDto;
import com.siam.mealcraft.data.models.fav.FavouriteWithMeal;
import com.siam.mealcraft.data.models.meal.FilteredMealsResponse;
import com.siam.mealcraft.data.models.meal.MealDto;
import com.siam.mealcraft.data.models.meal.MealEntity;
import com.siam.mealcraft.data.models.meal.MealsResponse;

import java.util.List;
import java.util.HashSet;
import java.util.Set;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
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

    public Single<MealsResponse> getMealDetails(String mealId) {
        return remoteDataSource.getMealDetails(mealId).subscribeOn(Schedulers.io());
    }

    public Single<FilteredMealsResponse> filterMealsByCategory(String category) {
        return remoteDataSource.filterMealsByCategory(category).subscribeOn(Schedulers.io());
    }

    public Single<MealsResponse> searchMealsByName(String name) {
        return remoteDataSource.searchMealsByName(name).subscribeOn(Schedulers.io());
    }

    public Completable insertMeal(MealEntity meal) {
        return localDataSource.insertMeal(meal).subscribeOn(Schedulers.io());
    }

    public Completable addToFavourites(String mealId) {
        return localDataSource.addToFavourites(mealId).subscribeOn(Schedulers.io());
    }

    public Completable removeFromFavourites(String mealId) {
        return localDataSource.removeFromFavourites(mealId).subscribeOn(Schedulers.io());
    }

    public Single<Boolean> isFavourite(String mealId) {
        return localDataSource.isFavourite(mealId).subscribeOn(Schedulers.io());
    }

    public Observable<List<FavouriteWithMeal>> getAllFavourites() {
        return localDataSource.getAllFavourites().subscribeOn(Schedulers.io());
    }

    public Completable syncFavourites( ) {
        return localDataSource.getAllFavourites()
                .firstOrError()
                .flatMapCompletable(favourites -> {
                    Set<String> ids = new HashSet<>();
                    for (FavouriteWithMeal fav : favourites) {
                        ids.add(fav.favourite.getMealId());
                    }
                    FavouriteStateManager.getInstance().updateFavouriteIds(ids);
                    return Completable.complete();
                })
                .subscribeOn(Schedulers.io());
    }
}

