package lt.mano.shadywallpaperfrontend.ui;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lt.mano.shadywallpaperfrontend.R;
import lt.mano.shadywallpaperfrontend.data.*;
import lt.mano.shadywallpaperfrontend.data.Thread;
import lt.mano.shadywallpaperfrontend.net.PagedDataService;
import lt.mano.shadywallpaperfrontend.net.PagedServiceWrapper;
import lt.mano.shadywallpaperfrontend.ui.widgets.PicassoImageView;

/**
 * Created by Darius on 2014.11.13.
 */
public class ThreadAdapter extends RecyclerView.Adapter<ThreadAdapter.ViewHolder> {

    private Context context;
    private OnItemClickListener listener;

    private PagedDataService<Thread> service;

    public ThreadAdapter(Context context, final PagedServiceWrapper<Thread> service){
        this.context = context;

        this.service = new PagedDataService<Thread>(
                service,
                new PagedDataService.ServiceLoadResult<Thread>() {
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
        private PicassoImageView image;

        public ViewHolder(CardView card, PicassoImageView image) {
            super(card);
            this.image = image;
            itemView.setOnClickListener(onClickListener);
        }

        public synchronized void setThread(Thread thread){
            itemView.setTag(thread);
            image.setImage(thread.getOpPost().getWallUrl());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        CardView view = (CardView) LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);
        PicassoImageView image = (PicassoImageView) view.findViewById(R.id.list_item_image);
        return new ViewHolder(view, image);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Thread wall = service.get(i);
        viewHolder.setThread(wall);
    }

    @Override
    public int getItemCount() {
        return service.getLoadedCount();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(listener != null)
                listener.onItemClick(view, (Thread) view.getTag());
        }
    };

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener{
        public void onItemClick(View view, Thread item);
    }
}
