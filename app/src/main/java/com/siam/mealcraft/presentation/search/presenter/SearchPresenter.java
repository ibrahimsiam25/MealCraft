package com.siam.mealcraft.presentation.search.presenter;

import com.siam.mealcraft.data.repo.MealsRepo;
import com.siam.mealcraft.presentation.search.views.ISearchView;

import java.util.Collections;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class SearchPresenter implements ISearchPresenter {

    private final ISearchView view;
    private final MealsRepo mealsRepo;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public SearchPresenter(ISearchView view, MealsRepo mealsRepo) {
        this.view = view;
        this.mealsRepo = mealsRepo;
    }

    @Override
    public void searchMeals(String query) {
        if (query == null || query.trim().isEmpty()) {
            return;
        }
        view.showLoading();
        disposables.add(mealsRepo.searchMealsByName(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            view.hideLoading();
                            if (response.meals != null) {
                                view.showMeals(response.meals);
                            } else {
                                view.showMeals(Collections.emptyList());
                            }
                        },
                        error -> {
                            view.hideLoading();
                            view.showError(error.getMessage());
                        }
                ));
    }

    @Override
    public void dispose() {
        disposables.dispose();
    }
}
