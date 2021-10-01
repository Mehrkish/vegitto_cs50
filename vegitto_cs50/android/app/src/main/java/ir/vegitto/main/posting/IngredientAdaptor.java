package ir.vegitto.main.posting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.vegitto.R;
import ir.vegitto.model.Food;

public class IngredientAdaptor extends RecyclerView.Adapter<IngredientAdaptor.IngredientViewHolder> {
    private List<Food.FoodIngredient> ingredients;

    IngredientAdaptor(List<Food.FoodIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    static class IngredientViewHolder extends RecyclerView.ViewHolder {
        private TextView name_textView;
        private TextView measure_textView;
        private ImageButton delete;

        IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            delete =  itemView.findViewById(R.id.posting_materials_IB_delete);
            name_textView = itemView.findViewById(R.id.posting_materials_ET_foodName);
            measure_textView = itemView.findViewById(R.id.posting_materials_ET_mount);
        }
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_posting_materials_item,parent,false);
        return new IngredientAdaptor.IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        String ingredient_name;
        String ingredient_amount;

        ingredient_name = ingredients.get(position).getIngredient();
        ingredient_amount = ingredients.get(position).getAmount();

        holder.name_textView.setText(ingredient_name);
        holder.measure_textView.setText(ingredient_amount);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getItemCount() != 1) {
                    ingredients.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position - 1, getItemCount());
                }
            }
        });

        holder.measure_textView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && (getItemCount() > position))
                    ingredients.get(position).setAmount(holder.measure_textView.getText().toString().trim());
            }
        });

        holder.name_textView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && (getItemCount() > position))
                    ingredients.get(position).setIngredient(holder.name_textView.getText().toString().trim());
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public List<Food.FoodIngredient> getIngredients() {return ingredients;}
}
