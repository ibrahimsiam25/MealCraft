package com.siam.mealcraft.presentation.splash.presenter;

import com.siam.mealcraft.data.repo.AuthRepo;
import com.siam.mealcraft.presentation.splash.views.SplashView;

public class SplashPresenterImpl implements SplashPresenter {
    private final SplashView view;
    private final AuthRepo authRepo;

    public SplashPresenterImpl(SplashView view, AuthRepo authRepo) {
        this.view = view;
        this.authRepo = authRepo;
    }

    public void checkAuthStatus() {
        if (authRepo.isUserLoggedIn()) {
            view.navigateToMain();
        } else {
            view.navigateToAuth();
        }
    }
}