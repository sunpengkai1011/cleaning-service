package hottopic.mit.co.nz.cleaningservice.view.home.me;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.BaseActivity;
import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.adapter.DiscountAdapter;
import hottopic.mit.co.nz.cleaningservice.entities.discounts.Discount;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.presenter.home.DiscountPresenterImpl;

public class DiscountActivity extends BaseActivity implements IDiscountView, DiscountAdapter.OnItemClickListener {
    private TextView tv_title;
    private RelativeLayout lyt_back;
    private RecyclerView rv_discount;
    private DiscountAdapter discountAdapter;

    private DiscountPresenterImpl discountPresenter;
    private List<Discount> discounts;
    private UserInfo userInfo;

    @Override
    public void initView() {
        setContentView(R.layout.activity_discount);
        tv_title = findViewById(R.id.tv_title);
        lyt_back = findViewById(R.id.lyt_back);
        rv_discount = findViewById(R.id.rv_discount);

    }

    @Override
    public void initData() {
        tv_title.setText(getResources().getString(R.string.title_discount));
        lyt_back.setVisibility(View.VISIBLE);

        discountPresenter = new DiscountPresenterImpl(this, this);
        discountPresenter.requestDiscouts();

        userInfo = (UserInfo) getIntent().getSerializableExtra(Constants.KEY_INTENT_USERINFO);
    }

    @Override
    public void initListener() {
        lyt_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lyt_back:
                this.finish();
                break;
        }
    }

    @Override
    public void getDiscountResult(List<Discount> discounts, int code) {
        this.discounts = discounts;
        if (Constants.RESPONSE_CODE_SUCCESSFUL == code){
            discountAdapter = new DiscountAdapter(this, discounts);
            discountAdapter.setOnItemClickListener(this);
            rv_discount.setLayoutManager(new LinearLayoutManager(this));
            rv_discount.setAdapter(discountAdapter);
            rv_discount.setVisibility(View.VISIBLE);
        }else {
            rv_discount.setVisibility(View.GONE);
        }
    }

    @Override
    public void getTopUpResult(UserInfo userInfo, int code) {
        if (Constants.RESPONSE_CODE_SUCCESSFUL == code){
            Toast.makeText(this, getResources().getString(R.string.top_up_successful), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DiscountActivity.this, UserActivity.class);
            intent.putExtra(Constants.KEY_INTENT_USERINFO, userInfo);
            DiscountActivity.this.setResult(RESULT_OK, intent);
            finish();
        }else {
            Toast.makeText(this, getResources().getString(R.string.top_up_fail), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(View view, final int position) {
        new AlertDialog.Builder(this).setMessage(getResources().getString(R.string.top_up_message))
                .setPositiveButton(getResources().getString(R.string.sure), new Dialog.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        discountPresenter.topUp(userInfo, discounts.get(position));
                    }
                }).setNegativeButton(getResources().getString(R.string.cancel), new Dialog.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }
}
