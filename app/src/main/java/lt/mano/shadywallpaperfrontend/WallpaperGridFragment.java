package lt.mano.shadywallpaperfrontend;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lt.mano.shadywallpaperfrontend.net.ShadyWallpaperService;

/**
 * Created by Darius on 2014.11.05.
 */
public class WallpaperGridFragment extends Fragment {

    private ServiceActivity activity;
    private ShadyWallpaperService service;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView view = (RecyclerView) inflater.inflate(R.layout.fragment_wallpaper_grid, container, false);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        view.setLayoutManager(layoutManager);
        view.setHasFixedSize(true);
        view.setAdapter(new WallpaperAdapter(getActivity(), service));
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.activity = (ServiceActivity) activity;
        this.service = this.activity.getService();
    }
}
