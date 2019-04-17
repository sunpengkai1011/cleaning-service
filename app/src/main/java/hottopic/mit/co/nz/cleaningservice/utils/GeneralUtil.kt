package hottopic.mit.co.nz.cleaningservice.utils

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import com.android.tu.loadingdialog.LoadingDialog
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import hottopic.mit.co.nz.cleaningservice.Constants
import hottopic.mit.co.nz.cleaningservice.entities.network.response.OrderResponse
import hottopic.mit.co.nz.cleaningservice.entities.network.response.ProductResponse
import hottopic.mit.co.nz.cleaningservice.entities.orders.*
import java.lang.reflect.Type
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object GeneralUtil {

    val currentTime: String
        get() {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH)
            val now = Date()
            return sdf.format(now)
        }

    /**
     * Get the Gson entity.
     * @return gson entity
     */
    val gson: Gson
        get() = GsonBuilder().serializeNulls().create()

    fun dip2px(context: Context, dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density + 0.5).toInt()
    }


    fun px2sp(context: Context, pxValue: Int): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    fun getWaitDialog(context: Context, title: String): LoadingDialog {
        val loadBuilder = LoadingDialog.Builder(context)
                .setMessage(title)
                .setCancelable(true)
                .setCancelOutside(true)
        return loadBuilder.create()
    }

    fun priceFormat(price: Float): String {
        val decimalFormat: DecimalFormat = if (price < 1) {
            DecimalFormat("0.0")
        } else {
            DecimalFormat(".0")
        }
        return decimalFormat.format(price.toDouble())
    }

    /**
     * Get the width of the device screen
     * @param activity activity entity
     * @return width of device screen
     */
    fun getScreenWidth(activity: Activity): Int {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    fun toJson(src: Any, typeOfSrc: Type): String {
        val gson = Gson()
        return gson.toJson(src, typeOfSrc)
    }

    fun <T> fromJson(json: String, type: Class<T>): T {
        val gson = Gson()
        return gson.fromJson(json, type)
    }
//
//    fun <T> fromJson(json: String, type: Type): T? {
//        val gson = Gson()
//        return gson.fromJson<T>(json, type)
//    }

    fun storDataBySP(context: Context, key: String, data: String) {
        val preferences = context.getSharedPreferences(Constants.SP_KEY, Context.MODE_PRIVATE)
        preferences.edit().putString(key, data).apply()
    }

    /**
     * Get the data from local by SharedPreferences.
     * @param context
     * @param key store key
     * @return data
     */
    fun getIntFromSP(context: Context, key: String): Int {
        val preferences = context.getSharedPreferences(Constants.SP_KEY, Context.MODE_PRIVATE)
        return preferences.getInt(key, 0)
    }


    /**
     * Storing the data in local by SharedPreferences.
     *
     * @param context
     * @param key     store key
     * @param data
     */
    fun storeIntIntoSP(context: Context, key: String, data: Int) {
        val preferences = context.getSharedPreferences(Constants.SP_KEY, Context.MODE_PRIVATE)
        preferences.edit().putInt(key, data).apply()
    }

    fun getDataFromSP(context: Context, key: String): String {
        val preferences = context.getSharedPreferences(Constants.SP_KEY, Context.MODE_PRIVATE)
        return preferences.getString(key, "")
    }

    fun calculateDuration(startTime: String?, endTime: String?): Int {
        val startStr = startTime?.split(" ".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
        val startHourStr = startStr?.get(1)?.split(":".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
        val endStr = endTime?.split(" ".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
        val endHourStr = endStr?.get(1)?.split(":".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
        val endMin = Integer.valueOf(endHourStr?.get(1))
        val startMin = Integer.valueOf(startHourStr?.get(1))
        val endHour = Integer.valueOf(endHourStr?.get(0))
        val startHour = Integer.valueOf(startHourStr?.get(0))
        return if (endMin > startMin && endMin - startMin > 30) {
            endHour - startHour + 1
        } else {
            endHour - startHour
        }
    }

    fun formatDiscount(discount: Float): String {
        val dis = (100 - discount * 100).toInt()
        return "More than 20 pieces will enjoy the $dis% discount."
    }

    fun sortingTypeData(products: List<ProductResponse>?) : ServiceTypes{
        val mainServiceTypes = mutableListOf<MainServiceType>()
        products?.groupBy { it.main_id }
                ?.flatMap {
                    val mainProductResponse = it.value[0]
                    val subServiceTypes = mutableListOf<SubServiceType>()
                    val mainServiceType = MainServiceType(mainProductResponse.main_id, mainProductResponse.main_type_name, subServiceTypes)
                    mainServiceTypes.add(mainServiceType)
                    it.value.groupBy { it1 -> it1.sub_id }
                            .flatMap { it1 ->
                                val subProductResponse = it1.value[0]
                                val serviceProducts = mutableListOf<ServiceProduct>()
                                val subServiceType = SubServiceType(subProductResponse.sub_id, subProductResponse.main_id, subProductResponse.sub_type_name
                                , subProductResponse.bulk_discount, subProductResponse.icon, serviceProducts)
                                mainServiceType.subServiceTypes?.add(subServiceType)
                                it1.value.flatMap { it2 ->
                                    val serviceProduct = ServiceProduct(it2.id, it2.main_id, it2.sub_id, it2.product_name, it2.product_icon, it2.unit_price, it2.unit, 0)
                                    subServiceType.products?.add(serviceProduct)
                                    mainServiceTypes
                                }
                            }
                }
        return ServiceTypes(mainServiceTypes)
    }

    fun sortingOrders(orderResponses: List<OrderResponse>?): List<Order> {
        val orders = mutableListOf<Order>()
        orderResponses?.groupBy { it.id }
                ?.flatMap {
                    val response = it.value[0]
                    val products = mutableListOf<ServiceProduct>()
                    val subServiceType = SubServiceType(response.sub_id, response.main_id, response.sub_type_name, response.bulk_discount
                    , response.sub_icon, products)
                    val order = Order(response.id, response.user_id, response.date, subServiceType, response.phone, response.city, response.suburb
                    , response.street, response.status, response.amount, response.duration, response.start_time, response.end_time, response.quantity
                    , response.feedback, response.rating)
                    orders.add(order)
                    it.value.flatMap {it1 ->
                        val product = ServiceProduct(it1.product_id, it1.main_id, it1.sub_id, it1.product_name, it1.product_icon
                        , it1.unit_price, it1.unit, it1.product_quantity)
                        subServiceType.products?.add(product)
                        orders
                    }
                }
        return orders
    }
}
