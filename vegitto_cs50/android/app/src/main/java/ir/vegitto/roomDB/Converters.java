package ir.vegitto.roomDB;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.room.TypeConverter;
import ir.vegitto.model.Food;

public class Converters {
    @TypeConverter
    public static List<String> fromString(String value) {
        Type listType = new TypeToken<List<String>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<String> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<Food.StepRecipe> stringToStepRecipes(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Food.StepRecipe>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String stepRecipesToString(List<Food.StepRecipe> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Food.StepRecipe>>() {
        }.getType();
        return gson.toJson(list, type);
    }

    @TypeConverter
    public static List<Food.FoodIngredient> stringToFoodIngredients(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Food.FoodIngredient>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String foodIngredientsToString(List<Food.FoodIngredient> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Food.FoodIngredient>>() {
        }.getType();
        return gson.toJson(list, type);
    }

    @TypeConverter
    public static List<Food.DietCategory> stringToDietCategory(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Food.DietCategory>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String DietCategoryToString(List<Food.DietCategory> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Food.DietCategory>>() {
        }.getType();
        return gson.toJson(list, type);
    }
}
