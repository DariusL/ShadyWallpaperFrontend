package lt.mano.shadywallpaperfrontend.ui;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import lt.mano.shadywallpaperfrontend.R;
import lt.mano.shadywallpaperfrontend.data.*;
import lt.mano.shadywallpaperfrontend.data.Thread;
import lt.mano.shadywallpaperfrontend.net.PagedDataService;
import lt.mano.shadywallpaperfrontend.net.PagedServiceWrapper;
import lt.mano.shadywallpaperfrontend.ui.widgets.PicassoImageView;

/**
 * Created by Darius on 2014.11.13.
 */
public class ThreadAdapter extends BaseAdapter<ThreadAdapter.ViewHolder> {

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
                        notifyInternal();
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
        private TextView title;
        private TextView threadId;

        public ViewHolder(CardView card) {
            super(card);
            this.image = (PicassoImageView) card.findViewById(R.id.list_item_image);
            this.title = (TextView) card.findViewById(R.id.list_item_text);
            this.threadId = (TextView) card.findViewById(R.id.list_item_thread_id);
            itemView.setOnClickListener(onClickListener);
        }

        public synchronized void setThread(Thread thread){
            itemView.setTag(thread);
            image.setImage(thread.getOpPost().getWallUrl());
            title.setText(Html.fromHtml(thread.getOpContent()).toString());
            threadId.setText(String.valueOf(thread.getId()));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        CardView view = (CardView) LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Thread thread = service.get(i);
        viewHolder.setThread(thread);
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
