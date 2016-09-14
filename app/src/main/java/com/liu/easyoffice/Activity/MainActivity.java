package com.liu.easyoffice.Activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.liu.easyoffice.Adapter.MyPagerAdapter;
import com.liu.easyoffice.R;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolBar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView slideLv;
    private ViewPager viewPager;
    private RadioGroup bottomRg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
//       设置点击左侧菜单侧滑按钮，弹出侧滑菜单
        toolBar.setTitle("易工作");
        if (toolBar != null) {
            setSupportActionBar(toolBar);
        }
        toolBar.setNavigationIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolBar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

//        viewpager+radiobtn 实现页面切换
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(0);//初始界面
        bottomRg.check(R.id.rbtn_msg);
        bottomRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_msg:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.rbtn_work:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.rbtn_contactor:
                        viewPager.setCurrentItem(2);
                }
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomRg.check(R.id.rbtn_msg);
                        break;
                    case 1:
                        bottomRg.check(R.id.rbtn_work);
                        break;
                    case 2:
                        bottomRg.check(R.id.rbtn_contactor);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化控件
     */
    private void init() {
        toolBar = ((Toolbar) findViewById(R.id.tl_custom));
        mDrawerLayout = ((DrawerLayout) findViewById(R.id.drawer));
        slideLv = ((ListView) findViewById(R.id.slide_lv));
        viewPager = ((ViewPager) findViewById(R.id.body_view_pager));//viewPager控件
        bottomRg = ((RadioGroup) findViewById(R.id.bottom_rg));//底部radioGroup
    }
}
