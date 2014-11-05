package lt.mano.shadywallpaperfrontend;

import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import lt.mano.shadywallpaperfrontend.imageutils.ImageUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Darius on 2014.11.05.
 */
public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.ViewHolder> {

    private OnItemClickListener listener;
    private List<Wallpaper> wallpaperList;

    private ShadyWallpaperService service;

    public WallpaperAdapter(ShadyWallpaperService service){
        this.service = service;
        service.boardWalls("wg", 1, new Callback<List<Wallpaper>>() {
            @Override
            public void success(List<Wallpaper> wallpapers, Response response) {
                WallpaperAdapter.this.wallpaperList = wallpapers;
                WallpaperAdapter.this.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.e("WallpaperAdapter", "Retrofit error " + retrofitError.toString());
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(ImageView image) {
            super(image);
            image.setOnClickListener(onClickListener);
        }

        public void setImage(Bitmap bitmap, Wallpaper wallpaper){
            ((ImageView)itemView).setImageBitmap(bitmap);
            itemView.setTag(wallpaper);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ImageView view = (ImageView) LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.grid_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Wallpaper wall = wallpaperList.get(i);

        viewHolder.setImage();
    }

    @Override
    public int getItemCount() {
        return wallpaperList.size();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(listener != null)
                listener.onItemClick((Wallpaper) view.getTag());
        }
    };

    public interface OnItemClickListener{
        public void onItemClick(Wallpaper item);
    }
}
