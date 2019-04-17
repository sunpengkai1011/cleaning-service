package hottopic.mit.co.nz.cleaningservice.view.order.implementation

import android.app.Activity
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import hottopic.mit.co.nz.cleaningservice.BaseActivity
import hottopic.mit.co.nz.cleaningservice.Constants
import hottopic.mit.co.nz.cleaningservice.R
import hottopic.mit.co.nz.cleaningservice.adapter.ClothesAdapter
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order
import hottopic.mit.co.nz.cleaningservice.presenter.order.implementation.OrderDetailPresenterImpl
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil
import hottopic.mit.co.nz.cleaningservice.view.order.inter.IOrderDetailView
import kotlinx.android.synthetic.main.activity_order_detail.*
import kotlinx.android.synthetic.main.view_title_bar.*

class OrderDetailActivity : BaseActivity(), IOrderDetailView {
    private var rating = 10
    private lateinit var order: Order
    private lateinit var orderDetailPresenter: OrderDetailPresenterImpl

    override fun initView() {
        setContentView(R.layout.activity_order_detail)
    }

    override fun initData() {
        order = intent.getSerializableExtra(Constants.KEY_INTENT_ORDER) as Order
        tvTitle.text = resources.getString(R.string.title_order_detail)
        lytRight.visibility = View.VISIBLE
        lytBack.visibility = View.VISIBLE
        ivIcon.setImageResource(R.drawable.icon_location)
        orderDetailPresenter = OrderDetailPresenterImpl(this, this)
        viewLoad()
    }

    override fun initListener() {
        lytBack.setOnClickListener(this)
        lytRight.setOnClickListener(this)
        rb_stars.setOnRatingChangeListener { ratingCount ->
            rating = (ratingCount * 2).toInt()
            order.rating = rating
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.lytBack -> this.finish()
            R.id.lytRight -> {
                var location = order.address
                location = location.replace(", ", "+")
                location = location.replace(" ", "+")
                val intent = Intent(this@OrderDetailActivity, MapActivity::class.java)
                intent.putExtra(Constants.KEY_INTENT_LOCATION, location)
                startActivity(intent)
            }
        }
    }

