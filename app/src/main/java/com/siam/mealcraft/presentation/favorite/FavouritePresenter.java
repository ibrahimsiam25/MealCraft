package com.siam.mealcraft.presentation.favorite;

import com.siam.mealcraft.data.repo.MealsRepo;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class FavouritePresenter implements IFavouritePresenter {

    private final IFavouriteView view;
    private final MealsRepo mealsRepo;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public FavouritePresenter(IFavouriteView view, MealsRepo mealsRepo) {
        this.view = view;
        this.mealsRepo = mealsRepo;
    }

    @Override
    public void getFavourites() {
        view.showLoading();
        disposables.add(mealsRepo.getAllFavourites()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        favourites -> {
                            view.hideLoading();
                            view.showFavourites(favourites);
                        },
                        error -> {
                            view.hideLoading();
                            view.showError(error.getMessage());
                        }
                ));
    }

    @Override
    public void removeFavourite(String mealId) {
        disposables.add(mealsRepo.removeFromFavourites(mealId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            mealsRepo.syncFavourites().subscribe();
                        },
                        error -> view.showError(error.getMessage())
                ));
    }

    @Override
    public void dispose() {
        disposables.dispose();
    }
}
