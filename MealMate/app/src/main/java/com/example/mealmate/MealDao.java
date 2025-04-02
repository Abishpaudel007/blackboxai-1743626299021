package com.example.mealmate;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface MealDao {
    @Insert
    void insert(Meal meal);

    @Update
    void update(Meal meal);

    @Delete
    void delete(Meal meal);

    @Query("SELECT * FROM meals ORDER BY name ASC")
    LiveData<List<Meal>> getAllMeals();

    @Query("SELECT * FROM meals WHERE dayOfWeek = :day ORDER BY name ASC")
    LiveData<List<Meal>> getMealsByDay(String day);

    @Query("SELECT * FROM meals WHERE isFavorite = 1 ORDER BY name ASC")
    LiveData<List<Meal>> getFavoriteMeals();

    @Query("SELECT * FROM meals WHERE id = :id")
    LiveData<Meal> getMealById(int id);

    @Query("SELECT * FROM meals WHERE name LIKE :query OR instructions LIKE :query")
    LiveData<List<Meal>> searchMeals(String query);
}