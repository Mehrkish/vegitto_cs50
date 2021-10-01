package ir.vegitto.main.homepage.search;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import com.hootsuite.nachos.NachoTextView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import ir.vegitto.R;
import ir.vegitto.model.Ingredient;
import ir.vegitto.tool.Constants;

public class SearchFragment extends DialogFragment {
    private View view;
    private NachoTextView searchBar;
    private ImageButton search;
    private SearchViewModel viewModel;
    private List<Ingredient> ingredientList = new ArrayList<>();
    private ArrayList<String> suggestions = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_search, container, false);

            initialize();

            setSuggestions();

            int width = getScreenWidth() * 2 / 3;
            searchBar.setWidth(width);

        }

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = NavHostFragment.findNavController(this);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> searched_ingredients = new ArrayList<>();
                searched_ingredients = searchBar.getChipValues();
                Objects.requireNonNull(navController.getPreviousBackStackEntry()).getSavedStateHandle().set(Constants.SEARCH_ON, searched_ingredients);
                dismiss();
            }
        });
    }

    private void initialize() {
        searchBar = view.findViewById(R.id.search_bar_auto);
        search = view.findViewById(R.id.search_btn);
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
    }

    private int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    private void setSuggestions() {
        viewModel.init();
        viewModel.getIngredientsLiveData().observe(getViewLifecycleOwner(), ingredients -> {
            if (ingredients != null) {
                ingredientList.clear();
                ingredientList.addAll(ingredients);
                for(Ingredient ingredient: ingredientList){
                    suggestions.add(ingredient.getName());
                }
                adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_dropdown_item_1line, suggestions);
                searchBar.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
