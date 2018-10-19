package hottopic.mit.co.nz.cleaningservice.view.login;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import hottopic.mit.co.nz.cleaningservice.BaseActivity;
import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.entities.UserInfo;
import hottopic.mit.co.nz.cleaningservice.presenter.login.LoginPresenterCompl;

public class LoginActivity extends BaseActivity implements ILoginView{
    private TextView tv_title;
    private EditText et_username, et_password;
    private Button btn_login;

    private LoginPresenterCompl loginPresenterCompl;

    @Override
    public void initView() {
        setContentView(R.layout.activity_login);
        tv_title = findViewById(R.id.tv_title);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
    }

    @Override
    public void initData() {
        tv_title.setText(getResources().getString(R.string.title_login));

        loginPresenterCompl = new LoginPresenterCompl(this);
    }

    @Override
    public void initListener() {
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                if (!TextUtils.isEmpty(username) &&
                        !TextUtils.isEmpty(password)){
                    loginPresenterCompl.doLogin(username, password);
                }else{
                    if (TextUtils.isEmpty(username)){
                        Toast.makeText(this, getResources().getString(R.string.toast_empty_username), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, getResources().getString(R.string.toast_empty_password), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    public void loginResult(UserInfo userInfo, int code) {
        switch (code){
            case Constants.RESPONSE_CODE_FAIL:
                Toast.makeText(this, "Login Failed!", Toast.LENGTH_SHORT).show();
                break;
            case Constants.RESPONSE_CODE_SUCCESSFUL:
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
