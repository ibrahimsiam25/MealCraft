package com.siam.mealcraft.presentation.category_meals;

import com.siam.mealcraft.data.models.meal.FilteredMeal;
import java.util.List;

public interface ICategoryMealsView {
    void showMeals(List<FilteredMeal> meals);
    void showError(String error);
    void showLoading();
    void hideLoading();
}
