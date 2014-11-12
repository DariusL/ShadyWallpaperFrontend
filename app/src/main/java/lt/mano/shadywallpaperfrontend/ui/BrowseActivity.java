package lt.mano.shadywallpaperfrontend.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import lt.mano.shadywallpaperfrontend.R;

/**
 * Created by Darius on 2014.11.10.
 */
public class BrowseActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, new BoardWallpaperGridFragment())
                .commit();
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
        return NAV_DRAWER_ITEM_WG;
    }
}
