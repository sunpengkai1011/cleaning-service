package hottopic.mit.co.nz.cleaningservice.view.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import hottopic.mit.co.nz.cleaningservice.BaseActivity;
import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceTypes;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.presenter.user.UserPresenterImpl;
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil;
import hottopic.mit.co.nz.cleaningservice.view.order.HomeActivity;
import hottopic.mit.co.nz.cleaningservice.view.order.DiscountActivity;
import hottopic.mit.co.nz.cleaningservice.view.order.OrdersActivity;

public class UserEditActivity extends BaseActivity implements IUserView {
    private TextView tv_title, tv_username, tv_user_role, tv_phone_number, tv_email, tv_address, tv_balance;
    private EditText et_phone_number, et_email, et_city, et_suburb, et_street;
    private Button btn_commit;
    private ImageView iv_icon, iv_top_up;
    private RelativeLayout lyt_back, lyt_right, lyt_user, lyt_edit, lyt_balance;

    private boolean isEdit = true;
    private UserPresenterImpl presenter;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setViewContent();
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_me_edit);
        tv_title = findViewById(R.id.tv_title);
        tv_username = findViewById(R.id.tv_username);
        tv_user_role = findViewById(R.id.tv_user_role);
        tv_phone_number = findViewById(R.id.tv_phone_number);
        tv_email = findViewById(R.id.tv_email);
        tv_address = findViewById(R.id.tv_address);
        tv_balance = findViewById(R.id.tv_balance);
        iv_top_up = findViewById(R.id.iv_top_up);
        et_phone_number = findViewById(R.id.et_phone_number);
        et_email = findViewById(R.id.et_email);
        et_city = findViewById(R.id.et_city);
        et_suburb = findViewById(R.id.et_suburb);
        et_street = findViewById(R.id.et_street);

        btn_commit = findViewById(R.id.btn_commit);
        lyt_back = findViewById(R.id.lyt_back);
        lyt_right = findViewById(R.id.lyt_right);
        iv_icon = findViewById(R.id.iv_icon);
        lyt_user = findViewById(R.id.lyt_user);
        lyt_edit = findViewById(R.id.lyt_edit);
        lyt_balance = findViewById(R.id.lyt_balance);
    }

    @Override
    public void initData() {
        lyt_back.setVisibility(View.VISIBLE);
        lyt_right.setVisibility(View.VISIBLE);
        iv_icon.setImageResource(R.drawable.icon_edit);
        if (Constants.userInfo != null){
            setViewContent();
        }
        presenter = new UserPresenterImpl(this, this);
    }

    @Override
    public void initListener() {
        btn_commit.setOnClickListener(this);
        lyt_back.setOnClickListener(this);
        lyt_right.setOnClickListener(this);
        iv_top_up.setOnClickListener(this);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_commit:
                if (isEdit) {
                    GeneralUtil.storeIntIntoSP(this, Constants.SP_KEY_LAST_LOGIN_TIMESTAMP, 0);
                    //Logout
                    Intent intent;
                    if (Constants.ROLE_CUSTOMER == Constants.userInfo.getRole_id()) {
                        intent = new Intent(UserEditActivity.this, HomeActivity.class);
                    }else{
                        intent = new Intent(UserEditActivity.this, OrdersActivity.class);
                    }
                    intent.putExtra(Constants.KEY_INTENT_CLOSETYPE, Constants.CLOSETYPE_LOGOUT);
                    startActivity(intent);
                }else {
                    editUserInfo();
                }
                break;
            case R.id.lyt_right:
                if (isEdit){
                    lyt_user.setVisibility(View.GONE);
                    lyt_edit.setVisibility(View.VISIBLE);
                    btn_commit.setText(getResources().getString(R.string.btn_edit));
                    tv_title.setText(getResources().getString(R.string.title_edit));
                    btn_commit.setBackgroundColor(getResources().getColor(R.color.background_title_bar));
                    isEdit = false;
                }else{
                    lyt_user.setVisibility(View.VISIBLE);
                    lyt_edit.setVisibility(View.GONE);
                    btn_commit.setText(getResources().getString(R.string.btn_logout));
                    tv_title.setText(getResources().getString(R.string.title_info));
                    btn_commit.setBackgroundColor(getResources().getColor(R.color.background_logout));
                    isEdit = true;
                }
                break;
            case R.id.lyt_back:
                this.finish();
                break;
            case R.id.iv_top_up:
                Intent intent = new Intent(UserEditActivity.this, DiscountActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void setViewContent(){
        UserInfo userInfo = Constants.userInfo;
        lyt_user.setVisibility(View.VISIBLE);
        lyt_edit.setVisibility(View.GONE);
        btn_commit.setText(getResources().getString(R.string.btn_logout));
        tv_title.setText(getResources().getString(R.string.title_info));
        btn_commit.setBackgroundColor(getResources().getColor(R.color.background_logout));
        tv_username.setText(userInfo.getUsername());
        tv_user_role.setText(userInfo.getRole_name());
        if (userInfo.getRole_id() == Constants.ROLE_CUSTOMER){
            lyt_balance.setVisibility(View.VISIBLE);
        }else {
            lyt_balance.setVisibility(View.GONE);
        }
        tv_phone_number.setText(userInfo.getPhone());
        tv_address.setText(userInfo.getAddress());
        tv_email.setText(userInfo.getEmail());
        tv_balance.setText(userInfo.formatBalance());
        et_phone_number.setText(userInfo.getPhone());
        et_email.setText(userInfo.getEmail());
        et_city.setText(userInfo.getCity());
        et_suburb.setText(userInfo.getSuburb());
        et_street.setText(userInfo.getStreet());
    }

    private void editUserInfo(){
        UserInfo userInfo = Constants.userInfo;
        String phoneNumber = et_phone_number.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String city = et_city.getText().toString().trim();
        String suburb = et_suburb.getText().toString().trim();
        String street = et_street.getText().toString().trim();

        if (!TextUtils.isEmpty(phoneNumber) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(city) &&
                !TextUtils.isEmpty(suburb) && !TextUtils.isEmpty(street)){
            userInfo.setPhone(phoneNumber);
            userInfo.setEmail(email);
            userInfo.setCity(city);
            userInfo.setCity(suburb);
            userInfo.setCity(street);
            presenter.userInfoEdit(userInfo);
        }else {
            if (TextUtils.isEmpty(phoneNumber)){
                Toast.makeText(this, getResources().getString(R.string.toast_phone_number), Toast.LENGTH_SHORT).show();
            }else if (TextUtils.isEmpty(email)){
                Toast.makeText(this, getResources().getString(R.string.toast_email), Toast.LENGTH_SHORT).show();
            }else if (TextUtils.isEmpty(city)){
                Toast.makeText(this, getResources().getString(R.string.toast_city), Toast.LENGTH_SHORT).show();
            }else if (TextUtils.isEmpty(suburb)){
                Toast.makeText(this, getResources().getString(R.string.toast_suburb), Toast.LENGTH_SHORT).show();
            }else if (TextUtils.isEmpty(street)){
                Toast.makeText(this, getResources().getString(R.string.toast_street), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void registerResult(boolean result, String message) {

    }

    @Override
    public void loginResult(UserInfo userInfo, ServiceTypes serviceTypes, String message) {

    }


    @Override
    public void userInfoEditResult(UserInfo userInfo, String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        if (userInfo != null){
            Constants.userInfo = userInfo;
            setViewContent();
        }
    }
}
