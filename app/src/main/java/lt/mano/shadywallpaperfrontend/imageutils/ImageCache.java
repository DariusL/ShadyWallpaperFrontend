package lt.mano.shadywallpaperfrontend.imageutils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Darius on 2014.11.06.
 */
public class ImageCache {
    private static final String FOLDER = "/ImageCache";
    private DiskLruCache storageCache;

    public ImageCache(Context context){
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            try {
                storageCache = DiskLruCache.open(new File(context.getExternalCacheDir().getAbsolutePath() + FOLDER),
                        1, 1, 20 * 1024 * 1024);
            }catch (IOException e){
                Log.e("", "Failed to open storage cache, " + e.toString());
                e.printStackTrace();
            }
        }
    }

    public InputStream read(int key){
        if(storageCache == null){
            return  null;
        }

        InputStream in = null;
        try{
            DiskLruCache.Snapshot s = storageCache.get(String.valueOf(key));
            if(s != null)
                in = s.getInputStream(0);
        }catch(IOException e){
            Log.e("DiskCacheWrapper", "Error reading item " + key);
            e.printStackTrace();
        }
        return in;
    }

    public void write(int key, CacheWriter writer){
        DiskLruCache.Editor editor = null;
        try{
            editor = storageCache.edit(String.valueOf(key));
            OutputStream out = editor.newOutputStream(0);
            writer.write(out);
            editor.commit();
        }catch(IOException e){
            Log.e("DiskCacheWrapper", "Error writing item " + key);
            e.printStackTrace();
        }
    }

    public interface CacheWriter{
        public void write(OutputStream stream);
    }
}
