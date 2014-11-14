package lt.mano.shadywallpaperfrontend.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import lt.mano.shadywallpaperfrontend.R;
import lt.mano.shadywallpaperfrontend.data.*;
import lt.mano.shadywallpaperfrontend.data.Thread;

/**
 * Created by Darius on 2014.11.10.
 */
public class BrowseActivity extends BaseActivity implements
        BoardWallpaperGridFragment.Callbacks,
        ThreadListFragment.Callbacks,
        BrowseFragment.Callbacks,
        BoardsFragment.Callbacks{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Fragment fragment = new BoardsFragment();
        pushFragment(fragment);
    }

    @Override
    protected void setupToolbar(Toolbar toolbar) {
        super.setupToolbar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_drawer);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNavDrawer();
            }
        });
    }

    @Override
    protected int getSelfNavItem() {
        return NAV_DRAWER_ITEM_BROWSE;
    }

    @Override
    public void onWallpaperClicked(Wallpaper wallpaper) {
        Fragment fragment = new FullscreenImageFragment();
        fragment.setArguments(FullscreenImageFragment.createBundle(wallpaper));
        pushFragment(fragment);
    }

    @Override
    public void onThreadClicked(Thread thread) {
        Fragment fragment = new ThreadWallpaperGridFragment();
        fragment.setArguments(ThreadWallpaperGridFragment.createArgs(thread.getBoard(), thread.getId()));
        pushFragment(fragment);
    }

    @Override
    public void browseThreads(String board) {
        Fragment fragment = new ThreadListFragment();
        fragment.setArguments(ThreadListFragment.createArgs(board));
        pushFragment(fragment);
    }

    @Override
    public void browseWalls(String board) {
        Fragment fragment = new BoardWallpaperGridFragment();
        fragment.setArguments(BoardWallpaperGridFragment.createArgs(board));
        pushFragment(fragment);
    }

    @Override
    public void openBoard(String board) {
        Fragment fragment = new BrowseFragment();
        fragment.setArguments(BrowseFragment.createArgs(board));
        pushFragment(fragment);
    }

    private void pushFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, fragment)
                .addToBackStack("")
                .commit();
    }
}
