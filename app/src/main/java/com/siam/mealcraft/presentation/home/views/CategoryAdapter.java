package com.siam.mealcraft.presentation.home.views;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.siam.mealcraft.R;
import com.siam.mealcraft.data.models.category.CategoryDto;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<CategoryDto> categories = new ArrayList<>();
    private final CategoryOnClickListener listener;

    public CategoryAdapter(CategoryOnClickListener listener) {
        this.listener = listener;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final View convertView;
        private final ImageView categoryImage;
        private final TextView categoryName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            convertView = itemView;
            categoryImage = convertView.findViewById(R.id.categoryImage);
            categoryName = convertView.findViewById(R.id.categoryName);
        }

        public void bind(CategoryDto category) {
            Glide.with(convertView)
                    .load(category.thumbnail)
                    .into(categoryImage);
            categoryName.setText(category.name);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onCategoryClick(category);
                }
            });
        }
    }
}