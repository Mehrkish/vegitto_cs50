package ir.vegitto.main.homepage.post;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;
import ir.vegitto.R;
import ir.vegitto.model.Food;
import ir.vegitto.widget.ExpandableTextView;

public class FoodFragment extends Fragment {
    private View view;
    private ViewPager2 food_pager;
    private TabLayout tabLayout;
    private ImageButton back;
    private TextView food_culture;
    private ExpandableTextView food_title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_food, container, false);
        initialize();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get current food
        Bundle bundle = getArguments();
        assert bundle != null;
        Food current_food = (Food) bundle.getSerializable("current_food");
        assert current_food != null;
        String food_name = current_food.getName();
        String food_nationality = current_food.getNationality();

        if (food_name != null)
            food_title.setText(food_name);

        if(food_nationality != null)
            food_culture.setText(food_nationality);

        // TabLayout implementation
        FoodPagerAdapter foodPagerAdapter = new FoodPagerAdapter(this, 2, current_food);
        food_pager.setAdapter(foodPagerAdapter);
        tabLayout.addTab(tabLayout.newTab().setText("محتوا"));
        tabLayout.addTab(tabLayout.newTab().setText("دستور پخت"));
        tabLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        new TabLayoutMediator(tabLayout, food_pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 1)
                    tab.setText(getResources().getString(R.string.recipe));
                else
                    tab.setText(getResources().getString(R.string.contents));
            }
        }
        ).attach();

        // Go back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });
    }

    private void initialize() {
        food_pager = view.findViewById(R.id.food_pager);
        tabLayout = view.findViewById(R.id.tabs_post);
        back = view.findViewById(R.id.back_post);
        food_culture = view.findViewById(R.id.date_created);
        food_title = view.findViewById(R.id.food_name_post); }
}
