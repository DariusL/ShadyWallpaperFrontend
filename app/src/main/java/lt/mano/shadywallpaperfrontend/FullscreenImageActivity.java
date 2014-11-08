package lt.mano.shadywallpaperfrontend;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.opengl.EGLExt;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
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

    private File imagePath;

    private PicassoImageView fullscreenImage;
    private View overlay;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_fullscreen_image);

        overlay = findViewById(R.id.overlay);

        fullscreenImage = (PicassoImageView) findViewById(R.id.image_fullscreen);
        url = getIntent().getExtras().getString(ARG_URL);
        fullscreenImage.setImage(url);

        imagePath = new File(getExternalCacheDir().getAbsolutePath() + "/" + url.replace('/', '*'));
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
                share();
                return true;
            default:
                return false;
        }
    }

    private Target imageTarget = new Target() {
        @Override
        public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    BitmapFactory.Options ops = new BitmapFactory.Options();
                    ops.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(imagePath.getAbsolutePath(), ops);
                    //first time sharing this file
                    if(ops.outHeight < 0 || ops.outWidth < 0) {

                        FileOutputStream out = null;
                        try {
                            out = new FileOutputStream(imagePath);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                        } catch (IOException e) {
                            e.printStackTrace();
                            onBitmapFailed(null);
                        } finally {
                            try {
                                if (out != null) {
                                    out.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("image/bmp");
                            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(imagePath));
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivity(Intent.createChooser(intent, "Share image"));
                            overlay.setVisibility(View.GONE);
                        }
                    });
                }
            }).start();
        }

        @Override
        public void onBitmapFailed(Drawable drawable) {
            overlay.setVisibility(View.GONE);
            //TODO: dialog on failure
        }

        @Override
        public void onPrepareLoad(Drawable drawable) {
        }
    };

    private void share(){
        overlay.setVisibility(View.VISIBLE);
        Picasso.with(this)
                .load(url)
                .into(imageTarget);
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
