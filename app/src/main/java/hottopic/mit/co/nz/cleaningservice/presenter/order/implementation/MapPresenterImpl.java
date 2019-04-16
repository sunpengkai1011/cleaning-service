package hottopic.mit.co.nz.cleaningservice.presenter.order.implementation;


import android.content.Context;

import hottopic.mit.co.nz.cleaningservice.entities.network.response.PlaceResponse;
import hottopic.mit.co.nz.cleaningservice.model.order.inter.IMap;
import hottopic.mit.co.nz.cleaningservice.model.order.implementation.MapModel;
import hottopic.mit.co.nz.cleaningservice.network.RequestCallBack;
import hottopic.mit.co.nz.cleaningservice.presenter.order.inter.IMapPresenter;
import hottopic.mit.co.nz.cleaningservice.view.order.inter.IMapView;

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
