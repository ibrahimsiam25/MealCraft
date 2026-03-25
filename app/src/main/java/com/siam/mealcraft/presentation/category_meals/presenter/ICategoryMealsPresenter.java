package com.siam.mealcraft.presentation.category_meals.presenter;

import com.siam.mealcraft.data.models.meal.FilteredMeal;

public interface ICategoryMealsPresenter {
    void loadMeals(String category);

    void onDestroy();
}
