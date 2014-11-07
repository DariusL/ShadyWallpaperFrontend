package lt.mano.shadywallpaperfrontend.net;

import java.util.List;

import lt.mano.shadywallpaperfrontend.Wallpaper;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Darius on 2014.11.05.
 */
public interface ShadyWallpaperService {

    @GET("/{board}/walls/{page}?r16x9=R1920By1080")
    public void boardWalls(
            @Path("board") String board,
            @Path("page") int page,
            Callback<List<Wallpaper>> callback);
}