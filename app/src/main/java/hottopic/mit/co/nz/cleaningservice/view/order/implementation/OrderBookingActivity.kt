package hottopic.mit.co.nz.cleaningservice.view.order.implementation

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import hottopic.mit.co.nz.cleaningservice.BaseActivity
import hottopic.mit.co.nz.cleaningservice.Constants
import hottopic.mit.co.nz.cleaningservice.R
import hottopic.mit.co.nz.cleaningservice.adapter.ClothesAdapter
import hottopic.mit.co.nz.cleaningservice.entities.network.params.OrderBookingParams
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceProduct
import hottopic.mit.co.nz.cleaningservice.entities.orders.SubServiceType
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo
import hottopic.mit.co.nz.cleaningservice.presenter.order.implementation.OrderBookingPresenterImpl
import hottopic.mit.co.nz.cleaningservice.presenter.order.inter.IOrderBookingPresenter
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil
import hottopic.mit.co.nz.cleaningservice.utils.mdatepicker.CustomDatePicker
import hottopic.mit.co.nz.cleaningservice.view.order.inter.IOrderBookingView
import kotlinx.android.synthetic.main.activity_booking.*
import kotlinx.android.synthetic.main.view_title_bar.*
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class OrderBookingActivity : BaseActivity(), IOrderBookingView {
    private lateinit var userInfo: UserInfo

    private lateinit var serviceType: SubServiceType
    private lateinit var clothesAdapter: ClothesAdapter

    private lateinit var subOptions: MutableList<String>
    private var subOptionPosition = 0

    private lateinit var datePicker: CustomDatePicker

    private lateinit var orderBookingPresenter: IOrderBookingPresenter

    override fun initView() {
        setContentView(R.layout.activity_booking)
    }

    override fun initData() {
        tvTitle.text = resources.getString(R.string.title_booking)
        lytBack.visibility = View.VISIBLE

        orderBookingPresenter = OrderBookingPresenterImpl(this, this)

        userInfo = Constants.userInfo
        serviceType = intent.getSerializableExtra(Constants.KEY_INTENT_SERVICETYPE) as SubServiceType

        if (!TextUtils.isEmpty(userInfo.username)) {
            tv_username.text = userInfo.username
        }
        tv_service_type.text = serviceType.type_name
        when (serviceType.id) {
            Constants.ID_SERVICE_G_CLEANING, Constants.ID_SERVICE_D_CLEANING -> initSpinnerData()
            Constants.ID_SERVICE_BUFFERING, Constants.ID_SERVICE_WATERBLASTING, Constants.ID_SERVICE_CARPETWASH -> tv_unit_price.text = serviceType.products?.get(0)?.formatPrice()
            Constants.ID_SERVICE_G_INRONING, Constants.ID_SERVICE_S_INRONING -> {
                tv_bulk.text = serviceType.formatBulkDiscount()
                initClothesList()
            }
        }
        initDatePicker()
        visibleByType()
    }

    override fun initListener() {
        lytBack.setOnClickListener(this)
        btn_booking.setOnClickListener(this)
        btn_date.setOnClickListener(this)
        cb_user_info.setOnCheckedChangeListener { _, b ->
            if (b) {
                et_city.setText(userInfo.city)
                et_suburb.setText(userInfo.suburb)
                et_street.setText(userInfo.street)
                et_phone.setText(userInfo.phone)
            } else {
                et_city.setText("")
                et_suburb.setText("")
                et_street.setText("")
                et_phone.setText("")
            }
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.lytBack -> this.finish()
            R.id.btn_booking -> bookingService()
            R.id.btn_date -> datePicker.show(btn_date.text.toString().trim { it <= ' ' })
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun success() {
        this.finish()
    }

    override fun bookingService() {
        var order: Order? = null
        val city = et_city.text.toString().trim { it <= ' ' }
        val suburb = et_suburb.text.toString().trim { it <= ' ' }
        val street = et_street.text.toString().trim { it <= ' ' }
        val date = btn_date.text.toString().trim { it <= ' ' }
        val phone = et_phone.text.toString().trim { it <= ' ' }

        when{
            city.isEmpty() -> showMessage(resources.getString(R.string.toast_city))
            suburb.isEmpty() -> showMessage(resources.getString(R.string.toast_suburb))
            street.isEmpty() -> showMessage(resources.getString(R.string.toast_street))
            phone.isEmpty() -> showMessage(resources.getString(R.string.toast_phone_number))
            else -> {
                when (serviceType.id) {
                    Constants.ID_SERVICE_G_CLEANING, Constants.ID_SERVICE_D_CLEANING -> order = Order(0, userInfo.id, date, createTimingService(serviceType), phone, city, suburb, street, 0, 0f, 0, "", "", 0, "", 10)
                    Constants.ID_SERVICE_BUFFERING, Constants.ID_SERVICE_WATERBLASTING, Constants.ID_SERVICE_CARPETWASH ->
                        when{
                            et_area.text.toString().isEmpty() -> showMessage("Please enter the area.")
                            else -> {
                                val area = Integer.valueOf(et_area.text.toString())
                                when{
                                    area > 0 -> {
                                        val amount = serviceType.products?.get(0)?.unit_price!!.times(area)
                                        order = Order(0, userInfo.id, date, serviceType, phone, city, suburb, street, 0, amount, 0, "", "", area, "", 10)
                                    }
                                    else -> showMessage("Please enter the area.")
                                }
                            }
                        }
                    Constants.ID_SERVICE_G_INRONING, Constants.ID_SERVICE_S_INRONING -> {
                        var amount = 0f
                        var totalPieces = 0
                        for ((_, _, _, _, _, unit_price, _, quantity) in serviceType.products!!) {
                            totalPieces += quantity
                            amount += quantity * unit_price
                        }
                        when{
                            totalPieces == 0 -> showMessage("Please enter the clothes pieces.")
                            totalPieces >= 20 -> {
                                amount *= serviceType.bulk_discount
                                order = Order(0, userInfo.id, date, serviceType, phone, city, suburb, street, 2, amount, 0, "", "", totalPieces, "", 10)
                            }
                            else -> order = Order(0, userInfo.id, date, serviceType, phone, city, suburb, street, 2, amount, 0, "", "", totalPieces, "", 10)
                        }
                    }
                }
                if (order != null) {
                    val orderBookingParams = OrderBookingParams()
                    orderBookingParams.order = order
                    orderBookingParams.products
                    orderBookingParams.products = order.subServiceType?.products
                    orderBookingPresenter.orderBooking(orderBookingParams)
                }
            }
        }
    }

    private fun visibleByType() {
        when (serviceType.id) {
            Constants.ID_SERVICE_G_CLEANING, Constants.ID_SERVICE_D_CLEANING -> {
                lyt_sub_option.visibility = View.VISIBLE
                lyt_area.visibility = View.GONE
                lyt_clothes.visibility = View.GONE
            }
            Constants.ID_SERVICE_BUFFERING, Constants.ID_SERVICE_WATERBLASTING, Constants.ID_SERVICE_CARPETWASH -> {
                lyt_sub_option.visibility = View.GONE
                lyt_clothes.visibility = View.GONE
                lyt_area.visibility = View.VISIBLE
            }
            Constants.ID_SERVICE_G_INRONING, Constants.ID_SERVICE_S_INRONING -> {
                lyt_sub_option.visibility = View.GONE
                lyt_area.visibility = View.GONE
                lyt_clothes.visibility = View.VISIBLE
                lyt_unit_price.visibility = View.GONE
            }
        }
    }


    private fun initDatePicker() {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH)
        val nowDate = Date()
        val endDate = Date()
        endDate.year = nowDate.year + 5
        val now = sdf.format(nowDate)
        val end = sdf.format(endDate)
        btn_date.text = now

        datePicker = CustomDatePicker(this, { time -> btn_date.text = time }, now, end)
        datePicker.showSpecificTime(true)
        datePicker.setIsLoop(false)
    }

    private fun initSpinnerData() {
        subOptions = mutableListOf()
        for ((_, _, _, product_name) in serviceType.products!!) {
            subOptions.add(product_name!!)
        }
        val arrayAdapter = object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subOptions) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                return setCentered(super.getView(position, convertView, parent))
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                return setCentered(super.getDropDownView(position, convertView, parent))
            }

            @SuppressLint("RtlHardcoded")
            private fun setCentered(view: View): View {
                val textView = view.findViewById<TextView>(android.R.id.text1)
                textView.height = resources.getDimensionPixelOffset(R.dimen.spinner_height)
                textView.textSize = GeneralUtil.px2sp(this@OrderBookingActivity, resources.getDimensionPixelOffset(R.dimen.textSize_middle)).toFloat()
                textView.gravity = Gravity.RIGHT or Gravity.CENTER_VERTICAL
                return view
            }
        }
        sp_sub_option.adapter = arrayAdapter
        sp_sub_option.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                subOptionPosition = i
                tv_unit_price.text = serviceType.products?.get(subOptionPosition)?.formatPrice()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
                subOptionPosition = 0
            }
        }
    }

    private fun initClothesList() {
        clothesAdapter = ClothesAdapter(this, Constants.ADAPTER_CLOTHES_BOOKING)
        clothesAdapter.setData(serviceType.products)
        rv_clothes.adapter = clothesAdapter
        rv_clothes.layoutManager = LinearLayoutManager(this)
    }

    private fun createTimingService(subServiceType: SubServiceType): SubServiceType {
        val products = mutableListOf<ServiceProduct>()
        products.add(subServiceType.products!![subOptionPosition])
        subServiceType.products = products
        return subServiceType
    }
}
