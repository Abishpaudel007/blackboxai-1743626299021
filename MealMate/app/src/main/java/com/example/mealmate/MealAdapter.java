package com.example.mealmate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {
    private List<Meal> meals;
    private final OnMealClickListener listener;

    public interface OnMealClickListener {
        void onEditClick(Meal meal);
        void onDeleteClick(Meal meal);
        void onFavoriteClick(Meal meal);
        void onMealClick(Meal meal);
    }

    public MealAdapter(OnMealClickListener listener) {
        this.listener = listener;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meal, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.bind(meal, listener);
    }

    @Override
    public int getItemCount() {
        return meals != null ? meals.size() : 0;
    }

    static class MealViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvMealName;
        private final TextView tvIngredients;
        private final ImageButton btnFavorite;
        private final ImageButton btnEdit;
        private final ImageButton btnDelete;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMealName = itemView.findViewById(R.id.tvMealName);
            tvIngredients = itemView.findViewById(R.id.tvIngredients);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        public void bind(Meal meal, OnMealClickListener listener) {
            tvMealName.setText(meal.name);
            tvIngredients.setText(String.join(", ", meal.ingredients));
            
            btnFavorite.setImageResource(meal.isFavorite ? 
                R.drawable.ic_favorite : R.drawable.ic_favorite_border);
                
            btnFavorite.setOnClickListener(v -> listener.onFavoriteClick(meal));
            btnEdit.setOnClickListener(v -> listener.onEditClick(meal));
            btnDelete.setOnClickListener(v -> listener.onDeleteClick(meal));
            itemView.setOnClickListener(v -> listener.onMealClick(meal));
        }
    }
}