package hottopic.mit.co.nz.cleaningservice.view.order;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.BaseActivity;
import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.adapter.OrderAdapter;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceTypes;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.presenter.order.OrderPresenterImpl;
import hottopic.mit.co.nz.cleaningservice.view.user.UserEditActivity;

public class OrdersActivity extends BaseActivity implements IOrderView{
    private TextView tv_title, tv_username, tv_user_role;
    private RelativeLayout lyt_back, lyt_header;
    private RecyclerView rv_orders;
    private SwipeRefreshLayout srl_orders;
    private OrderAdapter orderAdapter;

    private OrderPresenterImpl orderPresenter;

    @Override
    public void initView() {
        setContentView(R.layout.activity_user);
        tv_title = findViewById(R.id.tv_title);
        tv_username = findViewById(R.id.tv_username);
        tv_user_role = findViewById(R.id.tv_user_role);
        lyt_back = findViewById(R.id.lyt_back);
        lyt_header = findViewById(R.id.lyt_header);
        rv_orders = findViewById(R.id.rv_orders);
        srl_orders = findViewById(R.id.srl_orders);
    }

    @Override
    public void initData() {
        tv_title.setText(getResources().getString(R.string.title_me));
        lyt_back.setVisibility(View.VISIBLE);
        setData(Constants.userInfo);
        orderPresenter = new OrderPresenterImpl(this, this);
        getOrderByRole();
    }

    @Override
    public void initListener() {
        lyt_back.setOnClickListener(this);
        lyt_header.setOnClickListener(this);
        srl_orders.setOnRefreshListener(() -> {
            getOrderByRole();
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lyt_header:
                Intent editIntent = new Intent(this, UserEditActivity.class);
                startActivity(editIntent);
                break;
            case R.id.lyt_back:
                this.finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode){
            lyt_back.performClick();
        }
        return false;
    }

    private void setData(UserInfo userInfo){
        if (userInfo != null){
            tv_username.setText(userInfo.getUsername());
            if (userInfo.getRole_id() == Constants.ROLE_CUSTOMER){
                tv_user_role.setText(this.getResources().getString(R.string.role_customer));
            }else {
                tv_user_role.setText(this.getResources().getString(R.string.role_staff));
            }
        }
    }


    @Override
    public void getOrdersResult(List<Order> orders, String message) {
        if (srl_orders.isRefreshing()){
            srl_orders.setRefreshing(false);
        }
        if (orders != null){
            if (orderAdapter == null) {
                orderAdapter = new OrderAdapter(this);
                orderAdapter.setData(orders);
                orderAdapter.setOnItemClickListener((order, position) -> {
                    Intent intent = new Intent(OrdersActivity.this, OrderDetailActivity.class);
                    intent.putExtra(Constants.KEY_INTENT_ORDER, order);
                    startActivityForResult(intent, Constants.INTENT_REQUEST_ORDER_TO_DETAIL);
                });
                rv_orders.setLayoutManager(new LinearLayoutManager(this));
                rv_orders.setAdapter(orderAdapter);
            }else{
                orderAdapter.setData(orders);
                orderAdapter.notifyDataSetChanged();
                if (srl_orders.isRefreshing()){
                    srl_orders.setRefreshing(false);
                }
            }
        }else{
            Toast.makeText(this, "Get Orders Failed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode){
            if (Constants.INTENT_REQUEST_ORDER_TO_DETAIL == requestCode){
                getOrderByRole();
            }
        }
    }

    @Override
    public void orderStatusChangeResult(Order order, String message) {

    }

    @Override
    public void bookingResult(boolean result, String message) {

    }

    @Override
    public void getServiceError(String message) {

    }

    @Override
    public void getServiceTypes(ServiceTypes serviceTypes) {

    }

    private void getOrderByRole(){
        if (Constants.ROLE_CUSTOMER == Constants.userInfo.getRole_id()){
            orderPresenter.getOrdersByCustomer(Constants.userInfo.getUserId());
        }else {
            orderPresenter.getOrdersByStaff();
        }
    }
}
