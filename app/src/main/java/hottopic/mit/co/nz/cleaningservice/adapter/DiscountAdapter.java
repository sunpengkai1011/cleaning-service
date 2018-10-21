package hottopic.mit.co.nz.cleaningservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.entities.discounts.Discount;

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.DiscountViewHolder> implements View.OnClickListener{
    private List<Discount> discounts;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public DiscountAdapter(Context context, List<Discount> discounts){
        this.context = context;
        this.discounts = discounts;
    }

    @Override
    public DiscountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_discount, parent, false);
        DiscountViewHolder viewHolder = new DiscountViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DiscountViewHolder holder, int position) {
        holder.tv_balance.setText(String.valueOf(discounts.get(position).getBalance()));
        holder.tv_price.setText(String.valueOf(discounts.get(position).getPrice()));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return discounts.size();
    }

    public void setOnItemClickListener(DiscountAdapter.OnItemClickListener listener){
        onItemClickListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, (int) view.getTag());
        }
    }

    class DiscountViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_balance, tv_price;

        public DiscountViewHolder(View itemView) {
            super(itemView);
            tv_balance = itemView.findViewById(R.id.tv_discount);
            tv_price = itemView.findViewById(R.id.tv_price);
        }
    }
}
