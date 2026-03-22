package com.siam.mealcraft.presentation.home.views;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.siam.mealcraft.R;
import com.siam.mealcraft.data.models.category.CategoryDto;
import com.siam.mealcraft.data.models.meal.MealDto;
import com.siam.mealcraft.presentation.home.presenter.HomePresenter;
import com.siam.mealcraft.presentation.home.presenter.HomePresenterImpl;

import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment implements HomeView {

    private HomePresenter presenter;


    private View mealOfTheDayCard;
    private View mealOfTheDayLoading;


    private RecyclerView categoriesRecyclerView;
    private View categoriesLoading;
    private CategoryAdapter categoryAdapter;

 
    private TextView greetingText;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter = new HomePresenterImpl(requireContext(), this);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

  
        mealOfTheDayCard    = view.findViewById(R.id.mealOfTheDayCard);
        mealOfTheDayLoading = view.findViewById(R.id.mealOfTheDayLoading);
        categoriesRecyclerView = view.findViewById(R.id.categoriesRecyclerView);
        categoriesLoading   = view.findViewById(R.id.categoriesLoading);
        greetingText        = view.findViewById(R.id.greetingText);

        setGreeting();
        setupCategoriesRecyclerView();

 
        presenter.getMealOfTheDay();
        presenter.getCategories();
    }

    private void setGreeting() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        String greeting;
        if (hour >= 5 && hour < 12)       greeting = "Good Morning ☀️";
        else if (hour >= 12 && hour < 17)  greeting = "Good Afternoon 🌤️";
        else if (hour >= 17 && hour < 21)  greeting = "Good Evening 🌆";
        else                               greeting = "Good Night 🌙";
        greetingText.setText(greeting);
    }
private void setupCategoriesRecyclerView() {

    LinearLayoutManager layoutManager = new LinearLayoutManager(
            getContext(), LinearLayoutManager.VERTICAL, false
    );
    categoriesRecyclerView.setLayoutManager(layoutManager);


    categoriesRecyclerView.setNestedScrollingEnabled(false);

    categoryAdapter = new CategoryAdapter(category -> {
  
    });
    categoriesRecyclerView.setAdapter(categoryAdapter);
}
    private void bindMealOfTheDay(MealDto meal) {
        if (!isAdded()) return;

        TextView title    = mealOfTheDayCard.findViewById(R.id.mealName);
        TextView category = mealOfTheDayCard.findViewById(R.id.categoryChip);
        TextView area     = mealOfTheDayCard.findViewById(R.id.typeChip);
        ImageView image   = mealOfTheDayCard.findViewById(R.id.mealImage);

        title.setText(meal.name);
        category.setText(meal.category);
        area.setText(meal.area);

        Glide.with(this)
                .load(meal.thumbnail)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.shimmer_placeholder)
                        .error(R.drawable.ic_error))
                .into(image);

        mealOfTheDayCard.setOnClickListener(v -> {
    
        });
    }

 

    @Override
    public void showMealOfTheDay(MealDto meal) {
        if (isAdded()) bindMealOfTheDay(meal);
    }

    @Override
    public void showCategories(List<CategoryDto> categories) {
        if (isAdded() && categoryAdapter != null) {
            categoryAdapter.setCategories(categories);
        }
    }

    @Override
    public void showError(String message) {
        if (isAdded() && getView() != null) {
    
        }
    }

    @Override
    public void toggleMealOfTheDayLoading(boolean isLoading) {
        if (!isAdded()) return;
        mealOfTheDayLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        mealOfTheDayCard.setVisibility(isLoading ? View.GONE : View.VISIBLE);
    }

    @Override
    public void toggleCategoriesLoading(boolean isLoading) {
        if (!isAdded()) return;
        categoriesLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        categoriesRecyclerView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
    }
}