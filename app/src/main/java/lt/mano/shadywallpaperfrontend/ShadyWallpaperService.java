package lt.mano.shadywallpaperfrontend;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Darius on 2014.11.05.
 */
public interface ShadyWallpaperService {

    @GET("/{board}/threads/{page}?r16x9=R1920By1080")
    public void boardWalls(
            @Path("board") String board,
            @Path("page") int page,
            Callback<List<Wallpaper>> callback);
}
