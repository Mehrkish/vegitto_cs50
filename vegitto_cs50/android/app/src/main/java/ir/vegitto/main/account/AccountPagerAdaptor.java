package ir.vegitto.main.account;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import ir.vegitto.main.account.favoritePost.FavoritePost;
import ir.vegitto.main.account.userPost.UserPost;
import ir.vegitto.model.UserPrivateInfo;

public class AccountPagerAdaptor extends FragmentStateAdapter {
    private final int NUMBER_OF_PAGES;

    public AccountPagerAdaptor(@NonNull Fragment fragment, int number_of_pages) {
        super(fragment);
        NUMBER_OF_PAGES = number_of_pages;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new UserPost();

            case 1:
                return new FavoritePost();

            default:
                return new Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return NUMBER_OF_PAGES;
    }
}
