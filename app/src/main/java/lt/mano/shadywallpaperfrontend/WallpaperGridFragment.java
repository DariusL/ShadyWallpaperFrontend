package lt.mano.shadywallpaperfrontend;

import android.app.Activity;
import android.content.Context;
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

    private ServiceActivity activity;
    private ShadyWallpaperService service;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (RecyclerView) inflater.inflate(R.layout.fragment_wallpaper_grid, container, false);
        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.grid);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recycler.setLayoutManager(layoutManager);
        recycler.setHasFixedSize(true);
        recycler.setAdapter(new WallpaperAdapter(getActivity(), service));
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.activity = (ServiceActivity) activity;
        this.service = this.activity.getService();
    }

    @Override
    public void onItemClick(View view, Wallpaper item) {
    }

    private Point getWindowSize(){
        WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        manager.getDefaultDisplay().getSize(point);
        return point;
    }
}
