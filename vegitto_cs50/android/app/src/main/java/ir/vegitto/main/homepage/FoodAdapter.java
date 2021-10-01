package ir.vegitto.main.homepage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikhaellopez.circleview.CircleView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import ir.vegitto.R;
import ir.vegitto.model.Food;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private List<Food> foods;

    FoodAdapter(List<Food> foods) {
        this.foods = foods;
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {
        CircleView foodImage;
        TextView foodTitle, ingredients;
        CardView foodItem;

        FoodViewHolder(View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.food_image);
            foodTitle = itemView.findViewById(R.id.food_title_homepage);
            ingredients = itemView.findViewById(R.id.ingredients_homepage);
            foodItem = itemView.findViewById(R.id.card_food_item);
        }
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_homepage_foods_item, parent, false);
        return new FoodViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        final Food currentFood = foods.get(position);

        StringBuilder food_ingredient_str = new StringBuilder();
        int ingredient_position = 0;
        int ingredients_size = currentFood.getFood_ingredients().size();
        for (Food.FoodIngredient foodIngredient : currentFood.getFood_ingredients()) {
            food_ingredient_str.append(foodIngredient.getIngredient());
            if (ingredient_position != ingredients_size / 3)
                food_ingredient_str.append(", ");
            if (ingredient_position == ingredients_size / 3) {
                food_ingredient_str.append(" و ...");
                break;
            }
            ingredient_position += 1;
        }

        holder.foodTitle.setText(currentFood.getName());
        holder.ingredients.setText(food_ingredient_str);

        holder.foodItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("current_food", currentFood);
                Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_navigation_food, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }
}
