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

import lt.mano.shadywallpaperfrontend.net.PagedDataService;
import lt.mano.shadywallpaperfrontend.net.PagedServiceWrapper;
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

    private PagedDataService<Wallpaper> service;

    public WallpaperAdapter(Context context, final ShadyWallpaperService service){
        this.context = context;

        this.service = new PagedDataService<Wallpaper>(
                new PagedServiceWrapper<Wallpaper>() {
                    @Override
                    public void get(int page, Callback<List<Wallpaper>> callback) {
                        service.boardWalls("wg", page, callback);
                    }
                },
                new PagedDataService.ServiceLoadResult<Wallpaper>() {
                    @Override
                    public void dataUpdated() {
                        notifyDataSetChanged();
                    }

                    @Override
                    public void endReached() {
                    }
                },
                50
        );

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private Wallpaper wallpaper;

        public ViewHolder(GridImageView image) {
            super(image);
            image.setOnClickListener(onClickListener);
        }

        public synchronized void setImage(Wallpaper wallpaper){
            this.wallpaper = wallpaper;
            itemView.setTag(wallpaper);
            ((GridImageView)this.itemView).setImage(wallpaper.getWallUrl());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        GridImageView view = (GridImageView) LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.grid_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Wallpaper wall = service.get(i);
        viewHolder.setImage(wall);
    }

    @Override
    public int getItemCount() {
        return service.getLoadedCount();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(listener != null)
                listener.onItemClick(view, (Wallpaper) view.getTag());
        }
    };

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener{
        public void onItemClick(View view, Wallpaper item);
    }
}
