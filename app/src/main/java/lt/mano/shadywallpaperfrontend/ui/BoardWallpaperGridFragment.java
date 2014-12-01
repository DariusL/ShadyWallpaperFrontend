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
public class BoardWallpaperGridFragment extends ServiceFragment implements WallpaperAdapter.OnItemClickListener{

    protected static final String ARG_BOARD = "BoardWallpaperGridFragment.arg.board";
    private View loadingOverlay;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallpaper_grid, container, false);
        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.grid);
        loadingOverlay = view.findViewById(R.id.loading);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), findColumnCount());
        recycler.setLayoutManager(layoutManager);
        recycler.setHasFixedSize(true);
        WallpaperAdapter adapter = new WallpaperAdapter(getActivity(), createService(service));
        adapter.setDataObserver(new BaseAdapter.DataObserver() {
            @Override
            public void dataUpdated() {
                loadingOverlay.setVisibility(View.GONE);
            }
        });
        adapter.setOnItemClickListener(this);
        recycler.setAdapter(adapter);
        return view;
    }

    protected PagedServiceWrapper<Wallpaper> createService(final ShadyWallpaperService service){
        final String board = getArguments().getString(ARG_BOARD, "wg");
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

    private int findColumnCount(){
        WindowManager manager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        manager.getDefaultDisplay().getSize(point);
        int width = activity.getResources().getDimensionPixelSize(R.dimen.grid_column_min_width);
        return point.x / width;
    }

    @Override
    public void onItemClick(View view, Wallpaper item) {
        activity.onWallpaperClicked(item);
    }

    public static Bundle createArgs(String board){
        Bundle bundle = new Bundle();
        bundle.putString(ARG_BOARD, board);
        return bundle;
    }

    public interface Callbacks{
        public void onWallpaperClicked(Wallpaper wallpaper);
    }
}
