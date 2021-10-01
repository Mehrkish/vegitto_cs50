package ir.vegitto.main.homepage.post.recipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.vegitto.R;
import ir.vegitto.model.Food;

public class RecipeAdaptor extends RecyclerView.Adapter<RecipeAdaptor.RecipeViewHolder> {

    private List<Food.StepRecipe> steps;

    RecipeAdaptor(List<Food.StepRecipe> steps) {
        this.steps = steps;
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView step_num;
        TextView step_description;

        RecipeViewHolder(View itemView) {
            super(itemView);
            step_num = itemView.findViewById(R.id.step_recipe);
            step_description = itemView.findViewById(R.id.explanation_recipe);
        }
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_food_recipe_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {

        String step_description = steps.get(position).getRecipe();
        int step_num = steps.get(position).getStep_num();

        holder.step_description.setText(step_description);
        holder.step_num.setText("مرحله " + step_num);

    }

    @Override
    public int getItemCount() {
        return steps.size();
    }
}
