package hottopic.mit.co.nz.cleaningservice.view.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public class MeFragment extends Fragment implements IMeView{
    private TextView tv_title;
    private ImageView iv_icon;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initView(View view){
        tv_title = view.findViewById(R.id.tv_title);
    }

    private void initData(){
        tv_title.setText(getResources().getString(R.string.title_me));
    }

    private void initListener(){
    }
    @Override
    public void getUserInfo(UserInfo userInfo, int code) {

    }
}
