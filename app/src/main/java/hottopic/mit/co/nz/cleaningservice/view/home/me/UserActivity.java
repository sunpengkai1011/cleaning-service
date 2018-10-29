package hottopic.mit.co.nz.cleaningservice.view.home.me;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.BaseActivity;
import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.adapter.OrderAdapter;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.presenter.home.HomePresenterImpl;
import hottopic.mit.co.nz.cleaningservice.presenter.home.OrderPresenterImpl;
import hottopic.mit.co.nz.cleaningservice.view.home.order.IOrderView;
import hottopic.mit.co.nz.cleaningservice.view.home.order.OrderDetailActivity;
import hottopic.mit.co.nz.cleaningservice.view.login.LoginActivity;

public class UserActivity extends BaseActivity implements IOrderView{
    private TextView tv_title, tv_username, tv_user_role;
    private RelativeLayout lyt_back, lyt_header;
    private UserInfo userInfo;
    private RecyclerView rv_orders;
    private SwipeRefreshLayout srl_orders;
    private OrderAdapter orderAdapter;

    private OrderPresenterImpl orderPresenter;
    private HomePresenterImpl homePresenter;

    private boolean isNew = false;

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
        userInfo = (UserInfo)getIntent().getSerializableExtra(Constants.KEY_INTENT_USERINFO);
        setData();
        orderPresenter = new OrderPresenterImpl(this, this);
        orderPresenter.getOrders(userInfo.getUserId());
    }

    @Override
    public void initListener() {
        lyt_back.setOnClickListener(this);
        lyt_header.setOnClickListener(this);
        srl_orders.setOnRefreshListener(() -> {
            orderPresenter.getOrders(userInfo.getUserId());
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lyt_header:
                Intent editIntent = new Intent(this, UserEditActivity.class);
                editIntent.putExtra(Constants.KEY_INTENT_USERINFO, userInfo);
                startActivityForResult(editIntent, Constants.INTENT_REQUEST_ME_TO_EDIT);
                break;
            case R.id.lyt_back:
                if (isNew) {
                    setResult(RESULT_OK);
                }
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

    private void setData(){
        if (userInfo != null){
            tv_username.setText(userInfo.getUserName());
            if (userInfo.getUserRole().getRoleId() == Constants.ROLE_CUSTOMER){
                tv_user_role.setText(this.getResources().getString(R.string.role_customer));
            }else {
                tv_user_role.setText(this.getResources().getString(R.string.role_staff));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode){
            if (Constants.INTENT_REQUEST_ORDER_TO_DETAIL == requestCode){
                orderPresenter.getOrders(userInfo.getUserId());
                if (data != null && Constants.TYPE_PAYMENT_BALANCE == data.getIntExtra(Constants.KEY_INTENT_TO_PAYMENT, 0)) {
                    homePresenter = new HomePresenterImpl(this);
                    String username = userInfo.getUserName();
                    userInfo = homePresenter.getUserInfo(username);
                    isNew = true;
                }
            }else if(Constants.INTENT_REQUEST_ME_TO_EDIT == requestCode){
                if (data != null){
                    userInfo = (UserInfo) data.getSerializableExtra(Constants.KEY_INTENT_USERINFO);
                    isNew = true;
                }
            }
        }else if(Constants.LOGOUT == resultCode){
            setResult(Constants.LOGOUT);
            this.finish();
        }
    }

    @Override
    public void getOrdersResult(List orders, int code) {
        if (Constants.RESPONSE_CODE_SUCCESSFUL == code){
            if (orderAdapter == null) {
                orderAdapter = new OrderAdapter(this);
                orderAdapter.setData(orders);
                orderAdapter.setOnItemClickListener((order, position) -> {
                    Intent intent = new Intent(UserActivity.this, OrderDetailActivity.class);
                    intent.putExtra(Constants.KEY_INTENT_USERINFO, userInfo);
                    intent.putExtra(Constants.KEY_INTENT_ORDER, order);
                    intent.putExtra(Constants.KEY_INTENT_ORDER_POSITION, position);
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
    public void getStartedResult(int code) {

    }

    @Override
    public void getFinishedResult(int code) {

    }

    @Override
    public void bookingResult(int code) {

    }
}
