package ir.vegitto.registry.signUp;

import android.app.Application;
import android.content.Context;
import android.text.BoringLayout;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import ir.vegitto.api.Repository;
import ir.vegitto.model.AuthStatus;
import ir.vegitto.model.SignUpInputs;

public class SignUpViewModel extends AndroidViewModel {
    LiveData<AuthStatus> authStatusLiveData;
    private final Repository repo = Repository.getInstance(getApplication());

    public SignUpViewModel(@NonNull Application application) {
        super(application);
    }

    void sendSignUpInfo(Context context, SignUpInputs signUpInputs) {
        authStatusLiveData = repo.signUpRequest(context, signUpInputs);
    }

    LiveData<AuthStatus> getStatus() {
        return authStatusLiveData;
    }

}
