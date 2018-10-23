package hottopic.mit.co.nz.cleaningservice.view.home.order;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import hottopic.mit.co.nz.cleaningservice.BaseActivity;
import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceType;
import hottopic.mit.co.nz.cleaningservice.entities.users.UAddress;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.presenter.home.OrderPresenterImpl;
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil;
import hottopic.mit.co.nz.cleaningservice.utils.mdatepicker.CustomDatePicker;
import hottopic.mit.co.nz.cleaningservice.view.home.HomeActivity;

public class OrderBookingActivity extends BaseActivity implements IOrderView, AdapterView.OnItemSelectedListener {
    private TextView tv_title, tv_username, tv_price_per_hour, tv_date;
    private EditText et_city, et_suburb, et_street;
    private Spinner sp_service_type;
    private RelativeLayout lyt_back;
    private Button btn_booking;
    private List<ServiceType> types;
    private List<String> typeList;
    private ArrayAdapter<String> arrayAdapter;
    private int serviceTypeNum;
    private UserInfo userInfo;

    private CustomDatePicker datePicker;

    private OrderPresenterImpl orderPresenter;

    @Override
    public void initView() {
        setContentView(R.layout.activity_booking);
        tv_title = findViewById(R.id.tv_title);
        tv_username = findViewById(R.id.tv_username);
        sp_service_type = findViewById(R.id.sp_service_type);
        tv_price_per_hour = findViewById(R.id.tv_price_per_hour);
        tv_date = findViewById(R.id.tv_date);
        et_city = findViewById(R.id.et_city);
        et_suburb = findViewById(R.id.et_suburb);
        et_street = findViewById(R.id.et_street);
        lyt_back = findViewById(R.id.lyt_back);
        btn_booking = findViewById(R.id.btn_booking);
    }

    @Override
    public void initData() {
        tv_title.setText(getResources().getString(R.string.title_booking));
        lyt_back.setVisibility(View.VISIBLE);

        orderPresenter = new OrderPresenterImpl(this, this);
        orderPresenter.getServiceType();

        userInfo = (UserInfo) getIntent().getSerializableExtra(Constants.KEY_INTENT_USERINFO);
        if (userInfo != null && !TextUtils.isEmpty(userInfo.getUserName())){
            tv_username.setText(userInfo.getUserName());
        }
        initDatePicker();
    }

    @Override
    public void initListener() {
        lyt_back.setOnClickListener(this);
        btn_booking.setOnClickListener(this);
        tv_date.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lyt_back:
                this.finish();
                break;
            case R.id.btn_booking:
                booking();
                break;
            case R.id.tv_date:
                datePicker.show(tv_date.getText().toString().trim());
                break;
        }
    }

    @Override
    public void bookingResult(int code) {
        if (Constants.RESPONSE_CODE_SUCCESSFUL == code){
            Toast.makeText(this, getResources().getString(R.string.booking_success), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(OrderBookingActivity.this, HomeActivity.class);
            intent.putExtra(Constants.KEY_INTENT_BOOKING, true);
            this.setResult(RESULT_OK, intent);
            this.finish();
        }else{
            Toast.makeText(this, getResources().getString(R.string.booking_fail), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getServiceTypeResult(List<ServiceType> types, int code) {
        if (Constants.RESPONSE_CODE_SUCCESSFUL == code){
            this.types = types;
            createTypeList();
            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeList)
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
                    textView.setTextSize(GeneralUtil.px2sp(OrderBookingActivity.this, getResources().getDimensionPixelOffset(R.dimen.textSize_middle)));
                    textView.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
                    return view;
                }
            };
            sp_service_type.setAdapter(arrayAdapter);
            sp_service_type.setOnItemSelectedListener(this);
            tv_price_per_hour.setText(String.valueOf(types.get(0).getPricePerHour()));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        serviceTypeNum = i;
        tv_price_per_hour.setText(String.valueOf(types.get(i).getPricePerHour()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void createTypeList(){
        typeList = new ArrayList<>();
        for(ServiceType type : types) {
            typeList.add(type.getTypeName());
        }
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
        Date nowDate = new Date();
        Date endDate = new Date();
        endDate.setYear(nowDate.getYear() + 5);
        String now = sdf.format(nowDate);
        String end = sdf.format(endDate);
        tv_date.setText(now);

        datePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                tv_date.setText(time);
            }
        }, now, end);
        datePicker.showSpecificTime(true);
        datePicker.setIsLoop(false);
    }

    private void booking(){
        UAddress uAddress;
        ServiceType serviceType = types.get(serviceTypeNum);
        String city = et_city.getText().toString().trim();
        String suburb = et_suburb.getText().toString().trim();
        String street = et_street.getText().toString().trim();
        String date = tv_date.getText().toString().trim();
        if (!TextUtils.isEmpty(city) && !TextUtils.isEmpty(suburb) && !TextUtils.isEmpty(street)){
            uAddress = new UAddress(city, suburb, street);
            Order order = new Order(userInfo.getUserId(), serviceType, uAddress, date);
            orderPresenter.bookingService(order, userInfo);
        }else {
            if (TextUtils.isEmpty(city)){
                if (TextUtils.isEmpty(city)){
                    Toast.makeText(this, getResources().getString(R.string.toast_city), Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(suburb)){
                    Toast.makeText(this, getResources().getString(R.string.toast_suburb), Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(street)){
                    Toast.makeText(this, getResources().getString(R.string.toast_street), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void getOrdersResult(List<Order> orders, int code) {

    }

    @Override
    public void getStartedResult(int code) {

    }

    @Override
    public void getFinishedResult(int code) {

    }
}
