package hottopic.mit.co.nz.cleaningservice.view.order;


import hottopic.mit.co.nz.cleaningservice.entities.network.PlaceResponse;

public interface IMapView {
    void placeResponse(PlaceResponse response);
    void placeResponseError();
}
