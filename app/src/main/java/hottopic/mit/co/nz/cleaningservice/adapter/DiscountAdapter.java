package hottopic.mit.co.nz.cleaningservice.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.ViewGroup;
import android.widget.TextView;

import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Discount;

public class DiscountAdapter extends BaseAdapter<Discount, DiscountAdapter.DiscountViewHolder>{
    private OnItemClickListener onItemClickListener;

    public DiscountAdapter(Context context) {
        super(context);
    }

    public interface OnItemClickListener{
        void OnItemClick(Discount discount);
    }

    public void setOnItemClickListener(DiscountAdapter.OnItemClickListener listener){
        onItemClickListener = listener;
    }

    @Override
    public DiscountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DiscountViewHolder(context);
    }

    class DiscountViewHolder extends BaseViewHolder<Discount>{
        private TextView tv_balance, tv_was_price, tv_price;

        public DiscountViewHolder(Context context) {
            super(context, R.layout.item_discount);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_balance = itemView.findViewById(R.id.tv_balance);
            tv_was_price = itemView.findViewById(R.id.tv_was_price);

        }


        @Override
        public void onBindViewHolder(Discount model, int position) {
            tv_price.setText(model.formatPrice());
            tv_balance.setText(model.formatBalance());
            tv_was_price.setText(model.formatBalance());
            tv_was_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
            itemView.setOnClickListener(view -> {
                onItemClickListener.OnItemClick(model);
            });
        }
    }
}
