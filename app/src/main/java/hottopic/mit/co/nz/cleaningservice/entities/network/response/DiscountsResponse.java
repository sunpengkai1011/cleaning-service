package hottopic.mit.co.nz.cleaningservice.entities.network.response;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.orders.Discount;

public class DiscountsResponse extends BaseResponse {
    private List<Discount> discounts;

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }
}
