package ir.vegitto.api;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import ir.vegitto.main.homepage.HomepageFragment;
import ir.vegitto.model.AuthResponse;
import ir.vegitto.model.AuthStatus;
import ir.vegitto.model.Food;
import ir.vegitto.model.FoodResponse;
import ir.vegitto.model.Ingredient;
import ir.vegitto.model.LogInInputs;
import ir.vegitto.model.SignUpInputs;
import ir.vegitto.model.UserPrivateInfo;
import ir.vegitto.roomDB.FoodDao;
import ir.vegitto.roomDB.FoodDataBase;
import ir.vegitto.tool.AccessTokenHolder;
import ir.vegitto.tool.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private static Repository repository;
    private final ApiClient apiClient;
    private final FoodDao foodDao;

    private Repository(Application application) {
        apiClient = RetrofitService.getRetrofitInstance().create(ApiClient.class);
        FoodDataBase foodDataBase = FoodDataBase.getInstance(application);
        foodDao = foodDataBase.foodDao();
    }

    public static Repository getInstance(Application application) {
        if (repository == null) {
            repository = new Repository(application);
        }
        return repository;
    }

    // online mode
    // get online foods from server
    public LiveData<List<Food>> getOnlineFoods(HomepageFragment home, int limit, int offset) {
        MutableLiveData<List<Food>> foodLiveData = new MutableLiveData<>();
        home.showProgressView();
        Call<FoodResponse> call = apiClient.getFoodResponse(limit, offset);
        call.enqueue(new Callback<FoodResponse>() {
            @Override
            public void onResponse(@NotNull Call<FoodResponse> call, @NotNull Response<FoodResponse> response) {
                if (response.body() != null) {
                    foodLiveData.postValue(response.body().getResults());
                    if (offset == 0)
                        updateRoom(response.body().getResults());
                    home.hideProgressView();
                }
            }

            @Override
            public void onFailure(@NotNull Call<FoodResponse> call, @NotNull Throwable t) {
                Log.d("on_failure", Objects.requireNonNull(t.getMessage()));
                foodLiveData.postValue(null);
                home.hideProgressView();
            }
        });

        return foodLiveData;
    }

    // fetch all ingredients from the server
    public LiveData<List<Ingredient>> getAllIngredients() {
        MutableLiveData<List<Ingredient>> ingredientsLiveData = new MutableLiveData<>();
        Call<List<Ingredient>> call = apiClient.getIngredients();
        call.enqueue(new Callback<List<Ingredient>>() {
            @Override
            public void onResponse(@NotNull Call<List<Ingredient>> call, @NotNull Response<List<Ingredient>> response) {
                if (response.body() != null) {
                    ingredientsLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Ingredient>> call, @NotNull Throwable t) {
                Log.d("on_failure", Objects.requireNonNull(t.getMessage()));
                ingredientsLiveData.postValue(null);
            }
        });
        return ingredientsLiveData;
    }

    // find your desired input
    public LiveData<List<Food>> findFoods(HashMap<String, String> inquiries) {
        MutableLiveData<List<Food>> foodLiveData = new MutableLiveData<>();
        Call<List<Food>> call = apiClient.findFoods(inquiries);
        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(@NotNull Call<List<Food>> call, @NotNull Response<List<Food>> response) {
                if (response.body() != null) {
                    foodLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Food>> call, @NotNull Throwable t) {
                Log.d("on_failure", Objects.requireNonNull(t.getMessage()));
                foodLiveData.postValue(null);
            }
        });

        return foodLiveData;
    }

    // post SignInInputs to the server
    public LiveData<AuthStatus> signUpRequest(Context context, SignUpInputs signUpInputs) {
        MutableLiveData<AuthStatus> status = new MutableLiveData<>();
        Call<AuthResponse> call = apiClient.registerNewUser(signUpInputs);
        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(@NotNull Call<AuthResponse> call, @NotNull Response<AuthResponse> response) {
                String error = "error";
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getAccessToken() != null) {
                        AccessTokenHolder.setAccessToken(response.body().getAccessToken());
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor = preferences.edit();
                        String newRefreshToken = null;
                        newRefreshToken = response.body().getRefreshToken();
                        if (newRefreshToken != null)
                            editor.putString(Constants.REFRESH_TOKEN_PREFERENCES_KEY, newRefreshToken);
                        editor.apply();
                    }
                    status.postValue(new AuthStatus(true, "user registered successfully"));
                } else if (response.code() == 400) {
                    assert response.errorBody() != null;
                    try {
                        error = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    status.setValue(new AuthStatus(false, error));
                }
            }

            @Override
            public void onFailure(@NotNull Call<AuthResponse> call, @NotNull Throwable t) {
                status.postValue(new AuthStatus(false, t.getMessage()));
            }
        });
        return status;
    }

    // post LogInInputs to server
    public LiveData<AuthStatus> logInRequest(Context context, LogInInputs logInputs) {
        MutableLiveData<AuthStatus> status = new MutableLiveData<>();
        Call<AuthResponse> call = apiClient.authenticateUser(logInputs);
        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(@NotNull Call<AuthResponse> call, @NotNull Response<AuthResponse> response) {
                String error = "Error";
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getAccessToken() != null) {
                        AccessTokenHolder.setAccessToken(response.body().getAccessToken());
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor = preferences.edit();
                        String newRefreshToken = null;
                        newRefreshToken = response.body().getRefreshToken();
                        if (newRefreshToken != null)
                            editor.putString(Constants.REFRESH_TOKEN_PREFERENCES_KEY, newRefreshToken);
                        editor.apply();
                    }
                    status.postValue(new AuthStatus(true, "user authenticated successfully"));
                } else {
                    try {
                        assert response.errorBody() != null;
                        error = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    status.postValue(new AuthStatus(false, error));
                }
            }

            @Override
            public void onFailure(@NotNull Call<AuthResponse> call, @NotNull Throwable t) {
                status.postValue(new AuthStatus(false, t.getMessage()));
            }
        });
        return status;
    }

    // user's private profile info
    public LiveData<UserPrivateInfo> getUserPrivateInfo_Online(int userId) {
        MutableLiveData<UserPrivateInfo> userInfoLiveData = new MutableLiveData<>();
        Call<UserPrivateInfo> call = apiClient.getUserPrivateInfo(userId, "Bearer " + AccessTokenHolder.getAccessToken());
        call.enqueue(new Callback<UserPrivateInfo>() {
            @Override
            public void onResponse(@NotNull Call<UserPrivateInfo> call, @NotNull Response<UserPrivateInfo> response) {
                if (response.isSuccessful()) {
                    userInfoLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserPrivateInfo> call, @NotNull Throwable t) {
                Log.d("on_failure", Objects.requireNonNull(t.getMessage()));
                userInfoLiveData.postValue(null);
            }
        });
        return userInfoLiveData;
    }

    // get a new accessToken
    public LiveData<String> requestNewAccessToken(String refreshToken) {
        MutableLiveData<String> accessTokenLiveData = new MutableLiveData<>();
        Call<String> call = apiClient.requestNewAccessToken(refreshToken);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                if (response.isSuccessful()) {
                    accessTokenLiveData.postValue(response.body());
                }

                try {
                    assert response.errorBody() != null;
                    System.out.println(response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                Log.d("on_failure", Objects.requireNonNull(t.getMessage()));
                accessTokenLiveData.postValue(null);
            }
        });
        return accessTokenLiveData;
    }

    // get all foods
    public LiveData<List<Food>> GetOfflineFoods() {
        GetAllFoodsAsyncTask foodsAsyncTask = new GetAllFoodsAsyncTask(foodDao);
        foodsAsyncTask.execute();
        LiveData<List<Food>> foodsLiveData = null;
        try {
            foodsLiveData = foodsAsyncTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return foodsLiveData;
    }

    private static class GetAllFoodsAsyncTask extends AsyncTask<Void, Void, LiveData<List<Food>>> {

        private final FoodDao foodDao;

        private GetAllFoodsAsyncTask(FoodDao foodDao) {
            this.foodDao = foodDao;
        }

        @Override
        protected LiveData<List<Food>> doInBackground(Void... voids) {
            return foodDao.getAllFoods();
        }
    }

    // insert food into room
    private static class InsertFoodAsyncTask extends AsyncTask<Food, Void, Void> {

        private final FoodDao foodDao;

        private InsertFoodAsyncTask(FoodDao foodDao) {
            this.foodDao = foodDao;
        }

        @Override
        protected Void doInBackground(Food... foods) {
            foodDao.Insert(foods[0]);
            return null;
        }
    }

    private void Insert(Food food) {
        new InsertFoodAsyncTask(foodDao).execute(food);
    }

    // delete all foods from room
    private static class DeleteAllFoodAsyncTask extends AsyncTask<Void, Void, Void> {

        private final FoodDao foodDao;

        private DeleteAllFoodAsyncTask(FoodDao foodDao) {
            this.foodDao = foodDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            foodDao.DeleteAll();
            return null;
        }
    }

    private void DeleteAll() {
        new DeleteAllFoodAsyncTask(foodDao).execute();
    }

    // update room
    private void updateRoom(List<Food> foods) {
        repository.DeleteAll();
        for (Food food : foods) {
            repository.Insert(food);
        }
    }

    private static String getAccessToken() {
        return "Bearer " + AccessTokenHolder.getAccessToken();
    }
}