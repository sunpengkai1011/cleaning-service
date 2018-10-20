package hottopic.mit.co.nz.cleaningservice.utils;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.tu.loadingdialog.LoadingDialog;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.text.DecimalFormat;

import hottopic.mit.co.nz.cleaningservice.Constants;

public class GeneralUtil {

    public static int dip2px(Context context, int dp)
    {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp*density+0.5);
    }

    public static int px2dip(Context context, int px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static int px2sp(Context context, int pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(Context context, int spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static LoadingDialog getWaitDialog(Context context, String title){
        LoadingDialog.Builder loadBuilder =new LoadingDialog.Builder(context)
                .setMessage(title)
                .setCancelable(true)
                .setCancelOutside(true);
        return loadBuilder.create();
    }

    public static String secondToMinutes(int second){
        return second / 60 + " Mins";
    }

    public static int msToMinutes(int ms){
        return ms / 6000;
    }

    public static String mToKm(int meter){
        float distance = (float) meter / 1000;
        DecimalFormat decimalFormat = new DecimalFormat(".0");
        return decimalFormat.format(distance) + " Km";
    }

    public static AlertDialog mapMarkerDialog(Context context, String message,
                                              String posBtnMessage, Dialog.OnClickListener posListener,
                                              String neuBtnMessage, Dialog.OnClickListener neuListener,
                                              String negBtnMessage, Dialog.OnClickListener negListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(posBtnMessage, posListener)
                .setNeutralButton(neuBtnMessage, neuListener)
                .setNegativeButton(negBtnMessage, negListener);
        return builder.create();
    }

    public static AlertDialog twoBtnDialog(Context context, String message,
                                              String posBtnMessage, Dialog.OnClickListener posListener,
                                              String negBtnMessage, Dialog.OnClickListener negListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(posBtnMessage, posListener)
                .setNegativeButton(negBtnMessage, negListener);
        return builder.create();
    }

    public static String toJson(Object src, Type typeOfSrc){
        Gson gson = new Gson();
        return gson.toJson(src, typeOfSrc);
    }

    public static<T> T fromJson(String json, Class<T> type){
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
}
