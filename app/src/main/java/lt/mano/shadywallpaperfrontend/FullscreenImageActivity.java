package lt.mano.shadywallpaperfrontend;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Created by Darius on 2014.11.07.
 */
public class FullscreenImageActivity extends ActionBarActivity {

    private static final String ARG_URL = "FullscreenImageActivity.URL";
    private static final String ARG_WIDTH = "FullscreenImageActivity.WIDTH";
    private static final String ARG_HEIGHT = "FullscreenImageActivity.HEIGHT";
    private static final String ARG_POS_X = "FullscreenImageActivity.POS_X";
    private static final String ARG_POS_Y = "FullscreenImageActivity.POS_Y";

    private PicassoImageView fullscreenImage;
    private ImageView transitionImage;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen_image);

        fullscreenImage = (PicassoImageView) findViewById(R.id.image_fullscreen);
        transitionImage = (ImageView) findViewById(R.id.image_transition);

        Bundle data = getIntent().getExtras();
        url = data.getString(ARG_URL);

        fullscreenImage.setImage(url);
        if(savedInstanceState == null){

            int width = data.getInt(ARG_WIDTH);
            int height = data.getInt(ARG_HEIGHT);

            try{
                Bitmap bitmap = Picasso
                        .with(this)
                        .load(url)
                        .resize(width, height)
                        .get();

                transitionImage.setImageBitmap(bitmap);



            }catch (IOException e){

            }

        }else{
            fullscreenImage.setVisibility(View.VISIBLE);
        }
    }

    public static Bundle createBundle(Wallpaper wallpaper, int width, int height, int posX, int posY){
        Bundle bundle = new Bundle();
        bundle.putString(ARG_URL, wallpaper.getWallUrl());
        bundle.putInt(ARG_HEIGHT, height);
        bundle.putInt(ARG_WIDTH, width);
        bundle.putInt(ARG_POS_X, posX);
        bundle.putInt(ARG_POS_Y, posY);
        return bundle;
    }
}
