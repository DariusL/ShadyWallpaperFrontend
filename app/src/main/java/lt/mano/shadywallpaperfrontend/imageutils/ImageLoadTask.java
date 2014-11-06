package lt.mano.shadywallpaperfrontend.imageutils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import lt.mano.shadywallpaperfrontend.Wallpaper;
import lt.mano.shadywallpaperfrontend.WallpaperAdapter;

/**
 * Created by Darius on 2014.11.06.
 */
public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {
    private ImageManager manager;
    private WallpaperAdapter.ViewHolder holder;
    private Wallpaper wallpaper;

    public ImageLoadTask(ImageManager manager, WallpaperAdapter.ViewHolder holder, Wallpaper wallpaper){
        this.manager = manager;
        this.holder = holder;
        this.wallpaper = wallpaper;
    }
    @Override
    protected Bitmap doInBackground(Void... args) {
        return manager.getImage(new ImageManager.ImageArgs(wallpaper.getWallUrl(), ImageUtils.ScaleType.Uniform, 200, 0));
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        holder.setImage(bitmap, wallpaper);
    }


}
