package ir.vegitto.main.homepage.post.contents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.pavlospt.CircleView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ir.vegitto.R;
import ir.vegitto.model.Food;
import ir.vegitto.tool.PersianNumber;

public class FoodContentsFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private List<Food.FoodIngredient> ingredients;
    private CircleView meal, time, calorie;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_food_contents, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialize();

        ingredients = new ArrayList<>();
        Bundle bundle = getArguments();
        assert bundle != null;
        Food current_food = (Food) bundle.getSerializable("current_food");
        assert current_food != null;
        ingredients.addAll(current_food.getFood_ingredients());

        if (current_food.getMeal().size() != 0)
            meal.setTitleText(current_food.getMeal().get(0));

        if (current_food.getCalorie() != null) {
            calorie.setTitleText(PersianNumber.getPersianNumberString(String.valueOf(current_food.getCalorie())));
            calorie.setSubtitleText("کالری");
        }

        if (current_food.getTime() != 0) {
            time.setTitleText(PersianNumber.getPersianNumberString(String.valueOf(current_food.getTime())));
            time.setSubtitleText(PersianNumber.getPersianNumberString(String.valueOf(current_food.getTime() * 30) + " دقیقه"));
        }

        adaptor();
    }

    private void initialize(){
        recyclerView = view.findViewById(R.id.recycle_content);
        meal = view.findViewById(R.id.meal_circle_view);
        time = view.findViewById(R.id.time_circle_view);
        calorie = view.findViewById(R.id.calorie_circle_view);
    }

    private void adaptor(){
        ContentAdaptor contentAdaptor = new ContentAdaptor(ingredients);
        recyclerView.setAdapter(contentAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
