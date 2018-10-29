package hottopic.mit.co.nz.cleaningservice.network;

import hottopic.mit.co.nz.cleaningservice.entities.request.PlaceResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlaceService {

    @GET("maps/api/place/findplacefromtext/json")
    Call<PlaceResponse> getPlaceInfo(@Query("input") String input,
                                     @Query("inputtype") String inputtype,
                                     @Query("fields") String fields,
                                     @Query("key") String key);
}
