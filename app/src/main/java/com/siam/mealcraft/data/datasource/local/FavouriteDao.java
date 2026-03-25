package com.siam.mealcraft.data.datasource.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.siam.mealcraft.data.models.meal.FavouriteEntity;
import com.siam.mealcraft.data.models.meal.MealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(FavouriteEntity favourite);

    @Query("SELECT m.* FROM meals m INNER JOIN favourites f ON f.mealId = m.id ORDER BY f.timestamp DESC")
    Observable<List<MealEntity>> getAllFavouritesWithMeals();

    @Query("SELECT EXISTS(SELECT 1 FROM favourites WHERE mealId = :mealId)")
    Single<Boolean> isFavourite(String mealId);

    @Query("DELETE FROM favourites WHERE mealId = :mealId")
    Completable deleteByMealId(String mealId);
}