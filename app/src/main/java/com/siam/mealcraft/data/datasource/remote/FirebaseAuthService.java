package com.siam.mealcraft.data.datasource.remote;

import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import io.reactivex.rxjava3.core.Single;

public class FirebaseAuthService implements AuthService {
    private static final String TAG = "FirebaseAuthService";
    private final FirebaseAuth mAuth;

    public FirebaseAuthService() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public Single<String> login(String email, String password) {
        return Single.create(emitter -> {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(authResult -> {
                        FirebaseUser user = authResult.getUser();
                        if (user != null) {
                            emitter.onSuccess(user.getUid());
                        } else {
                            emitter.onError(new Exception("User is null"));
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Login failed", e);
                        emitter.onError(e);
                    });
        });
    }

    @Override
    public Single<String> register(String email, String password, String name) {
        return Single.create(emitter -> {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(authResult -> {
                        FirebaseUser user = authResult.getUser();
                        if (user != null) {
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();
                            user.updateProfile(profileUpdates)
                                    .addOnSuccessListener(aVoid -> emitter.onSuccess(user.getUid()))
                                    .addOnFailureListener(e -> {
                                        Log.e(TAG, "Profile update failed", e);
                                        emitter.onError(e);
                                    });
                        } else {
                            emitter.onError(new Exception("User is null after registration"));
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Registration failed", e);
                        emitter.onError(e);
                    });
        });
    }

    @Override
    public Single<String> signInAnonymously() {
        return Single.create(emitter -> {
            mAuth.signInAnonymously()
                    .addOnSuccessListener(authResult -> {
                        FirebaseUser user = authResult.getUser();
                        if (user != null) {
                            emitter.onSuccess(user.getUid());
                        } else {
                            emitter.onError(new Exception("User is null after anonymous sign in"));
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Anonymous sign in failed", e);
                        emitter.onError(e);
                    });
        });
    }

    @Override
    public boolean isUserLoggedIn() {
        return mAuth.getCurrentUser() != null;
    }

    public void logout() {
        mAuth.signOut();
    }
}