package hottopic.mit.co.nz.cleaningservice.network;

import android.content.Context;

import com.android.tu.loadingdialog.LoadingDialog;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.network.response.PlaceResponse;
import hottopic.mit.co.nz.cleaningservice.network.service.PlaceService;
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaceRequest {

    private RequestCallBack mCallBack;
    private LoadingDialog dialog;
    private String intput;
    private String inputtype;
    private String fields;

    public PlaceRequest(Context context, RequestCallBack callBack, String input){
        mCallBack = callBack;
        this.intput = input;
        this.inputtype = "textquery";
        this.fields = "formatted_address,name,geometry";
        this.dialog = GeneralUtil.getWaitDialog(context, "Waiting...");
    }

    public void getRequest(){
        dialog.show();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.GoogleMap_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        PlaceService request = retrofit.create(PlaceService.class);
        Call<PlaceResponse> call = request.getPlaceInfo(intput, inputtype, fields, Constants.APIKEY);
        call.enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                dialog.dismiss();
                mCallBack.requestCallBack(response.body());
            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {
                dialog.dismiss();
                mCallBack.requestFailure(call);
            }
        });
    }
}
