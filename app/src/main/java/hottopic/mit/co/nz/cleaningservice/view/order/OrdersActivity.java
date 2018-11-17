package hottopic.mit.co.nz.cleaningservice.view.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
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
import hottopic.mit.co.nz.cleaningservice.view.user.LoginActivity;
import hottopic.mit.co.nz.cleaningservice.view.user.UserEditActivity;

public class OrdersActivity extends BaseActivity implements IOrderView {
    private TextView tv_title, tv_username, tv_user_role;
    private RelativeLayout lyt_back, lyt_header, lyt_right;
    private ImageView iv_icon;
    private RecyclerView rv_orders;
    private SwipeRefreshLayout srl_orders;
    private OrderAdapter orderAdapter;

    private OrderPresenterImpl orderPresenter;
    private boolean isQuit = false;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isQuit = false;
        }
    };

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int statusType = intent.getIntExtra(Constants.KEY_INTENT_CLOSETYPE, 0);
        if (Constants.CLOSETYPE_LOGOUT == statusType){
            //open login page
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
            finish();
        }
    }

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
        lyt_right = findViewById(R.id.lyt_right);
        iv_icon = findViewById(R.id.iv_icon);
    }

    @Override
    public void initData() {
        tv_title.setText(getResources().getString(R.string.title_me));
        if (Constants.ROLE_CUSTOMER == Constants.userInfo.getRole_id()) {
            lyt_back.setVisibility(View.VISIBLE);
        }
        lyt_right.setVisibility(View.VISIBLE);
        iv_icon.setImageResource(R.drawable.icon_user);
        setData(Constants.userInfo);
        orderPresenter = new OrderPresenterImpl(this, this);
        getOrderByRole();
    }

    @Override
    public void initListener() {
        lyt_back.setOnClickListener(this);
        lyt_header.setOnClickListener(this);
        lyt_right.setOnClickListener(this);
        srl_orders.setOnRefreshListener(() -> {
            getOrderByRole();
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lyt_header:
            case R.id.lyt_right:
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
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (Constants.ROLE_CUSTOMER == Constants.userInfo.getRole_id()) {
                lyt_back.performClick();
            } else {
                //If click the Back key twice in 2 seconds, the program exits
                if (!isQuit) {
                    isQuit = true;
                    Toast.makeText(getApplicationContext(), "Press the return key again to exit the program!",
                            Toast.LENGTH_SHORT).show();
                    handler.sendEmptyMessageDelayed(Constants.WHAT_EXIT, 2000);
                } else {
                    finish();
                    System.exit(0);
                }
            }
        }
        return false;
    }

    private void setData(UserInfo userInfo) {
        if (userInfo != null) {
            tv_username.setText(userInfo.getUsername());
            tv_user_role.setText(userInfo.getRole_name());
        }
    }


    @Override
    public void getOrdersResult(List<Order> orders, String message) {
        if (srl_orders.isRefreshing()) {
            srl_orders.setRefreshing(false);
        }
        if (orders != null && orders.size() > 0) {
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
            } else {
                orderAdapter.setData(orders);
                orderAdapter.notifyDataSetChanged();
            }
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            if (Constants.INTENT_REQUEST_ORDER_TO_DETAIL == requestCode) {
                getOrderByRole();
            }
        }
    }

    @Override
    public void orderStatusChangeResult(boolean result, String message) {

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

    private void getOrderByRole() {
        if (Constants.ROLE_CUSTOMER == Constants.userInfo.getRole_id()) {
            orderPresenter.getOrdersByCustomer(Constants.userInfo.getUserId());
        } else {
            orderPresenter.getOrdersByStaff();
        }
    }
}
