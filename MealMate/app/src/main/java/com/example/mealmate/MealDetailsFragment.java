package com.example.mealmate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;

public class MealDetailsFragment extends Fragment {
    private MealViewModel mealViewModel;
    private Meal currentMeal;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mealViewModel = new ViewModelProvider(requireActivity()).get(MealViewModel.class);
        
        if (getArguments() != null) {
            int mealId = getArguments().getInt("meal_id");
            mealViewModel.getMealById(mealId).observe(getViewLifecycleOwner(), meal -> {
                if (meal != null) {
                    currentMeal = meal;
                    updateUI(view);
                }
            });
        }

        view.findViewById(R.id.btnAddToGrocery).setOnClickListener(v -> {
            if (currentMeal != null) {
                // TODO: Implement add to grocery list functionality
            }
        });
    }

    private void updateUI(View view) {
        TextView tvName = view.findViewById(R.id.tvMealName);
        ImageView ivImage = view.findViewById(R.id.ivMealImage);
        TextView tvIngredients = view.findViewById(R.id.tvIngredients);
        TextView tvInstructions = view.findViewById(R.id.tvInstructions);

        tvName.setText(currentMeal.name);
        tvIngredients.setText(String.join("\n", currentMeal.ingredients));
        tvInstructions.setText(currentMeal.instructions);

        if (currentMeal.imageUri != null && !currentMeal.imageUri.isEmpty()) {
            Glide.with(this)
                .load(currentMeal.imageUri)
                .placeholder(R.drawable.ic_meal_placeholder)
                .into(ivImage);
        }
    }
}