package com.siam.mealcraft.data.datasource.remote;




import com.siam.mealcraft.data.models.category.CategoriesResponse;
import com.siam.mealcraft.data.models.meal.FilteredMealsResponse;
import com.siam.mealcraft.data.models.meal.MealsResponse;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {
    @GET("random.php")
    public abstract Single<MealsResponse> getRandomMeal();

    @GET("categories.php")
    public abstract Single<CategoriesResponse> getCategories();



    @GET("search.php")
    public abstract Single<MealsResponse> searchMealsByName(@Query("s") String name);


    @GET("lookup.php")
    public abstract Single<MealsResponse> getMealDetails(@Query("i") String mealId);


    @GET("filter.php")
    public abstract Single<FilteredMealsResponse> filterMealsByCategory(@Query("c") String category);
}
