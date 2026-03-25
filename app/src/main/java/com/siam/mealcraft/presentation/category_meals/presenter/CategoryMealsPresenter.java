package com.siam.mealcraft.presentation.category_meals.presenter;

import com.siam.mealcraft.data.models.meal.FilteredMeal;
import com.siam.mealcraft.data.models.meal.MealEntity;
import com.siam.mealcraft.data.repo.MealsRepo;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import com.siam.mealcraft.data.repo.FavouriteStateManager;
import com.siam.mealcraft.presentation.category_meals.views.ICategoryMealsView;

public class CategoryMealsPresenter implements ICategoryMealsPresenter {
    private final ICategoryMealsView view;
    private final MealsRepo repo;
    private final CompositeDisposable disposable;
    private List<FilteredMeal> cachedMeals;

    public CategoryMealsPresenter(ICategoryMealsView view, MealsRepo repo) {
        this.view = view;
        this.repo = repo;
        this.disposable = new CompositeDisposable();
    }

    @Override
    public void loadMeals(String category) {
        if (cachedMeals != null && !cachedMeals.isEmpty()) {
            view.showMeals(cachedMeals);
            return;
        }

        view.showLoading();
        disposable.add(
                repo.filterMealsByCategory(category)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                response -> {
                                    view.hideLoading();
                                    cachedMeals = response.getMeals();
                                    view.showMeals(cachedMeals);
                                },
                                error -> {
                                    view.hideLoading();
                                    view.showError(error.getMessage());
                                }
                        )
        );
    }

    @Override
    public void toggleFavourite(FilteredMeal meal) {
    
        disposable.add(
            repo.isFavourite(meal.getIdMeal())
                .subscribeOn(Schedulers.io())
                .flatMapCompletable(isFav -> {
                    if (isFav) {
                        return repo.removeFromFavourites(meal.getIdMeal());
                    } else {
                        MealEntity entity = new MealEntity();
                        entity.setId(meal.getIdMeal());
                        entity.setName(meal.getStrMeal());
                        entity.setThumbnail(meal.getStrMealThumb());
                        return repo.insertMeal(entity)
                                   .andThen(repo.addToFavourites(meal.getIdMeal()));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                          
                            repo.syncFavourites().subscribe();
                     
                            try {
                              Set<String> current = FavouriteStateManager.getInstance().getCurrentFavourites();
                             Set<String> updated = new HashSet<>(current);
                                if (repo != null && meal != null) {
                                    if (current.contains(meal.getIdMeal())) {
                                        updated.remove(meal.getIdMeal());
                                    } else {
                                        updated.add(meal.getIdMeal());
                                    }
                                    FavouriteStateManager.getInstance().updateFavouriteIds(updated);
                                }
                            } catch (Exception ignored) {
                            }
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
