package hottopic.mit.co.nz.cleaningservice.view.payment;

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

import hottopic.mit.co.nz.cleaningservice.BaseActivity;
import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.presenter.payment.PaymentPresenterImpl;
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil;
import hottopic.mit.co.nz.cleaningservice.view.home.order.OrderBookingActivity;

public class PaymentActivity extends BaseActivity implements IPaymentView, AdapterView.OnItemSelectedListener{
    private TextView tv_title, tv_title_balance, tv_balance, tv_amount;
    private EditText et_card_no;
    private RelativeLayout lyt_back;
    private Spinner sp_payment_type;
    private Button btn_payment;

    private Order order;
    private int position;
    private UserInfo userInfo;
    private String feedback;
    private ArrayAdapter arrayAdapter;
    private int type_payment;

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

        order = (Order) getIntent().getSerializableExtra(Constants.KEY_INTENT_ORDER);
        userInfo = (UserInfo) getIntent().getSerializableExtra(Constants.KEY_INTENT_USERINFO);
        feedback = getIntent().getStringExtra(Constants.KEY_INTENT_FEEDBACK);
        position = getIntent().getIntExtra(Constants.KEY_INTENT_ORDER_POSITION, 0);

        tv_amount.setText(String.valueOf(order.getAmount()));
        tv_balance.setText(String.valueOf(userInfo.getBalance()));

        paymentPresenter = new PaymentPresenterImpl(this, this);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.payment_type))
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                return setCentered(super.getView(position, convertView, parent));
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent)
            {
                return setCentered(super.getDropDownView(position, convertView, parent));
            }

            private View setCentered(View view)
            {
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setHeight(getResources().getDimensionPixelOffset(R.dimen.spinner_height));
                textView.setTextSize(GeneralUtil.px2sp(PaymentActivity.this, getResources().getDimensionPixelOffset(R.dimen.textSize_middle)));
                textView.setGravity(Gravity.CENTER_VERTICAL);
                return view;
            }
        };
        sp_payment_type.setAdapter(arrayAdapter);
    }

    @Override
    public void initListener() {
        lyt_back.setOnClickListener(this);
        btn_payment.setOnClickListener(this);
        sp_payment_type.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_payment:
                switch (type_payment){
                    case Constants.TYPE_PAYMENT_CARD:
                        String card_no = et_card_no.getText().toString().trim();
                        if (!TextUtils.isEmpty(card_no)) {
                            paymentPresenter.paymentByCard(order.getAmount(), card_no, position, feedback);
                        }else{
                            Toast.makeText(this, "Please enter the card number", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case Constants.TYPE_PAYMENT_BALANCE:
                        if (order.getAmount() < userInfo.getBalance()){
                            paymentPresenter.paymentByBalance(order.getAmount(), userInfo, position, feedback);
                        }else{
                            Toast.makeText(this, "The balance is not enough, please top up first", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                break;
            case R.id.lyt_back:
                this.finish();
                break;
        }
    }

    @Override
    public void getPaymentResult(int paymentType, int code) {
        switch (paymentType){
            case Constants.TYPE_PAYMENT_CARD:
                if (Constants.RESPONSE_CODE_SUCCESSFUL == code){
                    Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    this.finish();
                }else{
                    Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
                }
                break;
            case Constants.TYPE_PAYMENT_BALANCE:
                if (Constants.RESPONSE_CODE_SUCCESSFUL == code){
                    Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    this.finish();
                }else{
                    Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        type_payment = i;
        switch (i){
            case Constants.TYPE_PAYMENT_CARD:
                et_card_no.setVisibility(View.VISIBLE);
                tv_title_balance.setVisibility(View.GONE);
                tv_balance.setVisibility(View.GONE);
                break;
            case Constants.TYPE_PAYMENT_BALANCE:
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
