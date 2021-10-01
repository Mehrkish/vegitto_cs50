package ir.vegitto.registry.logIn;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import ir.vegitto.api.Repository;
import ir.vegitto.model.AuthStatus;
import ir.vegitto.model.LogInInputs;

public class LogInViewModel extends AndroidViewModel {
    LiveData<AuthStatus> authStatusLiveData;

    private final Repository repo = Repository.getInstance(getApplication());

    public LogInViewModel(@NonNull Application application) { super(application); }

    public void sendLogInInfo(Context context, LogInInputs logInputs){
        authStatusLiveData = repo.logInRequest(context, logInputs);
    }

    LiveData<AuthStatus> getStatus() {
        return authStatusLiveData;
    }
}
