package lt.mano.shadywallpaperfrontend.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Darius on 2014.11.13.
 */
public class Thread {
    @SerializedName("Id")
    private long id;
    @SerializedName("OpContent")
    private String opContent;
    @SerializedName("OpPost")
    private Wallpaper opPost;
    @SerializedName("Board")
    private String board;

    public long getId() {
        return id;
    }

    public String getOpContent() {
        return opContent;
    }

    public Wallpaper getOpPost() {
        return opPost;
    }

    public String getBoard() {
        return opPost.getBoard();
    }
}
