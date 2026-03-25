package com.siam.mealcraft.presentation.search.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.siam.mealcraft.R;
import com.siam.mealcraft.data.models.meal.MealDto;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private final List<MealDto> meals = new ArrayList<>();
    private final OnMealClickListener listener;

    public interface OnMealClickListener {
        void onMealClick(String mealId);
    }

    public SearchAdapter(OnMealClickListener listener) {
        this.listener = listener;
    }

    public void setMeals(List<MealDto> meals) {
        this.meals.clear();
        if (meals != null) {
            this.meals.addAll(meals);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal_card, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        MealDto meal = meals.get(position);
        holder.tvMealName.setText(meal.name);
        Glide.with(holder.itemView.getContext())
                .load(meal.thumbnail)
                .into(holder.imgMeal);

        holder.itemView.setOnClickListener(v -> listener.onMealClick(meal.id));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMeal;
        TextView tvMealName;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMeal = itemView.findViewById(R.id.imgMeal);
            tvMealName = itemView.findViewById(R.id.tvMealName);
        }
    }
}