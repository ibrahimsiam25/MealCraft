package com.siam.mealcraft.presentation.meal_details;

import com.siam.mealcraft.data.models.meal.MealDto;

public interface IMealDetailsPresenter {
    void loadMealDetails(String mealId);
    void toggleFavourite(MealDto meal);
    void onDestroy();
}
