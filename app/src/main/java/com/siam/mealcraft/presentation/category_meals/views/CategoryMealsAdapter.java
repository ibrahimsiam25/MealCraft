package com.siam.mealcraft.presentation.category_meals.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.siam.mealcraft.R;
import com.siam.mealcraft.data.models.meal.FilteredMeal;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CategoryMealsAdapter extends RecyclerView.Adapter<CategoryMealsAdapter.MealViewHolder> {

    private final List<FilteredMeal> meals;
    private final OnMealClickListener clickListener;

    public interface OnMealClickListener {
        void onMealClick(FilteredMeal meal);
    }

    public CategoryMealsAdapter(List<FilteredMeal> meals, OnMealClickListener clickListener) {
        this.meals = meals;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal_card, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        FilteredMeal meal = meals.get(position);
        holder.tvMealName.setText(meal.getStrMeal());
        
        Glide.with(holder.itemView.getContext())
             .load(meal.getStrMealThumb())
             .into(holder.imgMeal);


        holder.itemView.setOnClickListener(v -> clickListener.onMealClick(meal));
    }

    @Override
    public int getItemCount() {
        return meals != null ? meals.size() : 0;
    }

    static class MealViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMeal;
        TextView tvMealName;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMeal = itemView.findViewById(R.id.imgMeal);
            tvMealName = itemView.findViewById(R.id.tvMealName);
        }
    }
}