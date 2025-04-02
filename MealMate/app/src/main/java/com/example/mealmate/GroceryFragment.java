package com.example.mealmate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class GroceryFragment extends Fragment {
    private MealViewModel mealViewModel;
    private GroceryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grocery, container, false);
        
        RecyclerView recyclerView = view.findViewById(R.id.grocery_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new GroceryAdapter((item, isChecked) -> {
            // TODO: Handle item checked state changes
            if (isChecked) {
                // Item checked - mark as purchased
            } else {
                // Item unchecked - mark as needed
            }
        });
        recyclerView.setAdapter(adapter);

        mealViewModel = new ViewModelProvider(this).get(MealViewModel.class);
        mealViewModel.getMealsByDay("").observe(getViewLifecycleOwner(), meals -> {
            List<String> allIngredients = new ArrayList<>();
            for (Meal meal : meals) {
                if (meal.ingredients != null) {
                    allIngredients.addAll(meal.ingredients);
                }
            }
            adapter.setItems(allIngredients);
        });

        view.findViewById(R.id.fabClearChecked).setOnClickListener(v -> {
            new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Clear Checked Items")
                .setMessage("Are you sure you want to remove all checked items?")
                .setPositiveButton("Clear", (dialog, which) -> {
                    adapter.removeCheckedItems();
                })
                .setNegativeButton("Cancel", null)
                .show();
        });

        return view;
    }
}