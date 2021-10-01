package ir.vegitto.model;

import java.io.Serializable;
import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import ir.vegitto.roomDB.Converters;

@Entity(tableName = "Food_Table")
@TypeConverters({Converters.class})
public class Food implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    public static class FoodIngredient implements Serializable {
        private Integer id;
        private String ingredient;
        private String amount;

        public FoodIngredient(String ingredient, String amount) {
            this.ingredient = ingredient;
            this.amount = amount;
        }

        public void setIngredient(String ingredient) {
            this.ingredient = ingredient;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public Integer getId() {
            return id;
        }

        public String getIngredient() {
            return ingredient;
        }

        public String getAmount() {
            return amount;
        }
    }

    @ColumnInfo(name = "food_ingredients")
    private List<FoodIngredient> food_ingredients;

    public static class StepRecipe implements Serializable {
        private Integer id;
        private int step_num;
        private String recipe;

        public StepRecipe(int step_sum, String recipe_step) {
            this.step_num = step_sum;
            this.recipe = recipe_step;
        }

        public void setRecipe(String recipe) {
            this.recipe = recipe;
        }

        public Integer getId() {
            return id;
        }

        public int getStep_num() {
            return step_num;
        }

        public String getRecipe() {
            return recipe;
        }
    }

    @ColumnInfo(name = "step_recipes")
    private List<StepRecipe> step_recipe;

    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "calorie")
    private String calorie;
    @ColumnInfo(name = "nationality")
    private String nationality;
    @ColumnInfo(name = "time")
    private int time;
    @ColumnInfo(name = "difficulty_level")
    private String difficulty_level;
    @ColumnInfo(name = "meal")
    private List<String> meal;

    public static class DietCategory implements Serializable {
        private Integer id;
        private String name;
        private String short_description;

        public DietCategory(String name, String short_description) {
            this.name = name;
            this.short_description = short_description;
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getShort_description() {
            return short_description;
        }
    }

    @ColumnInfo(name = "diet_category")
    private List<DietCategory> diet_category;

    @Ignore
    public Food() {

    }

    public Food(List<FoodIngredient> food_ingredients, List<StepRecipe> step_recipe, String name, String calorie, String nationality, int time, String difficulty_level, List<String> meal, List<DietCategory> diet_category) {
        this.food_ingredients = food_ingredients;
        this.step_recipe = step_recipe;
        this.name = name;
        this.calorie = calorie;
        this.nationality = nationality;
        this.time = time;
        this.difficulty_level = difficulty_level;
        this.meal = meal;
        this.diet_category = diet_category;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCalorie() {
        return calorie;
    }

    public String getNationality() {
        return nationality;
    }

    public int getTime() {
        return time;
    }

    public String getDifficulty_level() {
        return difficulty_level;
    }

    public List<String> getMeal() {
        return meal;
    }

    public List<DietCategory> getDiet_category() {
        return diet_category;
    }

    public List<StepRecipe> getStep_recipe() {
        return step_recipe;
    }

    public List<FoodIngredient> getFood_ingredients() {
        return food_ingredients;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStep_recipe(List<StepRecipe> step_recipe) {
        this.step_recipe = step_recipe;
    }
}
