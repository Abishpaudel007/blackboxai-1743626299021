package com.example.mealmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment implements MealAdapter.OnMealClickListener {
    private MealViewModel mealViewModel;
    private MealAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        
        RecyclerView recyclerView = view.findViewById(R.id.meal_plan_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MealAdapter(this);
        recyclerView.setAdapter(adapter);

        mealViewModel = new ViewModelProvider(this).get(MealViewModel.class);
        mealViewModel.getAllMeals().observe(getViewLifecycleOwner(), meals -> {
            adapter.setMeals(meals);
        });

        FloatingActionButton fab = view.findViewById(R.id.fab_add_meal);
        fab.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), MealActivity.class));
        });
        
        return view;
    }

    @Override
    public void onEditClick(Meal meal) {
        Intent intent = new Intent(getActivity(), MealActivity.class);
        intent.putExtra("meal_id", meal.id);
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(Meal meal) {
        mealViewModel.delete(meal);
    }

    @Override
    public void onFavoriteClick(Meal meal) {
        mealViewModel.toggleFavorite(meal);
    }

    @Override
    public void onMealClick(Meal meal) {
        MealDetailsFragment detailsFragment = new MealDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("meal_id", meal.id);
        detailsFragment.setArguments(args);

        getParentFragmentManager().beginTransaction()
            .replace(R.id.fragment_container, detailsFragment)
            .addToBackStack("meal_details")
            .commit();
    }
}
