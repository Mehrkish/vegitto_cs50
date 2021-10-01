package ir.vegitto.api;

import java.util.List;
import java.util.Map;

import ir.vegitto.model.AuthResponse;
import ir.vegitto.model.Food;
import ir.vegitto.model.FoodResponse;
import ir.vegitto.model.Ingredient;
import ir.vegitto.model.LogInInputs;
import ir.vegitto.model.SignUpInputs;
import ir.vegitto.model.UserPrivateInfo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiClient {

    @GET("foods/foods")
    Call<FoodResponse> getFoodResponse(
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("foods/foods")
    Call<List<Food>> findFoods(
            @QueryMap Map<String, String> inquiries
    );

    @GET("foods/ingredients")
    Call<List<Ingredient>> getIngredients();

    @GET("auth/users/{id}/")
    Call<UserPrivateInfo> getUserPrivateInfo(
            @Path("id") int userId,
            @Header("Authorization") String accessToken
    );

    @POST("auth/token/refresh")
    Call<String> requestNewAccessToken(
            @Body String refresh
    );

    @POST("auth/users/signup/")
    Call<AuthResponse> registerNewUser(
            @Body SignUpInputs signUpInputs
    );

    @POST("auth/users/login/")
    Call<AuthResponse> authenticateUser(
            @Body LogInInputs loginInputs
    );

}
