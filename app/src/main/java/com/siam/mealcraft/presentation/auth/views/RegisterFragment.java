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

public class RegisterFragment extends Fragment implements AuthView {

    private AuthPresenterImpl presenter;
    private ProgressBar progressBar;
    private Button btnSignUp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new AuthPresenterImpl(this);

        TextInputEditText etFullName = view.findViewById(R.id.etFullName);
        TextInputEditText etEmail = view.findViewById(R.id.etEmail);
        TextInputEditText etPassword = view.findViewById(R.id.etPassword);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        TextView tvLogin = view.findViewById(R.id.tvLogin);
        progressBar = view.findViewById(R.id.progressBar);

        if (btnSignUp != null) {
            btnSignUp.setOnClickListener(v -> {
                String fullName = etFullName != null && etFullName.getText() != null ? etFullName.getText().toString().trim() : "";
                String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
                String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(requireContext(), "Email and password are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                presenter.register(email, password, fullName);
            });
        }

        if (tvLogin != null) {
            tvLogin.setOnClickListener(v -> {
                try {
                    Navigation.findNavController(view).popBackStack();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public void showLoading() {
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
        if (btnSignUp != null) btnSignUp.setEnabled(false);
    }

    @Override
    public void hideLoading() {
        if (progressBar != null) progressBar.setVisibility(View.GONE);
        if (btnSignUp != null) btnSignUp.setEnabled(true);
    }

    @Override
    public void onSuccess() {
        if (getContext() != null) {
            Toast.makeText(getContext(), "Registration Successful.", Toast.LENGTH_SHORT).show();
        }
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
