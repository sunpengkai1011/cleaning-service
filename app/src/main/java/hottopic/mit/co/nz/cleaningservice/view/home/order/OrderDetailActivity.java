package hottopic.mit.co.nz.cleaningservice.view.home.order;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import hottopic.mit.co.nz.cleaningservice.BaseActivity;
import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.presenter.home.IOrderPresenter;
import hottopic.mit.co.nz.cleaningservice.presenter.home.OrderPresenterImpl;
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil;
import hottopic.mit.co.nz.cleaningservice.view.home.HomeActivity;

public class OrderDetailActivity extends BaseActivity implements IOrderDetailView{
    private TextView tv_title, tv_service_type, tv_price_per_hour, tv_date,
            tv_address, tv_status, tv_start_time, tv_finished_time,
            tv_duration, tv_amount, tv_feedback, tv_title_start_time,
            tv_title_finished_time, tv_title_duration, tv_title_amount,
            tv_title_feedback;
    private EditText et_feedback;
    private Button btn_started, btn_finished, btn_payment;
    private RelativeLayout lyt_back;
    private Order order;
    private int position;
    private UserInfo userInfo;

    private OrderPresenterImpl orderPresenter;

    @Override
    public void initView() {
        setContentView(R.layout.activity_order_detail);
        tv_title = findViewById(R.id.tv_title);
        tv_service_type = findViewById(R.id.tv_service_type);
        tv_price_per_hour = findViewById(R.id.tv_price_per_hour);
        tv_date = findViewById(R.id.tv_date);
        tv_address = findViewById(R.id.tv_address);
        tv_status = findViewById(R.id.tv_status);
        tv_start_time = findViewById(R.id.tv_start_time);
        tv_finished_time = findViewById(R.id.tv_finished_time);
        tv_duration = findViewById(R.id.tv_duration);
        tv_amount = findViewById(R.id.tv_amount);
        tv_feedback = findViewById(R.id.tv_feedback);
        btn_started = findViewById(R.id.btn_started);
        btn_finished = findViewById(R.id.btn_finished);
        tv_title_start_time = findViewById(R.id.tv_title_start_time);
        tv_title_finished_time = findViewById(R.id.tv_title_finished_time);
        tv_title_duration = findViewById(R.id.tv_title_duration);
        tv_title_amount = findViewById(R.id.tv_title_amount);
        tv_title_feedback = findViewById(R.id.tv_title_feedback);
        et_feedback = findViewById(R.id.et_feedback);
        btn_payment = findViewById(R.id.btn_payment);
        lyt_back = findViewById(R.id.lyt_back);
    }

    @Override
    public void initData() {
        order = (Order) getIntent().getSerializableExtra(Constants.KEY_INTENT_ORDER);
        userInfo = (UserInfo) getIntent().getSerializableExtra(Constants.KEY_INTENT_USERINFO);
        position = getIntent().getIntExtra(Constants.KEY_INTENT_ORDER_POSITION, 0);
        tv_title.setText(getResources().getString(R.string.title_order_detail));
        lyt_back.setVisibility(View.VISIBLE);
        if (order != null){
            tv_service_type.setText(order.getServiceType().getTypeName());
            tv_price_per_hour.setText(String.valueOf(order.getServiceType().getPricePerHour()));
            tv_date.setText(order.getDate());
            tv_address.setText(order.getuAddress().toString());
            tv_start_time.setText(order.getStartTime());
            tv_finished_time.setText(order.getEndTime());
            tv_duration.setText(String.valueOf(order.getDuration()));
            tv_amount.setText(String.valueOf(order.getAmount()));
            tv_feedback.setText(order.getFeedback());
            viewVisibleByStatus();
        }

        orderPresenter = new OrderPresenterImpl(this, this);
    }

