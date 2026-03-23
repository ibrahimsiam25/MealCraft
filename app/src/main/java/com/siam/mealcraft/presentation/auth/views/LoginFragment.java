package com.siam.mealcraft.presentation.auth.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.siam.mealcraft.MainActivity;
import com.siam.mealcraft.R;
import com.siam.mealcraft.presentation.auth.presenter.AuthPresenterImpl;

public class LoginFragment extends Fragment implements AuthView {

    private AuthPresenterImpl presenter;
    private ProgressBar progressBar;
    private Button btnLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new AuthPresenterImpl(this);

        TextInputEditText etEmail = view.findViewById(R.id.etEmail);
        TextInputEditText etPassword = view.findViewById(R.id.etPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        Button btnGuest = view.findViewById(R.id.btnGuest);
        TextView tvRegister = view.findViewById(R.id.tvRegister);
        progressBar = view.findViewById(R.id.progressBar);

        if (btnLogin != null) {
            btnLogin.setOnClickListener(v -> {
                String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
                String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(requireContext(), "Email and password are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                presenter.login(email, password);
            });
        }

        if (btnGuest != null) {
            btnGuest.setOnClickListener(v -> {
                presenter.signInAnonymously();
            });
        }

        if (tvRegister != null) {
            tvRegister.setOnClickListener(v -> {
                try {
                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public void showLoading() {
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
        if (btnLogin != null) btnLogin.setEnabled(false);
    }

    @Override
    public void hideLoading() {
        if (progressBar != null) progressBar.setVisibility(View.GONE);
        if (btnLogin != null) btnLogin.setEnabled(true);
    }

    @Override
    public void onSuccess() {
        startActivity(new Intent(requireActivity(), MainActivity.class));
        requireActivity().finish();
    }

    @Override
    public void onError(String message) {
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        }
    }
}
