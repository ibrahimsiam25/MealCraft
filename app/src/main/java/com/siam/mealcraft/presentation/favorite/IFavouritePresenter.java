package com.siam.mealcraft.presentation.favorite;

public interface IFavouritePresenter {
    void getFavourites();
    void removeFavourite(String mealId);
    void dispose();
}
