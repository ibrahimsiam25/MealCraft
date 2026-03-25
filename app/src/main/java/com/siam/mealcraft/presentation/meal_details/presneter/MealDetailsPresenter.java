package com.siam.mealcraft.presentation.meal_details.presneter;

import com.siam.mealcraft.data.models.meal.MealDto;
import com.siam.mealcraft.data.models.meal.MealEntity;
import com.siam.mealcraft.data.repo.MealsRepo;
import com.siam.mealcraft.presentation.meal_details.views.IMealDetailsView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.core.Single;

public class MealDetailsPresenter implements IMealDetailsPresenter {
    private final IMealDetailsView view;
    private final MealsRepo repo;
    private final CompositeDisposable disposable;

    public MealDetailsPresenter(IMealDetailsView view, MealsRepo repo) {
        this.view = view;
        this.repo = repo;
        this.disposable = new CompositeDisposable();
    }

    @Override
    public void loadMealDetails(String mealId) {
        view.showLoading();
        disposable.add(
            Single.zip(
                repo.getMealDetails(mealId),
                repo.isFavourite(mealId),
                (response, isFav) -> {
                    return new Object[]{response.meals.get(0), isFav};
                }
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                result -> {
                    view.hideLoading();
                    MealDto meal = (MealDto) result[0];
                    boolean isFav = (boolean) result[1];
                    view.showMealDetails(meal);
                    view.setFavouriteIcon(isFav);
                },
                error -> {
                    view.hideLoading();
                    view.showError(error.getMessage());
                }
            )
        );
    }

    @Override
    public void toggleFavourite(MealDto meal) {
        disposable.add(
            repo.isFavourite(meal.id)
                .subscribeOn(Schedulers.io())
                .flatMapCompletable(isFav -> {
                    if (isFav) {
                        return repo.removeFromFavourites(meal.id);
                    } else {
                        MealEntity entity = new MealEntity();
                        entity.setId(meal.id);
                        entity.setName(meal.name);
                        entity.setThumbnail(meal.thumbnail);
                        return repo.insertMeal(entity)
                                   .andThen(repo.addToFavourites(meal.id));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                       
                            disposable.add(
                                repo.isFavourite(meal.id)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                        isFav -> view.setFavouriteIcon(isFav),
                                        err -> {}
                                    )
                            );
                        },
                        error -> view.showError(error.getMessage())
                )
        );
    }

    @Override
    public void onDestroy() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
