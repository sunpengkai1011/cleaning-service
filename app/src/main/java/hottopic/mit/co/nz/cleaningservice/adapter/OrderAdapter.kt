package hottopic.mit.co.nz.cleaningservice.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import android.widget.TextView

import hottopic.mit.co.nz.cleaningservice.Constants
import hottopic.mit.co.nz.cleaningservice.R
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order

@Suppress("DEPRECATION")
class OrderAdapter(context: Context) : BaseAdapter<Order, OrderAdapter.OrderViewHolder>(context) {
    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(context)
    }

    interface OnItemClickListener {
        fun onItemClick(order: Order, position: Int)
    }

    fun setOnItemClickListener(listener: OrderAdapter.OnItemClickListener) {
        onItemClickListener = listener
    }

    inner class OrderViewHolder(context: Context) : BaseViewHolder<Order>(context, R.layout.item_order) {
        private val tvDate: TextView = itemView.findViewById(R.id.tv_date)
        private val tvServiceType: TextView = itemView.findViewById(R.id.tv_service_type)
        private val tvStatus: TextView = itemView.findViewById(R.id.tv_status)

        @SuppressLint("ResourceAsColor")
        override fun onBindViewHolder(model: Order, position: Int) {
            val strs = model.date!!.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            tvDate.text = strs[0]
            tvServiceType.text = model.subServiceType?.type_name
            when (model.status) {
                Constants.STATUS_ORDER_BOOKED -> {
                    tvStatus.text = context.resources.getString(R.string.status_booked)
                    tvStatus.setTextColor(context.resources.getColor(R.color.status_other))
                }
                Constants.STATUS_ORDER_STARTED -> {
                    tvStatus.text = context.resources.getString(R.string.status_started)
                    tvStatus.setTextColor(context.resources.getColor(R.color.status_other))
                }
                Constants.STATUS_ORDER_FINISHED -> {
                    tvStatus.text = context.resources.getString(R.string.status_finished)
                    tvStatus.setTextColor(context.resources.getColor(R.color.status_other))
                }
                Constants.STATUS_ORDER_PAID -> {
                    tvStatus.text = context.resources.getString(R.string.status_paid)
                    tvStatus.setTextColor(context.resources.getColor(R.color.status_payment))
                }
            }
            itemView.setOnClickListener { onItemClickListener!!.onItemClick(model, position) }
        }
    }
}
