package lt.mano.shadywallpaperfrontend.ui.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Darius on 2014.11.07.
 */
public class GridImageView extends PicassoImageView {

    public GridImageView(Context context) {
        super(context);
    }
    public GridImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public GridImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @TargetApi(21)
    public GridImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}
