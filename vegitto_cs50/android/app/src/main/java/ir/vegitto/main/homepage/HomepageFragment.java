package ir.vegitto.main.homepage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.vegitto.R;
import ir.vegitto.model.Food;
import ir.vegitto.tool.Constants;
import ir.vegitto.tool.EndlessRecyclerViewScrollListener;

public class HomepageFragment extends Fragment {
    private ImageButton search;
    private View view;
    private List<Food> foods = new ArrayList<>();
    private FoodAdapter foodAdapter;
    private RecyclerView recyclerView;
    private HomepageViewModel viewModel;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_homepage, container, false);

            initialize();

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            viewModel = new ViewModelProvider(this).get(HomepageViewModel.class);

            getData(0);

            setRecyclerView(linearLayoutManager);

            EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    getData(page);
                }
            };
            recyclerView.addOnScrollListener(scrollListener);

        } else
            initialize();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = NavHostFragment.findNavController(this);
        final NavBackStackEntry navBackStackEntry = navController.getBackStackEntry(R.id.navigation_home);

        HashMap<String, String> inquiries = new HashMap<>();
        final LifecycleEventObserver observer = new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                if (event.equals(Lifecycle.Event.ON_RESUME)
                        && navBackStackEntry.getSavedStateHandle().contains(Constants.SEARCH_ON)) {
                    // return search inquiries
                    List<String> searched_ingredients = navBackStackEntry.getSavedStateHandle().get(Constants.SEARCH_ON);
                    inquiries.put("search", "");
                    System.out.println(searched_ingredients);
                    if (searched_ingredients != null) {
                        for (String inquiry: searched_ingredients)
                            inquiries.put("ingredients", inquiry);
                        recyclerView.clearOnScrollListeners();
                        getSearchResults(inquiries);
                        navBackStackEntry.getSavedStateHandle().remove("SEARCH_ON");
                    }
                }
            }
        };
        navBackStackEntry.getLifecycle().addObserver(observer);

        getViewLifecycleOwner().getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                if (event.equals(Lifecycle.Event.ON_DESTROY)) {
                    navBackStackEntry.getLifecycle().removeObserver(observer);
                }
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_navigation_search);
            }
        });
    }

    private void initialize() {
        search = view.findViewById(R.id.search);
        recyclerView = view.findViewById(R.id.recipes);
        progressBar = view.findViewById(R.id.homepage_progressbar);
    }

    private void getData(int offset) {
        viewModel.init(this, offset);
        viewModel.getFoodsLiveData().observe(getViewLifecycleOwner(), foodList -> {
            if (foodList != null) {
                foods.addAll(foodList);
                foodList.clear();
                foodAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getSearchResults(HashMap<String, String> inquiries) {
        viewModel.init(inquiries);
        viewModel.getFoodsLiveData().observe(getViewLifecycleOwner(), foodList -> {
            if (foodList != null) {
                foods.clear();
                foods.addAll(foodList);
                foodAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setRecyclerView(LinearLayoutManager linearLayoutManager) {
        foodAdapter = new FoodAdapter(foods);
        recyclerView.setAdapter(foodAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void showProgressView() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressView() {
        progressBar.setVisibility(View.INVISIBLE);
    }

}
