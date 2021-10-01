package ir.vegitto.main;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import io.sentry.Sentry;
import ir.vegitto.R;

public class MainActivity extends AppCompatActivity {
    private static DrawerLayout navDrawer;
    private BottomNavigationView bottomNav;
    private NavHostFragment navHostFragment;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Sentry.captureMessage("testing SDK setup");
        setContentView(R.layout.activity_main);

        initialize();

        if (navHostFragment != null) {
            NavigationUI.setupWithNavController(bottomNav, navHostFragment.getNavController());
        }

        navigationView.setItemIconTintList(null);
        // Disable swipe gesture
        navDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void onBackPressed() {
        if (navDrawer.isDrawerOpen(GravityCompat.START))
            navDrawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    public void initialize() {
        navDrawer = findViewById(R.id.drawer_layout);
        bottomNav = findViewById(R.id.bottom_navigation);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navigationView = findViewById(R.id.nav_view);
    }

    public static DrawerLayout getNavDrawer() {
        return navDrawer;
    }

}
