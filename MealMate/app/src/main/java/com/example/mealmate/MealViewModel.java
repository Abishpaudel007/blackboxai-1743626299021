package com.example.mealmate;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class MealViewModel extends AndroidViewModel {
    private MealRepository repository;
    private LiveData<List<Meal>> allMeals;

    public MealViewModel(Application application) {
        super(application);
        repository = new MealRepository(application);
        allMeals = repository.getAllMeals();
    }

    public LiveData<List<Meal>> getAllMeals() {
        return allMeals;
    }

    public LiveData<Meal> getMealById(int id) {
        return repository.getMealById(id);
    }

    public LiveData<List<Meal>> getMealsByDay(String day) {
        return repository.getMealsByDay(day);
    }

    public LiveData<List<Meal>> getFavoriteMeals() {
        return repository.getFavoriteMeals();
    }

    public void insert(Meal meal) {
        repository.insert(meal);
    }

    public void update(Meal meal) {
        repository.update(meal);
    }

    public void delete(Meal meal) {
        repository.delete(meal);
    }

    public void toggleFavorite(Meal meal) {
        repository.toggleFavorite(meal);
    }
}