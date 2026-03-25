package com.siam.mealcraft.data.datasource.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.siam.mealcraft.data.models.meal.MealEntity;



import io.reactivex.rxjava3.core.Completable;

@Dao
public interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeal(MealEntity meal);

    @Delete
    Completable delete(MealEntity meal);
}