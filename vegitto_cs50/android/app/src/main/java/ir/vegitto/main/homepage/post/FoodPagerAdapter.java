package ir.vegitto.main.homepage.post;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import ir.vegitto.main.homepage.post.contents.FoodContentsFragment;
import ir.vegitto.main.homepage.post.recipe.FoodRecipeFragment;
import ir.vegitto.model.Food;

public class FoodPagerAdapter extends FragmentStateAdapter {
    private final int NUMBER_OF_PAGES;
    private Food current_food;

    public FoodPagerAdapter(@NonNull Fragment fragment, int number_of_pages, Food food) {
        super(fragment);
        NUMBER_OF_PAGES = number_of_pages;
        current_food = food;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("current_food", current_food);
        switch (position) {
            case 1:
                FoodRecipeFragment foodRecipeFragment = new FoodRecipeFragment();
                foodRecipeFragment.setArguments(bundle);
                return foodRecipeFragment;

            case 0:
                FoodContentsFragment foodContentsFragment = new FoodContentsFragment();
                foodContentsFragment.setArguments(bundle);
                return foodContentsFragment;

            default:
                return new Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return NUMBER_OF_PAGES;
    }
}
