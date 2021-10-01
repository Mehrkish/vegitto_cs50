package ir.vegitto.main.account;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.auth0.android.jwt.JWT;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import ir.vegitto.R;
import ir.vegitto.main.MainActivity;
import ir.vegitto.tool.AccessTokenHolder;
import ir.vegitto.tool.Constants;

public class AccountFragment extends Fragment {
    private ViewPager2 viewPager;
    private DrawerLayout navDrawer;
    private TextView username, followers, following, bio;
    private ImageButton menu, direct, share;
    private TabLayout tabLayout;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_account, container, false);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AccountViewModel viewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        // Check if accessToken is valid
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String refreshToken = preferences.getString(Constants.REFRESH_TOKEN_PREFERENCES_KEY, "");
        viewModel.init_refresh(refreshToken);

        JWT jwt_refresh = new JWT(refreshToken);
        Integer userId = jwt_refresh.getClaim("user_id").asInt();
        if (AccessTokenHolder.getAccessToken() != null) {
            JWT jwt_access = new JWT(AccessTokenHolder.getAccessToken());
            if (jwt_access.isExpired(10)) {
                viewModel.requestNewAccessToken().observe(getViewLifecycleOwner(), newAccessToken -> {
                    if (newAccessToken != null) {
                        AccessTokenHolder.setAccessToken(newAccessToken);
                    }
                });
            }
        } else {
            viewModel.requestNewAccessToken().observe(getViewLifecycleOwner(), newAccessToken -> {
                if (newAccessToken != null) {
                    AccessTokenHolder.setAccessToken(newAccessToken);
                }
            });
        }

        initialize();

        viewModel.init(userId);
        viewModel.getUserPrivateInfoLiveData().observe(getViewLifecycleOwner(), userPrivateInfo -> {
            if (userPrivateInfo != null) {
                username.setText(userPrivateInfo.getUsername());
                followers.setText(kmConverter(8000000));
                following.setText(kmConverter(8));
                bio.setText("با من زندگیت سبز می‌شود ...");
            }
        });

        AccountPagerAdaptor accountPagerAdapter = new AccountPagerAdaptor(this, 2);
        viewPager.setAdapter(accountPagerAdapter);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.userPost));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.favoritePost));
        tabLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0)
                tab.setText(getResources().getString(R.string.userPost));
            else
                tab.setText(getResources().getString(R.string.favoritePost));
        }
        ).attach();

        menu.setOnClickListener(v -> {
            navDrawer = MainActivity.getNavDrawer();
            // If the navigation drawer is not open then open it, if its already open then close it.
            if (!navDrawer.isDrawerOpen(GravityCompat.START))
                navDrawer.openDrawer(GravityCompat.START);
            else
                navDrawer.closeDrawer(GravityCompat.END);
        });
    }

    private static String kmConverter(long count) {
        if (count < 1000) return "" + count;
        int exp = (int) (Math.log(count) / Math.log(1000));
        DecimalFormat format = new DecimalFormat("0.#");
        String value = format.format(count / Math.pow(1000, exp));
        return String.format("%s%c", value, "kmbtpe".charAt(exp - 1));
    }

    private void initialize() {
        menu = view.findViewById(R.id.menu_icon);
        direct = view.findViewById(R.id.direct_icon);
        share = view.findViewById(R.id.share_icon);
        bio = view.findViewById(R.id.profile_bio);
        viewPager = view.findViewById(R.id.profile_pager);
        tabLayout = view.findViewById(R.id.profile_tabs);
        username = view.findViewById(R.id.profile_username);
        followers = view.findViewById(R.id.profile_followers_number);
        following = view.findViewById(R.id.profile_followings_number);
    }
}