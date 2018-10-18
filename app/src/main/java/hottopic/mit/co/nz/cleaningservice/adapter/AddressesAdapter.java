package hottopic.mit.co.nz.cleaningservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import industryproject.mit.deliveryoptimise.R;

public class AddressesAdapter extends BaseAdapter {
    private List<String> infos;
    private Context mContext;
    private int type;

    public AddressesAdapter(Context context, List<String> infos, int type){
        this.infos = infos;
        mContext = context;
        this.type = type;
    }
    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public Object getItem(int i) {
        return infos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_listview, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_content = view.findViewById(R.id.tv_content);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }


        switch (type){
            case 0:
                viewHolder.tv_content.setText(infos.get(i));
                break;
            case 1:
                String str;
                if (i == 0){
                    str = "Start Point : " + infos.get(i);
                }else if (i == infos.size() - 1){
                    str = "Destination : " + infos.get(i);
                }else {
                    str = "Way Point : " + infos.get(i);
                }
                viewHolder.tv_content.setText(str);
                break;

        }

        return view;
    }

    class ViewHolder{
        TextView tv_content;
    }
}


