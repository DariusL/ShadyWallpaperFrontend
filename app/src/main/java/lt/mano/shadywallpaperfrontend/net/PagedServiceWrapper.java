package lt.mano.shadywallpaperfrontend.net;

import java.util.List;

import retrofit.Callback;

/**
 * Created by Darius on 2014.11.08.
 */
public interface PagedServiceWrapper <T> {
    void get(int page, Callback<List<T>> callback);
}
