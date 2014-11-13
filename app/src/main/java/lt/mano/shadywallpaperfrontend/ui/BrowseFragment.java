package lt.mano.shadywallpaperfrontend.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lt.mano.shadywallpaperfrontend.R;

/**
 * Created by Darius on 2014.11.13.
 */
public class BrowseFragment extends Fragment {

    private static final String ARG_BOARD = "BrowseFragment.arg.board";
    private String board;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Callbacks callbacks = (Callbacks) getActivity();
        View view = inflater.inflate(R.layout.fragment_browse, container, false);

        board = getArguments().getString(ARG_BOARD);

        View threads = view.findViewById(R.id.browse_threads);
        threads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callbacks.browseThreads(board);
            }
        });
        View walls = view.findViewById(R.id.browse_walls);
        walls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callbacks.browseWalls(board);
            }
        });

        return view;
    }

    public static Bundle createArgs(String board){
        Bundle bundle = new Bundle();
        bundle.putString(ARG_BOARD, board);
        return bundle;
    }

    public interface Callbacks{
        public void browseThreads(String board);
        public void browseWalls(String board);
    }
}
