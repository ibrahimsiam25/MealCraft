package com.siam.mealcraft.data.datasource.remote;




import com.siam.mealcraft.data.models.category.CategoriesResponse;
import com.siam.mealcraft.data.models.meal.MealsResponse;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface MealService {
    @GET("random.php")
    public abstract Single<MealsResponse> getRandomMeal();

    @GET("categories.php")
    public abstract Single<CategoriesResponse> getCategories();




}
