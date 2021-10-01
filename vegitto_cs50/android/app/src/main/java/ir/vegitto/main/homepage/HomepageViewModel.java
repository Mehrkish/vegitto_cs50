package ir.vegitto.main.homepage;

import android.app.Application;

import com.novoda.merlin.MerlinsBeard;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import ir.vegitto.api.Repository;
import ir.vegitto.model.Food;

public class HomepageViewModel extends AndroidViewModel {
    private LiveData<List<Food>> foodsLiveData;
    private final Repository repo = Repository.getInstance(getApplication());

    public HomepageViewModel(@NonNull Application application) {
        super(application);
    }

    void init(HomepageFragment home, int offset) {
        MerlinsBeard beard = MerlinsBeard.from(getApplication());

        if (beard.isConnected())
            foodsLiveData = repo.getOnlineFoods(home, 10, offset);
        else if (offset == 0)
            foodsLiveData = repo.GetOfflineFoods();
    }

    void init(HashMap<String, String> inquiries) {
        foodsLiveData = repo.findFoods(inquiries);
    }

    LiveData<List<Food>> getFoodsLiveData() {
        return foodsLiveData;
    }
}
