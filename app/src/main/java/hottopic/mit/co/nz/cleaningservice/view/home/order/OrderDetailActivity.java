package hottopic.mit.co.nz.cleaningservice.view.home.order;

import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.BaseActivity;
import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.adapter.ClothesAdapter;
import hottopic.mit.co.nz.cleaningservice.adapter.OrderAdapter;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.presenter.home.OrderPresenterImpl;
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil;
import hottopic.mit.co.nz.cleaningservice.utils.custom.RatingBar;
import hottopic.mit.co.nz.cleaningservice.view.map.MapActivity;
import hottopic.mit.co.nz.cleaningservice.view.payment.PaymentActivity;

public class OrderDetailActivity extends BaseActivity implements IOrderView{
    private TextView tv_title, tv_service_type, tv_sub_option, tv_unit_price,
            tv_date, tv_address, tv_status, tv_start_time, tv_finished_time,
            tv_duration, tv_amount, tv_feedback, tv_area, tv_bulk, tv_phone;
    private EditText et_feedback;
    private Button btn_commit;
    private RelativeLayout lyt_back, lyt_price, lyt_area, lyt_started, lyt_finished, lyt_duration,
            lyt_amount, lyt_feedback, lyt_clothes, lyt_right, lyt_rating;
    private ImageView iv_icon;
    private RecyclerView rv_clothes;
    private Order order;
    private int position;
    private UserInfo userInfo;
    private RatingBar rb_stars;
    private int rating = 10;

    private OrderPresenterImpl orderPresenter;

    @Override
    public void initView() {
        setContentView(R.layout.activity_order_detail);
        tv_title = findViewById(R.id.tv_title);
        tv_service_type = findViewById(R.id.tv_service_type);
        tv_sub_option = findViewById(R.id.tv_sub_option);
        tv_unit_price = findViewById(R.id.tv_unit_price);
        tv_date = findViewById(R.id.tv_date);
        tv_address = findViewById(R.id.tv_address);
        tv_status = findViewById(R.id.tv_status);
        tv_start_time = findViewById(R.id.tv_start_time);
        tv_finished_time = findViewById(R.id.tv_finished_time);
        tv_duration = findViewById(R.id.tv_duration);
        tv_amount = findViewById(R.id.tv_amount);
        tv_feedback = findViewById(R.id.tv_feedback);
        tv_area = findViewById(R.id.tv_area);
        tv_bulk = findViewById(R.id.tv_bulk);
        tv_phone = findViewById(R.id.tv_phone);

        et_feedback = findViewById(R.id.et_feedback);
        btn_commit = findViewById(R.id.btn_commit);
        iv_icon = findViewById(R.id.iv_icon);

        lyt_back = findViewById(R.id.lyt_back);
        lyt_price = findViewById(R.id.lyt_price);
        lyt_area = findViewById(R.id.lyt_area);
        lyt_started = findViewById(R.id.lyt_started);
        lyt_finished = findViewById(R.id.lyt_finished);
        lyt_duration = findViewById(R.id.lyt_duration);
        lyt_amount = findViewById(R.id.lyt_amount);
        lyt_feedback = findViewById(R.id.lyt_feedback);
        lyt_clothes = findViewById(R.id.lyt_clothes);
        rv_clothes = findViewById(R.id.rv_clothes);
        lyt_right = findViewById(R.id.lyt_right);
        lyt_rating = findViewById(R.id.lyt_rating);
        rb_stars = findViewById(R.id.rb_stars);
    }

    @Override
    public void initData() {
        order = (Order) getIntent().getSerializableExtra(Constants.KEY_INTENT_ORDER);
        userInfo = (UserInfo) getIntent().getSerializableExtra(Constants.KEY_INTENT_USERINFO);
        position = getIntent().getIntExtra(Constants.KEY_INTENT_ORDER_POSITION, 0);
        tv_title.setText(getResources().getString(R.string.title_order_detail));
        lyt_right.setVisibility(View.VISIBLE);
        lyt_back.setVisibility(View.VISIBLE);
        iv_icon.setImageResource(R.drawable.icon_location);
        if (order != null){
            tv_service_type.setText(order.getServiceType().getTypeName());
            tv_date.setText(order.getDate());
            tv_address.setText(order.getuAddress().toString());
            if (!TextUtils.isEmpty(order.getPhone())) {
                tv_phone.setText(order.getPhone());
            }
            viewVisibleByType();


        }
        orderPresenter = new OrderPresenterImpl(this, this);
    }

