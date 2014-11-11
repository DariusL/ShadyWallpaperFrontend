package lt.mano.shadywallpaperfrontend.ui.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Darius on 2014.11.07.
 */
public class PicassoImageView extends ImageView{
    protected String url = null;
    protected boolean measured = false;

    public PicassoImageView(Context context) {
        super(context);
    }
    public PicassoImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public PicassoImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @TargetApi(21)
    public PicassoImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        measured = true;
        if (url != null) {
            setImage();
        }
    }

    public void setImage(String url){
        this.url = url;
        if(measured){
            setImage();
        }
    }

    protected void setImage(){
        Picasso.with(getContext())
                .load(url)
                .resize(getWidth(), getHeight())
                .centerCrop()
                .into(this);
    }
}
