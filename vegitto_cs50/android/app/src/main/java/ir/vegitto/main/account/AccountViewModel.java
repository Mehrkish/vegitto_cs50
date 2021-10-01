package ir.vegitto.main.account;

import android.app.Application;

import com.novoda.merlin.MerlinsBeard;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import androidx.lifecycle.LiveData;
import ir.vegitto.api.Repository;
import ir.vegitto.model.UserPrivateInfo;

public class AccountViewModel extends AndroidViewModel {
    private LiveData<UserPrivateInfo> userPrivateInfoLiveData;
    private LiveData<String> accessTokenLiveData;
    private final Repository repo = Repository.getInstance(getApplication());

    public AccountViewModel(@NonNull Application application) {
        super(application);
    }

    void init_refresh(String refreshToken) {
        MerlinsBeard beard = MerlinsBeard.from(getApplication());

        if (beard.isConnected())
            accessTokenLiveData = repo.requestNewAccessToken(refreshToken);
    }

    void init(Integer userId) {
        MerlinsBeard beard = MerlinsBeard.from(getApplication());

        if (beard.isConnected())
            userPrivateInfoLiveData = repo.getUserPrivateInfo_Online(userId);
    }

    LiveData<UserPrivateInfo> getUserPrivateInfoLiveData() {
        return userPrivateInfoLiveData;
    }

    LiveData<String> requestNewAccessToken() {
        return accessTokenLiveData;
    }
}
