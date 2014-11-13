package lt.mano.shadywallpaperfrontend.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lt.mano.shadywallpaperfrontend.R;
import lt.mano.shadywallpaperfrontend.data.*;
import lt.mano.shadywallpaperfrontend.data.Thread;
import lt.mano.shadywallpaperfrontend.net.PagedServiceWrapper;
import lt.mano.shadywallpaperfrontend.net.ShadyWallpaperService;
import retrofit.Callback;

/**
 * Created by Darius on 2014.11.13.
 */
public class ThreadListFragment extends ServiceFragment implements ThreadAdapter.OnItemClickListener{

    protected static final String ARG_BOARD = "BoardWallpaperGridFragment.arg.board";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thread_list, container, false);
        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        recycler.setLayoutManager(layoutManager);
        ThreadAdapter adapter = new ThreadAdapter(activity, createService(service));
        adapter.setOnItemClickListener(this);
        recycler.setAdapter(adapter);
        return view;
    }

    protected PagedServiceWrapper<Thread> createService(final ShadyWallpaperService service){
        final String board = getArguments().getString(ARG_BOARD, "wg");
        return new PagedServiceWrapper<Thread>(){

            @Override
            public void get(int page, Callback<List<Thread>> callback) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("r16x9", r16by9);
                map.put("r4by3", r4by3);
                service.threads(board, page, map, callback);
            }
        };
    }

    @Override
    public void onItemClick(View view, Thread item) {
        activity.onThreadClicked(item);
    }

    public static Bundle createArgs(String board){
        Bundle bundle = new Bundle();
        bundle.putString(ARG_BOARD, board);
        return bundle;
    }

    public interface Callbacks{
        public void onThreadClicked(Thread wallpaper);
    }
}
