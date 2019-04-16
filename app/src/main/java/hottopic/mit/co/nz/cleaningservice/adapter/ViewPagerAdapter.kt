package hottopic.mit.co.nz.cleaningservice.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.squareup.picasso.Picasso

import hottopic.mit.co.nz.cleaningservice.R
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil

class ViewPagerAdapter(private val mContext: Context) : RecyclerView.Adapter<ViewPagerAdapter.CarPhotoViewHolder>() {
    private lateinit var viewList: IntArray
    private var inflate: LayoutInflater? = null
    private var imageView: ImageView? = null

    init {
        inflate = LayoutInflater.from(mContext)

    }

    fun setData(viewList: IntArray) {
        this.viewList = viewList
    }

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarPhotoViewHolder {
        if (inflate == null)
            inflate = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflate!!.inflate(R.layout.item_detail_view_pager, null)
        imageView = v!!.findViewById(R.id.iv_detail)
        //Dynamically retrieves the device screen width, and set the size of ImageView
        val width = GeneralUtil.getScreenWidth(mContext as Activity)
        val height = width * 2 / 3
        imageView!!.layoutParams.width = width
        imageView!!.layoutParams.height = height
        return CarPhotoViewHolder(v)
    }

    override fun onBindViewHolder(holder: CarPhotoViewHolder, position: Int) {
        Picasso.with(mContext).isLoggingEnabled = true
        Picasso.with(mContext).load(viewList[position]).resize(600, 400).into(imageView)
    }

    override fun getItemCount(): Int {
        return viewList.size
    }


    class CarPhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
