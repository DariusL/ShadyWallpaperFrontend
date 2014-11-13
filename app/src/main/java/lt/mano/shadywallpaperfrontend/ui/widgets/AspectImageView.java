package lt.mano.shadywallpaperfrontend.ui.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;

import com.squareup.picasso.Picasso;

/**
 * Created by Darius on 2014.11.14.
 */
public class AspectImageView extends PicassoImageView {
    public AspectImageView(Context context) {
        super(context);
    }
    public AspectImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public AspectImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @TargetApi(21)
    public AspectImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec((int) (widthMeasureSpec * 0.5625), MeasureSpec.EXACTLY));
    }
}
