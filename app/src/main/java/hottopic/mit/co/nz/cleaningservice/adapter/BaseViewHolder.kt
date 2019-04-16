package hottopic.mit.co.nz.cleaningservice.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater

abstract class BaseViewHolder<M>(protected var context: Context, layoutId: Int) : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(layoutId, null)) {

    abstract fun onBindViewHolder(model: M, position: Int)
}
