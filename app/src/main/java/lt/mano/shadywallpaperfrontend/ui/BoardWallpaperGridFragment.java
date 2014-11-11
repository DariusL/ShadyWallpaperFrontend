package lt.mano.shadywallpaperfrontend.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.List;

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

    protected PagedServiceWrapper<Wallpaper> createService(final ShadyWallpaperService service){
        final String board = getArguments().getString(ARG_BOARD, "wg");
        return new PagedServiceWrapper<Wallpaper>(){

            @Override
            public void get(int page, Callback<List<Wallpaper>> callback) {
                service.boardWalls(board, page, callback);
            }
        };
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.activity = (BaseActivity) activity;
        this.service = this.activity.getService();
    }

    @Override
    public void onItemClick(View view, Wallpaper item) {
        Intent intent = new Intent(getActivity(), FullscreenImageActivity.class);
        intent.putExtra(FullscreenImageActivity.ARG_URL, item.getWallUrl());
        startActivity(intent);
    }
}
