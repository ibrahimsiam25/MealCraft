package com.siam.mealcraft.presentation.meal_details.views;

import com.siam.mealcraft.data.models.meal.MealDto;

public interface IMealDetailsView {
    void showMealDetails(MealDto meal);
    void setFavouriteIcon(boolean isFav);
    void showError(String error);
    void showLoading();
    void hideLoading();
}
