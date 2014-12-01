package lt.mano.shadywallpaperfrontend.ui;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Darius on 2014.12.01.
 */
public abstract class BaseAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    private DataObserver observer = new DataObserver() {
        @Override
        public void dataUpdated() {}
    };

    public void setDataObserver(DataObserver observer){
        this.observer = observer;
    }

    protected void notifyInternal(){
        observer.dataUpdated();
        notifyDataSetChanged();
    }

    public interface DataObserver{
        public void dataUpdated();
    }
}
