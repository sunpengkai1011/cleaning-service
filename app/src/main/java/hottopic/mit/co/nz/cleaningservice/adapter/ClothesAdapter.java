package hottopic.mit.co.nz.cleaningservice.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceProduct;

public class ClothesAdapter extends BaseAdapter<ServiceProduct, ClothesAdapter.ClothesViewHolder>{
    private int type;
    public ClothesAdapter(Context context, int type) {
        super(context);
        this.type = type;
    }

    @Override
    public ClothesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ClothesViewHolder(context);
    }

    class ClothesViewHolder extends BaseViewHolder<ServiceProduct>{
        private ImageView iv_clothes_icon;
        private TextView tv_clothes, tv_unit_price, tv_quantity;
        private RelativeLayout lyt_add, lyt_minus, lyt_quantity;
        private EditText et_quantity;

        public ClothesViewHolder(Context context) {
            super(context, R.layout.item_clothes);
            iv_clothes_icon = itemView.findViewById(R.id.iv_clothes_icon);
            tv_clothes = itemView.findViewById(R.id.tv_clothes);
            tv_unit_price = itemView.findViewById(R.id.tv_unit_price);
            lyt_add = itemView.findViewById(R.id.lyt_add);
            lyt_minus = itemView.findViewById(R.id.lyt_minus);
            et_quantity = itemView.findViewById(R.id.et_quantity);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            lyt_quantity = itemView.findViewById(R.id.lyt_quantity);
        }

        @Override
        public void onBindViewHolder(ServiceProduct model, int position) {
            iv_clothes_icon.setImageResource(context.getResources().getIdentifier(model.getIcon(), "drawable", context.getPackageName()));
            tv_clothes.setText(model.getProduct_name());
            tv_unit_price.setText(model.formatPrice());
            switch (type){
                case Constants.ADAPTER_CLOTHES_BOOKING:
                    tv_quantity.setVisibility(View.GONE);
                    lyt_add.setOnClickListener(v->{
                        int quantity = Integer.valueOf(et_quantity.getText().toString()) + 1;
                        model.setQuantity(quantity);
                        et_quantity.setText(String.valueOf(quantity));
                    });
                    lyt_minus.setOnClickListener(v->{
                        int quantity = Integer.valueOf(et_quantity.getText().toString());
                        if (quantity > 0){
                            quantity -= 1;
                        }else{
                            quantity = 0;
                        }
                        model.setQuantity(quantity);
                        et_quantity.setText(String.valueOf(quantity));
                    });
                    et_quantity.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            if (!TextUtils.isEmpty(editable.toString())){
                                int quantity = Integer.valueOf(editable.toString());
                                model.setQuantity(quantity);
                            }
                        }
                    });
                    break;
                case Constants.ADAPTER_CLOTHES_DETAIL:
                    tv_quantity.setVisibility(View.VISIBLE);
                    lyt_add.setVisibility(View.GONE);
                    lyt_minus.setVisibility(View.GONE);
                    et_quantity.setVisibility(View.GONE);
                    tv_quantity.setText(String.valueOf(model.getQuantity()));
                    break;
            }
        }
    }
}
