package com.liu.easyoffice.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.liu.easyoffice.Adapter.MyPagerAdapter;
import com.liu.easyoffice.MyView.MyViewPager;
import com.liu.easyoffice.R;

import org.xutils.x;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.CommandNotificationMessage;
import io.rong.message.ContactNotificationMessage;
import io.rong.message.ImageMessage;
import io.rong.message.InformationNotificationMessage;
import io.rong.message.LocationMessage;
import io.rong.message.ProfileNotificationMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolBar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView slideLv;
    private MyViewPager viewPager;
    private RadioGroup bottomRg;
    private ImageView userImg;
    private TextView userName;
    //从preference读取
    String Token;
    String userId;
    String name;
    String portraitUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        msgInit();
        rongConnect();
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
                        viewPager.setCurrentItem(0, false);//false为禁止动画
                        break;
                    case R.id.rbtn_work:
                        viewPager.setCurrentItem(1, false);
                        break;
                    case R.id.rbtn_contactor:
                        viewPager.setCurrentItem(2, false);
                }
            }
        });
        viewPager.setOnPageChangeListener(new MyViewPager.OnPageChangeListener() {//滑动页面切换底部按钮
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
        userImg = ((ImageView) findViewById(R.id.slide_user_img));//用户头像
        userName = ((TextView) findViewById(R.id.slide_user_name));//用户姓名
        viewPager = ((MyViewPager) findViewById(R.id.body_view_pager));//viewPager控件
        bottomRg = ((RadioGroup) findViewById(R.id.bottom_rg));//底部radioGroup
    }

    /**
     * 信息初始化 用户头像 用户姓名等
     */
    private void msgInit() {
        SharedPreferences preferences = getApplication().getSharedPreferences("user", MODE_PRIVATE);
        Token = preferences.getString("userToken", null);
        userId = preferences.getString("userId", "0");
        name = preferences.getString("userName", "用户不存在");
        portraitUri = preferences.getString("imgUrl", "");
        userName.setText(name);
        RongIM.getInstance().refreshUserInfoCache(new UserInfo(userId, name, Uri.parse(portraitUri)));//设置头像
        x.image().bind(userImg, portraitUri);
    }

    /**
     * 融云连接
     */
    private void rongConnect() {
        RongIM.connect(Token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Log.e("main", "tokenCorrect");
            }

            @Override
            public void onSuccess(String s) {
                Log.e("main", s);
                Toast.makeText(getApplicationContext(), "登陆成功！", LENGTH_SHORT).show();

//                RongIMClient.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
//                    @Override
//                    public boolean onReceived(Message message, int i) {
//                        Toast.makeText(MainActivity.this,message.getTargetId(),Toast.LENGTH_SHORT).show();
//                        return false;
//                    }
//                });

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e("main", errorCode.getMessage() + "");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent=new Intent();
        switch (item.getItemId()){
            case R.id.menu_create_chat:
                intent.setClass(this,CreateGroup.class);
                startActivity(intent);
                break;
            case R.id.menu_add_friend:
                intent.setClass(this,AddFriendActivity.class);
                startActivity(intent);
                break;
        }
        Log.e("menu",item.getTitle()+"");
        Toast.makeText(this,item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }

}
