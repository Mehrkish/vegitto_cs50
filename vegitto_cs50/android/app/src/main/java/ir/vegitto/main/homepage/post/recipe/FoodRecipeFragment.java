package ir.vegitto.main.homepage.post.recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ir.vegitto.R;
import ir.vegitto.model.Food;

public class FoodRecipeFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Food.StepRecipe> steps;
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_food_recipe, container, false);

        initialize();

        steps = new ArrayList<>();
        Bundle bundle = getArguments();
        assert bundle != null;
        Food current_food = (Food) bundle.getSerializable("current_food");
        assert current_food != null;
        steps.addAll(current_food.getStep_recipe());

        adaptor();

        return view;
    }

    private void initialize() {
        recyclerView = view.findViewById(R.id.recycle_recipe);
    }

    private void adaptor(){
        RecipeAdaptor recipeAdaptor = new RecipeAdaptor(steps);
        recyclerView.setAdapter(recipeAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
