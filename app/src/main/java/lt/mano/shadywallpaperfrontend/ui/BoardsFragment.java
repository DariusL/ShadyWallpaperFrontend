package lt.mano.shadywallpaperfrontend.ui;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import lt.mano.shadywallpaperfrontend.R;

/**
 * Created by Darius on 2014.11.13.
 */
public class BoardsFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static String[] BOARDS = new String[]{"wg", "w"};
    private Callbacks callbacks;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ListView view = (ListView) inflater.inflate(R.layout.fragment_board_list, container, false);
        Context context = getActivity();
        callbacks = (Callbacks) context;
        Resources r = context.getResources();
        ListAdapter adapter = new ArrayAdapter<String>(
                context,
                R.layout.boards_list_item,
                BOARDS
        );
        view.setAdapter(adapter);
        view.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        callbacks.openBoard(BOARDS[i]);
    }

    public interface Callbacks{
        public void openBoard(String board);
    }
}
