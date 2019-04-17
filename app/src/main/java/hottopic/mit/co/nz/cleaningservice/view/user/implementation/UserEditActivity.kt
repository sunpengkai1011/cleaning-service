package hottopic.mit.co.nz.cleaningservice.view.user.implementation

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import hottopic.mit.co.nz.cleaningservice.BaseActivity
import hottopic.mit.co.nz.cleaningservice.Constants
import hottopic.mit.co.nz.cleaningservice.R
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo
import hottopic.mit.co.nz.cleaningservice.presenter.user.implementation.UserEditPresenterImpl
import hottopic.mit.co.nz.cleaningservice.presenter.user.inter.IUserEditPresenter
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil
import hottopic.mit.co.nz.cleaningservice.view.order.implementation.DiscountActivity
import hottopic.mit.co.nz.cleaningservice.view.order.implementation.HomeActivity
import hottopic.mit.co.nz.cleaningservice.view.order.implementation.OrdersActivity
import hottopic.mit.co.nz.cleaningservice.view.user.inter.IUserEditView
import kotlinx.android.synthetic.main.activity_me_edit.*
import kotlinx.android.synthetic.main.view_title_bar.*

class UserEditActivity : BaseActivity(), IUserEditView {
    private var isEdit = true
    private lateinit var presenter: IUserEditPresenter

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setViewContent(Constants.userInfo)
    }

    override fun initView() {
        setContentView(R.layout.activity_me_edit)
    }

    override fun initData() {
        lytBack.visibility = View.VISIBLE
        lytRight.visibility = View.VISIBLE
        ivIcon.setImageResource(R.drawable.icon_edit)
        setViewContent(Constants.userInfo)
        presenter = UserEditPresenterImpl(this, this)
    }

    override fun initListener() {
        btn_commit.setOnClickListener(this)
        lytBack.setOnClickListener(this)
        lytRight.setOnClickListener(this)
        iv_top_up.setOnClickListener(this)
    }

    @SuppressLint("ResourceAsColor")
    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_commit -> edit()
            R.id.lytRight -> navigateToEdit()
            R.id.lytBack -> this.finish()
            R.id.iv_top_up -> navigateToTopUp()
        }
    }

    private fun setViewContent(userInfo: UserInfo) {
        lyt_user.visibility = View.VISIBLE
        lyt_edit.visibility = View.GONE
        btn_commit.text = resources.getString(R.string.btn_logout)
        tvTitle.text = resources.getString(R.string.title_info)
        btn_commit.setBackgroundColor(ContextCompat.getColor(this, R.color.background_logout))
        tv_username.text = userInfo.username
        tv_user_role.text = userInfo.role_name
        if (userInfo.role_id == Constants.ROLE_CUSTOMER) {
            lyt_balance.visibility = View.VISIBLE
        } else {
            lyt_balance.visibility = View.GONE
        }
        tv_phone_number.text = userInfo.phone
        tv_address.text = userInfo.address
        tv_email.text = userInfo.email
        tv_balance.text = userInfo.formatBalance()
        et_phone_number.setText(userInfo.phone)
        et_email.setText(userInfo.email)
        et_city.setText(userInfo.city)
        et_suburb.setText(userInfo.suburb)
        et_street.setText(userInfo.street)
    }

    override fun navigateToEdit() {
        when{
            isEdit -> {
                lyt_user.visibility = View.GONE
                lyt_edit.visibility = View.VISIBLE
                btn_commit.text = resources.getString(R.string.btn_edit)
                tvTitle.text = resources.getString(R.string.title_edit)
                btn_commit.setBackgroundColor(ContextCompat.getColor(this, R.color.background_title_bar))
                isEdit = false
            }
            !isEdit -> {
                lyt_user.visibility = View.VISIBLE
                lyt_edit.visibility = View.GONE
                btn_commit.text = resources.getString(R.string.btn_logout)
                tvTitle.text = resources.getString(R.string.title_info)
                btn_commit.setBackgroundColor(ContextCompat.getColor(this, R.color.background_logout))
                isEdit = true
            }
        }
    }

    override fun edit() {
        if (isEdit) {
            GeneralUtil.storeIntIntoSP(this, Constants.SP_KEY_LAST_LOGIN_TIMESTAMP, 0)
            //Logout
            val intent = if (Constants.ROLE_CUSTOMER == Constants.userInfo.role_id) {
                Intent(this@UserEditActivity, HomeActivity::class.java)
            } else {
                Intent(this@UserEditActivity, OrdersActivity::class.java)
            }
            intent.putExtra(Constants.KEY_INTENT_CLOSETYPE, Constants.CLOSE_TYPE_LOGOUT)
            startActivity(intent)
        } else {
            editUserInfo()
        }
    }

    override fun navigateToTopUp() {
        val intent = Intent(this@UserEditActivity, DiscountActivity::class.java)
        startActivity(intent)
    }


    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showUserInfo(userInfo: UserInfo) {
        setViewContent(userInfo)
    }

    override fun editAvalable() {
        isEdit = true
    }

    private fun editUserInfo() {
        val userInfo = Constants.userInfo
        val phoneNumber = et_phone_number.text.toString().trim { it <= ' ' }
        val email = et_email.text.toString().trim { it <= ' ' }
        val city = et_city.text.toString().trim { it <= ' ' }
        val suburb = et_suburb.text.toString().trim { it <= ' ' }
        val street = et_street.text.toString().trim { it <= ' ' }

        when {
            TextUtils.isEmpty(phoneNumber) -> showMessage(resources.getString(R.string.toast_phone_number))
            TextUtils.isEmpty(email) -> showMessage(resources.getString(R.string.toast_email))
            TextUtils.isEmpty(city) -> showMessage(resources.getString(R.string.toast_city))
            TextUtils.isEmpty(suburb) -> showMessage(resources.getString(R.string.toast_suburb))
            TextUtils.isEmpty(street) -> showMessage(resources.getString(R.string.toast_street))
            else -> {
                userInfo.phone = phoneNumber
                userInfo.email = email
                userInfo.city = city
                userInfo.city = suburb
                userInfo.city = street
                presenter.editUserInfo(userInfo)
            }
        }

    }
}
