package com.siam.mealcraft.presentation.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.siam.mealcraft.R;
import com.siam.mealcraft.data.models.fav.FavouriteWithMeal;
import com.siam.mealcraft.data.repo.MealsRepo;

import java.util.List;

public class FavoriteFragment extends Fragment implements IFavouriteView, FavouriteAdapter.OnMealClickListener {

    private IFavouritePresenter presenter;
    private ProgressBar progressBar;
    private RecyclerView rvFavourites;
    private TextView tvEmptyState;
    private FavouriteAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new FavouritePresenter(this, new MealsRepo(requireContext()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);
        rvFavourites = view.findViewById(R.id.rvFavourites);
        tvEmptyState = view.findViewById(R.id.tvEmptyState);

        rvFavourites.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        adapter = new FavouriteAdapter(this);
        rvFavourites.setAdapter(adapter);

        presenter.getFavourites();
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
    public void showFavourites(List<FavouriteWithMeal> favourites) {
        if (favourites == null || favourites.isEmpty()) {
            tvEmptyState.setVisibility(View.VISIBLE);
            rvFavourites.setVisibility(View.GONE);
        } else {
            tvEmptyState.setVisibility(View.GONE);
            rvFavourites.setVisibility(View.VISIBLE);
            adapter.setMeals(favourites);
        }
    }

    @Override
    public void showError(String message) {
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMealClick(String mealId) {
        Bundle bundle = new Bundle();
        bundle.putString("mealId", mealId);
        Navigation.findNavController(requireView()).navigate(R.id.action_favoriteFragment_to_mealDetailsFragment, bundle);
    }

    @Override
    public void onFavouriteClick(String mealId) {
        presenter.removeFavourite(mealId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }
}
