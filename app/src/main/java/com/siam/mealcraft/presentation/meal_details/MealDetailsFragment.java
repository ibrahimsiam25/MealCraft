package com.siam.mealcraft.presentation.meal_details;

import android.os.Bundle;
import android.util.Log;
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
import com.siam.mealcraft.R;
import com.siam.mealcraft.data.models.meal.MealDto;
import com.siam.mealcraft.data.repo.MealsRepo;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class MealDetailsFragment extends Fragment implements IMealDetailsView {

    private static final String TAG = "MealDetailsFragment";

    private IMealDetailsPresenter presenter;
    private String mealId;

    private ImageView imgMealDetails, imgFavouriteDetail;
    private TextView tvTitle, tvCategoryArea, tvInstructions;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private YouTubePlayerView youtubePlayerView;
    private MealDto currentMeal;
    private boolean isCurrentlyFav = false;

    // ─────────────────────────────────────────
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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setupClickListeners();

        if (mealId != null) {
            presenter.loadMealDetails(mealId);
        }
    }


    private void initViews(View view) {
        imgMealDetails    = view.findViewById(R.id.imgMealDetails);
        imgFavouriteDetail = view.findViewById(R.id.imgFavouriteDetail);
        tvTitle           = view.findViewById(R.id.tvMealDetailsTitle);
        tvCategoryArea    = view.findViewById(R.id.tvCategoryArea);
        tvInstructions    = view.findViewById(R.id.tvMealDetailsInstructions);
        progressBar       = view.findViewById(R.id.progressBarDetail);
        toolbar           = view.findViewById(R.id.toolbarMealDetails);
        youtubePlayerView = view.findViewById(R.id.youtubePlayerView);
    }

    private void setupClickListeners() {
        toolbar.setNavigationOnClickListener(v ->
                Navigation.findNavController(v).navigateUp());

        imgFavouriteDetail.setOnClickListener(v -> {
            if (currentMeal != null) {
                presenter.toggleFavourite(currentMeal);
            }
        });
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

        setupYoutubePlayer(meal);
    }

    private void setupYoutubePlayer(MealDto meal) {
        String videoId = extractYoutubeId(meal.strYoutube);

        if (videoId.isEmpty()) {
            hideYoutubeSection();
            return;
        }


        getLifecycle().addObserver(youtubePlayerView);
     youtubePlayerView.setVisibility(View.VISIBLE);

        youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {

            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                Log.d(TAG, "YouTube player ready, loading video: " + videoId);
                youTubePlayer.cueVideo(videoId, 0);
            }

            @Override
            public void onError(@NonNull YouTubePlayer youTubePlayer,
                                @NonNull PlayerConstants.PlayerError error) {
                Log.e(TAG, "YouTube player error: " + error.name());
                youtubePlayerView.setVisibility(View.GONE);
            }
        });
    }

    private void hideYoutubeSection() {
        if (youtubePlayerView != null) youtubePlayerView.setVisibility(View.GONE);
    }

    private String extractYoutubeId(String url) {
        if (url == null || url.isEmpty()) return "";
        try {
            if (url.contains("v=")) {
                return url.split("v=")[1].split("&")[0];
            }
            if (url.contains("youtu.be/")) {
                return url.substring(url.lastIndexOf("/") + 1).split("\\?")[0];
            }
        } catch (Exception e) {
            Log.e(TAG, "extractYoutubeId error: " + e.getMessage());
        }
        return "";
    }

    @Override
    public void setFavouriteIcon(boolean isFav) {
        this.isCurrentlyFav = isFav;
        imgFavouriteDetail.setImageResource(
                isFav ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
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
        try {
            if (youtubePlayerView != null) {
                youtubePlayerView.release();
            }
        } catch (Exception ignored) {}
        presenter.onDestroy();
    }
}