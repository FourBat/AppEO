package com.liu.easyoffice.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.liu.easyoffice.Utils.Utils;
import com.liu.easyoffice.R;
import com.liu.easyoffice.pojo.Company;
import com.liu.easyoffice.pojo.Group;
import com.liu.easyoffice.pojo.User;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

/**
 * Created by hui on 2016/9/13.
 */
public class LoginActivity extends Activity {
    private static final String TAG = "net";
    private EditText userName;
    private EditText userPwd;
    private Button btnLogin;
    private EditText userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();
        checkLogin();
    }

    /**
     * 初始化控件
     */
    private void init() {
        userName = ((EditText) findViewById(R.id.user_name));
        userPwd = ((EditText) findViewById(R.id.user_pwd));
        userId = ((EditText) findViewById(R.id.login_et_number));
        btnLogin = ((Button) findViewById(R.id.btn_login));
    }

    private void checkLogin() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = userName.getText().toString();
                String pwd = userPwd.getText().toString();
                String id = userId.getText().toString();
                requestForCom(id);
                final RequestParams params = new RequestParams(Utils.LOGIN_URL);
                params.addParameter("userName", name);
                params.addParameter("userPwd", pwd);
                params.addParameter("userId", id);
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i(TAG, result);
                        /**
                         * 数据存储 token，用户名，用户头像
                         */
                        Gson gson=new Gson();
                        User user=gson.fromJson(result, User.class);
                        SharedPreferences preferences=getApplication().getSharedPreferences("user",MODE_PRIVATE);
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putString("userId",user.getUserId());
                        editor.putString("userName",user.getUserName());
                        editor.putString("userToken",user.userToken);
                        editor.putString("imgUrl",user.getImgUrl());
                        editor.commit();
                        Toast.makeText(LoginActivity.this,preferences.getString("imgUrl",""),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Log.i(TAG, ex.getMessage());
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        });
    }

    /**
     * 根据当前登录id 判断当前所在公司
     * @param tel
     */
    private void requestForCom(final String tel){
        final RequestParams params=new RequestParams(Utils.GET_COMPANY);
        params.addParameter("tel",tel);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                final Gson gson=new Gson();
                Company company=gson.fromJson(result, Company.class);
                SharedPreferences preferences=getApplication().getSharedPreferences("company",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putLong("tcId",company.getTcId());
                editor.putString("tcName",company.getTcName());
                editor.commit();
                /**
                 * 查询当前第一个组id
                 */
                RequestParams currentGId=new RequestParams(Utils.GET_GROUP_CURRENT_ID);
                currentGId.addParameter("companyId",company.getTcId());
                currentGId.addParameter("parentId",0);
                x.http().get(currentGId, new CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result){
                        Gson gson1=new Gson();
                        Group group=gson1.fromJson(result, Group.class);
                        SharedPreferences preferences=getApplication().getSharedPreferences("company",MODE_PRIVATE);
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putLong("tgId",group.getTgId());
                        editor.commit();
                    }
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
