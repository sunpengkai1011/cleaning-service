package hottopic.mit.co.nz.cleaningservice.view.home.me;

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
import hottopic.mit.co.nz.cleaningservice.entities.users.UAddress;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.presenter.home.MeEditPresenterImpl;

public class IUserEditActivity extends BaseActivity implements IUserEditView {
    private TextView tv_title, tv_username, tv_user_role;
    private EditText et_phone_number, et_email,
        et_city, et_suburb, et_street;
    private Button btn_edit;
    private RelativeLayout lyt_back;
    private UserInfo userInfo;

    private MeEditPresenterImpl meEditPresenter;

    @Override
    public void initView() {
        setContentView(R.layout.activity_me_edit);
        tv_title = findViewById(R.id.tv_title);
        tv_username = findViewById(R.id.tv_username);
        tv_user_role = findViewById(R.id.tv_user_role);
        et_phone_number = findViewById(R.id.et_phone_number);
        et_email = findViewById(R.id.et_email);
        et_city = findViewById(R.id.et_city);
        et_suburb = findViewById(R.id.et_suburb);
        et_street = findViewById(R.id.et_street);

        btn_edit = findViewById(R.id.btn_edit);
        lyt_back = findViewById(R.id.lyt_back);
    }

    @Override
    public void initData() {
        tv_title.setText(getResources().getString(R.string.title_edit));
        lyt_back.setVisibility(View.VISIBLE);
        userInfo = (UserInfo) getIntent().getSerializableExtra(Constants.KEY_INTENT_USERINFO);
        if (userInfo != null){
            setTextToEditView();
        }

        meEditPresenter = new MeEditPresenterImpl(this, this);
    }

    @Override
    public void initListener() {
        btn_edit.setOnClickListener(this);
        lyt_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_edit:
                editUserInfo();
                break;
            case R.id.lyt_back:
                this.finish();
                break;
        }
    }

    @Override
    public void editUserInfoResult(UserInfo userInfo, int code) {
        switch (code){
            case Constants.RESPONSE_CODE_FAIL:
                Toast.makeText(this, "Edit Failed!", Toast.LENGTH_SHORT).show();
                break;
            case Constants.RESPONSE_CODE_SUCCESSFUL:
                Toast.makeText(this, "Edit Successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(IUserEditActivity.this, UserActivity.class);
                intent.putExtra(Constants.KEY_INTENT_USERINFO, userInfo);
                IUserEditActivity.this.setResult(RESULT_OK, intent);
                startActivity(intent);
                this.finish();
                break;
        }
    }

    private void setTextToEditView(){
        tv_username.setText(userInfo.getUserName());
        if (userInfo.getUserRole().getRoleId() == Constants.ROLE_CUSTOMER){
            tv_user_role.setText(getResources().getString(R.string.role_customer));
        }else {
            tv_user_role.setText(getResources().getString(R.string.role_customer));
        }
        et_phone_number.setText(userInfo.getPhoneNumber());
        et_email.setText(userInfo.getEmail());
        et_city.setText(userInfo.getuAddress().getCity());
        et_suburb.setText(userInfo.getuAddress().getSuburb());
        et_street.setText(userInfo.getuAddress().getStreet());
    }

    private void editUserInfo(){
        String phoneNumber = et_phone_number.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String city = et_city.getText().toString().trim();
        String suburb = et_suburb.getText().toString().trim();
        String street = et_street.getText().toString().trim();

        if (!TextUtils.isEmpty(phoneNumber) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(city) &&
                !TextUtils.isEmpty(suburb) && !TextUtils.isEmpty(street)){
            UAddress address = new UAddress(city, suburb, street);
            userInfo.setPhoneNumber(phoneNumber);
            userInfo.setEmail(email);
            userInfo.setuAddress(address);
            meEditPresenter.editUserInfo(userInfo);
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
}
