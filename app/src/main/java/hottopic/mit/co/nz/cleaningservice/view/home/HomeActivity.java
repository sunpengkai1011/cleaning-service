package hottopic.mit.co.nz.cleaningservice.view.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.ArrayList;
import java.util.List;

import hottopic.mit.co.nz.cleaningservice.BaseActivity;
import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.adapter.ServiceAdapter;
import hottopic.mit.co.nz.cleaningservice.adapter.ViewPagerAdapter;
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceType;
import hottopic.mit.co.nz.cleaningservice.entities.orders.TitleModel;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.presenter.home.HomePresenterImpl;
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil;
import hottopic.mit.co.nz.cleaningservice.view.home.me.UserActivity;
import hottopic.mit.co.nz.cleaningservice.view.home.me.UserEditActivity;
import hottopic.mit.co.nz.cleaningservice.view.home.order.OrderBookingActivity;
import hottopic.mit.co.nz.cleaningservice.view.login.LoginActivity;

public class HomeActivity extends BaseActivity implements IHomeView, RecyclerViewPager.OnPageChangedListener, ServiceAdapter.OnItemClickedListener {
    private TextView tv_title, tv_page_number;
    private RecyclerView rv_cleaning;
    private ServiceAdapter cleaningAdapter;
    private ImageView iv_icon;
    private RelativeLayout lyt_right;
    private RecyclerViewPager rvp_ad;
    private HomePresenterImpl homePresenter;
    private UserInfo userInfo;

    private int totalPages = 1;
    private int autoPage = 0;
    private boolean isQuit = false;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Constants.WHAT_ADVERTISEMENT:
                    autoPage++;
                    if (autoPage > totalPages) {
                        autoPage = 0;
                        rvp_ad.scrollToPosition(autoPage);
                    } else {
                        rvp_ad.scrollToPosition(autoPage);
                    }
                    handler.sendEmptyMessageDelayed(Constants.WHAT_ADVERTISEMENT, 3000);
                    break;
                case Constants.WHAT_EXIT:
                    isQuit = false;
                    break;
            }
        }
    };

    private static final int[] ad_list = {R.drawable.advertisement_1, R.drawable.advertisement_2, R.drawable.advertisement_3,
            R.drawable.advertisement_4};

    @Override
    public void initView() {
        setContentView(R.layout.activity_home);
        tv_title = findViewById(R.id.tv_title);
        tv_page_number = findViewById(R.id.tv_page_number);
        rv_cleaning = findViewById(R.id.rv_cleaning);
        rvp_ad = findViewById(R.id.rvp_ad);
        lyt_right = findViewById(R.id.lyt_right);
        iv_icon = findViewById(R.id.iv_icon);
    }

    @Override
    public void initData() {
        tv_title.setText("HOME");
        lyt_right.setVisibility(View.VISIBLE);
        iv_icon.setImageResource(R.drawable.icon_user);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(GeneralUtil.dip2px(this,30), GeneralUtil.dip2px(this, 30));
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        iv_icon.setLayoutParams(layoutParams);
        homePresenter = new HomePresenterImpl(this, this);
        homePresenter.getServiceTypes();

        userInfo = (UserInfo) getIntent().getSerializableExtra(Constants.KEY_INTENT_USERINFO);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);//LinearLayoutManager.HORIZONTAL 设置水平滚动
        rvp_ad.setLayoutManager(layoutManager);
        rvp_ad.setAdapter(new ViewPagerAdapter(this, ad_list));
        totalPages = ad_list.length;
        tv_page_number.setText("1/" + totalPages);
        //Start the automatic page turning
        handler.sendEmptyMessageDelayed(Constants.WHAT_ADVERTISEMENT, 3000);
    }

    @Override
    public void initListener() {
        rvp_ad.addOnPageChangedListener(this);
        cleaningAdapter.setOnItemClickedListener(this);
        lyt_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lyt_right:
                Intent intent = new Intent(HomeActivity.this, UserActivity.class);
                intent.putExtra(Constants.KEY_INTENT_USERINFO, userInfo);
                startActivityForResult(intent, Constants.INTENT_REQUEST_HOME_TO_USER);
                break;
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
    public void getServiceTypes(List<ServiceType> cleaningTypes, List<ServiceType> ironingTypes) {
        List dataList = new ArrayList();
        TitleModel titleModel = new TitleModel("Cleaning Service");
        dataList.add(titleModel);
        if (cleaningTypes != null) {
            dataList.addAll(cleaningTypes);
        }
        titleModel = new TitleModel("Ironing Service");
        dataList.add(titleModel);
        if (ironingTypes != null) {
            dataList.addAll(ironingTypes);
        }
        initServiceAdapter(dataList);
    }

    private void initServiceAdapter(List dataList) {
        cleaningAdapter = new ServiceAdapter(this);
        cleaningAdapter.setData(dataList);
        rv_cleaning.setAdapter(cleaningAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (dataList.get(position) instanceof TitleModel) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
        rv_cleaning.setLayoutManager(gridLayoutManager);
        rv_cleaning.setHasFixedSize(true);
        rv_cleaning.setNestedScrollingEnabled(false);
        rv_cleaning.setFocusable(false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void OnPageChanged(int oldPosition, int newPosition) {
        //User to show the current photo position
        Log.d("OnPageChanged", newPosition + "");
        tv_page_number.setText(newPosition + 1 + "/" + totalPages);
        autoPage = newPosition;
    }

    @Override
    public void OnItemClicked(ServiceType serviceType) {
        Intent intent = new Intent(HomeActivity.this, OrderBookingActivity.class);
        intent.putExtra(Constants.KEY_INTENT_SERVICETYPE, serviceType);
        intent.putExtra(Constants.KEY_INTENT_USERINFO, userInfo);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Constants.LOGOUT == resultCode){
            this.finish();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
        }else if(RESULT_OK == resultCode){
            userInfo = homePresenter.getUserInfo(userInfo.getUserName());
        }
    }
}
