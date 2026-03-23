package com.siam.mealcraft.presentation.meal_details;

import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.siam.mealcraft.R;
import com.siam.mealcraft.data.models.meal.MealDto;
import com.siam.mealcraft.data.repo.MealsRepo;

public class MealDetailsFragment extends Fragment implements IMealDetailsView {

    private IMealDetailsPresenter presenter;
    private String mealId;

    private ImageView imgMealDetails, imgFavouriteDetail;
    private TextView tvTitle, tvCategoryArea, tvInstructions;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private MaterialButton btnYoutube;
    
    private MealDto currentMeal;
    private boolean isCurrentlyFav = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mealId = getArguments().getString("mealId");
        }
        presenter = new MealDetailsPresenter(this, new MealsRepo(requireContext()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgMealDetails = view.findViewById(R.id.imgMealDetails);
        imgFavouriteDetail = view.findViewById(R.id.imgFavouriteDetail);
        tvTitle = view.findViewById(R.id.tvMealDetailsTitle);
        tvCategoryArea = view.findViewById(R.id.tvCategoryArea);
        tvInstructions = view.findViewById(R.id.tvMealDetailsInstructions);
        progressBar = view.findViewById(R.id.progressBarDetail);
        toolbar = view.findViewById(R.id.toolbarMealDetails);
        btnYoutube = view.findViewById(R.id.btnYoutube);

        toolbar.setNavigationOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        imgFavouriteDetail.setOnClickListener(v -> {
            if (currentMeal != null) {
                presenter.toggleFavourite(currentMeal);
            }
        });

        if (mealId != null) {
            presenter.loadMealDetails(mealId);
        }
    }

    @Override
    public void showMealDetails(MealDto meal) {
        this.currentMeal = meal;
        tvTitle.setText(meal.name);
        tvCategoryArea.setText("Category: " + meal.category + " • Area: " + meal.area);
        tvInstructions.setText(meal.instructions);

        Glide.with(this)
             .load(meal.thumbnail)
             .into(imgMealDetails);

        if (meal.strYoutube != null && !meal.strYoutube.isEmpty()) {
            btnYoutube.setVisibility(View.VISIBLE);
            btnYoutube.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(meal.strYoutube));
                startActivity(intent);
            });
        } else {
            btnYoutube.setVisibility(View.GONE);
        }
    }

    @Override
    public void setFavouriteIcon(boolean isFav) {
        this.isCurrentlyFav = isFav;
        if (isFav) {
            imgFavouriteDetail.setImageResource(R.drawable.ic_favorite);
        } else {
            imgFavouriteDetail.setImageResource(R.drawable.ic_favorite_border);
        }
    }

    @Override
    public void showError(String error) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
    }
}