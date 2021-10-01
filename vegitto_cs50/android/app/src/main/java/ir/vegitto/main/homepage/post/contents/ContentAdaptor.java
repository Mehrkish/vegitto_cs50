package ir.vegitto.main.homepage.post.contents;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.vegitto.R;
import ir.vegitto.model.Food;

public class ContentAdaptor extends RecyclerView.Adapter<ContentAdaptor.ContentViewHolder> {
    private List<Food.FoodIngredient> ingredients;

    ContentAdaptor(List<Food.FoodIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    static class ContentViewHolder extends RecyclerView.ViewHolder {
        private TextView name_textView;
        private TextView measure_textView;

        ContentViewHolder(@NonNull View itemView) {
            super(itemView);
            name_textView = itemView.findViewById(R.id.food_name_content);
            measure_textView = itemView.findViewById(R.id.food_measure_content);
        }
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_food_contents_item,parent,false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {

        String ingredient_name;
        String ingredient_amount;

        ingredient_name = ingredients.get(position).getIngredient();
        ingredient_amount = ingredients.get(position).getAmount();

        holder.name_textView.setText(ingredient_name);
        if (!ingredient_amount.equals(""))
            holder.measure_textView.setText(ingredient_amount);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

}
