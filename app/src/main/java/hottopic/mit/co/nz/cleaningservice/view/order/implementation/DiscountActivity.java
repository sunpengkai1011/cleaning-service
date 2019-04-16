package hottopic.mit.co.nz.cleaningservice.view.order.implementation;

import android.app.AlertDialog;
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
import hottopic.mit.co.nz.cleaningservice.entities.orders.Discount;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.presenter.order.implementation.PaymentPresenterImpl;
import hottopic.mit.co.nz.cleaningservice.view.order.inter.IPaymentView;

public class DiscountActivity extends BaseActivity implements IPaymentView {
    private TextView tv_title;
    private RelativeLayout lyt_back;
    private RecyclerView rv_discount;
    private DiscountAdapter discountAdapter;

    private PaymentPresenterImpl discountPresenter;

    @Override
    public void initView() {
        setContentView(R.layout.activity_discount);
        tv_title = findViewById(R.id.tv_title);
        lyt_back = findViewById(R.id.lytBack);
        rv_discount = findViewById(R.id.rv_discount);

    }

    @Override
    public void initData() {
        tv_title.setText(getResources().getString(R.string.title_discount));
        lyt_back.setVisibility(View.VISIBLE);

        discountPresenter = new PaymentPresenterImpl(this, this);
        discountPresenter.getDiscounts();
    }

    @Override
    public void initListener() {
        lyt_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lytBack:
                this.finish();
                break;
        }
    }

    @Override
    public void getDiscountsResult(List<Discount> discounts, String message) {
        if (discounts != null) {
            discountAdapter = new DiscountAdapter(this);
            discountAdapter.setData(discounts);
            discountAdapter.setOnItemClickListener(discount -> {
                new AlertDialog.Builder(this).setMessage(getResources().getString(R.string.top_up_message))
                        .setPositiveButton(getResources().getString(R.string.sure), (dialogInterface, i) -> {
                            Intent intent = new Intent(DiscountActivity.this, PaymentActivity.class);
                            intent.putExtra(Constants.KEY_INTENT_DISCOUNT, discount);
                            startActivity(intent);
                        }).
                        setNegativeButton(getResources().getString(R.string.cancel), (dialogInterface, i) -> {
                        }).show();
            });
            rv_discount.setLayoutManager(new LinearLayoutManager(this));
            rv_discount.setAdapter(discountAdapter);
            rv_discount.setVisibility(View.VISIBLE);
        }else{
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getPaymentBalanceResult(UserInfo userInfo, String message, int type) {

    }

    @Override
    public void getPaymentCardResult(boolean result, String message) {

    }
}