    @Override
    public void initListener() {
        lyt_back.setOnClickListener(this);
        lyt_right.setOnClickListener(this);
        rb_stars.setOnRatingChangeListener(ratingCount -> {
            rating = (int) (ratingCount * 2);
            order.setRating(rating);
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lyt_back:
                this.finish();
                break;
            case R.id.lyt_right:
                String location = order.getuAddress().toString();
                location = location.replace(", ", "+");
                location = location.replace(" ", "+");
                Intent intent = new Intent(OrderDetailActivity.this, MapActivity.class);
                intent.putExtra(Constants.KEY_INTENT_LOCATION, location);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void getStartedResult(int code) {
        if (Constants.RESPONSE_CODE_SUCCESSFUL == code){
            Toast.makeText(this, getResources().getString(R.string.toast_service_started_success), Toast.LENGTH_SHORT).show();
            this.setResult(RESULT_OK);
            finish();
        }else{
            Toast.makeText(this, getResources().getString(R.string.toast_service_started_failed), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getFinishedResult(int code) {
        if (Constants.RESPONSE_CODE_SUCCESSFUL == code){
            Toast.makeText(this, getResources().getString(R.string.toast_service_finished_success), Toast.LENGTH_SHORT).show();
            this.setResult(RESULT_OK);
            finish();
        }else{
            Toast.makeText(this, getResources().getString(R.string.toast_service_finished_failed), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void bookingResult(int code) {

    }

    private void viewVisibleByType(){
        switch (order.getServiceType().getTypeId()){
            case Constants.ID_SERVICE_G_CLEANING:
            case Constants.ID_SERVICE_D_CLEANING:
                timerCleaningOrder();
                break;
            case Constants.ID_SERVICE_BUFFERING:
            case Constants.ID_SERVICE_WATERBLASTING:
            case Constants.ID_SERVICE_CARPETWASH:
                areaCleaningOrder();
                break;
            case Constants.ID_SERVICE_G_INRONING:
            case Constants.ID_SERVICE_S_INRONING:
                ironingOrder();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode){
            if (Constants.INTENT_REQUEST_DETAIL_TO_PAYMENT == requestCode){
                if (data != null){
                    Intent intent = new Intent();
                    intent.putExtra(Constants.KEY_INTENT_TO_PAYMENT, data.getIntExtra(Constants.KEY_INTENT_TO_PAYMENT, 1));
                    setResult(RESULT_OK, intent);
                }else {
                    this.setResult(RESULT_OK);
                }
                finish();
            }
        }
    }

    @Override
    public void getOrdersResult(List<Order> orders, int code) {

    }

    private void timerCleaningOrder(){
        tv_sub_option.setText(order.getSubOption().getSubOptionName());
        tv_unit_price.setText(String.valueOf(order.getSubOption().formatPrice()));
        lyt_area.setVisibility(View.GONE);
        lyt_clothes.setVisibility(View.GONE);
        switch (order.getStatus()){
            case Constants.STATUS_ORDER_BOOKED:
                lyt_started.setVisibility(View.GONE);
                lyt_finished.setVisibility(View.GONE);
                lyt_duration.setVisibility(View.GONE);
                lyt_amount.setVisibility(View.GONE);
                lyt_feedback.setVisibility(View.GONE);
                lyt_rating.setVisibility(View.GONE);
                btn_commit.setVisibility(View.VISIBLE);

                tv_status.setText(getResources().getString(R.string.status_booked));
                tv_status.setTextColor(getResources().getColor(R.color.status_other));
                btn_commit.setText(getResources().getString(R.string.btn_started));
                btn_commit.setOnClickListener(view -> {
                    orderPresenter.startedOrder(order.getUserId(), position, GeneralUtil.getCurrentTime());
                });
                break;
            case Constants.STATUS_ORDER_STARTED:
                tv_start_time.setVisibility(View.VISIBLE);
                lyt_finished.setVisibility(View.GONE);
                lyt_duration.setVisibility(View.GONE);
                lyt_amount.setVisibility(View.GONE);
                lyt_feedback.setVisibility(View.GONE);
                lyt_rating.setVisibility(View.GONE);
                btn_commit.setVisibility(View.VISIBLE);

                tv_status.setText(getResources().getString(R.string.status_started));
                tv_status.setTextColor(getResources().getColor(R.color.status_other));
                btn_commit.setText(getResources().getString(R.string.btn_finished));
                tv_start_time.setText(order.getStartTime());
                btn_commit.setOnClickListener(view -> {
                    orderPresenter.finishedOrder(order.getUserId(), position, GeneralUtil.getCurrentTime());
                });
                break;
            case Constants.STATUS_ORDER_FINISHED:
                tv_feedback.setVisibility(View.GONE);
                btn_commit.setVisibility(View.VISIBLE);

                tv_status.setText(getResources().getString(R.string.status_finished));
                tv_status.setTextColor(getResources().getColor(R.color.status_other));
                btn_commit.setText(getResources().getString(R.string.btn_payment));
                tv_start_time.setText(order.getStartTime());
                tv_finished_time.setText(order.getEndTime());
                tv_duration.setText(order.formatDuration());
                tv_amount.setText(order.formatAmount());
                btn_commit.setOnClickListener(view -> {
                    String feedback = et_feedback.getText().toString().trim();
                    Intent intent = new Intent(OrderDetailActivity.this, PaymentActivity.class);
                    if (!TextUtils.isEmpty(feedback)){
                        intent.putExtra(Constants.KEY_INTENT_FEEDBACK, feedback);
                    }else{
                        intent.putExtra(Constants.KEY_INTENT_FEEDBACK, "");
                    }
                    intent.putExtra(Constants.KEY_INTENT_ORDER_POSITION, position);
                    intent.putExtra(Constants.KEY_INTENT_USERINFO, userInfo);
                    intent.putExtra(Constants.KEY_INTENT_ORDER, order);
                    intent.putExtra(Constants.KEY_INTENT_TO_PAYMENT, Constants.INTENT_REQUEST_DETAIL_TO_PAYMENT);
                    startActivityForResult(intent, Constants.INTENT_REQUEST_DETAIL_TO_PAYMENT);
                });
                break;
            case Constants.STATUS_ORDER_PAID:
                et_feedback.setVisibility(View.GONE);
                btn_commit.setVisibility(View.GONE);

                tv_status.setText(getResources().getString(R.string.status_paid));
                tv_status.setTextColor(getResources().getColor(R.color.status_payment));
                tv_start_time.setText(order.getStartTime());
                tv_finished_time.setText(order.getEndTime());
                tv_duration.setText(order.formatDuration());
                tv_amount.setText(order.formatAmount());
                float stars = (float)order.getRating() / 2;
                rb_stars.setStar(stars);
                rb_stars.setClickable(false);
                if (!TextUtils.isEmpty(order.getFeedback())){
                    tv_feedback.setText(order.getFeedback());
                }else {
                    lyt_feedback.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void areaCleaningOrder(){
        tv_sub_option.setVisibility(View.GONE);
        tv_unit_price.setText(String.valueOf(order.getServiceType().formatPrice()));
        lyt_area.setVisibility(View.VISIBLE);
        tv_area.setText(order.formatArea());
        lyt_clothes.setVisibility(View.GONE);
        tv_amount.setText(order.formatAmount());
        lyt_started.setVisibility(View.GONE);
        lyt_finished.setVisibility(View.GONE);
        lyt_duration.setVisibility(View.GONE);
        switch (order.getStatus()){
            case Constants.STATUS_ORDER_BOOKED:
            case Constants.STATUS_ORDER_STARTED:
            case Constants.STATUS_ORDER_FINISHED:
                tv_feedback.setVisibility(View.GONE);
                btn_commit.setVisibility(View.VISIBLE);

                tv_status.setText(getResources().getString(R.string.status_finished));
                tv_status.setTextColor(getResources().getColor(R.color.status_other));
                btn_commit.setText(getResources().getString(R.string.btn_payment));

                btn_commit.setOnClickListener(view -> {
                    String feedback = et_feedback.getText().toString().trim();
                    Intent intent = new Intent(OrderDetailActivity.this, PaymentActivity.class);
                    if (!TextUtils.isEmpty(feedback)){
                        intent.putExtra(Constants.KEY_INTENT_FEEDBACK, feedback);
                    }else{
                        intent.putExtra(Constants.KEY_INTENT_FEEDBACK, "");
                    }
                    intent.putExtra(Constants.KEY_INTENT_ORDER_POSITION, position);
                    intent.putExtra(Constants.KEY_INTENT_USERINFO, userInfo);
                    intent.putExtra(Constants.KEY_INTENT_ORDER, order);
                    intent.putExtra(Constants.KEY_INTENT_TO_PAYMENT, Constants.INTENT_REQUEST_DETAIL_TO_PAYMENT);
                    startActivityForResult(intent, Constants.INTENT_REQUEST_DETAIL_TO_PAYMENT);
                });
                break;
            case Constants.STATUS_ORDER_PAID:
                et_feedback.setVisibility(View.GONE);
                btn_commit.setVisibility(View.GONE);

                tv_status.setText(getResources().getString(R.string.status_paid));
                tv_status.setTextColor(getResources().getColor(R.color.status_payment));
                float stars = (float)order.getRating() / 2;
                rb_stars.setStar(stars);
                rb_stars.setClickable(false);
                if (!TextUtils.isEmpty(order.getFeedback())){
                    tv_feedback.setText(order.getFeedback());
                }else {
                    lyt_feedback.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void ironingOrder(){
        tv_sub_option.setVisibility(View.GONE);
        lyt_price.setVisibility(View.GONE);
        lyt_area.setVisibility(View.GONE);
        lyt_clothes.setVisibility(View.VISIBLE);
        tv_amount.setText(order.formatAmount());
        lyt_started.setVisibility(View.GONE);
        lyt_finished.setVisibility(View.GONE);
        lyt_duration.setVisibility(View.GONE);
        ClothesAdapter clothesAdapter = new ClothesAdapter(this, Constants.ADAPTER_CLOTHES_DETAIL);
        clothesAdapter.setData(order.getServiceType().getClothesTypes());
        rv_clothes.setAdapter(clothesAdapter);
        rv_clothes.setLayoutManager(new LinearLayoutManager(this));
        if (order.getQuantity() > 20){
            tv_bulk.setText(order.getServiceType().formatBulkDiscount());
        }
        switch (order.getStatus()){
            case Constants.STATUS_ORDER_BOOKED:
            case Constants.STATUS_ORDER_STARTED:
            case Constants.STATUS_ORDER_FINISHED:
                btn_commit.setVisibility(View.VISIBLE);
                tv_feedback.setVisibility(View.GONE);

                tv_status.setText(getResources().getString(R.string.status_finished));
                tv_status.setTextColor(getResources().getColor(R.color.status_other));
                btn_commit.setText(getResources().getString(R.string.btn_payment));

                btn_commit.setOnClickListener(view -> {
                    String feedback = et_feedback.getText().toString().trim();
                    Intent intent = new Intent(OrderDetailActivity.this, PaymentActivity.class);
                    if (!TextUtils.isEmpty(feedback)){
                        intent.putExtra(Constants.KEY_INTENT_FEEDBACK, feedback);
                    }else{
                        intent.putExtra(Constants.KEY_INTENT_FEEDBACK, "");
                    }
                    intent.putExtra(Constants.KEY_INTENT_ORDER_POSITION, position);
                    intent.putExtra(Constants.KEY_INTENT_USERINFO, userInfo);
                    intent.putExtra(Constants.KEY_INTENT_ORDER, order);
                    intent.putExtra(Constants.KEY_INTENT_TO_PAYMENT, Constants.INTENT_REQUEST_DETAIL_TO_PAYMENT);
                    startActivityForResult(intent, Constants.INTENT_REQUEST_DETAIL_TO_PAYMENT);
                });
                break;
            case Constants.STATUS_ORDER_PAID:
                btn_commit.setVisibility(View.GONE);
                et_feedback.setVisibility(View.GONE);
                float stars = (float)order.getRating() / 2;
                rb_stars.setStar(stars);
                rb_stars.setClickable(false);
                tv_status.setText(getResources().getString(R.string.status_paid));
                tv_status.setTextColor(getResources().getColor(R.color.status_payment));
                if (!TextUtils.isEmpty(order.getFeedback())){
                    tv_feedback.setText(order.getFeedback());
                }else {
                    lyt_feedback.setVisibility(View.GONE);
                }
                break;
        }
    }
}
