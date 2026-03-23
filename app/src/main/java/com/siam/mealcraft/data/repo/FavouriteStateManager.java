package com.siam.mealcraft.data.repo;

import java.util.HashSet;
import java.util.Set;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class FavouriteStateManager {
    private static FavouriteStateManager instance;
    private final BehaviorSubject<Set<String>> favouriteIdsSubject;

    private FavouriteStateManager() {
        favouriteIdsSubject = BehaviorSubject.createDefault(new HashSet<>());
    }

    public static synchronized FavouriteStateManager getInstance() {
        if (instance == null) {
            instance = new FavouriteStateManager();
        }
        return instance;
    }

    public Observable<Set<String>> getFavouriteIds() {
        return favouriteIdsSubject;
    }

    public void updateFavouriteIds(Set<String> ids) {
        favouriteIdsSubject.onNext(ids);
    }

    public Set<String> getCurrentFavourites() {
        return favouriteIdsSubject.getValue() != null ? favouriteIdsSubject.getValue() : new HashSet<>();
    }
}
