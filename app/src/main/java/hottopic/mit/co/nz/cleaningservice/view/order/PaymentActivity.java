package hottopic.mit.co.nz.cleaningservice.view.order;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.BaseActivity;
import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.entities.network.params.PaymentParams;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Discount;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.presenter.order.PaymentPresenterImpl;
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil;
import hottopic.mit.co.nz.cleaningservice.view.user.UserEditActivity;

public class PaymentActivity extends BaseActivity implements IPaymentView, AdapterView.OnItemSelectedListener {
    private TextView tv_title, tv_title_balance, tv_balance, tv_amount;
    private EditText et_card_no;
    private RelativeLayout lyt_back;
    private Spinner sp_payment_type;
    private Button btn_payment;

    private Order order;
    private UserInfo userInfo;
    private ArrayAdapter arrayAdapter;
    private int type_payment;
    private int orderRequest = 0;
    private Discount discount;

    private PaymentPresenterImpl paymentPresenter;

    @Override
    public void initView() {
        setContentView(R.layout.activity_payment);
        tv_amount = findViewById(R.id.tv_amount);
        tv_title = findViewById(R.id.tv_title);
        tv_title_balance = findViewById(R.id.tv_title_balance);
        tv_balance = findViewById(R.id.tv_balance);
        et_card_no = findViewById(R.id.et_card_no);
        lyt_back = findViewById(R.id.lyt_back);
        btn_payment = findViewById(R.id.btn_payment);
        sp_payment_type = findViewById(R.id.sp_payment_type);
    }

    @Override
    public void initData() {
        tv_title.setText(getResources().getString(R.string.title_payment));
        lyt_back.setVisibility(View.VISIBLE);


        userInfo = Constants.userInfo;
        orderRequest = getIntent().getIntExtra(Constants.KEY_INTENT_TO_PAYMENT, 0);
        paymentPresenter = new PaymentPresenterImpl(this, this);

        if (orderRequest != 0) {
            order = (Order) getIntent().getSerializableExtra(Constants.KEY_INTENT_ORDER);
            tv_balance.setText(String.valueOf(userInfo.formatBalance()));
            tv_amount.setText(order.formatAmount());
            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.payment_type)) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    return setCentered(super.getView(position, convertView, parent));
                }

                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    return setCentered(super.getDropDownView(position, convertView, parent));
                }

                private View setCentered(View view) {
                    TextView textView = view.findViewById(android.R.id.text1);
                    textView.setHeight(getResources().getDimensionPixelOffset(R.dimen.spinner_height));
                    textView.setTextSize(GeneralUtil.px2sp(PaymentActivity.this, getResources().getDimensionPixelOffset(R.dimen.textSize_middle)));
                    textView.setGravity(Gravity.CENTER_VERTICAL);
                    return view;
                }
            };
            sp_payment_type.setAdapter(arrayAdapter);
        } else {
            sp_payment_type.setVisibility(View.INVISIBLE);
            discount = (Discount) getIntent().getSerializableExtra(Constants.KEY_INTENT_DISCOUNT);
            tv_amount.setText(discount.formatPrice());
        }
    }

    @Override
    public void initListener() {
        lyt_back.setOnClickListener(this);
        btn_payment.setOnClickListener(this);
        sp_payment_type.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_payment:
                switch (type_payment) {
                    case Constants.TYPE_PAYMENT_CARD:
                        String card_no = et_card_no.getText().toString().trim();
                        if (!TextUtils.isEmpty(card_no)) {
                            PaymentParams paymentParams = new PaymentParams();
                            if (orderRequest == 0) {
                                discount = (Discount) getIntent().getSerializableExtra(Constants.KEY_INTENT_DISCOUNT);
                                paymentParams.setOrder(false);
                                paymentParams.setStatus(Constants.STATUS_ORDER_PAID);
                                paymentParams.setBalance(userInfo.getBalance() + discount.getBalance());
                                paymentParams.setUser_id(userInfo.getUserId());
                                paymentPresenter.paymentByBalance(paymentParams);
                            } else {
                                paymentParams.setStatus(Constants.STATUS_ORDER_PAID);
                                paymentParams.setOrder_id(order.getId());
                                paymentParams.setFeedback(order.getFeedback());
                                paymentParams.setRating(order.getRating());
                                paymentPresenter.paymentByCard(paymentParams);
                            }
                        } else {
                            Toast.makeText(this, getResources().getString(R.string.toast_card_no), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case Constants.TYPE_PAYMENT_BALANCE:
                        PaymentParams paymentParams = new PaymentParams();
                        if (orderRequest != 0) {
                            paymentParams.setStatus(Constants.STATUS_ORDER_PAID);
                            paymentParams.setOrder(true);
                            paymentParams.setOrder_id(order.getId());
                            paymentParams.setFeedback(order.getFeedback());
                            paymentParams.setRating(order.getRating());
                            paymentParams.setBalance(userInfo.getBalance() - order.getAmount());
                        } else {
                            discount = (Discount) getIntent().getSerializableExtra(Constants.KEY_INTENT_DISCOUNT);
                            paymentParams.setOrder(false);
                            paymentParams.setBalance(userInfo.getBalance() + discount.getBalance());
                        }
                        paymentParams.setUser_id(userInfo.getUserId());
                        paymentPresenter.paymentByBalance(paymentParams);
                        break;
                }
                break;
            case R.id.lyt_back:
                this.finish();
                break;
        }
    }

    @Override
    public void getDiscountsResult(List<Discount> discounts, String message) {

    }

    @Override
    public void getPaymentBalanceResult(UserInfo userInfo, String message, int type) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        if (userInfo != null) {
            Constants.userInfo = userInfo;
            if (Constants.TYPE_PAYMENT_ORDER == type) {
                setResult(RESULT_OK);
            } else {
                Intent intent = new Intent(this, UserEditActivity.class);
                startActivity(intent);
            }
            this.finish();
        }
    }

    @Override
    public void getPaymentCardResult(boolean result, String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        if(result) {
            setResult(RESULT_OK);
            this.finish();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        type_payment = i;
        switch (i) {
            case Constants.TYPE_PAYMENT_CARD:
                btn_payment.setEnabled(true);
                et_card_no.setVisibility(View.VISIBLE);
                tv_title_balance.setVisibility(View.GONE);
                tv_balance.setVisibility(View.GONE);
                break;
            case Constants.TYPE_PAYMENT_BALANCE:
                if (userInfo.getBalance() < order.getAmount()) {
                    btn_payment.setEnabled(false);
                } else {
                    btn_payment.setEnabled(true);
                }
                et_card_no.setVisibility(View.GONE);
                tv_title_balance.setVisibility(View.VISIBLE);
                tv_balance.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
