package com.siam.mealcraft.presentation.category_meals;

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
    private final OnFavouriteClickListener favClickListener;
    private Set<String> favoriteIds = new HashSet<>();

    public interface OnMealClickListener {
        void onMealClick(FilteredMeal meal);
    }

    public interface OnFavouriteClickListener {
        void onFavouriteClick(FilteredMeal meal);
    }

    public CategoryMealsAdapter(List<FilteredMeal> meals, OnMealClickListener clickListener, OnFavouriteClickListener favClickListener) {
        this.meals = meals;
        this.clickListener = clickListener;
        this.favClickListener = favClickListener;
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

        // Intial favored state to be configured appropriately (if list contains fav state) 
        // For now, use border. The presenter can update the state.
        boolean isFav = favoriteIds.contains(meal.getIdMeal());
        holder.btnFav.setImageResource(isFav ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);

        holder.itemView.setOnClickListener(v -> clickListener.onMealClick(meal));
        holder.btnFav.setOnClickListener(v -> favClickListener.onFavouriteClick(meal));
    }

    @Override
    public int getItemCount() {
        return meals != null ? meals.size() : 0;
    }

    public void setFavouriteIds(Set<String> favoriteIds) {
        this.favoriteIds = favoriteIds != null ? favoriteIds : new HashSet<>();
        notifyDataSetChanged();
    }

    static class MealViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMeal;
        ImageView btnFav;
        TextView tvMealName;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMeal = itemView.findViewById(R.id.imgMeal);
            btnFav = itemView.findViewById(R.id.btnFav);
            tvMealName = itemView.findViewById(R.id.tvMealName);
        }
    }
}