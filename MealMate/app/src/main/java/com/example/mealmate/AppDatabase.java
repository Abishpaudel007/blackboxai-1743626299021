package com.example.mealmate;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class, Meal.class}, version = 2, exportSchema = false)
@TypeConverters(IngredientsConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract MealDao mealDao();
    
    // Singleton pattern to prevent multiple instances
    private static volatile AppDatabase INSTANCE;
    
    public static AppDatabase getDatabase(final android.content.Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = androidx.room.Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class,
                        "mealmate_database"
                    ).fallbackToDestructiveMigration()
                     .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            // You can add initial data here if needed
                        }
                     })
                     .build();
                }
            }
        }
        return INSTANCE;
    }
}