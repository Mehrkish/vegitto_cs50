package ir.vegitto.main.posting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import ir.vegitto.R;
import ir.vegitto.model.Food;

public class PostingFragment2 extends Fragment {

    private View view;
    private Food food;
    List<Food.StepRecipe> stepRecipe = new ArrayList<>();
    private RecyclerView recipeRV;
    private RecipeAdaptor recipeAdaptor;
    private Button addRecipeB;
    private Button backStep;
    private Button record;


    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_posting2, container, false);
        }
        // set food from navigation bundle
        assert getArguments() != null;
        food = (Food) getArguments().getSerializable("foodObject");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialize();


        // check blank is fill or not & add new blank
        addRecipeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = stepRecipe.size() - 1;
                //get ET from Recycle view to extract text
                EditText lastETRecipe = recipeRV.getChildAt(size).findViewById(R.id.posting_steps_ET_recipe);
                String recipe = lastETRecipe.getText().toString().trim();
                // check blank
                if (recipe.equals(""))
                    Toast.makeText(getActivity(), "please fill blanks", Toast.LENGTH_SHORT).show();
                else {
                    //add blank recipe
                    stepRecipe = recipeAdaptor.getStepRecipe();
                    stepRecipe.add(new Food.StepRecipe(stepRecipe.size() + 1, ""));
                    recipeAdaptor.notifyItemInserted(stepRecipe.size() - 1);
                    recipeRV.scrollToPosition(stepRecipe.size() - 1);
                    addRecipeB.requestFocusFromTouch();
                }
            }
        });

        //back step
        backStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Navigation.findNavController(view).popBackStack(); }
        });

        //record
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // post food to server
            }
        });

        //first empty blank
        stepRecipe.add(new Food.StepRecipe(1, ""));

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
        setRecyclerViewRecipe(linearLayoutManager1);
    }


    private void initialize() {
        recipeRV = view.findViewById(R.id.posting_RV_steps);
        addRecipeB = view.findViewById(R.id.posting_B_add_step);
        backStep = view.findViewById(R.id.posting_B_back_step);
        record = view.findViewById(R.id.posting_B_record);
    }

    private void setRecyclerViewRecipe(LinearLayoutManager linearLayoutManager) {
        recipeAdaptor = new RecipeAdaptor(stepRecipe);
        recipeRV.setAdapter(recipeAdaptor);
        recipeRV.setLayoutManager(linearLayoutManager);
    }

    private void foodInitializing() {
        stepRecipe = recipeAdaptor.getStepRecipe();
        food.setStep_recipe(stepRecipe);
    }
}
