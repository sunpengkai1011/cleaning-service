package hottopic.mit.co.nz.cleaningservice.view.home.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.adapter.OrderAdapter;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceType;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.presenter.home.OrderPresenterImpl;

public class OrderFragment extends Fragment implements IOrderView, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, OrderAdapter.OnItemClickListener{
    private TextView tv_title;
    private RelativeLayout lyt_right;
    private ImageView iv_icon;
    private LinearLayout lyt_title;
    private RecyclerView rv_orders;
    private UserInfo userInfo;
    private SwipeRefreshLayout srl_orders;
    private OrderAdapter orderAdapter;

    private OrderPresenterImpl orderPresenter;
    private List<Order> orders;

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
        rv_orders = view.findViewById(R.id.rv_orders);
        lyt_title = view.findViewById(R.id.lyt_title);
        srl_orders = view.findViewById(R.id.srl_orders);
    }

    private void initData(){
        tv_title.setText(getResources().getString(R.string.title_order));
        iv_icon.setImageResource(R.drawable.logo_plus);
        lyt_right.setVisibility(View.VISIBLE);

        userInfo = (UserInfo) getActivity().getIntent().getSerializableExtra(Constants.KEY_INTENT_USERINFO);
        orderPresenter = new OrderPresenterImpl(getActivity(), this);
        if (userInfo != null) {
            orderPresenter.getOrder(userInfo);
        }else{
            listVisible(View.GONE);
        }
    }

    private void initListener(){
        lyt_right.setOnClickListener(this);
        srl_orders.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lyt_right:
                Intent intent = new Intent(getActivity(), OrderBookingActivity.class);
                intent.putExtra(Constants.KEY_INTENT_USERINFO, userInfo);
                startActivityForResult(intent, Constants.INTENT_REQUEST_ODER_TO_CREATE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (getActivity().RESULT_OK == resultCode){
            if (Constants.INTENT_REQUEST_ODER_TO_CREATE == requestCode || Constants.INTENT_REQUEST_ORDER_TO_DETAIL == requestCode) {
                getOrders();
            }
        }
    }

    @Override
    public void getOrdersResult(List<Order> orders, int code) {
        if (Constants.RESPONSE_CODE_SUCCESSFUL == code){
            this.orders = orders;
            if (orderAdapter == null) {
                orderAdapter = new OrderAdapter(getActivity());
                orderAdapter.setOnItemClickListener(this);
                rv_orders.setLayoutManager(new LinearLayoutManager(getActivity()));
                rv_orders.setAdapter(orderAdapter);
            }
            orderAdapter.setOrders(orders);
            orderAdapter.notifyDataSetChanged();
            listVisible(View.VISIBLE);
            if (srl_orders.isRefreshing()){
                srl_orders.setRefreshing(false);
            }
        }else{
            listVisible(View.GONE);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
        intent.putExtra(Constants.KEY_INTENT_USERINFO, userInfo);
        intent.putExtra(Constants.KEY_INTENT_ORDER, orders.get(position));
        intent.putExtra(Constants.KEY_INTENT_ORDER_POSITION, position);
        startActivityForResult(intent, Constants.INTENT_REQUEST_ORDER_TO_DETAIL);
    }

    @Override
    public void onRefresh() {
        getOrders();
        listVisible(View.GONE);
    }

    private void getOrders(){
        orderPresenter.getOrder(userInfo);
    }

    private void listVisible(int visible){
        lyt_title.setVisibility(visible);
        rv_orders.setVisibility(visible);
    }

    @Override
    public void getStartedResult(int code) {

    }

    @Override
    public void getFinishedResult(int code) {

    }

    @Override
    public void bookingResult(int code) {

    }

    @Override
    public void getServiceTypeResult(List<ServiceType> types, int code) {

    }
}
