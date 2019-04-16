package hottopic.mit.co.nz.cleaningservice.adapter

import android.content.Context
import android.graphics.Paint
import android.view.ViewGroup
import android.widget.TextView

import hottopic.mit.co.nz.cleaningservice.R
import hottopic.mit.co.nz.cleaningservice.entities.orders.Discount

class DiscountAdapter(context: Context) : BaseAdapter<Discount, DiscountAdapter.DiscountViewHolder>(context) {
    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(discount: Discount)
    }

    fun setOnItemClickListener(listener: DiscountAdapter.OnItemClickListener) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscountViewHolder {
        return DiscountViewHolder(context)
    }

    inner class DiscountViewHolder(context: Context) : BaseViewHolder<Discount>(context, R.layout.item_discount) {
        private val tvBalance: TextView = itemView.findViewById(R.id.tv_balance)
        private val tvWasPrice: TextView = itemView.findViewById(R.id.tv_was_price)
        private val tvPrice: TextView = itemView.findViewById(R.id.tv_price)


        override fun onBindViewHolder(model: Discount, position: Int) {
            tvPrice.text = model.formatPrice()
            tvBalance.text = model.formatBalance()
            tvWasPrice.text = model.formatBalance()
            tvWasPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
            itemView.setOnClickListener { onItemClickListener!!.onItemClick(model) }
        }
    }
}
