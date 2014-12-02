package lt.mano.shadywallpaperfrontend.data;

import com.google.gson.annotations.SerializedName;

import java.lang.String;
/**
 * Created by Darius on 2014.11.05.
 */
public class Wallpaper {
    @SerializedName("WallUrl")
    private String wallUrl;
    @SerializedName("Id")
    private long id;
    @SerializedName("Board")
    private String board;

    public String getWallUrl() {
        return wallUrl;
    }

    public long getId(){
        return id;
    }

    public String getBoard(){
        return board;
    }
}
