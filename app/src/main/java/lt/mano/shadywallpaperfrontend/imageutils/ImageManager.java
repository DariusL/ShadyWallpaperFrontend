package lt.mano.shadywallpaperfrontend.imageutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Environment;
import android.util.Log;
import android.view.WindowManager;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import lt.mano.shadywallpaperfrontend.net.NetworkingUtils;

/**
 * Created by Darius on 2014.11.06.
 */
public class ImageManager {

    private static ImageManager instance;
    private ImageCache cache;
    private Context context;

    public static synchronized ImageManager getInstance(Context context){
        if(instance == null)
            instance = new ImageManager(context);
        return instance;
    }

    private ImageManager(Context context){
        cache = new ImageCache(context);
        this.context = context;
    }

    public static class ImageArgs{
        public final String url;
        public final ImageUtils.ScaleType scaleType;
        public final int width;
        public final int height;

        public ImageArgs(String url, ImageUtils.ScaleType scaleType, int width, int height){
            this.url = url;
            this.scaleType = scaleType;
            this.width = width;
            this.height = height;
        }

    }

    public Bitmap getImage(final ImageArgs args){
        final int key = args.url.hashCode();

        InputStream stream = cache.read(key);

        if(stream == null){
            NetworkingUtils.get(args.url, new NetworkingUtils.NetworkReader() {
                @Override
                public void read(final InputStream urlStream) {
                    cache.write(key, new ImageCache.CacheWriter() {
                        @Override
                        public void write(final OutputStream fileStream) {
                            try {
                                byte[] buffer = new byte[1024]; // Adjust if you want
                                int bytesRead;
                                while ((bytesRead = urlStream.read(buffer)) != -1) {
                                    fileStream.write(buffer, 0, bytesRead);
                                }
                                fileStream.close();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
            stream = cache.read(key);
        }

        int width = getWindowSize();
        BitmapFactory.Options ops = new BitmapFactory.Options();
        ops.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(stream, null, ops);

        try{
            stream.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        stream = cache.read(key);

        int bitmapWidth = ops.outWidth > ops.outHeight ? ops.outWidth : ops.outHeight;

        int sample = bitmapWidth / width;
        sample--;
        if(sample < 1)
            sample = 1;

        ops.inSampleSize = sample;
        ops.inDither = true;
        ops.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeStream(stream, null, ops);

        return ImageUtils.convert(bitmap, args.width, args.height, args.scaleType);
    }

    private int getWindowSize(){
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        manager.getDefaultDisplay().getSize(size);
        return size.x > size.y ? size.x : size.y;
    }
}
