package hottopic.mit.co.nz.cleaningservice.presenter.home;

import android.content.Context;

import java.util.IdentityHashMap;
import java.util.List;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.discounts.Discount;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.model.home.DiscountModel;
import hottopic.mit.co.nz.cleaningservice.model.home.IDiscount;
import hottopic.mit.co.nz.cleaningservice.view.home.me.IDiscountView;

public class DiscountPresenterImpl implements IDiscountPresenter{
    private IDiscountView iDiscountView;
    private IDiscount iDiscount;
    private Context context;

    public DiscountPresenterImpl(Context context, IDiscountView iDiscountView) {
        this.iDiscountView = iDiscountView;
        this.context = context;
    }

    @Override
    public void requestDiscouts() {
        iDiscount = new DiscountModel(context);
        List<Discount> discounts = iDiscount.requestDiscountData();
        if (discounts != null && discounts.size() > 0){
            iDiscountView.getDiscountResult(discounts, Constants.RESPONSE_CODE_SUCCESSFUL);
        }else {
            iDiscountView.getDiscountResult(discounts, Constants.RESPONSE_CODE_FAIL);
        }
    }

    @Override
    public void topUp(UserInfo userInfo, Discount discount) {
        iDiscount = new DiscountModel(context);
        if (iDiscount.topUp(userInfo, discount)){
            iDiscountView.getTopUpResult(iDiscount.getUserInfo(), Constants.RESPONSE_CODE_SUCCESSFUL);
        }else{
            iDiscountView.getTopUpResult(new UserInfo(), Constants.RESPONSE_CODE_FAIL);
        }
    }
}
