package hottopic.mit.co.nz.cleaningservice.entities.network.response;

import java.util.List;

public class GetOrdersResponse extends BaseResponse {
    private List<OrderResponse> orderResponses;

    public List<OrderResponse> getOrderResponses() {
        return orderResponses;
    }

    public void setOrderResponses(List<OrderResponse> orderResponses) {
        this.orderResponses = orderResponses;
    }
}
