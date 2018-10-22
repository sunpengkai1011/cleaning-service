package hottopic.mit.co.nz.cleaningservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> implements View.OnClickListener{
    private List<Order> orders;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public OrderAdapter(Context context) {
        this.context = context;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        OrderViewHolder viewHoder = new OrderViewHolder(view);
        view.setOnClickListener(this);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        String[] strs = orders.get(position).getDate().split(" ");
        holder.tv_date.setText(strs[0]);
        holder.tv_service_type.setText(orders.get(position).getServiceType().getTypeName());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (onItemClickListener != null){
            onItemClickListener.onItemClick(view, (int) view.getTag());
        }
    }

    class OrderViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_date, tv_service_type;

        public OrderViewHolder(View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_service_type = itemView.findViewById(R.id.tv_service_type);
        }
    }
}
