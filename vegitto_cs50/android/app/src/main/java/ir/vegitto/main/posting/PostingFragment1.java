package ir.vegitto.main.posting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
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

public class PostingFragment1 extends Fragment {

    private View view;
    private EditText foodNameET;
    private EditText countryNameET;
    private EditText calorieET;
    private EditText timeET;
    private EditText detailET;
    private ImageView imageIV;
    private CheckBox breakfast;
    private CheckBox lunch;
    private CheckBox dinner;
    private CheckBox dessert;
    private Food food;
    private List<String> meal = new ArrayList<>();
    List<Food.FoodIngredient> ingredients = new ArrayList<>();
    private RecyclerView ingredientRV;
    private IngredientAdaptor ingredientAdaptor;
    private Button addIngredientB;
    private Button navTo;


    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_posting1, container, false);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialize();

        // check blank is fill or not & add new blank
        addIngredientB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = ingredients.size() - 1;
                //get ET from Recycle view to extract text
                EditText lastETFoodName = ingredientRV.getChildAt(size).findViewById(R.id.posting_materials_ET_foodName);
                EditText lastETMount = ingredientRV.getChildAt(size).findViewById(R.id.posting_materials_ET_mount);
                String food = lastETFoodName.getText().toString().trim();
                String mount = lastETMount.getText().toString().trim();
                // check blanks
                if (mount.equals("") || food.equals(""))
                    Toast.makeText(getActivity(), "please fill blanks", Toast.LENGTH_SHORT).show();
                else {
                    //add ingredient
                    ingredients = ingredientAdaptor.getIngredients();
                    ingredients.add(new Food.FoodIngredient("", ""));
                    ingredientAdaptor.notifyItemInserted(ingredients.size() - 1);
                    ingredientRV.scrollToPosition(ingredients.size() - 1);
                    addIngredientB.requestFocusFromTouch();
                }
            }
        });
        // go to next page
        navTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navTo.requestFocusFromTouch();
                foodInitializing();
                // set bundle to sent food
                Bundle bundle = new Bundle();
                bundle.putSerializable("foodObject", food);
                Navigation.findNavController(v).navigate(R.id.action_navigation_posting_to_postingFragment2,bundle);
            }
        });

        //first empty blank
        ingredients.add(new Food.FoodIngredient("", ""));

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
        setRecyclerViewIngredient(linearLayoutManager1);

        // get checked box button text
        breakfast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    meal.add(buttonView.getText().toString());
                else
                    meal.remove(buttonView.getText().toString());
            }
        });
        lunch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    meal.add(buttonView.getText().toString());
                else
                    meal.remove(buttonView.getText().toString());
            }
        });
        dinner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    meal.add(buttonView.getText().toString());
                else
                    meal.remove(buttonView.getText().toString());
            }
        });
        dessert.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    meal.add(buttonView.getText().toString());
                else
                    meal.remove(buttonView.getText().toString());
            }
        });

    }

    private void initialize() {
        foodNameET = view.findViewById(R.id.posting_ET_foodName);
        countryNameET = view.findViewById(R.id.posting_ET_region);
        calorieET = view.findViewById(R.id.posting_ET_calorie);
        timeET = view.findViewById(R.id.posting_ET_time);
        detailET = view.findViewById(R.id.posting_ET_detail);
        imageIV = view.findViewById(R.id.posting_image);
        ingredientRV = view.findViewById(R.id.posting_RV_foods);
        addIngredientB = view.findViewById(R.id.posting_B_add_material);
        navTo = view.findViewById(R.id.posting_B_next_step);
        breakfast = view.findViewById(R.id.posting_CB_breakfast);
        lunch = view.findViewById(R.id.posting_CB_lunch);
        dinner = view.findViewById(R.id.posting_CB_dinner);
        dessert = view.findViewById(R.id.posting_CB_dessert);
    }

    private void setRecyclerViewIngredient(LinearLayoutManager linearLayoutManager) {
        ingredientAdaptor = new IngredientAdaptor(ingredients);
        ingredientRV.setAdapter(ingredientAdaptor);
        ingredientRV.setLayoutManager(linearLayoutManager);
    }

    //gather all information
    private void foodInitializing() {
        String name = foodNameET.getText().toString().trim();
        String country = countryNameET.getText().toString().trim();
        int time = Integer.parseInt(timeET.getText().toString().trim());
        String calorie = "";
        calorie = calorieET.getText().toString().trim();
        String detail = "";
        detail = detailET.getText().toString().trim();
        ingredients = ingredientAdaptor.getIngredients();
        food = new Food(ingredients, null, name, calorie, country, time, "easy", meal, null);
    }
}
