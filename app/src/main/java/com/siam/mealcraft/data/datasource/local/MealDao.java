package com.siam.mealcraft.data.datasource.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.siam.mealcraft.data.models.meal.MealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeal(MealEntity meal);

    @Delete
    Completable delete(MealEntity meal);
}