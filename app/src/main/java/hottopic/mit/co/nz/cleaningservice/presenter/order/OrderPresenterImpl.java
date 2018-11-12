package hottopic.mit.co.nz.cleaningservice.presenter.order;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.network.response.BooleanResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.response.GetOrdersResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.params.OrderBookingParams;
import hottopic.mit.co.nz.cleaningservice.entities.network.response.OrderResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.response.ProductResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.response.ServiceTypesResponse;
import hottopic.mit.co.nz.cleaningservice.entities.orders.MainServiceType;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceProduct;
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceTypes;
import hottopic.mit.co.nz.cleaningservice.entities.orders.SubServiceType;
import hottopic.mit.co.nz.cleaningservice.model.order.IOrder;
import hottopic.mit.co.nz.cleaningservice.model.order.OrderModel;
import hottopic.mit.co.nz.cleaningservice.view.order.IOrderView;

public class OrderPresenterImpl implements IOrderPresenter{
    private IOrder iOrder;
    private IOrderView iOrderView;
    private Context context;

    public OrderPresenterImpl(Context context) {
        this.context = context;
        iOrder = new OrderModel(context, iOrderView);
    }

    public OrderPresenterImpl(Context context, IOrderView iOrderView) {
        this.context = context;
        this.iOrderView = iOrderView;
        iOrder = new OrderModel(context, iOrderView);
    }

    @Override
    public void getOrdersByCustomer(int userId) {
        iOrder.getOrdersByCustomer(userId);
    }

    @Override
    public void getOrdersByStaff() {
        iOrder.getOrdersByStaff();
    }

    @Override
    public void startedOrder(Order order) {
        iOrder.startedOrder(order);
    }

    @Override
    public void finishedOrder(Order order) {
        iOrder.finishedOrder(order);
    }

    @Override
    public void orderBooking(OrderBookingParams orderBookingParams) {
        iOrder.orderBooking(orderBookingParams);
    }

    @Override
    public void getOrdersResult(GetOrdersResponse response) {
        if (response == null){
            iOrderView.getOrdersResult(null, "Get orders failed");
        }else{
            if (Constants.RESPONSE_CODE_SUCCESSFUL == response.getCode()) {
                iOrderView.getOrdersResult(sortingOrders(response.getOrderResponses()), response.getMessage());
            }else{
                iOrderView.getOrdersResult(null, response.getMessage());
            }
        }
    }

    @Override
    public void orderStatusChangeResult(BooleanResponse response) {
        if (response == null){
            iOrderView.orderStatusChangeResult(false, "Get orders failed");
        }else{
            iOrderView.orderStatusChangeResult(response.getResult(), response.getMessage());
        }
    }

    @Override
    public void orderBookingResult(BooleanResponse response) {
        if (response == null){
            iOrderView.bookingResult(false, "Service booking failed");
        }else{
            iOrderView.bookingResult(response.getResult(), response.getMessage());
        }
    }

    @Override
    public void getServiceTypes() {
        iOrder = new OrderModel(context, iOrderView);
        iOrder.getServiceTypes();
    }

    @Override
    public void serviceTypesResult(ServiceTypesResponse response) {
        if (response == null){
            iOrderView.getServiceError("Get service type failed");
        }else{
            if (Constants.RESPONSE_CODE_SUCCESSFUL == response.getCode()){
                iOrderView.getServiceTypes(sortingTypeData(response.getServiceTypes()));
            }else{
                iOrderView.getServiceError(response.getMessage());
            }
        }
    }

