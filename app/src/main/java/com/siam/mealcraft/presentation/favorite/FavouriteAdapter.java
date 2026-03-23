package com.siam.mealcraft.presentation.favorite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.siam.mealcraft.R;
import com.siam.mealcraft.data.models.fav.FavouriteWithMeal;

import java.util.ArrayList;
import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {

    private List<FavouriteWithMeal> meals = new ArrayList<>();
    private final OnMealClickListener listener;

    public interface OnMealClickListener {
        void onMealClick(String mealId);
        void onFavouriteClick(String mealId);
    }

    public FavouriteAdapter(OnMealClickListener listener) {
        this.listener = listener;
    }

    public void setMeals(List<FavouriteWithMeal> meals) {
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
        FavouriteWithMeal item = meals.get(position);
        holder.tvMealName.setText(item.meal.getName());
        Glide.with(holder.itemView.getContext())
                .load(item.meal.getThumbnail())
                .into(holder.imgMeal);

        holder.btnFav.setImageResource(R.drawable.ic_favorite);

        holder.itemView.setOnClickListener(v -> listener.onMealClick(item.meal.getId()));
        holder.btnFav.setOnClickListener(v -> listener.onFavouriteClick(item.meal.getId()));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMeal, btnFav;
        TextView tvMealName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMeal = itemView.findViewById(R.id.imgMeal);
            btnFav = itemView.findViewById(R.id.btnFav);
            tvMealName = itemView.findViewById(R.id.tvMealName);
        }
    }
}