    override fun showOrderDetail() {
        when (order.subServiceType?.id) {
            Constants.ID_SERVICE_G_CLEANING, Constants.ID_SERVICE_D_CLEANING -> timerCleaningOrder()
            Constants.ID_SERVICE_BUFFERING, Constants.ID_SERVICE_WATERBLASTING, Constants.ID_SERVICE_CARPETWASH -> areaCleaningOrder()
            Constants.ID_SERVICE_G_INRONING, Constants.ID_SERVICE_S_INRONING -> ironingOrder()
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun success() {
        setResult(Activity.RESULT_OK)
        this.finish()
    }

    private fun viewLoad() {
        tv_service_type.text = order.subServiceType?.type_name
        tv_date.text = order.date
        tv_address.text = order.address
        if (!order.phone.isNullOrEmpty()) {
            tv_phone.text = order.phone
        }
        showOrderDetail()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Activity.RESULT_OK == resultCode) {
            this.setResult(Activity.RESULT_OK)
            finish()
        }
    }

    private fun timerCleaningOrder() {
        tv_sub_option.text = order.subServiceType?.products?.get(0)?.product_name
        tv_unit_price.text = order.subServiceType?.products?.get(0)?.formatPrice()
        lyt_area.visibility = View.GONE
        lyt_clothes.visibility = View.GONE
        when (order.status) {
            Constants.STATUS_ORDER_BOOKED -> {
                lyt_started.visibility = View.GONE
                lyt_finished.visibility = View.GONE
                lyt_duration.visibility = View.GONE
                lyt_amount.visibility = View.GONE
                lyt_feedback.visibility = View.GONE
                lyt_rating.visibility = View.GONE
                btn_commit.visibility = View.VISIBLE

                tv_status.text = resources.getString(R.string.status_booked)
                tv_status.setTextColor(ContextCompat.getColor(this, R.color.status_other))
                if (Constants.ROLE_CUSTOMER == Constants.userInfo.role_id) {
                    btn_commit.visibility = View.GONE
                } else {
                    btn_commit.text = resources.getString(R.string.btn_started)
                    order.start_time = GeneralUtil.currentTime
                    order.status = Constants.STATUS_ORDER_STARTED
                    btn_commit.setOnClickListener { orderDetailPresenter.orderStarted(order) }
                }
            }
            Constants.STATUS_ORDER_STARTED -> {
                tv_start_time.visibility = View.VISIBLE
                lyt_finished.visibility = View.GONE
                lyt_duration.visibility = View.GONE
                lyt_amount.visibility = View.GONE
                lyt_feedback.visibility = View.GONE
                lyt_rating.visibility = View.GONE
                btn_commit.visibility = View.VISIBLE

                tv_status.text = resources.getString(R.string.status_started)
                tv_status.setTextColor(ContextCompat.getColor(this, R.color.status_other))
                tv_start_time.text = order.start_time
                if (Constants.ROLE_CUSTOMER == Constants.userInfo.role_id) {
                    btn_commit.visibility = View.GONE
                } else {
                    btn_commit.text = resources.getString(R.string.btn_finished)
                    order.end_time = GeneralUtil.currentTime
                    order.duration = GeneralUtil.calculateDuration(order.start_time, order.end_time)
                    val amount = order.subServiceType?.products?.get(0)?.unit_price!!.times(order.duration)
                    order.amount = amount
                    order.status = Constants.STATUS_ORDER_FINISHED
                    btn_commit.setOnClickListener { orderDetailPresenter.orderFinished(order) }
                }
            }
            Constants.STATUS_ORDER_FINISHED -> {
                tv_feedback.visibility = View.GONE
                btn_commit.visibility = View.VISIBLE

                tv_status.text = resources.getString(R.string.status_finished)
                tv_status.setTextColor(ContextCompat.getColor(this, R.color.status_other))
                btn_commit.text = resources.getString(R.string.btn_payment)
                tv_start_time.text = order.start_time
                tv_finished_time.text = order.end_time
                tv_duration.text = order.formatDuration()
                tv_amount.text = order.formatAmount()
                btn_commit.setOnClickListener { finishedRequest() }
            }
            Constants.STATUS_ORDER_PAID -> {
                tv_start_time.text = order.start_time
                tv_finished_time.text = order.end_time
                tv_duration.text = order.formatDuration()
                tv_amount.text = order.formatAmount()
                viewByPaid()
            }
        }
    }

    private fun areaCleaningOrder() {
        tv_sub_option.visibility = View.GONE
        tv_unit_price.text = order.subServiceType?.products?.get(0)?.formatPrice()
        lyt_area.visibility = View.VISIBLE
        tv_area.text = order.formatQuantity()
        lyt_clothes.visibility = View.GONE
        tv_amount.text = order.formatAmount()
        lyt_started.visibility = View.GONE
        lyt_finished.visibility = View.GONE
        lyt_duration.visibility = View.GONE
        when (order.status) {
            Constants.STATUS_ORDER_BOOKED, Constants.STATUS_ORDER_STARTED -> {
                lyt_feedback.visibility = View.GONE
                lyt_rating.visibility = View.GONE
                btn_commit.visibility = View.VISIBLE

                tv_status.text = resources.getString(R.string.status_booked)
                tv_status.setTextColor(ContextCompat.getColor(this, R.color.status_other))
                if (Constants.ROLE_CUSTOMER == Constants.userInfo.role_id) {
                    btn_commit.visibility = View.GONE
                } else {
                    btn_commit.text = resources.getString(R.string.btn_finished)
                    order.end_time = GeneralUtil.currentTime
                    order.status = Constants.STATUS_ORDER_FINISHED
                    btn_commit.setOnClickListener { orderDetailPresenter.orderFinished(order) }
                }
            }
            Constants.STATUS_ORDER_FINISHED -> {
                tv_feedback.visibility = View.GONE
                btn_commit.visibility = View.VISIBLE

                tv_status.text = resources.getString(R.string.status_finished)
                tv_status.setTextColor(ContextCompat.getColor(this, R.color.status_other))
                btn_commit.text = resources.getString(R.string.btn_payment)

                btn_commit.setOnClickListener { finishedRequest() }
            }
            Constants.STATUS_ORDER_PAID -> viewByPaid()
        }
    }

    private fun ironingOrder() {
        tv_sub_option.visibility = View.GONE
        lyt_price.visibility = View.GONE
        lyt_area.visibility = View.GONE
        lyt_clothes.visibility = View.VISIBLE
        tv_amount.text = order.formatAmount()
        lyt_started.visibility = View.GONE
        lyt_finished.visibility = View.GONE
        lyt_duration.visibility = View.GONE
        val clothesAdapter = ClothesAdapter(this, Constants.ADAPTER_CLOTHES_DETAIL)
        clothesAdapter.setData(order.subServiceType?.products)
        rv_clothes.adapter = clothesAdapter
        rv_clothes.layoutManager = LinearLayoutManager(this)
        if (order.quantity > 20) {
            tv_bulk.text = order.subServiceType?.formatBulkDiscount()
        }
        when (order.status) {
            Constants.STATUS_ORDER_BOOKED, Constants.STATUS_ORDER_STARTED, Constants.STATUS_ORDER_FINISHED -> {
                btn_commit.visibility = View.VISIBLE
                tv_feedback.visibility = View.GONE

                tv_status.text = resources.getString(R.string.status_finished)
                tv_status.setTextColor(ContextCompat.getColor(this, R.color.status_other))
                btn_commit.text = resources.getString(R.string.btn_payment)

                btn_commit.setOnClickListener { finishedRequest() }
            }
            Constants.STATUS_ORDER_PAID -> viewByPaid()
        }
    }

    private fun viewByPaid() {
        btn_commit.visibility = View.GONE
        et_feedback.visibility = View.GONE
        val stars = order.rating.toFloat() / 2
        rb_stars.setStar(stars)
        rb_stars.isClickable = false
        tv_status.text = resources.getString(R.string.status_paid)
        tv_status.setTextColor(ContextCompat.getColor(this, R.color.status_payment))
        if (!TextUtils.isEmpty(order.feedback)) {
            tv_feedback.text = order.feedback
        } else {
            lyt_feedback.visibility = View.GONE
        }
    }


    private fun finishedRequest() {
        val feedback = et_feedback.text.toString().trim { it <= ' ' }
        val intent = Intent(this@OrderDetailActivity, PaymentActivity::class.java)
        if (feedback.isNotEmpty()) {
            order.feedback = feedback
        } else {
            order.feedback = ""
        }
        intent.putExtra(Constants.KEY_INTENT_ORDER, order)
        intent.putExtra(Constants.KEY_INTENT_TO_PAYMENT, Constants.INTENT_REQUEST_DETAIL_TO_PAYMENT)
        startActivityForResult(intent, Constants.INTENT_REQUEST_DETAIL_TO_PAYMENT)
    }
}
