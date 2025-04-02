package com.example.mealmate;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.mealmate.databinding.ActivityMealBinding;
import java.util.Arrays;
import java.util.List;

public class MealActivity extends AppCompatActivity {
    private ActivityMealBinding binding;
    private MealViewModel mealViewModel;
    private Meal existingMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMealBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mealViewModel = new ViewModelProvider(this).get(MealViewModel.class);

        // Check if we're editing an existing meal
        int mealId = getIntent().getIntExtra("meal_id", -1);
        if (mealId != -1) {
            mealViewModel.getMealById(mealId).observe(this, meal -> {
                if (meal != null) {
                    existingMeal = meal;
                    populateForm(meal);
                }
            });
        }

        binding.btnSave.setOnClickListener(v -> saveMeal());
    }

    private void populateForm(Meal meal) {
        binding.etName.setText(meal.name);
        binding.etIngredients.setText(String.join("\n", meal.ingredients));
        binding.etInstructions.setText(meal.instructions);
    }

    private void saveMeal() {
        String name = binding.etName.getText().toString().trim();
        String ingredientsText = binding.etIngredients.getText().toString().trim();
        String instructions = binding.etInstructions.getText().toString().trim();

        if (name.isEmpty() || ingredientsText.isEmpty()) {
            Toast.makeText(this, "Name and ingredients are required", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> ingredients = Arrays.asList(ingredientsText.split("\\n"));

        if (existingMeal != null) {
            // Update existing meal
            existingMeal.name = name;
            existingMeal.ingredients = ingredients;
            existingMeal.instructions = instructions;
            mealViewModel.update(existingMeal);
        } else {
            // Create new meal
            Meal newMeal = new Meal(name, ingredients, instructions);
            mealViewModel.insert(newMeal);
        }

        Toast.makeText(this, "Meal saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}