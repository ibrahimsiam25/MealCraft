package com.siam.mealcraft.presentation.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.siam.mealcraft.R;
import com.siam.mealcraft.data.models.meal.MealDto;
import com.siam.mealcraft.data.repo.MealsRepo;
import com.siam.mealcraft.presentation.search.SearchAdapter;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.disposables.Disposable;

public class SearchFragment extends Fragment implements ISearchView, SearchAdapter.OnMealClickListener {

    private ISearchPresenter presenter;
    private ProgressBar progressBar;
    private RecyclerView rvSearchResults;
    private EditText etSearch;
    private SearchAdapter adapter;
    private PublishSubject<String> searchSubject = PublishSubject.create();
    private Disposable searchDisposable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SearchPresenter(this, new MealsRepo(requireContext()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progressBar);
        rvSearchResults = view.findViewById(R.id.rvSearchResults);
        etSearch = view.findViewById(R.id.etSearch);

        rvSearchResults.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        adapter = new SearchAdapter(this);
        rvSearchResults.setAdapter(adapter);

        setupSearch();
    }

    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    searchSubject.onNext(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        searchDisposable = searchSubject
                .debounce(500, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query -> {
                    if (query.isEmpty()) {
                        adapter.setMeals(Collections.emptyList());
                    } else {
                        presenter.searchMeals(query);
                    }
                });
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
    public void showMeals(List<MealDto> meals) {
        adapter.setMeals(meals);
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
        Navigation.findNavController(requireView()).navigate(R.id.mealDetailsFragment, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (searchDisposable != null && !searchDisposable.isDisposed()) {
            searchDisposable.dispose();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }
}
