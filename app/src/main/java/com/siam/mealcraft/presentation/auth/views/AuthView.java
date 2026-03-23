package com.siam.mealcraft.presentation.auth.views;

public interface AuthView {
    void showLoading();
    void hideLoading();
    void onSuccess();
    void onError(String message);
}
