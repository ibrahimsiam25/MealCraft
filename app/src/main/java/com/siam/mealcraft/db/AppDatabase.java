package com.siam.mealcraft.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.siam.mealcraft.data.datasource.local.FavouriteDao;
import com.siam.mealcraft.data.datasource.local.MealDao;
import com.siam.mealcraft.data.models.meal.FavouriteEntity;
import com.siam.mealcraft.data.models.meal.MealEntity;


@Database(
        entities = {
                MealEntity.class,
                FavouriteEntity.class
        },
        version = 1
)
@TypeConverters({DBConverters.class})
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "meals_db";

    private static AppDatabase db = null;

    public static AppDatabase getInstance(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                    .build();
        }
        return db;
    }

    public abstract MealDao mealDao();
    public abstract FavouriteDao favouriteDao();
}