package ir.vegitto.main.posting;


import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.vegitto.R;
import ir.vegitto.model.Food;

public class RecipeAdaptor extends RecyclerView.Adapter<RecipeAdaptor.StepsViewHolder> {
    List<Food.StepRecipe> steps;


    RecipeAdaptor(List<Food.StepRecipe> steps){ this.steps = steps; }

    static class StepsViewHolder extends RecyclerView.ViewHolder{
        private TextView step_num;
        private EditText step_description;
        private ImageButton delete;

        public StepsViewHolder(@NonNull View itemView) {
            super(itemView);
            step_num = itemView.findViewById(R.id.posting_steps_TV_stepNum);
            step_description = itemView.findViewById(R.id.posting_steps_ET_recipe);
            delete = itemView.findViewById(R.id.posting_steps_IB_delete);
        }
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_posting_steps_item, parent, false);
        return new RecipeAdaptor.StepsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        String step_description = steps.get(position).getRecipe();
        int step_num = steps.get(position).getStep_num();

        holder.step_description.setText(step_description);
        holder.step_num.setText("مرحله " + step_num);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getItemCount() != 1){
                    steps.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position - 1, getItemCount());
                }
            }
        });

        holder.step_description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && (getItemCount() > position))
                    steps.get(position).setRecipe(holder.step_description.getText().toString().trim());
            }
        });

    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public List<Food.StepRecipe> getStepRecipe() {return steps;}

}
