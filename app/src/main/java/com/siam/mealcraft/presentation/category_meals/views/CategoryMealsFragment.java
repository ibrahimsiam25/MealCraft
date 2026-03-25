package com.siam.mealcraft.presentation.category_meals.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.siam.mealcraft.R;
import com.siam.mealcraft.data.models.meal.FilteredMeal;
// favourites handled in details screen only
import com.siam.mealcraft.data.repo.MealsRepo;
import com.siam.mealcraft.presentation.category_meals.presenter.CategoryMealsPresenter;
import com.siam.mealcraft.presentation.category_meals.presenter.ICategoryMealsPresenter;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;

public class CategoryMealsFragment extends Fragment implements ICategoryMealsView {

    private ICategoryMealsPresenter presenter;
    private RecyclerView rvMeals;
    private ProgressBar progressBar;
    private TextView errorText;
    private Toolbar toolbar;
    private String categoryName;
    private CategoryMealsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryName = getArguments().getString("categoryName", "Category");
        }
        presenter = new CategoryMealsPresenter(this, new MealsRepo(requireContext()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        rvMeals = view.findViewById(R.id.rvCategoryMeals);
        progressBar = view.findViewById(R.id.progressBarCategory);
        errorText = view.findViewById(R.id.errorTextCategory);
        toolbar = view.findViewById(R.id.toolbarCategory);

        toolbar.setTitle(categoryName);
        toolbar.setNavigationOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        rvMeals.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        presenter.loadMeals(categoryName);
    }


    @Override
    public void showMeals(List<FilteredMeal> meals) {
        errorText.setVisibility(View.GONE);
        rvMeals.setVisibility(View.VISIBLE);
        adapter = new CategoryMealsAdapter(meals, meal -> navigateToDetails(meal.getIdMeal()));
        rvMeals.setAdapter(adapter);
    }

    @Override
    public void showError(String error) {
        errorText.setVisibility(View.VISIBLE);
        errorText.setText(error);
        rvMeals.setVisibility(View.GONE);
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        errorText.setVisibility(View.GONE);
        rvMeals.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    private void navigateToDetails(String mealId) {
        Bundle bundle = new Bundle();
        bundle.putString("mealId", mealId);
        Navigation.findNavController(requireView()).navigate(R.id.mealDetailsFragment, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
    }
}