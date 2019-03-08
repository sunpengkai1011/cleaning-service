package hottopic.mit.co.nz.cleaningservice.presenter.order;


import android.content.Context;

import hottopic.mit.co.nz.cleaningservice.entities.network.response.PlaceResponse;
import hottopic.mit.co.nz.cleaningservice.model.order.IMap;
import hottopic.mit.co.nz.cleaningservice.model.order.MapModel;
import hottopic.mit.co.nz.cleaningservice.network.RequestCallBack;
import hottopic.mit.co.nz.cleaningservice.view.order.IMapView;

public class MapPresenterImpl implements IMapPresenter, RequestCallBack{
    private Context context;
    private IMapView iMapView;
    private IMap iMap;

    public MapPresenterImpl(Context context, IMapView iMapView) {
        this.context = context;
        this.iMapView = iMapView;
        this.iMap = new MapModel(context, this);
    }

    @Override
    public void placeRequest(String location) {
        iMap.requestOptimiseRoutes(location);
    }

    @Override
    public void requestCallBack(Object o) {
        iMapView.placeResponse((PlaceResponse) o);
    }

    @Override
    public void requestFailure(Object o) {
        iMapView.placeResponseError();
    }
}
