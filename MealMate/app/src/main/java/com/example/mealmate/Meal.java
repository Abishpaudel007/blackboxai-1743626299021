package com.example.mealmate;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import java.util.List;

@Entity(tableName = "meals")
public class Meal {
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    public String name;
    public String imageUri;
    
    @TypeConverters(IngredientsConverter.class)
    public List<String> ingredients;
    
    public String instructions;
    public String dayOfWeek; // For meal planning
    public boolean isFavorite;

    public Meal(String name, List<String> ingredients, String instructions) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    // Room requires a no-arg constructor
    public Meal() {}
}