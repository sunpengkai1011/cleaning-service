package hottopic.mit.co.nz.cleaningservice.view.order.implementation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import hottopic.mit.co.nz.cleaningservice.BaseActivity
import hottopic.mit.co.nz.cleaningservice.Constants
import hottopic.mit.co.nz.cleaningservice.R
import hottopic.mit.co.nz.cleaningservice.adapter.OrderAdapter
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo
import hottopic.mit.co.nz.cleaningservice.presenter.order.implementation.OrdersPresenterImpl
import hottopic.mit.co.nz.cleaningservice.view.order.inter.IOrdersView
import hottopic.mit.co.nz.cleaningservice.view.user.implementation.LoginActivity
import hottopic.mit.co.nz.cleaningservice.view.user.implementation.UserEditActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.view_title_bar.*
import java.util.concurrent.TimeUnit

class OrdersActivity : BaseActivity(), IOrdersView, OrderAdapter.OnItemClickListener {
    private var orderAdapter: OrderAdapter? = null

    private lateinit var orderPresenter: OrdersPresenterImpl
    private var isQuit = false

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val statusType = intent?.getIntExtra(Constants.KEY_INTENT_CLOSETYPE, 0)
        if (Constants.CLOSE_TYPE_LOGOUT == statusType) {
            //open login page
            val login = Intent(this, LoginActivity::class.java)
            startActivity(login)
            finish()
        }
    }

    override fun initView() {
        setContentView(R.layout.activity_user)
    }

    override fun initData() {
        tvTitle.text = resources.getString(R.string.title_me)
        if (Constants.ROLE_CUSTOMER == Constants.userInfo.role_id) {
            lytBack.visibility = View.VISIBLE
        }
        lytRight.visibility = View.VISIBLE
        ivIcon.setImageResource(R.drawable.icon_user)
        setData(Constants.userInfo)
        orderPresenter = OrdersPresenterImpl(this, this)
        getOrderByRole()
    }

    override fun initListener() {
        lytBack.setOnClickListener(this)
        lyt_header.setOnClickListener(this)
        lytRight.setOnClickListener(this)
        srl_orders.setOnRefreshListener { getOrderByRole() }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.lyt_header, R.id.lytRight -> navigateToUserInfo()
            R.id.lytBack -> this.finish()
        }
    }

    @SuppressLint("CheckResult")
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (Constants.ROLE_CUSTOMER == Constants.userInfo.role_id) {
                lytBack.performClick()
            } else {
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

    private fun setData(userInfo: UserInfo?) {
        if (userInfo != null) {
            tv_username.text = userInfo.username
            tv_user_role.text = userInfo.role_name
        }
    }

    override fun onItemClick(order: Order, position: Int) {
        navigateToDetail(order)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Activity.RESULT_OK == resultCode) {
            if (Constants.INTENT_REQUEST_ORDER_TO_DETAIL == requestCode) {
                getOrderByRole()
            }
        }
    }

    override fun navigateToDetail(order: Order) {
        val intent = Intent(this@OrdersActivity, OrderDetailActivity::class.java)
        intent.putExtra(Constants.KEY_INTENT_ORDER, order)
        startActivityForResult(intent, Constants.INTENT_REQUEST_ORDER_TO_DETAIL)
    }

    override fun navigateToUserInfo() {
        val editIntent = Intent(this, UserEditActivity::class.java)
        startActivity(editIntent)
    }

    override fun showOrders(orders: List<Order>) {
        if (srl_orders.isRefreshing) {
            srl_orders.isRefreshing = false
        }
        if (orderAdapter == null) {
            orderAdapter = OrderAdapter(this)
            orderAdapter!!.setData(orders)
            orderAdapter!!.setOnItemClickListener(this)
            rv_orders.layoutManager = LinearLayoutManager(this)
            rv_orders.adapter = orderAdapter
        } else {
            orderAdapter!!.setData(orders)
            orderAdapter!!.notifyDataSetChanged()
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }



    private fun getOrderByRole() {
        if (Constants.ROLE_CUSTOMER == Constants.userInfo.role_id) {
            orderPresenter.getOrdersByCustomer(Constants.userInfo.id)
        } else {
            orderPresenter.getOrdersByStaff()
        }
    }
}
