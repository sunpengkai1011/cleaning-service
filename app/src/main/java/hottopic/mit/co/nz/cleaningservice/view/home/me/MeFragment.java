package hottopic.mit.co.nz.cleaningservice.view.home.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.view.login.LoginActivity;

public class MeFragment extends Fragment implements View.OnClickListener{
    private TextView tv_title, tv_username, tv_user_role, tv_phone_number,
            tv_email, tv_address, tv_balance;
    private RelativeLayout lyt_right;
    private Button btn_logout;
    private UserInfo userInfo;
    private ImageView iv_icon, iv_top_up;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initView(View view){
        tv_title = view.findViewById(R.id.tv_title);
        tv_username = view.findViewById(R.id.tv_username);
        tv_user_role = view.findViewById(R.id.tv_user_role);
        tv_phone_number = view.findViewById(R.id.tv_phone_number);
        tv_email = view.findViewById(R.id.tv_email);
        tv_address = view.findViewById(R.id.tv_address);
        tv_balance = view.findViewById(R.id.tv_balance);
        iv_top_up = view.findViewById(R.id.iv_top_up);
        lyt_right = view.findViewById(R.id.lyt_right);
        btn_logout = view.findViewById(R.id.btn_logout);
        iv_icon = view.findViewById(R.id.iv_icon);
    }

    private void initData(){
        tv_title.setText(getResources().getString(R.string.title_me));
        lyt_right.setVisibility(View.VISIBLE);
        iv_icon.setImageResource(R.drawable.logo_edit);
        userInfo = (UserInfo) getActivity().getIntent().getSerializableExtra(Constants.KEY_INTENT_USERINFO);
        setData();
    }

    private void initListener(){
        btn_logout.setOnClickListener(this);
        lyt_right.setOnClickListener(this);
        iv_top_up.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_logout:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.lyt_right:
                Intent editIntent = new Intent(getActivity(), MeEditActivity.class);
                editIntent.putExtra(Constants.KEY_INTENT_USERINFO, userInfo);
                startActivityForResult(editIntent, Constants.INTENT_REQUEST_ME_TO_EDIT);
                break;
            case R.id.iv_top_up:
                Intent topUpIntent = new Intent(getActivity(), DiscountActivity.class);
                topUpIntent.putExtra(Constants.KEY_INTENT_USERINFO, userInfo);
                startActivityForResult(topUpIntent, Constants.INTENT_REQUEST_ME_TO_DISCOUNT);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (getActivity().RESULT_OK == resultCode) {
            if (Constants.INTENT_REQUEST_ME_TO_EDIT == requestCode|| Constants.INTENT_REQUEST_ME_TO_DISCOUNT == requestCode) {
                userInfo = (UserInfo) data.getSerializableExtra(Constants.KEY_INTENT_USERINFO);
                setData();
            }
        }
    }

    private void setData(){
        if (userInfo != null){
            tv_username.setText(userInfo.getUserName());
            if (userInfo.getUserRole().getRoleId() == Constants.ROLE_CUSTOMER){
                tv_user_role.setText(getActivity().getResources().getString(R.string.role_customer));
            }else {
                tv_user_role.setText(getActivity().getResources().getString(R.string.role_customer));
            }
            tv_phone_number.setText(userInfo.getPhoneNumber());
            tv_email.setText(userInfo.getEmail());
            tv_address.setText(userInfo.getuAddress().toString());
            tv_balance.setText(String.valueOf(userInfo.getBalance()));
        }
    }
}
