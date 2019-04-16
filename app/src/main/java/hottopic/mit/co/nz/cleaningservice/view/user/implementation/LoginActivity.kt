package hottopic.mit.co.nz.cleaningservice.view.user.implementation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import hottopic.mit.co.nz.cleaningservice.BaseActivity
import hottopic.mit.co.nz.cleaningservice.Constants
import hottopic.mit.co.nz.cleaningservice.R
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceTypes
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo
import hottopic.mit.co.nz.cleaningservice.presenter.user.implementation.LoginPresenterImpl
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil
import hottopic.mit.co.nz.cleaningservice.view.order.implementation.HomeActivity
import hottopic.mit.co.nz.cleaningservice.view.order.implementation.OrdersActivity
import hottopic.mit.co.nz.cleaningservice.view.user.inter.ILoginView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.view_title_bar.*
import java.util.concurrent.TimeUnit

class LoginActivity : BaseActivity(), ILoginView {

    private lateinit var loginPresenterImpl: LoginPresenterImpl

    private var isQuit = false

    override fun initView() {
        setContentView(R.layout.activity_login)
    }

    override fun initData() {
        tvTitle.text = resources.getString(R.string.title_login)
        tvToRegister.paint.flags = Paint.UNDERLINE_TEXT_FLAG

        loginPresenterImpl = LoginPresenterImpl(this, this)
//        autoLogin()
    }

    override fun initListener() {
        btnLogin.setOnClickListener(this)
        tvToRegister.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnLogin -> {
                loginPresenterImpl.login(etUsername.text.toString(), etPassword.text.toString())
            }
            R.id.tvToRegister -> {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivityForResult(intent, Constants.INTENT_REQUEST_LOGIN_TO_REGISTER)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Activity.RESULT_OK == resultCode) {
            if (data != null) {
                val username = data.getStringExtra(Constants.KEY_INTENT_USERNAME) ?: ""
                etUsername.setText(username)
            }
        }
    }

    @SuppressLint("CheckResult")
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
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
        return false
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToHome(userInfo: UserInfo, serviceTypes: ServiceTypes) {
        Constants.userInfo = userInfo
        val intent: Intent
        if (Constants.ROLE_CUSTOMER == userInfo.role_id) {
            intent = Intent(this@LoginActivity, HomeActivity::class.java)
            intent.putExtra(Constants.KEY_INTENT_SERVICETYPE, serviceTypes)
        } else {
            intent = Intent(this@LoginActivity, OrdersActivity::class.java)
        }
        startActivity(intent)
        this.finish()
    }

    fun autoLogin(){
        val storeUserInfo = GeneralUtil.getDataFromSP(this, Constants.SP_KEY_USERINFO)
        if (!TextUtils.isEmpty(storeUserInfo)) {
            val userInfo = GeneralUtil.fromJson(storeUserInfo, UserInfo::class.java)
            etUsername.setText(userInfo.username)

            val lastLoginTimestamp = GeneralUtil.getIntFromSP(this, Constants.SP_KEY_LAST_LOGIN_TIMESTAMP)
            //Check last login timestamp, if less than 1 day, do not ask for login
            if (System.currentTimeMillis() / 1000 - lastLoginTimestamp < Constants.MAX_SIGNIN_DURATION) {
                Constants.userInfo = userInfo
                val serviceTypes = GeneralUtil.fromJson(GeneralUtil.getDataFromSP(this, Constants.SP_KEY_SERVICE_TYPE), ServiceTypes::class.java)
                val intent: Intent
                if (Constants.ROLE_CUSTOMER == userInfo.role_id) {
                    intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    intent.putExtra(Constants.KEY_INTENT_SERVICETYPE, serviceTypes)
                } else {
                    intent = Intent(this, OrdersActivity::class.java)
                }
                startActivity(intent)
                this.finish()
            }
        }
    }
}
