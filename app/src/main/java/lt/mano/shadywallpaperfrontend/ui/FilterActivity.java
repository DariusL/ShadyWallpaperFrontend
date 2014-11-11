package lt.mano.shadywallpaperfrontend.ui;

import android.os.Bundle;
import android.support.v4.preference.PreferenceFragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import lt.mano.shadywallpaperfrontend.R;
import lt.mano.shadywallpaperfrontend.ui.BaseActivity;

/**
 * Created by Darius on 2014.11.09.
 */
public class FilterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_filter);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, new SettingsFragment())
                .commit();
    }

    @Override
    protected void setupToolbar(Toolbar toolbar) {
        super.setupToolbar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected int getSelfNavItem() {
        return NAV_DRAWER_ITEM_FILTER;
    }

    public static class SettingsFragment extends PreferenceFragment{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.settings);
        }
    }
}
