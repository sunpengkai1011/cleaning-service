package hottopic.mit.co.nz.cleaningservice.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.CarPhotoViewHolder> {
    private int[] viewList;
    private Context mContext;
    private LayoutInflater inflate;
    private ImageView imageView;

    public ViewPagerAdapter(Context context) {
        mContext = context;
        inflate = LayoutInflater.from(mContext);

    }
    public void setData(int[] viewList) {
        this.viewList = viewList;
    }


    @Override
    public CarPhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflate == null)
            inflate = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.item_detail_view_pager, null);
        imageView = v.findViewById(R.id.iv_detail);
        //Dynamically retrieves the device screen width, and set the size of ImageView
        int width = GeneralUtil.getScreenWidth((Activity) mContext);
        int height = (int) (width * 2 / 3);
        imageView.getLayoutParams().width = width;
        imageView.getLayoutParams().height = height;
        return new CarPhotoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CarPhotoViewHolder holder, int position) {
        Picasso.with(mContext).setLoggingEnabled(true);
        Picasso.with(mContext).load(viewList[position]).resize(600, 400).into(imageView);
    }

    @Override
    public int getItemCount() {
        return viewList.length;
    }


    static class CarPhotoViewHolder extends RecyclerView.ViewHolder {
        CarPhotoViewHolder(View itemView) {
            super(itemView);
        }
    }
}
