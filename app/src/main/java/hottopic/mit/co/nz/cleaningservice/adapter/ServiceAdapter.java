package hottopic.mit.co.nz.cleaningservice.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.entities.orders.SubServiceType;
import hottopic.mit.co.nz.cleaningservice.entities.orders.TitleModel;

public class ServiceAdapter extends BaseAdapter<Object, BaseViewHolder> {
    private static final int TYPE_TITLE = 0;
    private static final int TYPE_SERVICE = 1;

    private OnItemClickedListener onItemClickedListener;

    public ServiceAdapter(Context context) {
        super(context);
    }

    public interface OnItemClickedListener {
        void OnItemClicked(SubServiceType serviceType);
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (TYPE_TITLE == viewType) {
            return new TitleViewHolder(context);
        } else {
            return new ServiceViewHolder(context);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (dataList.get(position) instanceof TitleModel) {
            return TYPE_TITLE;
        } else {
            return TYPE_SERVICE;
        }
    }

    private class ServiceViewHolder extends BaseViewHolder<SubServiceType> {
        private TextView tv_service;
        private ImageView iv_icon;

        public ServiceViewHolder(Context context) {
            super(context, R.layout.item_service_type);
            tv_service = itemView.findViewById(R.id.tv_service);
            iv_icon = itemView.findViewById(R.id.iv_icon);
        }

        @Override
        public void onBindViewHolder(SubServiceType model, int position) {
            tv_service.setText(model.getType_name());
            iv_icon.setImageResource(context.getResources().getIdentifier(model.getIcon(), "drawable", context.getPackageName()));
            itemView.setOnClickListener(v -> {
                if (onItemClickedListener != null) {
                    onItemClickedListener.OnItemClicked(model);
                }
            });
        }
    }

    private class TitleViewHolder extends BaseViewHolder<TitleModel> {
        private TextView tv_service;

        public TitleViewHolder(Context context) {
            super(context, R.layout.item_title);
            tv_service = itemView.findViewById(R.id.tv_service);
        }

        @Override
        public void onBindViewHolder(TitleModel model, int position) {
            tv_service.setText(model.getTitle());
        }
    }
}
