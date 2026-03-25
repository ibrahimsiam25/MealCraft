package com.siam.mealcraft.presentation.favorite.views;

import com.siam.mealcraft.data.models.meal.MealEntity;
import java.util.List;

public interface IFavouriteView {
    void showLoading();
    void hideLoading();
    void showFavourites(List<MealEntity> favourites);
    void showError(String message);
}
