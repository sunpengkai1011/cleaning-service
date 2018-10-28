package hottopic.mit.co.nz.cleaningservice.view.register;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import javax.xml.transform.Result;

import hottopic.mit.co.nz.cleaningservice.BaseActivity;
import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.entities.users.UAddress;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.presenter.register.RegisterPresenterImpl;
import hottopic.mit.co.nz.cleaningservice.view.login.LoginActivity;

public class RegisterActivity extends BaseActivity implements IRegisterView {
    private TextView tv_title;
    private EditText et_username, et_password, et_confirm_pwd, et_phone_number,
            et_email, et_city, et_suburb, et_street;
    private Button btn_register;
    private RelativeLayout lyt_back;

    private RegisterPresenterImpl registerPresenter;

    @Override
    public void initView() {
        setContentView(R.layout.activity_register);

        tv_title = findViewById(R.id.tv_title);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        et_confirm_pwd = findViewById(R.id.et_confirm_pwd);
        et_phone_number = findViewById(R.id.et_phone_number);
        et_email = findViewById(R.id.et_email);
        et_city = findViewById(R.id.et_city);
        et_suburb = findViewById(R.id.et_suburb);
        et_street = findViewById(R.id.et_street);

        btn_register = findViewById(R.id.btn_register);

        lyt_back = findViewById(R.id.lyt_back);
    }

    @Override
    public void initData() {
        tv_title.setText(R.string.title_register);
        lyt_back.setVisibility(View.VISIBLE);
        registerPresenter = new RegisterPresenterImpl(this, this);
    }

    @Override
    public void initListener() {
        btn_register.setOnClickListener(this);
        lyt_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register:
                registerUser();
                break;
            case R.id.lyt_back:
                this.finish();
                break;
        }
    }

    @Override
    public void registerResult(UserInfo userInfo, int code) {
        switch (code){
            case Constants.RESPONSE_CODE_FAIL:
                Toast.makeText(this, "Register Failed!", Toast.LENGTH_SHORT).show();
                break;
            case Constants.RESPONSE_CODE_SUCCESSFUL:
                Toast.makeText(this, "Register Successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.putExtra(Constants.KEY_INTENT_USERINFO, userInfo);
                RegisterActivity.this.setResult(RESULT_OK, intent);
                this.finish();
                break;
        }
    }

    private void registerUser(){
        String userId = String.valueOf(System.currentTimeMillis());
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String confirm_pwd = et_confirm_pwd.getText().toString().trim();
        String phone_number = et_phone_number.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String city = et_city.getText().toString().trim();
        String suburb = et_suburb.getText().toString().trim();
        String street = et_street.getText().toString().trim();

        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirm_pwd) &&
                !TextUtils.isEmpty(phone_number) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(city) &&
                !TextUtils.isEmpty(suburb) && !TextUtils.isEmpty(street)){
            if (!password.equals(confirm_pwd)){
                Toast.makeText(this, getResources().getString(R.string.toast_pwd_different), Toast.LENGTH_SHORT).show();
            }else{
                UAddress address = new UAddress(city, suburb, street);
                UserInfo userInfo = new UserInfo(userId, username, password, phone_number, email, address);
                registerPresenter.doRegister(userInfo);
            }
        }else {
            if (TextUtils.isEmpty(username)){
                Toast.makeText(this, getResources().getString(R.string.toast_empty_username), Toast.LENGTH_SHORT).show();
            }else if (TextUtils.isEmpty(password)){
                Toast.makeText(this, getResources().getString(R.string.toast_empty_password), Toast.LENGTH_SHORT).show();
            }else if (TextUtils.isEmpty(confirm_pwd)){
                Toast.makeText(this, getResources().getString(R.string.toast_confirm_pwd), Toast.LENGTH_SHORT).show();
            }else if (TextUtils.isEmpty(phone_number)){
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
}
