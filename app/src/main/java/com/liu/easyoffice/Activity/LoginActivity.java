package com.liu.easyoffice.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.liu.easyoffice.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by hui on 2016/9/13.
 */
public class LoginActivity extends Activity {
    private static final String TAG ="net" ;
    private EditText userName;
    private EditText userPwd;
    private Button btnLogin;
    private final static String URL="http://10.40.5.46:8080/Xutils/login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();
        checkLogin();//gewgewg
        checkLogin();
    }

    /**
     * 初始化控件
     */
    private void init(){
        userName = ((EditText) findViewById(R.id.user_name));
        userPwd = ((EditText) findViewById(R.id.user_pwd));
        btnLogin = ((Button) findViewById(R.id.btn_login));
    }
    private void checkLogin(){

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=userName.getText().toString();
                String pwd=userPwd.getText().toString();
                RequestParams params=new RequestParams(URL);
                params.addParameter("userName",name);
                params.addParameter("userPwd",pwd);
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i(TAG,result);
                        if(result.equals("true")){
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(LoginActivity.this,"用户名或者密码错误",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Log.i(TAG,ex.getMessage());
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
}
