package hottopic.mit.co.nz.cleaningservice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        initView();
        initData();
        initListener();
    }

    public abstract void initView();
    public abstract void initData();
    public abstract void initListener();
}
