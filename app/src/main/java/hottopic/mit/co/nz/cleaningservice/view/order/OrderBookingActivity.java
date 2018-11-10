package hottopic.mit.co.nz.cleaningservice.view.order;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
import hottopic.mit.co.nz.cleaningservice.adapter.ClothesAdapter;
import hottopic.mit.co.nz.cleaningservice.entities.network.OrderBooking;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceProduct;
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceTypes;
import hottopic.mit.co.nz.cleaningservice.entities.orders.SubServiceType;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.presenter.order.OrderPresenterImpl;
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil;
import hottopic.mit.co.nz.cleaningservice.utils.mdatepicker.CustomDatePicker;

import static android.widget.AdapterView.OnItemSelectedListener;

public class OrderBookingActivity extends BaseActivity implements IOrderView{
    private TextView tv_title, tv_username, tv_unit_price, tv_bulk, tv_service_type;
    private RelativeLayout lyt_unit_price, lyt_area, lyt_clothes, lyt_sub_option;
    private CheckBox cb_user_info;
    private EditText et_city, et_suburb, et_street, et_area, et_phone;
    private RelativeLayout lyt_back;
    private RecyclerView rv_clothes;
    private Button btn_booking, btn_date;
    private Spinner sp_sub_option;
    private UserInfo userInfo;

    private SubServiceType serviceType;
    private ClothesAdapter clothesAdapter;

    private List<String> subOptions;
    private int subOptionPosition = 0;

    private CustomDatePicker datePicker;

    private OrderPresenterImpl orderPresenter;

    @Override
    public void initView() {
        setContentView(R.layout.activity_booking);
        tv_title = findViewById(R.id.tv_title);
        tv_username = findViewById(R.id.tv_username);
        tv_unit_price = findViewById(R.id.tv_unit_price);
        tv_bulk = findViewById(R.id.tv_bulk);
        tv_service_type = findViewById(R.id.tv_service_type);
        cb_user_info = findViewById(R.id.cb_user_info);
        lyt_unit_price = findViewById(R.id.lyt_unit_price);
        lyt_area = findViewById(R.id.lyt_area);
        lyt_clothes = findViewById(R.id.lyt_clothes);
        lyt_sub_option = findViewById(R.id.lyt_sub_option);
        sp_sub_option = findViewById(R.id.sp_sub_option);
        btn_date = findViewById(R.id.btn_date);
        et_city = findViewById(R.id.et_city);
        et_suburb = findViewById(R.id.et_suburb);
        et_street = findViewById(R.id.et_street);
        et_area = findViewById(R.id.et_area);
        et_phone = findViewById(R.id.et_phone);
        rv_clothes = findViewById(R.id.rv_clothes);
        lyt_back = findViewById(R.id.lyt_back);
        btn_booking = findViewById(R.id.btn_booking);
    }

    @Override
    public void initData() {
        tv_title.setText(getResources().getString(R.string.title_booking));
        lyt_back.setVisibility(View.VISIBLE);

        orderPresenter = new OrderPresenterImpl(this, this);

        userInfo = Constants.userInfo;
        serviceType = (SubServiceType) getIntent().getSerializableExtra(Constants.KEY_INTENT_SERVICETYPE);

        if (userInfo != null && !TextUtils.isEmpty(userInfo.getUsername())){
            tv_username.setText(userInfo.getUsername());
        }
        if (serviceType != null) {
            tv_service_type.setText(serviceType.getType_name());
            switch (serviceType.getId()){
                case Constants.ID_SERVICE_G_CLEANING:
                case Constants.ID_SERVICE_D_CLEANING:
                    initSpinnerData();
                    break;
                case Constants.ID_SERVICE_BUFFERING:
                case Constants.ID_SERVICE_WATERBLASTING:
                case Constants.ID_SERVICE_CARPETWASH:
                    tv_unit_price.setText(serviceType.getProducts().get(0).formatPrice());
                    break;
                case Constants.ID_SERVICE_G_INRONING:
                case Constants.ID_SERVICE_S_INRONING:
                    tv_bulk.setText(serviceType.formatBulkDiscount());
                    initClothesList();
                    break;
            }
        }
        initDatePicker();
        visibleByType();
    }

