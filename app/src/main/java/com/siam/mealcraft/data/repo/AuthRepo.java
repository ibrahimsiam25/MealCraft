package com.siam.mealcraft.data.repo;

import com.siam.mealcraft.data.datasource.remote.AuthService;
import com.siam.mealcraft.data.datasource.remote.FirebaseAuthService;

import io.reactivex.rxjava3.core.Single;

public class AuthRepo {
    private final AuthService authService;

    public AuthRepo() {
        this.authService = new FirebaseAuthService();
    }

    public Single<String> login(String email, String password) {
        return authService.login(email, password);
    }

    public Single<String> register(String email, String password, String name) {
        return authService.register(email, password, name);
    }

    public Single<String> signInAnonymously() {
        return authService.signInAnonymously();
    }

    public boolean isUserLoggedIn() {
        return authService.isUserLoggedIn();
    }

    public void logout() {
        ((FirebaseAuthService) authService).logout();
    }
}
