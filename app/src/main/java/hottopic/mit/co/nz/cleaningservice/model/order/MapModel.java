package hottopic.mit.co.nz.cleaningservice.model.order;

import android.content.Context;

import hottopic.mit.co.nz.cleaningservice.network.PlaceRequest;
import hottopic.mit.co.nz.cleaningservice.network.RequestCallBack;

public class MapModel implements IMap {
    private Context context;
    private RequestCallBack requestCallBack;

    public MapModel(Context context, RequestCallBack requestCallBack) {
        this.context = context;
        this.requestCallBack = requestCallBack;
    }

    @Override
    public void requestOptimiseRoutes(String location) {
        PlaceRequest placeRequest = new PlaceRequest(context, requestCallBack, location);
        placeRequest.getRequest();
    }
}
