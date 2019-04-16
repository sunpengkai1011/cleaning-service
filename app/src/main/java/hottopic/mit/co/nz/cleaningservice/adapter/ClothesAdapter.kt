package hottopic.mit.co.nz.cleaningservice.adapter

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import hottopic.mit.co.nz.cleaningservice.Constants
import hottopic.mit.co.nz.cleaningservice.R
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceProduct

class ClothesAdapter(context: Context, private val type: Int) : BaseAdapter<ServiceProduct, ClothesAdapter.ClothesViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClothesViewHolder {
        return ClothesViewHolder(context)
    }

    inner class ClothesViewHolder(context: Context) : BaseViewHolder<ServiceProduct>(context, R.layout.item_clothes) {
        private val ivClothesIcon: ImageView = itemView.findViewById(R.id.iv_clothes_icon)
        private val tvClothes: TextView = itemView.findViewById(R.id.tv_clothes)
        private val tvUnitPrice: TextView = itemView.findViewById(R.id.tv_unit_price)
        private val tvQuantity: TextView = itemView.findViewById(R.id.tv_quantity)
        private val lytAdd: RelativeLayout = itemView.findViewById(R.id.lyt_add)
        private val lytMinus: RelativeLayout = itemView.findViewById(R.id.lyt_minus)
        private val etQuantity: EditText = itemView.findViewById(R.id.et_quantity)

        override fun onBindViewHolder(model: ServiceProduct, position: Int) {
            ivClothesIcon.setImageResource(context.resources.getIdentifier(model.icon, "drawable", context.packageName))
            tvClothes.text = model.product_name
            tvUnitPrice.text = model.formatPrice()
            when (type) {
                Constants.ADAPTER_CLOTHES_BOOKING -> {
                    tvQuantity.visibility = View.GONE
                    lytAdd.setOnClickListener {
                        val quantity = Integer.valueOf(etQuantity.text.toString()) + 1
                        model.quantity = quantity
                        etQuantity.setText(quantity.toString())
                    }
                    lytMinus.setOnClickListener {
                        var quantity = Integer.valueOf(etQuantity.text.toString())
                        if (quantity > 0) {
                            quantity -= 1
                        } else {
                            quantity = 0
                        }
                        model.quantity = quantity
                        etQuantity.setText(quantity.toString())
                    }
                    etQuantity.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                        }

                        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                        }

                        override fun afterTextChanged(editable: Editable) {
                            if (!TextUtils.isEmpty(editable.toString())) {
                                val quantity = Integer.valueOf(editable.toString())
                                model.quantity = quantity
                            }
                        }
                    })
                }
                Constants.ADAPTER_CLOTHES_DETAIL -> {
                    tvQuantity.visibility = View.VISIBLE
                    lytAdd.visibility = View.GONE
                    lytMinus.visibility = View.GONE
                    etQuantity.visibility = View.GONE
                    tvQuantity.text = model.quantity.toString()
                }
            }
        }
    }
}
