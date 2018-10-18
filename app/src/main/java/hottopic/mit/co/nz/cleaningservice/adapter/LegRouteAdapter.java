package hottopic.mit.co.nz.cleaningservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.entities.LegInfo;

public class LegRouteAdapter extends RecyclerView.Adapter<LegRouteAdapter.LegRouteViewHolder>{
    private Context mContext;
    private List<LegInfo> legs;

    public LegRouteAdapter(Context context, List<LegInfo> legs){
        mContext = context;
        this.legs = legs;
    }

    @Override
    public LegRouteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_leg_route, parent, false);
        LegRouteViewHolder viewHolder = new LegRouteViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LegRouteViewHolder holder, int position) {
        holder.tv_origin.setText(legs.get(position).getStart_address());
        holder.tv_destination.setText(legs.get(position).getEnd_address());
        holder.tv_distance.setText(legs.get(position).getDistance().getText());
        holder.tv_duration.setText(legs.get(position).getDuration().getText());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return legs.size();
    }

    class LegRouteViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_origin, tv_destination, tv_distance, tv_duration;

        public LegRouteViewHolder(View itemView) {
            super(itemView);
            tv_origin = itemView.findViewById(R.id.tv_origin);
            tv_destination = itemView.findViewById(R.id.tv_destination);
            tv_distance = itemView.findViewById(R.id.tv_distance);
            tv_duration = itemView.findViewById(R.id.tv_duration);
        }
    }
}
