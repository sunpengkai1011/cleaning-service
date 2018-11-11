package hottopic.mit.co.nz.cleaningservice.network.service;

import hottopic.mit.co.nz.cleaningservice.entities.network.BooleanResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.DiscountsResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.PaymentParams;
import hottopic.mit.co.nz.cleaningservice.entities.network.UserInfoResponse;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;

public interface PaymentService {

    @GET("cleaning/payment/discount")
    Single<DiscountsResponse> getDiscouts();

    @PUT("cleaning/payment/balance")
    Single<UserInfoResponse> paymentByBalance(@Body PaymentParams paymentBalance);

    @PUT("cleaning/payment/card")
    Single<BooleanResponse> paymentByCard(@Body PaymentParams paymentBalance);
}
