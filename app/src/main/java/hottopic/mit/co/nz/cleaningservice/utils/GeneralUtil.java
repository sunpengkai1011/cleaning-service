package hottopic.mit.co.nz.cleaningservice.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;

import com.android.tu.loadingdialog.LoadingDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import hottopic.mit.co.nz.cleaningservice.Constants;

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
        int dis = (int) (100 - discount * 100);
        return "More than 20 pieces will enjoy the " + dis + "% discount.";
    }

    /**
     * Get the Gson entity.
     * @return gson entity
     */
    public static Gson getGson() {
        return new GsonBuilder().serializeNulls().create();
    }
}
