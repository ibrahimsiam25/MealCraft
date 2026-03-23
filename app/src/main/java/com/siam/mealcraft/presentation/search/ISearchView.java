package com.siam.mealcraft.presentation.search;

import com.siam.mealcraft.data.models.meal.MealDto;
import java.util.List;

public interface ISearchView {
    void showLoading();
    void hideLoading();
    void showMeals(List<MealDto> meals);
    void showError(String message);
}
