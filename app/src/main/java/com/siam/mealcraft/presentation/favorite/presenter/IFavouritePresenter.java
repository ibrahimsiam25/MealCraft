package com.siam.mealcraft.presentation.favorite.presenter;

public interface IFavouritePresenter {
    void getFavourites();
    void removeFavourite(String mealId);
    void dispose();
}
