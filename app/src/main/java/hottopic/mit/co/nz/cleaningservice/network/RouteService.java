package hottopic.mit.co.nz.cleaningservice.network;

import hottopic.mit.co.nz.cleaningservice.entities.RouteResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RouteService {

    @GET("maps/api/directions/json")
    Call<RouteResponse> getRouteInfo(@Query("origin") String origin,
                                     @Query("destination") String destination,
                                     @Query("waypoints") String waypoints,
                                     @Query("departure_time") String departure_time,
                                     @Query("key") String key,
                                     @Query("optimizeWaypoints") boolean optimizeWaypoints);
}
