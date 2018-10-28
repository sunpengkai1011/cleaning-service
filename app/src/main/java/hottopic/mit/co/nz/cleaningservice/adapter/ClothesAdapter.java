package hottopic.mit.co.nz.cleaningservice.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.List;

import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.entities.orders.ironing.ClothesType;

public class ClothesAdapter extends BaseAdapter<ClothesType, ClothesAdapter.ClothesViewHolder>{

    public ClothesAdapter(Context context) {
        super(context);
    }

    @Override
    public ClothesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ClothesViewHolder(context);
    }

    class ClothesViewHolder extends BaseViewHolder<ClothesType>{
        private ImageView iv_clothes_icon;
        private TextView tv_clothes, tv_unit_price;
        private RelativeLayout lyt_add, lyt_minus;
        private EditText et_quantity;

        public ClothesViewHolder(Context context) {
            super(context, R.layout.item_clothes);
            iv_clothes_icon = itemView.findViewById(R.id.iv_clothes_icon);
            tv_clothes = itemView.findViewById(R.id.tv_clothes);
            tv_unit_price = itemView.findViewById(R.id.tv_unit_price);
            lyt_add = itemView.findViewById(R.id.lyt_add);
            lyt_minus = itemView.findViewById(R.id.lyt_minus);
            et_quantity = itemView.findViewById(R.id.et_quantity);
        }

        @Override
        public void onBindViewHolder(ClothesType model, int position) {
            iv_clothes_icon.setImageResource(model.getIcon());
            tv_clothes.setText(model.getClothesName());
            tv_unit_price.setText(model.formatPriceForBooking());
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
        }
    }
}
