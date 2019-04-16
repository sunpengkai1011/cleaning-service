package hottopic.mit.co.nz.cleaningservice.view.user.implementation

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Toast
import hottopic.mit.co.nz.cleaningservice.BaseActivity
import hottopic.mit.co.nz.cleaningservice.Constants
import hottopic.mit.co.nz.cleaningservice.R
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo
import hottopic.mit.co.nz.cleaningservice.presenter.user.inter.IRegisterPresenter
import hottopic.mit.co.nz.cleaningservice.presenter.user.implementation.RegisterPresenterImpl
import hottopic.mit.co.nz.cleaningservice.view.user.inter.IRegisterView
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.view_title_bar.*

class RegisterActivity : BaseActivity(), IRegisterView {
    private lateinit var username: String
    private lateinit var presenter: IRegisterPresenter

    override fun initView() {
        setContentView(R.layout.activity_register)
    }

    override fun initData() {
        tvTitle.setText(R.string.title_register)
        lytBack.visibility = View.VISIBLE
        presenter = RegisterPresenterImpl(this, this)
    }

    override fun initListener() {
        btn_register.setOnClickListener(this)
        lytBack.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_register -> registerUser()
            R.id.lytBack -> this.finish()
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun returnLogin() {
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        intent.putExtra(Constants.KEY_INTENT_USERNAME, username)
        this@RegisterActivity.setResult(Activity.RESULT_OK, intent)
        this.finish()
    }

    private fun registerUser() {
        username = et_username.text.toString()
        val password = et_password.text.toString()
        val confirmPwd = et_confirm_pwd.text.toString()
        val phoneNumber = et_phone_number.text.toString()
        val email = et_email.text.toString()
        val city = et_city.text.toString()
        val suburb = et_suburb.text.toString()
        val street = et_street.text.toString()
        when{
            username.isEmpty() -> showMessage(resources.getString(R.string.toast_empty_username))
            password.isEmpty() -> showMessage(resources.getString(R.string.toast_empty_password))
            confirmPwd.isEmpty() -> showMessage(resources.getString(R.string.toast_confirm_pwd))
            phoneNumber.isEmpty() -> showMessage(resources.getString(R.string.toast_phone_number))
            email.isEmpty() -> showMessage(resources.getString(R.string.toast_email))
            city.isEmpty() -> showMessage(resources.getString(R.string.toast_city))
            suburb.isEmpty() -> showMessage(resources.getString(R.string.toast_suburb))
            street.isEmpty() -> showMessage(resources.getString(R.string.toast_street))
            else ->
                if (password != confirmPwd){
                    showMessage(resources.getString(R.string.toast_pwd_different))
                }else{
                    val userInfo = UserInfo(username, password, phoneNumber, email, city, suburb, street, Constants.ROLE_CUSTOMER, 0f)
                    presenter.register(userInfo)
                }
        }
    }
}