    public ServiceTypes sortingTypeData(List<ProductResponse> products){
        ServiceTypes serviceTypes = new ServiceTypes();
        List<MainServiceType> mainServiceTypes = new ArrayList<>();
        int sub_position = 0;
        int main_position = 0;
        int main_id = products.get(0).getMain_id();
        int sub_id = products.get(0).getSub_id();
        MainServiceType initMain = new MainServiceType(products.get(0).getMain_id(), products.get(0).getMain_type_name(), new ArrayList<>());
        SubServiceType initSub = new SubServiceType(products.get(0).getSub_id(), products.get(0).getMain_id(),
                products.get(0).getSub_type_name(), products.get(0).getBulk_discount(), products.get(0).getIcon(), new ArrayList<>());
        mainServiceTypes.add(initMain);
        mainServiceTypes.get(main_position).getSubServiceTypes().add(initSub);
        for(int i = 0; i < products.size(); i++){
            ServiceProduct serviceProduct = new ServiceProduct(products.get(i).getId(),
                    products.get(i).getMain_id(), products.get(i).getSub_id(), products.get(i).getProduct_name(),
                    products.get(i).getUnit_price(), products.get(i).getUnit(), products.get(i).getProduct_icon());
            if (sub_id != products.get(i).getSub_id()){
                SubServiceType subServiceType;
                if (main_id != products.get(i).getMain_id()){
                    main_id = products.get(i).getMain_id();
                    main_position ++;
                    MainServiceType mainServiceType = new MainServiceType(products.get(i).getMain_id(), products.get(i).getMain_type_name(), new ArrayList<>());
                    mainServiceTypes.add(mainServiceType);
                    sub_position = 0;
                }else {
                    sub_position++;
                }
                sub_id = products.get(i).getSub_id();
                subServiceType = new SubServiceType(products.get(i).getSub_id(), products.get(i).getMain_id(),
                        products.get(i).getSub_type_name(), products.get(i).getBulk_discount(), products.get(i).getIcon(),new ArrayList<>());
                mainServiceTypes.get(main_position).getSubServiceTypes().add(subServiceType);

            }
            mainServiceTypes.get(main_position).getSubServiceTypes().get(sub_position).getProducts().add(serviceProduct);
        }
        serviceTypes.setMainServiceTypes(mainServiceTypes);
        return serviceTypes;
    }

    private List<Order> sortingOrders(List<OrderResponse> orderResponses){
        List<Order> orders = new ArrayList<>();
        int id = orderResponses.get(0).getId();
        int order_position = 0;
        SubServiceType initSubType = new SubServiceType(orderResponses.get(0).getSub_id(),
                                                        orderResponses.get(0).getMain_id(),
                                                        orderResponses.get(0).getSub_type_name(),
                                                        orderResponses.get(0).getBulk_discount(),
                                                        orderResponses.get(0).getSub_icon(),
                                                        new ArrayList<>());
        Order initOrder = new Order(id,
                                    orderResponses.get(0).getUser_id(),
                                    orderResponses.get(0).getDate(),
                                    initSubType,
                                    orderResponses.get(0).getPhone(),
                                    orderResponses.get(0).getCity(),
                                    orderResponses.get(0).getSuburb(),
                                    orderResponses.get(0).getStreet(),
                                    orderResponses.get(0).getStatus(),
                                    orderResponses.get(0).getAmount(),
                                    orderResponses.get(0).getDuration(),
                                    orderResponses.get(0).getStart_time(),
                                    orderResponses.get(0).getEnd_time(),
                                    orderResponses.get(0).getQuantity(),
                                    orderResponses.get(0).getFeedback(),
                                    orderResponses.get(0).getRating());
        orders.add(initOrder);
        for(int i = 0; i < orderResponses.size(); i++){
            ServiceProduct product = new ServiceProduct(orderResponses.get(i).getProduct_id(),
                                                        orderResponses.get(i).getMain_id(),
                                                        orderResponses.get(i).getSub_id(),
                                                        orderResponses.get(i).getProduct_name(),
                                                        orderResponses.get(i).getProduct_icon(),
                                                        orderResponses.get(i).getUnit_price(),
                                                        orderResponses.get(i).getUnit(),
                                                        orderResponses.get(i).getProduct_quantity());

            if (id != orderResponses.get(i).getId()){
                id = orderResponses.get(i).getId();
                SubServiceType subServiceType = new SubServiceType(orderResponses.get(i).getSub_id(),
                        orderResponses.get(i).getMain_id(),
                        orderResponses.get(i).getSub_type_name(),
                        orderResponses.get(i).getBulk_discount(),
                        orderResponses.get(i).getSub_icon(),
                        new ArrayList<>());
                Order order = new Order(id,
                        orderResponses.get(i).getUser_id(),
                        orderResponses.get(i).getDate(),
                        subServiceType,
                        orderResponses.get(i).getPhone(),
                        orderResponses.get(i).getCity(),
                        orderResponses.get(i).getSuburb(),
                        orderResponses.get(i).getStreet(),
                        orderResponses.get(i).getStatus(),
                        orderResponses.get(i).getAmount(),
                        orderResponses.get(i).getDuration(),
                        orderResponses.get(i).getStart_time(),
                        orderResponses.get(i).getEnd_time(),
                        orderResponses.get(i).getQuantity(),
                        orderResponses.get(i).getFeedback(),
                        orderResponses.get(i).getRating());
                orders.add(order);
                order_position++;
            }
            orders.get(order_position).getSubServiceType().getProducts().add(product);
        }
        return orders;
    }
}
