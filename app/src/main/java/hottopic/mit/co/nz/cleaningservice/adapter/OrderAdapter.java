package hottopic.mit.co.nz.cleaningservice.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;

public class OrderAdapter extends BaseAdapter<Order, OrderAdapter.OrderViewHolder>{
    private OnItemClickListener onItemClickListener;

    public OrderAdapter(Context context) {
        super(context);
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderViewHolder(context);
    }

    public interface OnItemClickListener{
        void onItemClick(Order order, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }

    class OrderViewHolder extends BaseViewHolder<Order>{
        private TextView tv_date, tv_service_type, tv_status;

        public OrderViewHolder(Context context) {
            super(context, R.layout.item_order);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_service_type = itemView.findViewById(R.id.tv_service_type);
            tv_status = itemView.findViewById(R.id.tv_status);
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(Order model, int position) {
            String[] strs = model.getDate().split(" ");
            tv_date.setText(strs[0]);
            tv_service_type.setText(model.getServiceType().getTypeName());
            switch (model.getStatus()){
                case Constants.STATUS_ORDER_BOOKED:
                    tv_status.setText(context.getResources().getString(R.string.status_booked));
                    tv_status.setTextColor(context.getResources().getColor(R.color.status_other));
                    break;
                case Constants.STATUS_ORDER_STARTED:
                    tv_status.setText(context.getResources().getString(R.string.status_started));
                    tv_status.setTextColor(context.getResources().getColor(R.color.status_other));
                    break;
                case Constants.STATUS_ORDER_FINISHED:
                    tv_status.setText(context.getResources().getString(R.string.status_finished));
                    tv_status.setTextColor(context.getResources().getColor(R.color.status_other));
                    break;
                case Constants.STATUS_ORDER_PAID:
                    tv_status.setText(context.getResources().getString(R.string.status_paid));
                    tv_status.setTextColor(context.getResources().getColor(R.color.status_payment));
                    break;
            }
            itemView.setOnClickListener(v -> {
                onItemClickListener.onItemClick(model, position);
            });
        }
    }
}
