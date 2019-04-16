package hottopic.mit.co.nz.cleaningservice

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

abstract class BaseActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        getSupportActionBar().hide();
        initView()
        initData()
        initListener()
    }

    abstract fun initView()
    abstract fun initData()
    abstract fun initListener()
}
