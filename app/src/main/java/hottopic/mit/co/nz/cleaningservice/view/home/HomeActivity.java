package hottopic.mit.co.nz.cleaningservice.view.home;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import hottopic.mit.co.nz.cleaningservice.BaseActivity;
import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.adapter.MyFragmentPagerAdapter;
import hottopic.mit.co.nz.cleaningservice.view.home.me.MeFragment;
import hottopic.mit.co.nz.cleaningservice.view.home.order.OrderFragment;

public class HomeActivity extends BaseActivity {
    private ViewPager vp_fragment;
    private TabLayout tab_fragment;
    private MyFragmentPagerAdapter adapter;
    private TabLayout.Tab tab_order;
    private TabLayout.Tab tab_me;

    private List<String> titles;
    private List<Fragment> fragments;

    @Override
    public void initView() {
        setContentView(R.layout.activity_home);
        vp_fragment = findViewById(R.id.vp_fragment);
        tab_fragment = findViewById(R.id.tab_fragment);
    }

    @Override
    public void initData() {
        initTabInfo();
        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles);
        vp_fragment.setAdapter(adapter);

        tab_fragment.setupWithViewPager(vp_fragment);

        tab_order = tab_fragment.getTabAt(0).setCustomView(getTabView(0));
        tab_me = tab_fragment.getTabAt(1).setCustomView(getTabView(1));
    }

    @Override
    public void initListener() {
    }

    @Override
    public void onClick(View view) {

    }

    private View getTabView(int position){
        View view = LayoutInflater.from(this).inflate(R.layout.tab_item, null);
        TextView tab_text = view.findViewById(R.id.tab_text);

        tab_text.setText(titles.get(position));
        return view;
    }

    private void initTabInfo(){
        titles = new ArrayList<>();
        titles.add(getResources().getString(R.string.tab_order));
        titles.add(getResources().getString(R.string.tab_me));

        fragments = new ArrayList<>();
        fragments.add(new OrderFragment());
        fragments.add(new MeFragment());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
