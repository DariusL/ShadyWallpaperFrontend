package lt.mano.shadywallpaperfrontend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import lt.mano.shadywallpaperfrontend.net.ShadyWallpaperService;

/**
 * Created by Darius on 2014.11.05.
 */
public class WallpaperGridFragment extends Fragment implements WallpaperAdapter.OnItemClickListener{

    private static final String STATE_SCROLL = "WallpaperGridFragment.State.Scroll";

    private MainActivity activity;
    private ShadyWallpaperService service;
    private RecyclerView recycler;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallpaper_grid, container, false);
        recycler = (RecyclerView) view.findViewById(R.id.grid);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recycler.setLayoutManager(layoutManager);
        recycler.setHasFixedSize(true);
        WallpaperAdapter adapter = new WallpaperAdapter(getActivity(), service);
        adapter.setOnItemClickListener(this);
        recycler.setAdapter(adapter);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.activity = (MainActivity) activity;
        this.service = this.activity.getService();
    }

    @Override
    public void onItemClick(View view, Wallpaper item) {
        Intent intent = new Intent(getActivity(), FullscreenImageActivity.class);
        intent.putExtra(FullscreenImageActivity.ARG_URL, item.getWallUrl());
        startActivity(intent);
    }

    private Point getWindowSize(){
        WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        manager.getDefaultDisplay().getSize(point);
        return point;
    }
}
