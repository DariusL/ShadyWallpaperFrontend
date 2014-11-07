package lt.mano.shadywallpaperfrontend;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import lt.mano.shadywallpaperfrontend.net.ShadyWallpaperService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Darius on 2014.11.05.
 */
public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.ViewHolder> {

    private Context context;
    private OnItemClickListener listener;
    private List<Wallpaper> wallpaperList;

    private ShadyWallpaperService service;

    public WallpaperAdapter(Context context, ShadyWallpaperService service){
        this.service = service;
        this.context = context;

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
        private Wallpaper wallpaper;

        public ViewHolder(ImageView image) {
            super(image);
            image.setOnClickListener(onClickListener);
        }

        public synchronized void setImage(Wallpaper wallpaper){
            this.wallpaper = wallpaper;
            Picasso.with(context)
                    .load(wallpaper.getWallUrl())
                    .resize(300, 0)
                    .into((ImageView) itemView);
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
        viewHolder.setImage(wall);
    }

    @Override
    public int getItemCount() {
        if(wallpaperList == null)
            return 0;
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
