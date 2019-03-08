package hottopic.mit.co.nz.cleaningservice.view.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import hottopic.mit.co.nz.cleaningservice.view.order.OrdersActivity;

public class LoginActivity extends BaseActivity implements IUserView {
    private static final int MAX_SIGNIN_DURATION = 60 * 60 * 24; // Remain login for 24 hours
    private TextView tv_title, tv_to_register;
    private EditText et_username, et_password;
    private Button btn_login;

    private UserPresenterImpl loginPresenterImpl;

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
    public void initView() {
        setContentView(R.layout.activity_login);
        tv_title = findViewById(R.id.tv_title);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        tv_to_register = findViewById(R.id.tv_to_register);
    }

    @Override
    public void initData() {
        tv_title.setText(getResources().getString(R.string.title_login));
        tv_to_register.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        loginPresenterImpl = new UserPresenterImpl(this, this);

        String storeUserInfo = GeneralUtil.getDataFromSP(this, Constants.SP_KEY_USERINFO);
        if (!TextUtils.isEmpty(storeUserInfo)) {
            UserInfo userInfo = GeneralUtil.fromJson(storeUserInfo, UserInfo.class);
            et_username.setText(userInfo.getUsername());

            int lastLoginTimestamp = GeneralUtil.getIntFromSP(this, Constants.SP_KEY_LAST_LOGIN_TIMESTAMP);
            // Check last login timestamp, if less than 1 day, do not ask for login
            if (System.currentTimeMillis() / 1000 - lastLoginTimestamp < MAX_SIGNIN_DURATION) {
                Constants.userInfo = userInfo;
                ServiceTypes serviceTypes = GeneralUtil.fromJson(GeneralUtil.getDataFromSP(this, Constants.SP_KEY_SERVICE_TYPE), ServiceTypes.class);
                Intent intent;
                if (Constants.ROLE_CUSTOMER == userInfo.getRole_id()) {
                    intent = new Intent(LoginActivity.this, HomeActivity.class);
                    if (serviceTypes != null && serviceTypes.getMainServiceTypes().size() > 0) {
                        intent.putExtra(Constants.KEY_INTENT_SERVICETYPE, serviceTypes);
                    }
                } else {
                    intent = new Intent(this, OrdersActivity.class);
                }
                startActivity(intent);
                this.finish();
            }
        }
    }

    @Override
    public void initListener() {
        btn_login.setOnClickListener(this);
        tv_to_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                    loginPresenterImpl.doLogin(username, password);
                } else {
                    if (TextUtils.isEmpty(username)) {
                        Toast.makeText(this, getResources().getString(R.string.toast_empty_username), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, getResources().getString(R.string.toast_empty_password), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.tv_to_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, Constants.INTENT_REQUEST_LOGIN_TO_REGISTER);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            String username = data.getStringExtra(Constants.KEY_INTENT_USERNAME);
            if (!TextUtils.isEmpty(username)) {
                et_username.setText(username);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
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
        return false;
    }

    @Override
    public void registerResult(boolean result, String message) {

    }

    @Override
    public void loginResult(UserInfo userInfo, ServiceTypes serviceTypes, String message) {
        if (userInfo != null) {
            Constants.userInfo = userInfo;
            Intent intent;
            GeneralUtil.storDataBySP(this, Constants.SP_KEY_USERINFO, GeneralUtil.toJson(userInfo, UserInfo.class));
            GeneralUtil.storDataBySP(this, Constants.SP_KEY_SERVICE_TYPE, GeneralUtil.toJson(serviceTypes, ServiceTypes.class));
            GeneralUtil.storeIntIntoSP(this, Constants.SP_KEY_LAST_LOGIN_TIMESTAMP, (int) (System.currentTimeMillis() / 1000));
            if (Constants.ROLE_CUSTOMER == userInfo.getRole_id()) {
                intent = new Intent(LoginActivity.this, HomeActivity.class);
                if (serviceTypes != null && serviceTypes.getMainServiceTypes().size() > 0) {
                    intent.putExtra(Constants.KEY_INTENT_SERVICETYPE, serviceTypes);
                }
            } else {
                intent = new Intent(this, OrdersActivity.class);
            }
            startActivity(intent);
            this.finish();
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void userInfoEditResult(UserInfo userInfo, String message) {

    }
}
