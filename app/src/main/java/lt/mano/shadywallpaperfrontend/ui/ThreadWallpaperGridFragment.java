package lt.mano.shadywallpaperfrontend.ui;

import android.os.Bundle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lt.mano.shadywallpaperfrontend.data.Wallpaper;
import lt.mano.shadywallpaperfrontend.net.PagedServiceWrapper;
import lt.mano.shadywallpaperfrontend.net.ShadyWallpaperService;
import retrofit.Callback;

/**
 * Created by Darius on 2014.11.12.
 */
public class ThreadWallpaperGridFragment extends BoardWallpaperGridFragment{

    protected static final String ARG_THREAD = "ThreadWallpaperGridFragment.arg.thread";

    @Override
    protected PagedServiceWrapper<Wallpaper> createService(final ShadyWallpaperService service) {
        final String board = "wg";//getArguments().getString(ARG_BOARD, "wg");
        final int thread = 0;
        return new PagedServiceWrapper<Wallpaper>(){

            @Override
            public void get(int page, Callback<List<Wallpaper>> callback) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("r16x9", r16by9);
                map.put("r4by3", r4by3);
                service.threadWalls(board, thread, page, map, callback);
            }
        };
    }

    public static Bundle createArgs(String board, long thread){
        Bundle bundle = new Bundle();
        bundle.putString(ARG_BOARD, board);
        bundle.putLong(ARG_THREAD, thread);
        return bundle;
    }
}
