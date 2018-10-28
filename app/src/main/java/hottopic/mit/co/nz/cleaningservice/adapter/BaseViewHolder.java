package hottopic.mit.co.nz.cleaningservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

public abstract class BaseViewHolder<M> extends RecyclerView.ViewHolder {
    protected Context context;

    public BaseViewHolder(Context context, int layoutId) {
        super(LayoutInflater.from(context).inflate(layoutId, null));
        this.context = context;
    }

    public abstract void onBindViewHolder(M model, int position);
}
