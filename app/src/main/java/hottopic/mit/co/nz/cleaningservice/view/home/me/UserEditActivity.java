package hottopic.mit.co.nz.cleaningservice.view.home.me;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import hottopic.mit.co.nz.cleaningservice.BaseActivity;
import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.entities.users.UAddress;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.presenter.home.MeEditPresenterImpl;
import hottopic.mit.co.nz.cleaningservice.view.login.LoginActivity;

public class UserEditActivity extends BaseActivity implements IUserEditView {
    private TextView tv_title, tv_username, tv_user_role, tv_phone_number, tv_email, tv_address, tv_balance;
    private EditText et_phone_number, et_email, et_city, et_suburb, et_street;
    private Button btn_commit;
    private ImageView iv_icon, iv_top_up;
    private RelativeLayout lyt_back, lyt_right, lyt_user, lyt_edit;
    private UserInfo userInfo;

    private boolean isEdit = true;
    private boolean isNew = false;

    private MeEditPresenterImpl meEditPresenter;

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
    }

    @Override
    public void initData() {
        tv_title.setText(getResources().getString(R.string.title_info));
        lyt_back.setVisibility(View.VISIBLE);
        lyt_right.setVisibility(View.VISIBLE);
        iv_icon.setImageResource(R.drawable.icon_edit);
        lyt_user.setVisibility(View.VISIBLE);
        lyt_edit.setVisibility(View.GONE);
        userInfo = (UserInfo) getIntent().getSerializableExtra(Constants.KEY_INTENT_USERINFO);
        if (userInfo != null){
            setTextToEditView();
        }

        meEditPresenter = new MeEditPresenterImpl(this, this);
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
                    setResult(Constants.LOGOUT);
                    this.finish();
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
                if (isNew){
                    Intent intent = new Intent();
                    intent.putExtra(Constants.KEY_INTENT_USERINFO, userInfo);
                    UserEditActivity.this.setResult(RESULT_OK, intent);
                }
                this.finish();
                break;
            case R.id.iv_top_up:
                Intent intent = new Intent(UserEditActivity.this, DiscountActivity.class);
                intent.putExtra(Constants.KEY_INTENT_USERINFO, userInfo);
                startActivityForResult(intent, Constants.INTENT_REQUEST_ME_TO_DISCOUNT);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode){
            lyt_back.performClick();
        }
        return false;
    }

    @Override
    public void editUserInfoResult(UserInfo userInfo, int code) {
        switch (code){
            case Constants.RESPONSE_CODE_FAIL:
                Toast.makeText(this, "Edit Failed!", Toast.LENGTH_SHORT).show();
                break;
            case Constants.RESPONSE_CODE_SUCCESSFUL:
                Toast.makeText(this, "Edit Successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra(Constants.KEY_INTENT_USERINFO, userInfo);
                UserEditActivity.this.setResult(RESULT_OK, intent);
                this.finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode){
            if (Constants.INTENT_REQUEST_ME_TO_DISCOUNT == requestCode){
                if (data != null){
                    userInfo = (UserInfo) data.getSerializableExtra(Constants.KEY_INTENT_USERINFO);
                    setTextToEditView();
                    isNew = true;
                }
            }
        }
    }

    private void setTextToEditView(){
        tv_username.setText(userInfo.getUserName());
        if (userInfo.getUserRole().getRoleId() == Constants.ROLE_CUSTOMER){
            tv_user_role.setText(getResources().getString(R.string.role_customer));
        }else {
            tv_user_role.setText(getResources().getString(R.string.role_staff));
        }
        tv_phone_number.setText(userInfo.getPhoneNumber());
        tv_address.setText(userInfo.getuAddress().toString());
        tv_email.setText(userInfo.getEmail());
        tv_balance.setText(userInfo.formatBalance());
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
