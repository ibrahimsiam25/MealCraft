package com.siam.mealcraft.presentation.favorite.views;

import com.siam.mealcraft.data.models.fav.FavouriteWithMeal;
import java.util.List;

public interface IFavouriteView {
    void showLoading();
    void hideLoading();
    void showFavourites(List<FavouriteWithMeal> favourites);
    void showError(String message);
}
