package lt.mano.shadywallpaperfrontend.ui.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;

import com.squareup.picasso.Picasso;

/**
 * Created by Darius on 2014.11.08.
 */
public class FullscreenImageView extends PicassoImageView {
    public FullscreenImageView(Context context) {
        super(context);
    }

    public FullscreenImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullscreenImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @TargetApi(21)
    public FullscreenImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void setImage() {
        Picasso.with(getContext())
                .load(url)
                .resize(getWidth(), getHeight())
                .centerInside()
                .into(this);
    }
}
