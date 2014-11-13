package lt.mano.shadywallpaperfrontend.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;

import lt.mano.shadywallpaperfrontend.R;
import lt.mano.shadywallpaperfrontend.net.ShadyWallpaperService;

/**
 * Created by Darius on 2014.11.13.
 */
public class ServiceFragment extends Fragment {

    protected BrowseActivity activity;
    protected ShadyWallpaperService service;

    protected String r16by9;
    protected String r4by3;

    protected String r16by9key;
    protected String r16by9def;
    protected String r4by3key;
    protected String r4by3def;

    private void loadStrings(){
        Resources r = activity.getResources();
        r16by9def = r.getString(R.string.filter_default_r16by9);
        r16by9key = r.getString(R.string.filter_key_r16by9);
        r4by3def = r.getString(R.string.filter_default_r4by3);
        r4by3key = r.getString(R.string.filter_key_r4by3);
    }

    private SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            updateResolutions(sharedPreferences);
        }
    };

    private void updateResolutions(SharedPreferences sharedPreferences){
        r16by9 = sharedPreferences.getString(r16by9key, r16by9def);
        r4by3 = sharedPreferences.getString(r4by3key, r4by3def);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.activity = (BrowseActivity) activity;
        this.service = this.activity.getService();
        loadStrings();
        updateResolutions(PreferenceManager.getDefaultSharedPreferences(activity));
        PreferenceManager
                .getDefaultSharedPreferences(activity)
                .registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        PreferenceManager
                .getDefaultSharedPreferences(activity)
                .unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }
}