    @Override
    public void initListener() {
        lyt_back.setOnClickListener(this);
        btn_started.setOnClickListener(this);
        btn_finished.setOnClickListener(this);
        btn_payment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lyt_back:
                this.finish();
                break;
            case R.id.btn_finished:
                orderPresenter.finishedOrder(position, GeneralUtil.getCurrentTime());
                break;
            case R.id.btn_started:
                orderPresenter.startedOrder(position, GeneralUtil.getCurrentTime());
                break;
            case R.id.btn_payment:
                String feedback = et_feedback.getText().toString().trim();
                if (!TextUtils.isEmpty(feedback)){

                }
                break;
        }
    }

    @Override
    public void getStartedResult(int code) {
        if (Constants.RESPONSE_CODE_SUCCESSFUL == code){
            Toast.makeText(this, getResources().getString(R.string.toast_service_started_success), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(OrderDetailActivity.this, HomeActivity.class);
            this.setResult(RESULT_OK, intent);
            finish();
        }else{
            Toast.makeText(this, getResources().getString(R.string.toast_service_started_failed), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getFinishedResult(int code) {
        if (Constants.RESPONSE_CODE_SUCCESSFUL == code){
            Toast.makeText(this, getResources().getString(R.string.toast_service_finished_success), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(OrderDetailActivity.this, HomeActivity.class);
            this.setResult(RESULT_OK, intent);
            finish();
        }else{
            Toast.makeText(this, getResources().getString(R.string.toast_service_finished_failed), Toast.LENGTH_SHORT).show();
        }
    }

    private void viewVisibleByStatus(){
        switch (order.getOrderStatus()){
            case Constants.STATUS_ORDER_BOOKED:
                tv_status.setText(getResources().getString(R.string.status_booked));
                tv_title_start_time.setVisibility(View.GONE);
                tv_title_finished_time.setVisibility(View.GONE);
                tv_title_duration.setVisibility(View.GONE);
                tv_title_amount.setVisibility(View.GONE);
                tv_title_feedback.setVisibility(View.GONE);
                tv_start_time.setVisibility(View.GONE);
                tv_finished_time.setVisibility(View.GONE);
                tv_duration.setVisibility(View.GONE);
                tv_amount.setVisibility(View.GONE);
                tv_feedback.setVisibility(View.GONE);
                btn_started.setVisibility(View.VISIBLE);
                btn_finished.setVisibility(View.GONE);
                btn_payment.setVisibility(View.GONE);
                et_feedback.setVisibility(View.GONE);
                break;
            case Constants.STATUS_ORDER_STARTED:
                tv_status.setText(getResources().getString(R.string.status_started));
                tv_title_start_time.setVisibility(View.VISIBLE);
                tv_title_finished_time.setVisibility(View.GONE);
                tv_title_duration.setVisibility(View.GONE);
                tv_title_amount.setVisibility(View.GONE);
                tv_title_feedback.setVisibility(View.GONE);
                tv_start_time.setVisibility(View.VISIBLE);
                tv_finished_time.setVisibility(View.GONE);
                tv_duration.setVisibility(View.GONE);
                tv_amount.setVisibility(View.GONE);
                tv_feedback.setVisibility(View.GONE);
                btn_started.setVisibility(View.GONE);
                btn_finished.setVisibility(View.VISIBLE);
                btn_payment.setVisibility(View.GONE);
                et_feedback.setVisibility(View.GONE);
                break;
            case Constants.STATUS_ORDER_FINISHED:
                tv_status.setText(getResources().getString(R.string.status_finished));
                tv_title_start_time.setVisibility(View.VISIBLE);
                tv_title_finished_time.setVisibility(View.VISIBLE);
                tv_title_duration.setVisibility(View.VISIBLE);
                tv_title_amount.setVisibility(View.VISIBLE);
                tv_title_feedback.setVisibility(View.VISIBLE);
                tv_start_time.setVisibility(View.VISIBLE);
                tv_finished_time.setVisibility(View.VISIBLE);
                tv_duration.setVisibility(View.VISIBLE);
                tv_amount.setVisibility(View.VISIBLE);
                tv_feedback.setVisibility(View.GONE);
                btn_started.setVisibility(View.GONE);
                btn_finished.setVisibility(View.GONE);
                btn_payment.setVisibility(View.VISIBLE);
                et_feedback.setVisibility(View.VISIBLE);
                break;
            case Constants.STATUS_ORDER_PAID:
                tv_status.setText(getResources().getString(R.string.status_paid));
                tv_title_start_time.setVisibility(View.VISIBLE);
                tv_title_finished_time.setVisibility(View.VISIBLE);
                tv_title_duration.setVisibility(View.VISIBLE);
                tv_title_amount.setVisibility(View.VISIBLE);
                tv_title_feedback.setVisibility(View.VISIBLE);
                tv_start_time.setVisibility(View.VISIBLE);
                tv_finished_time.setVisibility(View.VISIBLE);
                tv_duration.setVisibility(View.VISIBLE);
                tv_amount.setVisibility(View.VISIBLE);
                tv_feedback.setVisibility(View.VISIBLE);
                btn_started.setVisibility(View.GONE);
                btn_finished.setVisibility(View.GONE);
                btn_payment.setVisibility(View.GONE);
                et_feedback.setVisibility(View.GONE);
                break;
        }
//        if (Constants.ROLE_CUSTOMER == userInfo.getUserRole().getRoleId()){
//            btn_started.setVisibility(View.GONE);
//            btn_finished.setVisibility(View.GONE);
//        }else{
//            btn_payment.setVisibility(View.GONE);
//            et_feedback.setVisibility(View.GONE);
//        }
    }
}
