package hottopic.mit.co.nz.cleaningservice.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;

import com.android.tu.loadingdialog.LoadingDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.entities.network.ProductResponse;
import hottopic.mit.co.nz.cleaningservice.entities.orders.MainServiceType;
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceProduct;
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceTypes;
import hottopic.mit.co.nz.cleaningservice.entities.orders.SubServiceType;
import io.reactivex.Observable;
import io.reactivex.Single;

public class GeneralUtil {

    public static int dip2px(Context context, int dp)
    {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp*density+0.5);
    }


    public static int px2sp(Context context, int pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static LoadingDialog getWaitDialog(Context context, String title){
        LoadingDialog.Builder loadBuilder =new LoadingDialog.Builder(context)
                .setMessage(title)
                .setCancelable(true)
                .setCancelOutside(true);
        return loadBuilder.create();
    }

    public static String priceFormat(float price){
        DecimalFormat decimalFormat;
        if (price < 1){
            decimalFormat = new DecimalFormat("0.0");
        }else {
            decimalFormat = new DecimalFormat(".0");
        }
        return decimalFormat.format(price);
    }

    /**
     * Get the width of the device screen
     * @param activity activity entity
     * @return width of device screen
     */
    public static int getScreenWidth(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static String toJson(Object src, Type typeOfSrc){
        Gson gson = new Gson();
        return gson.toJson(src, typeOfSrc);
    }

    public static<T> T fromJson(String json, Class<T> type){
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    public static<T> T fromJson(String json, Type type){
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    public static void storDataBySP(Context context, String key, String data){
        SharedPreferences preferences = context.getSharedPreferences(Constants.SP_KEY, Context.MODE_PRIVATE);
        preferences.edit().putString(key, data).commit();
    }

    public static String getDataFromSP(Context context, String key){
        SharedPreferences preferences = context.getSharedPreferences(Constants.SP_KEY, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public static String getCurrentTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
        Date now = new Date();
        return sdf.format(now);
    }

    public static int calculateDuration(String startTime, String endTime){
        String[] startStr = startTime.split(" ");
        String[] startHourStr = startStr[1].split(":");
        String[] endStr = endTime.split(" ");
        String[] endHourStr = endStr[1].split(":");
        int endMin = Integer.valueOf(endHourStr[1]);
        int startMin = Integer.valueOf(startHourStr[1]);
        int endHour = Integer.valueOf(endHourStr[0]);
        int startHour = Integer.valueOf(startHourStr[0]);
        int duration;
        if (endMin > startMin && endMin - startMin > 30){
            duration = endHour - startHour + 1;
        }else{
            duration = endHour - startHour;
        }
        return duration;
    }

    public static String formatDiscount(float discount){
        int dis = (int)((1 - discount) * 100);
        return "More than 20 pieces will enjoy the " + dis + "% discount.";
    }

    /**
     * Get the Gson entity.
     * @return gson entity
     */
    public static Gson getGson() {
        return new GsonBuilder().serializeNulls().create();
    }

    public static ServiceTypes sortingTypeData(List<ProductResponse> products){
        ServiceTypes serviceTypes = new ServiceTypes();
        List<MainServiceType> mainServiceTypes = new ArrayList<>();
        int main_id = 0;
        int sub_id = 0;
        int sub_position = 0;
        int main_position = 0;
        for(int i = 0; i < products.size(); i++){
            ServiceProduct serviceProduct = new ServiceProduct(products.get(i).getId(),
                    products.get(i).getMain_id(), products.get(i).getSub_id(), products.get(i).getProduct_name(),
                    products.get(i).getUnit_price(), products.get(i).getUnit());
            if (Constants.TYPE_G_T_SHIRT == products.get(i).getId() || Constants.TYPE_S_T_SHIRT == products.get(i).getId()){
                serviceProduct.setIcon(R.drawable.icon_shirt);
            }
            if (Constants.TYPE_G_LONG_DRESS == products.get(i).getId() || Constants.TYPE_S_LONG_DRESS == products.get(i).getId()){
                serviceProduct.setIcon(R.drawable.icon_long_dress);
            }
            if (Constants.TYPE_G_JACKET == products.get(i).getId() || Constants.TYPE_S_JACKET == products.get(i).getId()){
                serviceProduct.setIcon(R.drawable.icon_jacket);
            }
            if (Constants.TYPE_G_SCHOOL_UNIFORM == products.get(i).getId() || Constants.TYPE_S_SCHOOL_UNIFORM == products.get(i).getId()){
                serviceProduct.setIcon(R.drawable.icon_uniform);
            }
            if (i == 0){
                main_id = products.get(i).getMain_id();
                sub_id = products.get(i).getSub_id();
                sub_position = i;
                main_position = i;
                MainServiceType mainServiceType = new MainServiceType(products.get(i).getMain_id(), products.get(i).getMain_type_name(), new ArrayList<>());
                SubServiceType subServiceType = new SubServiceType(products.get(i).getSub_id(), products.get(i).getMain_id(),
                        products.get(i).getSub_type_name(), products.get(i).getBulk_discount(), new ArrayList<>());
                setIcon(sub_id, subServiceType);
                mainServiceTypes.add(mainServiceType);
                mainServiceTypes.get(main_position).getSubServiceTypes().add(subServiceType);
            }else{
                if (sub_id != products.get(i).getSub_id()){
                    SubServiceType subServiceType;
                    if (main_id != products.get(i).getMain_id()){
                        main_id = products.get(i).getMain_id();
                        main_position ++;
                        MainServiceType mainServiceType = new MainServiceType(products.get(i).getMain_id(), products.get(i).getMain_type_name(), new ArrayList<>());
                        mainServiceTypes.add(mainServiceType);
                        sub_position = 0;
                        sub_id = products.get(i).getSub_id();
                        subServiceType = new SubServiceType(products.get(i).getSub_id(), products.get(i).getMain_id(),
                                products.get(i).getSub_type_name(), products.get(i).getBulk_discount(), new ArrayList<>());
                        mainServiceTypes.get(main_position).getSubServiceTypes().add(subServiceType);
                    }else {
                        sub_id = products.get(i).getSub_id();
                        subServiceType = new SubServiceType(products.get(i).getSub_id(), products.get(i).getMain_id(),
                                products.get(i).getSub_type_name(), products.get(i).getBulk_discount(), new ArrayList<>());
                        mainServiceTypes.get(main_position).getSubServiceTypes().add(subServiceType);
                        sub_position++;
                    }
                    setIcon(sub_id, subServiceType);
                }
            }
            mainServiceTypes.get(main_position).getSubServiceTypes().get(sub_position).getProducts().add(serviceProduct);
        }
        serviceTypes.setMainServiceTypes(mainServiceTypes);
        return serviceTypes;
    }

    private static SubServiceType setIcon(int sub_id, SubServiceType subServiceType){
        if (Constants.TYPE_D_CLEANING == sub_id){
            subServiceType.setIcon(R.drawable.icon_d_cleaning);
        }
        if (Constants.TYPE_G_CLEANING == sub_id){
            subServiceType.setIcon(R.drawable.icon_g_cleaning);
        }
        if (Constants.TYPE_BUFFERING == sub_id){
            subServiceType.setIcon(R.drawable.icon_buffering);
        }
        if (Constants.TYPE_CARPET_WASHING == sub_id){
            subServiceType.setIcon(R.drawable.icon_carpet_wash);
        }
        if (Constants.TYPE_WATER_BLASTING == sub_id){
            subServiceType.setIcon(R.drawable.icon_water_blast);
        }
        if (Constants.TYPE_G_IRONING == sub_id){
            subServiceType.setIcon(R.drawable.icon_iron);
        }
        if (Constants.TYPE_S_IRONING == sub_id){
            subServiceType.setIcon(R.drawable.icon_steam);
        }
        return subServiceType;
    }
}