    @Override
    public void initListener() {
        lyt_back.setOnClickListener(this);
        btn_booking.setOnClickListener(this);
        btn_date.setOnClickListener(this);
        cb_user_info.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                et_city.setText(userInfo.getCity());
                et_suburb.setText(userInfo.getSuburb());
                et_street.setText(userInfo.getStreet());
                et_phone.setText(userInfo.getPhone());
            }else{
                et_city.setText("");
                et_suburb.setText("");
                et_street.setText("");
                et_phone.setText("");
            }
        });
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
            case R.id.btn_date:
                datePicker.show(btn_date.getText().toString().trim());
                break;
        }
    }
    @Override
    public void getOrdersResult(List<Order> orders, String message) {

    }

    @Override
    public void orderStatusChangeResult(Order order, String message) {

    }

    @Override
    public void bookingResult(boolean result, String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        if (result){
            this.finish();
        }
    }

    @Override
    public void getServiceError(String message) {

    }

    @Override
    public void getServiceTypes(ServiceTypes serviceTypes) {

    }

    private void visibleByType(){
        if (serviceType != null) {
            switch (serviceType.getId()){
                case Constants.ID_SERVICE_G_CLEANING:
                case Constants.ID_SERVICE_D_CLEANING:
                    lyt_sub_option.setVisibility(View.VISIBLE);
                    lyt_area.setVisibility(View.GONE);
                    lyt_clothes.setVisibility(View.GONE);
                    break;
                case Constants.ID_SERVICE_BUFFERING:
                case Constants.ID_SERVICE_WATERBLASTING:
                case Constants.ID_SERVICE_CARPETWASH:
                    lyt_sub_option.setVisibility(View.GONE);
                    lyt_clothes.setVisibility(View.GONE);
                    lyt_area.setVisibility(View.VISIBLE);
                    break;
                case Constants.ID_SERVICE_G_INRONING:
                case Constants.ID_SERVICE_S_INRONING:
                    lyt_sub_option.setVisibility(View.GONE);
                    lyt_area.setVisibility(View.GONE);
                    lyt_clothes.setVisibility(View.VISIBLE);
                    lyt_unit_price.setVisibility(View.GONE);
                    break;
            }
        }
    }


    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
        Date nowDate = new Date();
        Date endDate = new Date();
        endDate.setYear(nowDate.getYear() + 5);
        String now = sdf.format(nowDate);
        String end = sdf.format(endDate);
        btn_date.setText(now);

        datePicker = new CustomDatePicker(this, time -> btn_date.setText(time), now, end);
        datePicker.showSpecificTime(true);
        datePicker.setIsLoop(false);
    }

    private void initSpinnerData(){
        if (serviceType != null){
            subOptions = new ArrayList<>();
            for (ServiceProduct product : serviceType.getProducts()){
                subOptions.add(product.getProduct_name());
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subOptions)
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
            sp_sub_option.setAdapter(arrayAdapter);
            sp_sub_option.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    subOptionPosition = i;
                    tv_unit_price.setText(serviceType.getProducts().get(subOptionPosition).formatPrice());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    subOptionPosition = 0;
                }
            });
        }
    }

    private void initClothesList(){
        clothesAdapter = new ClothesAdapter(this, Constants.ADAPTER_CLOTHES_BOOKING);
        clothesAdapter.setData(serviceType.getProducts());
        rv_clothes.setAdapter(clothesAdapter);
        rv_clothes.setLayoutManager(new LinearLayoutManager(this));
    }

    private void booking(){
        Order order = null;
        String city = et_city.getText().toString().trim();
        String suburb = et_suburb.getText().toString().trim();
        String street = et_street.getText().toString().trim();
        String date = btn_date.getText().toString().trim();
        String phone = et_phone.getText().toString().trim();
        if (!TextUtils.isEmpty(city) && !TextUtils.isEmpty(suburb) && !TextUtils.isEmpty(street) && !TextUtils.isEmpty(phone)){
            if (serviceType != null) {
                switch (serviceType.getId()){
                    case Constants.ID_SERVICE_G_CLEANING:
                    case Constants.ID_SERVICE_D_CLEANING:
                        order = new Order(userInfo.getUserId(), date, createTimingService(serviceType), phone, city, suburb, street, 0, 0, 0, 10);
                        break;
                    case Constants.ID_SERVICE_BUFFERING:
                    case Constants.ID_SERVICE_WATERBLASTING:
                    case Constants.ID_SERVICE_CARPETWASH:
                        if (!TextUtils.isEmpty(et_area.getText().toString())){
                            int area = Integer.valueOf(et_area.getText().toString());
                            if (area > 0) {
                                float amount = area * serviceType.getProducts().get(0).getUnit_price();
                                order = new Order(userInfo.getUserId(), date, serviceType, phone, city, suburb, street, 0, amount, area, 10);
                            }else {
                                Toast.makeText(this, "Please enter the area.", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(this, "Please enter the area.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        break;
                    case Constants.ID_SERVICE_G_INRONING:
                    case Constants.ID_SERVICE_S_INRONING:
                        float amount = 0;
                        int total_pieces = 0;
                        for(ServiceProduct clothesType : serviceType.getProducts()){
                            total_pieces += clothesType.getQuantity();
                            amount += clothesType.getQuantity() * clothesType.getUnit_price();
                        }
                        if (total_pieces != 0){
                            if (total_pieces >= 20){
                                amount = amount * serviceType.getBulk_discount();
                            }
                            order = new Order(userInfo.getUserId(), date, serviceType, phone, city, suburb, street, 0, amount, total_pieces, 10);
                        }else{
                            Toast.makeText(this, "Please enter the clothes pieces.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        break;
                }
            }
            if (order != null) {
                OrderBooking orderBooking = new OrderBooking();
                orderBooking.setOrder(order);
                orderBooking.getProducts();
                orderBooking.setProducts(order.getSubServiceType().getProducts());
                orderPresenter.orderBooking(orderBooking);
            }
        }else {
            if (TextUtils.isEmpty(city)){
                if (TextUtils.isEmpty(city)){
                    Toast.makeText(this, getResources().getString(R.string.toast_city), Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(suburb)){
                    Toast.makeText(this, getResources().getString(R.string.toast_suburb), Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(street)){
                    Toast.makeText(this, getResources().getString(R.string.toast_street), Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(phone)){
                    Toast.makeText(this, getResources().getString(R.string.toast_phone_number), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private SubServiceType createTimingService(SubServiceType subServiceType){
        List<ServiceProduct> products = new ArrayList<>();
        products.add(subServiceType.getProducts().get(subOptionPosition));
        SubServiceType timingService = subServiceType;
        timingService.setProducts(products);
        return timingService;
    }
}
