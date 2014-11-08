package lt.mano.shadywallpaperfrontend;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.EGLExt;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Created by Darius on 2014.11.07.
 */
public class FullscreenImageActivity extends ActionBarActivity {

    public static final String ARG_URL = "FullscreenImageActivity.URL";
    public static final String ARG_WIDTH = "FullscreenImageActivity.WIDTH";
    public static final String ARG_HEIGHT = "FullscreenImageActivity.HEIGHT";
    public static final String ARG_POS_X = "FullscreenImageActivity.POS_X";
    public static final String ARG_POS_Y = "FullscreenImageActivity.POS_Y";

    private PicassoImageView fullscreenImage;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_fullscreen_image);
        fullscreenImage = (PicassoImageView) findViewById(R.id.image_fullscreen);
        fullscreenImage.setImage(getIntent().getExtras().getString(ARG_URL));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fullscreen_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_share_image:
                //TODO: implement
                return true;
            default:
                return false;
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
