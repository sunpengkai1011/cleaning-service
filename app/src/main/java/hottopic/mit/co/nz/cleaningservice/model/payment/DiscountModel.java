package hottopic.mit.co.nz.cleaningservice.model.payment;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.top_up.Discount;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil;

public class DiscountModel implements IDiscount {
    private Context context;
    private UserInfo userInfo;

    public DiscountModel(Context context) {
        this.context = context;
    }

    public UserInfo getUserInfo(String username) {
        userInfo = GeneralUtil.fromJson(GeneralUtil.getDataFromSP(context, username), UserInfo.class);
        return userInfo;
    }

    @Override
    public List<Discount> requestDiscountData() {
        List<Discount> discounts = new ArrayList<>();
        Discount discount1 = new Discount(1, 1000, 800);
        discounts.add(discount1);
        Discount discount2 = new Discount(2, 900, 750);
        discounts.add(discount2);
        Discount discount3 = new Discount(3, 800, 700);
        discounts.add(discount3);
        Discount discount4 = new Discount(4, 600, 550);
        discounts.add(discount4);
        Discount discount5 = new Discount(5, 500, 480);
        discounts.add(discount5);
        Discount discount6 = new Discount(6, 400, 390);
        discounts.add(discount6);
        return discounts;
    }

    @Override
    public boolean topUp(UserInfo userInfo, Discount discount) {
        if (userInfo != null && discount != null){
            if (discount.getBalance() != 0){
                float balance = discount.getBalance() + userInfo.getBalance();
                userInfo.setBalance(balance);
                GeneralUtil.storDataBySP(context, userInfo.getUserName(), GeneralUtil.toJson(userInfo, UserInfo.class));
                return true;
            }else {
                return false;
            }
        }
        return false;
    }


}
