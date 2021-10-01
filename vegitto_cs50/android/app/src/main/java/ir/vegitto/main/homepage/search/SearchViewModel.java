package ir.vegitto.main.homepage.search;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import ir.vegitto.api.Repository;
import ir.vegitto.model.Ingredient;

public class SearchViewModel extends AndroidViewModel {
    private LiveData<List<Ingredient>> ingredientsLiveData;
    private Repository repo = Repository.getInstance(getApplication());

    public SearchViewModel(@NonNull Application application) {
        super(application);
    }

    void init() {
        ingredientsLiveData = repo.getAllIngredients();
    }

    LiveData<List<Ingredient>> getIngredientsLiveData() {
        return ingredientsLiveData;
    }
}
