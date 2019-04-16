package hottopic.mit.co.nz.cleaningservice.view.order.implementation

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager
import hottopic.mit.co.nz.cleaningservice.BaseActivity
import hottopic.mit.co.nz.cleaningservice.Constants
import hottopic.mit.co.nz.cleaningservice.R
import hottopic.mit.co.nz.cleaningservice.adapter.ServiceAdapter
import hottopic.mit.co.nz.cleaningservice.adapter.ViewPagerAdapter
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceTypes
import hottopic.mit.co.nz.cleaningservice.entities.orders.SubServiceType
import hottopic.mit.co.nz.cleaningservice.entities.orders.TitleModel
import hottopic.mit.co.nz.cleaningservice.presenter.order.implementation.OrderPresenterImpl
import hottopic.mit.co.nz.cleaningservice.view.order.inter.IHomeView
import hottopic.mit.co.nz.cleaningservice.view.user.implementation.LoginActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.view_title_bar.*
import java.util.concurrent.TimeUnit

class HomeActivity : BaseActivity(), IHomeView, RecyclerViewPager.OnPageChangedListener, ServiceAdapter.OnItemClickedListener {
    companion object {
        private val ad_list = intArrayOf(R.drawable.advertisement_1, R.drawable.advertisement_2, R.drawable.advertisement_3, R.drawable.advertisement_4)
    }
    private lateinit var cleaningAdapter: ServiceAdapter
    private lateinit var mSubscription: CompositeDisposable

    private var totalPages = 1
    private var autoPage = 0
    private var isQuit = false

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val statusType = intent.getIntExtra(Constants.KEY_INTENT_CLOSETYPE, 0)
        if (Constants.CLOSE_TYPE_LOGOUT == statusType) {
            //open login page
            val login = Intent(this, LoginActivity::class.java)
            startActivity(login)
            finish()
        }
    }

    override fun initView() {
        setContentView(R.layout.activity_home)

    }

    @SuppressLint("SetTextI18n")
    override fun initData() {
        tvTitle.text = "HOME"
        lytRight.visibility = View.VISIBLE
        ivIcon.setImageResource(R.drawable.icon_purchase)
        val serviceTypes = intent.getSerializableExtra(Constants.KEY_INTENT_SERVICETYPE) as ServiceTypes?
        mSubscription = CompositeDisposable()
        cleaningAdapter = ServiceAdapter(this)
        if(serviceTypes != null) viewDisplay(serviceTypes)
        initAdvert()
    }

    override fun initListener() {
        rvp_ad.addOnPageChangedListener(this)
        lytRight.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.lytRight -> navigateToOrder()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mSubscription.clear()
    }

    @SuppressLint("CheckResult")
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                //If click the Back key twice in 2 seconds, the program exits
                if (!isQuit) {
                    isQuit = true
                    Toast.makeText(applicationContext, "Press the return key again to exit the program!",
                            Toast.LENGTH_SHORT).show()
                    Observable.timer(Constants.EXIT_WAIT_TIME, TimeUnit.SECONDS)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe{isQuit = false}
                } else {
                    finish()
                    System.exit(0)
                }
            }
        }
        return false
    }

    override fun navigateToOrder() {
        val intent = Intent(this@HomeActivity, OrdersActivity::class.java)
        startActivity(intent)
    }

    override fun goToAdverse() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun navigateToBooking(serviceType: SubServiceType) {
        val intent = Intent(this@HomeActivity, OrderBookingActivity::class.java)
        intent.putExtra(Constants.KEY_INTENT_SERVICETYPE, serviceType)
        startActivity(intent)
    }

    private fun viewDisplay(serviceTypes: ServiceTypes) {
        val dataList = mutableListOf<Any>()
        val mainServiceTypes = serviceTypes.mainServiceTypes
        if (mainServiceTypes != null) {
            for (mainServiceType in mainServiceTypes) {
                dataList.add(TitleModel(mainServiceType.type_name!!))
                dataList.addAll(mainServiceType.subServiceTypes!!)
            }
        }
        initServiceAdapter(dataList)
    }

    @SuppressLint("CheckResult", "SetTextI18n")
    private fun initAdvert(){
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvp_ad.layoutManager = layoutManager
        val adapter = ViewPagerAdapter(this)
        adapter.setData(ad_list)
        rvp_ad.adapter = adapter
        totalPages = ad_list.size
        tv_page_number.text = "1/$totalPages"

        val subscription = Observable.interval(3, 3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    autoPage++
                    if (autoPage > totalPages) {
                        autoPage = 0
                        rvp_ad!!.scrollToPosition(autoPage)
                    } else {
                        rvp_ad!!.scrollToPosition(autoPage)
                    }
                }
        mSubscription.add(subscription)
    }
    private fun initServiceAdapter(dataList: List<*>) {
        cleaningAdapter.setData(dataList)
        cleaningAdapter.setOnItemClickedListener(this)
        rv_cleaning.adapter = cleaningAdapter
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (dataList[position] is TitleModel) {
                    2
                } else {
                    1
                }
            }
        }
        rv_cleaning.layoutManager = gridLayoutManager
        rv_cleaning.setHasFixedSize(true)
        rv_cleaning.isNestedScrollingEnabled = false
        rv_cleaning.isFocusable = false
    }

    @SuppressLint("SetTextI18n")
    override fun OnPageChanged(oldPosition: Int, newPosition: Int) {
        //User to show the current photo position
        Log.d("OnPageChanged", newPosition.toString() + "")
        tv_page_number!!.text = (newPosition + 1).toString() + "/" + totalPages
        autoPage = newPosition
    }

    override fun onItemClicked(serviceType: SubServiceType?) {
        if (serviceType != null) {
            navigateToBooking(serviceType)
        }
    }
}
