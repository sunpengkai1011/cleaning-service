package hottopic.mit.co.nz.cleaningservice.view.home.order;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceType;

public interface IOrderBookingView {
    void bookingResult(int code);
    void getServiceTypeResult(List<ServiceType> types, int code);
}
