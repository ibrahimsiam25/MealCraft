package com.siam.mealcraft.data.models.fav;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.siam.mealcraft.data.models.meal.MealEntity;


public class FavouriteWithMeal {
    @Embedded
    public FavouriteEntity favourite;

    @Relation(
            parentColumn = "mealId",
            entityColumn = "id"
    )
    public MealEntity meal;
}