package com.siam.mealcraft.presentation.favorite.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.siam.mealcraft.R;
import com.siam.mealcraft.data.models.meal.MealEntity;

import java.util.ArrayList;
import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {

    private List<MealEntity> meals = new ArrayList<>();
    private final OnMealClickListener listener;

    public interface OnMealClickListener {
        void onMealClick(String mealId);
    }

    public FavouriteAdapter(OnMealClickListener listener) {
        this.listener = listener;
    }

    public void setMeals(List<MealEntity> meals) {
        this.meals = meals;
        notifyDataSetDataSetChanged();
    }

    private void notifyDataSetDataSetChanged() {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MealEntity item = meals.get(position);
        holder.tvMealName.setText(item.getName());
        Glide.with(holder.itemView.getContext())
            .load(item.getThumbnail())
            .into(holder.imgMeal);

        holder.itemView.setOnClickListener(v -> listener.onMealClick(item.getId()));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMeal;
        TextView tvMealName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMeal = itemView.findViewById(R.id.imgMeal);
            tvMealName = itemView.findViewById(R.id.tvMealName);
        }
    }
}
