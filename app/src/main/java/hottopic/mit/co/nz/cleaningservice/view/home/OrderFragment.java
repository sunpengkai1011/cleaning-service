package hottopic.mit.co.nz.cleaningservice.view.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;

public class OrderFragment extends Fragment implements IOrderView, View.OnClickListener{
    private TextView tv_title;
    private RelativeLayout lyt_right;
    private ImageView iv_icon;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initView(View view){
        tv_title = view.findViewById(R.id.tv_title);
        lyt_right = view.findViewById(R.id.lyt_right);
        iv_icon = view.findViewById(R.id.iv_icon);
    }

    private void initData(){
        tv_title.setText(getResources().getString(R.string.title_order));
        iv_icon.setImageResource(R.drawable.logo_plus);
        lyt_right.setVisibility(View.VISIBLE);
    }

    private void initListener(){
        lyt_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lyt_right:
                Toast.makeText(getActivity(), "new order", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    public void getOrdersResult(List<Order> orders, int code) {

    }
}
