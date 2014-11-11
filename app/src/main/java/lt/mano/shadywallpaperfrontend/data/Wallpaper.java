package lt.mano.shadywallpaperfrontend.data;

import com.google.gson.annotations.SerializedName;

import java.lang.String;
/**
 * Created by Darius on 2014.11.05.
 */
public class Wallpaper {
    @SerializedName("WallUrl")
    private String wallUrl;
    @SerializedName("ThumbUrl")
    private String thumbUrl;
    @SerializedName("Id")
    private long id;

    public String getWallUrl() {
        return wallUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public long getId(){
        return id;
    }
}
