package com.siam.mealcraft.data.datasource.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;


import com.siam.mealcraft.data.models.fav.FavouriteEntity;
import com.siam.mealcraft.data.models.fav.FavouriteWithMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(FavouriteEntity favourite);

    @Transaction
    @Query("SELECT * FROM favourites ORDER BY timestamp DESC")
    Observable<List<FavouriteWithMeal>> getAllFavouritesWithMeals();

    @Query("SELECT EXISTS(SELECT 1 FROM favourites WHERE mealId = :mealId)")
    Single<Boolean> isFavourite(String mealId);

    @Query("DELETE FROM favourites WHERE mealId = :mealId")
    Completable deleteByMealId(String mealId);
}