package hottopic.mit.co.nz.cleaningservice.entities.network;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceProduct;

public class OrderBooking {
    private Order order;
    private List<ServiceProduct> products;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<ServiceProduct> getProducts() {
        return products;
    }

    public void setProducts(List<ServiceProduct> products) {
        this.products = products;
    }
}
