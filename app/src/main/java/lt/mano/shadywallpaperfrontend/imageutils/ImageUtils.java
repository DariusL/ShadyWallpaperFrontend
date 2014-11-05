package lt.mano.shadywallpaperfrontend.imageutils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * Created by Darius on 2014.11.05.
 */
public class ImageUtils {

    public enum ScaleType{
        Inside, Crop, Stretch, Uniform
    }

    public static Bitmap convert(Bitmap original, int newWidth, int newHeight, ScaleType scaleType){
        Matrix matrix = new Matrix();
        int originalWidth = original.getWidth();
        int originalHeight = original.getHeight();

        float originalRatio = originalWidth / (float)originalHeight;

        if(scaleType == ScaleType.Uniform){
            if(newWidth == 0)
                newWidth = newHeight * (int)originalRatio;
            else if(newHeight == 0)
                newHeight = newWidth / (int)originalRatio;
            else
                throw new IllegalArgumentException("With ScaleType.Uniform, width or height has to be zero");
            scaleType = ScaleType.Stretch;
        }

        float newRatio = newWidth / (float)newHeight;

        float centerX = newWidth / 2.0f;
        float centerY = newHeight / 2.0f;
        float scaleX;
        float scaleY;

        switch (scaleType){

            case Inside:
                if(newRatio > originalRatio)
                    scaleX = scaleY = (float)newHeight / originalHeight;
                else
                    scaleX = scaleY = (float)newWidth / originalWidth;
                break;
            case Crop:
                if(newRatio < originalRatio)
                    scaleX = scaleY = (float)newHeight / originalHeight;
                else
                    scaleX = scaleY = (float)newWidth / originalWidth;
                break;
            case Uniform:
            case Stretch:
                scaleX = (float)newWidth / originalWidth;
                scaleY = (float)newHeight / originalHeight;
                break;
            default:
                throw new IllegalArgumentException();
        }

        matrix.setScale(scaleX, scaleY, centerX, centerY);
        Bitmap ret = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(ret);
        canvas.setMatrix(matrix);
        canvas.drawBitmap(original, centerX - originalWidth / 2, centerY
                - originalHeight / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
        return ret;
    }
}
