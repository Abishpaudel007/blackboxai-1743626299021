package com.example.mealmate;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MealRepository {
    private MealDao mealDao;
    private Executor executor = Executors.newSingleThreadExecutor();

    public MealRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        mealDao = database.mealDao();
    }

    public LiveData<List<Meal>> getAllMeals() {
        return mealDao.getAllMeals();
    }

    public LiveData<Meal> getMealById(int id) {
        return mealDao.getMealById(id);
    }

    public LiveData<List<Meal>> getMealsByDay(String day) {
        return mealDao.getMealsByDay(day);
    }

    public LiveData<List<Meal>> getFavoriteMeals() {
        return mealDao.getFavoriteMeals();
    }

    public LiveData<List<Meal>> searchMeals(String query) {
        return mealDao.searchMeals("%" + query + "%");
    }

    public void insert(Meal meal) {
        executor.execute(() -> mealDao.insert(meal));
    }

    public void update(Meal meal) {
        executor.execute(() -> mealDao.update(meal));
    }

    public void delete(Meal meal) {
        executor.execute(() -> mealDao.delete(meal));
    }

    public void toggleFavorite(Meal meal) {
        executor.execute(() -> {
            meal.isFavorite = !meal.isFavorite;
            mealDao.update(meal);
        });
    }
}