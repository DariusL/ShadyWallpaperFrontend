package lt.mano.shadywallpaperfrontend.net;

import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Darius on 2014.11.08.
 */
public class PagedDataService <T> {

    private final PagedServiceWrapper<T> serviceWrapper;
    private final List<T> data = new ArrayList<T>();
    private final int pageSize;
    private final ServiceLoadResult<T> listener;

    private boolean allLoaded = false;
    private boolean loading = false;
    private int page = 1;

    public PagedDataService(PagedServiceWrapper<T> service, ServiceLoadResult<T> listener, int pageSize){
        this.serviceWrapper = service;
        this.pageSize = pageSize;
        this.listener = listener;
        loadMore();
    }

    public int getLoadedCount(){
        return data.size();
    }

    public T get(int i){
        if(i + pageSize > getLoadedCount())
            loadMore();

        return data.get(i);
    }

    private void loadMore(){
        if(loading || allLoaded)
            return;

        loading = true;
        serviceWrapper.get(page, callback);
    }

    private Callback<List<T>> callback = new Callback<List<T>>() {
        @Override
        public void success(List<T> ts, Response response) {
            loading = false;
            listener.dataUpdated();
            if(ts.size() > 0){
                page++;
                data.addAll(ts);
            }else{
                allLoaded = true;
                listener.endReached();
            }
        }

        @Override
        public void failure(RetrofitError retrofitError) {
            Log.e("PagedDataService", retrofitError.toString());
            loading = false;
            allLoaded = true;
        }
    };

    public boolean areAllPagesLoaded(){
        return allLoaded;
    }

    public interface ServiceLoadResult<T>{
        public void dataUpdated();
        public void endReached();
    }
 }
