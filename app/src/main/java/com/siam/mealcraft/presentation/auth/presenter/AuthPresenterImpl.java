package com.siam.mealcraft.presentation.auth.presenter;

import android.annotation.SuppressLint;
import com.siam.mealcraft.data.repo.AuthRepo;
import com.siam.mealcraft.presentation.auth.views.AuthView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AuthPresenterImpl implements AuthPresenter {

    private final AuthView view;
    private final AuthRepo authRepo;

    public AuthPresenterImpl(AuthView view) {
        this.view = view;
        this.authRepo = new AuthRepo();
    }

    @Override
    @SuppressLint("CheckResult")
    public void login(String email, String password) {
        view.showLoading();
        authRepo.login(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        uid -> {
                            view.hideLoading();
                            view.onSuccess();
                        },
                        error -> {
                            view.hideLoading();
                            view.onError(error.getLocalizedMessage() != null ? error.getLocalizedMessage() : error.getMessage());
                        }
                );
    }

    @Override
    @SuppressLint("CheckResult")
    public void register(String email, String password, String name) {
        view.showLoading();
        authRepo.register(email, password, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        uid -> {
                            view.hideLoading();
                            view.onSuccess();
                        },
                        error -> {
                            view.hideLoading();
                            view.onError(error.getLocalizedMessage() != null ? error.getLocalizedMessage() : error.getMessage());
                        }
                );
    }

    @Override
    @SuppressLint("CheckResult")
    public void signInAnonymously() {
        view.showLoading();
        authRepo.signInAnonymously()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        uid -> {
                            view.hideLoading();
                            view.onSuccess();
                        },
                        error -> {
                            view.hideLoading();
                            view.onError(error.getLocalizedMessage() != null ? error.getLocalizedMessage() : error.getMessage());
                        }
                );
    }
}
