package com.siam.mealcraft.data.datasource.remote;

import io.reactivex.rxjava3.core.Single;

public interface AuthService {
    Single<String> login(String email, String password);
    Single<String> register(String email, String password, String name);
    Single<String> signInAnonymously();
    boolean isUserLoggedIn();
}
