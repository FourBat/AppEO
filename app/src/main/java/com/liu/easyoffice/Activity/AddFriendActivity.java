package com.liu.easyoffice.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.liu.easyoffice.R;
import com.liu.easyoffice.Utils.MySharePreference;
import com.liu.easyoffice.Utils.SendAddFriend;
import com.liu.easyoffice.Utils.Utils;
import com.liu.easyoffice.pojo.User;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imlib.ipc.RongService;
import io.rong.message.ContactNotificationMessage;

/**
 * Created by hui on 2016/9/24.
 */

public class AddFriendActivity extends Activity {
    private EditText etUserId;
    private ListView friends;
    private SimpleAdapter adapter;
    private List<Map<String, String>> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        init();
        searchClick();
    }
    /**
     * 初始化控件
     */
    private void init(){
        etUserId = ((EditText) findViewById(R.id.add_friend_et_number));
        friends = ((ListView) findViewById(R.id.add_friend_lv));
        String froms[] = {"imgUrl", "userName", "userId"};
        int to[] = {R.id.search_friend_item_iv,R.id.search_friend_item_name, R.id.search_friend_item_id};
        adapter = new SimpleAdapter(this, list, R.layout.search_friend_item, froms, to);
        friends.setAdapter(adapter);
        listItemClick();
    }
    /**
     * 搜索事件触发
     */
    public void searchClick(){
        etUserId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Toast.makeText(AddFriendActivity.this,charSequence, Toast.LENGTH_SHORT).show();
               searchFormServe(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public void searchFormServe(String userId){
        RequestParams params=new RequestParams(Utils.SEARCH_FRIEND_URL);
        params.addParameter("friendId",userId);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("result",result);
                if(list.size()!=0){
                    list.remove(0);
                }
                Map<String,String> map=new HashMap<String, String>();
                if(!result.equals("null")){
                    Gson gson=new Gson();
                    User user=gson.fromJson(result, User.class);
                    adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                        public boolean setViewValue(View view, Object data,
                                                    String textRepresentation) {
                            //判断是否为我们要处理的对象
                            if(view instanceof ImageView && data instanceof String){
                                ImageView iv = (ImageView) view;
                                x.image().bind(iv,data+"");
                                return true;
                            }else
                                return false;
                        }
                    });
                    map.put("imgUrl", user.getImgUrl());
                    map.put("userName", user.getUserName());
                    map.put("userId", user.getUserId());
                    list.add(map);
                }
                adapter.notifyDataSetChanged();
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
    public void listItemClick(){
        friends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String targetId= ((TextView) view.findViewById(R.id.search_friend_item_id)).getText().toString();
                ContactNotificationMessage contact = ContactNotificationMessage.obtain(ContactNotificationMessage.CONTACT_OPERATION_REQUEST,MySharePreference.getCurrentUser(getApplicationContext()).getUserId(),targetId,"hhh");
                contact.setExtra("I'm Bob");
                SendAddFriend.sendMessage(contact);
            }
        });
    }
}
