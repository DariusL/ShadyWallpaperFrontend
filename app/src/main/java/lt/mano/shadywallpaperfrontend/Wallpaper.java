package lt.mano.shadywallpaperfrontend;

import com.google.gson.annotations.SerializedName;

import java.lang.String;
/**
 * Created by Darius on 2014.11.05.
 */
public class Wallpaper {
    @SerializedName(value="WallUrl")
    public String wallUrl;
    @SerializedName(value="ThumbUrl")
    public String thumbUrl;

    public String getWallUrl() {
        return wallUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }
}
