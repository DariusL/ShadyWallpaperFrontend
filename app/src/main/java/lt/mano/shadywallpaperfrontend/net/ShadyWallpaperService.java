package lt.mano.shadywallpaperfrontend.net;

import java.util.List;
import java.util.Map;

import lt.mano.shadywallpaperfrontend.data.Wallpaper;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;

/**
 * Created by Darius on 2014.11.05.
 */
public interface ShadyWallpaperService {

    @GET("/{board}/walls/{page}")
    public void boardWalls(
        @Path("board") String board,
        @Path("page") int page,
        @QueryMap Map<String, String> filterOps,
        Callback<List<Wallpaper>> callback);

    @GET("/{board}/{thread}/walls/{page}")
    public void threadWalls(
        @Path("board") String board,
        @Path("thread") long thread,
        @Path("page") int page,
        @QueryMap Map<String, String> filterOps,
        Callback<List<Wallpaper>> callback);

    @GET("/{board}/threads/{page}")
    public void threads(
        @Path("board") String board,
        @Path("page") int page,
        @QueryMap Map<String, String> filterOps,
        Callback<List<Wallpaper>> callback);
}