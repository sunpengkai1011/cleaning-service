package hottopic.mit.co.nz.cleaningservice.view.order;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.BaseActivity;
import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.adapter.DiscountAdapter;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Discount;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.presenter.order.DiscountPresenterImpl;
import hottopic.mit.co.nz.cleaningservice.view.user.UserEditActivity;

public class DiscountActivity extends BaseActivity implements IDiscountView {
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

        userInfo = Constants.userInfo;
    }

    @Override
    public void initListener() {
        lyt_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lyt_back:
                this.finish();
                break;
        }
    }

    @Override
    public void getDiscountResult(List<Discount> discounts, int code) {
        this.discounts = discounts;
        if (Constants.RESPONSE_CODE_SUCCESSFUL == code) {
            discountAdapter = new DiscountAdapter(this);
            discountAdapter.setData(discounts);
            discountAdapter.setOnItemClickListener(discount -> {
                new AlertDialog.Builder(this).setMessage(getResources().getString(R.string.top_up_message))
                        .setPositiveButton(getResources().getString(R.string.sure), (dialogInterface, i) -> {
                            Intent intent = new Intent(DiscountActivity.this, PaymentActivity.class);
                            intent.putExtra(Constants.KEY_INTENT_TO_PAYMENT, Constants.INTENT_REQUEST_DICOUNT_TO_PAYMENT);
                            intent.putExtra(Constants.KEY_INTENT_DISCOUNT, discount);
                            startActivityForResult(intent, Constants.INTENT_REQUEST_DICOUNT_TO_PAYMENT);
                        }).
                        setNegativeButton(getResources().getString(R.string.cancel), (dialogInterface, i) -> {
                        }).show();
            });
            rv_discount.setLayoutManager(new LinearLayoutManager(this));
            rv_discount.setAdapter(discountAdapter);
            rv_discount.setVisibility(View.VISIBLE);
        } else {
            rv_discount.setVisibility(View.GONE);
        }
    }

    @Override
    public void getTopUpResult(UserInfo userInfo, int code) {
    }
}
