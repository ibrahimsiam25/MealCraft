package com.siam.mealcraft.data.models.meal;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(
        tableName = "favourites",
        foreignKeys = @ForeignKey(
                entity = MealEntity.class,
                parentColumns = "id",
                childColumns = "mealId",
                onDelete = ForeignKey.NO_ACTION
        ),
        indices = {@Index("mealId")}
)
public class FavouriteEntity {
    @PrimaryKey
    @NonNull
    private String mealId;

    private long timestamp;



    public FavouriteEntity(@NonNull String mealId, long timestamp) {
        this.mealId = mealId;
        this.timestamp = timestamp;
    }

    @NonNull
    public String getMealId() {
        return mealId;
    }

    public void setMealId(@NonNull String mealId) {
        this.mealId = mealId;
    }

    public long getTimestamp() {
        return timestamp;
    }

}