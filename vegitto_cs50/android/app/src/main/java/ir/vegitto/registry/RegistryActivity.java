package ir.vegitto.registry;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;

import androidx.appcompat.app.AppCompatActivity;
import ir.vegitto.R;
import ir.vegitto.main.MainActivity;
import ir.vegitto.tool.AccessTokenHolder;
import ir.vegitto.tool.Constants;

public class RegistryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Check if the user has already registered
        if (!preferences.contains(Constants.REFRESH_TOKEN_PREFERENCES_KEY)) {
            setContentView(R.layout.activity_registry);
        } else {
            JWT jwt = new JWT(preferences.getString(Constants.REFRESH_TOKEN_PREFERENCES_KEY, ""));
            if (!jwt.isExpired(10))
                startMainActivity();
            else
                setContentView(R.layout.activity_registry);
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
