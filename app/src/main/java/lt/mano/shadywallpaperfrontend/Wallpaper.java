package lt.mano.shadywallpaperfrontend;

import java.lang.String;
/**
 * Created by Darius on 2014.11.05.
 */
public class Wallpaper {
    private String wallUrl;
    private String thumbUrl;

    public Wallpaper(String wallUrl, String thumbUrl){
        this.thumbUrl = thumbUrl;
        this.wallUrl = wallUrl;
    }

    public String getWallUrl() {
        return wallUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }
}
