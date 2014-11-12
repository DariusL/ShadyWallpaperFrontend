package lt.mano.shadywallpaperfrontend.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lt.mano.shadywallpaperfrontend.R;
import lt.mano.shadywallpaperfrontend.data.Wallpaper;
import lt.mano.shadywallpaperfrontend.net.PagedDataService;
import lt.mano.shadywallpaperfrontend.net.PagedServiceWrapper;
import lt.mano.shadywallpaperfrontend.net.ShadyWallpaperService;
import retrofit.Callback;

/**
 * Created by Darius on 2014.11.05.
 */
public class BoardWallpaperGridFragment extends Fragment implements WallpaperAdapter.OnItemClickListener{

    public static final String ARG_BOARD = "BoardWallpaperGridFragment.arg.board";

    private BaseActivity activity;
    private ShadyWallpaperService service;

    protected String r16by9;
    protected String r4by3;

    protected String r16by9key;
    protected String r16by9def;
    protected String r4by3key;
    protected String r4by3def;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallpaper_grid, container, false);
        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.grid);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recycler.setLayoutManager(layoutManager);
        recycler.setHasFixedSize(true);
        WallpaperAdapter adapter = new WallpaperAdapter(getActivity(), createService(service));
        adapter.setOnItemClickListener(this);
        recycler.setAdapter(adapter);
        return view;
    }

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

    protected PagedServiceWrapper<Wallpaper> createService(final ShadyWallpaperService service){
        final String board = "wg";//getArguments().getString(ARG_BOARD, "wg");
        return new PagedServiceWrapper<Wallpaper>(){

            @Override
            public void get(int page, Callback<List<Wallpaper>> callback) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("r16x9", r16by9);
                map.put("r4by3", r4by3);
                service.boardWalls(board, page, map, callback);
            }
        };
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.activity = (BaseActivity) activity;
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

    @Override
    public void onItemClick(View view, Wallpaper item) {
        Intent intent = new Intent(getActivity(), FullscreenImageActivity.class);
        intent.putExtra(FullscreenImageActivity.ARG_URL, item.getWallUrl());
        startActivity(intent);
    }
}
