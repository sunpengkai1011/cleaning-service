package hottopic.mit.co.nz.cleaningservice.view.map;


import hottopic.mit.co.nz.cleaningservice.entities.request.PlaceResponse;

public interface IMapView {
    void placeResponse(PlaceResponse response);
    void placeResponseError();
}
