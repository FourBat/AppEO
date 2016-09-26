package com.liu.easyoffice.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liu.easyoffice.R;
import com.liu.easyoffice.Utils.Utils;
import com.liu.easyoffice.pojo.User;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * Created by hui on 2016/9/23.
 */

public class CreateGroup extends Activity {
    String imgURl;
    ListView contactsLv;
    SimpleAdapter adapter;
    List<Map<String, Object>> list = new ArrayList<>();
    List<User> users;
    LinearLayout parent;
    Map<String, View> views = new HashMap<>();
    List<String> chatUserIds = new ArrayList<>();//添加讨论则成员的id
    Map<String, String> chatUserNames = new HashMap<>();//添加讨论组成员的姓名
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_contacts);
        initView();
    }

    private void initView() {
        contactsLv = ((ListView) findViewById(R.id.choose_contacts_lv));
        parent = (LinearLayout) findViewById(R.id.dynamic_add_contact);//显示添加创建讨论组的父布局
        btnConfirm = ((Button) findViewById(R.id.dynamic_add_btn_confirm));//确定按钮
        String froms[] = {"img", "userName", "id", "imgUrl"};
        int to[] = {R.id.choose_contacts_item_iv, R.id.choose_contacts_item_name, R.id.choose_contacts_item_id, R.id.choose_contacts_item_imgUrl};
        adapter = new SimpleAdapter(this, list, R.layout.choose_contacts_item, froms, to);
        contactsLv.setAdapter(adapter);
        itemClic();
        initMsg();
        confirmChat();
    }

    private void initMsg() {
        String userId = getApplication().getSharedPreferences("user", Context.MODE_PRIVATE).getString("userId", "");
        String userName = getApplication().getSharedPreferences("user", Context.MODE_PRIVATE).getString("userName", "");
        imgURl = getApplication().getSharedPreferences("user", Context.MODE_PRIVATE).getString("imgUrl", "");
        Log.i("name", userName);
        RequestParams params = new RequestParams(Utils.CONTACTS_URL);
        params.addParameter("userId", userId);
        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                users = gson.fromJson(result, new TypeToken<List<User>>() {
                }.getType());
                for (final User user : users) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    adapter.setViewBinder(new SimpleAdapter.ViewBinder() {

                        public boolean setViewValue(View view, Object data,
                                                    String textRepresentation) {
                            //判断是否为我们要处理的对象
                            if (view instanceof ImageView && data instanceof String) {
                                ImageView iv = (ImageView) view;
                                x.image().bind(iv, data + "");
                                return true;
                            } else
                                return false;
                        }
                    });
                    Log.e("img", user.getUserId());
                    map.put("img", user.getImgUrl());
                    map.put("userName", user.getUserName());
                    map.put("id", user.getUserId());
                    map.put("imgUrl", user.getImgUrl());
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

    /**
     * 点击联系人
     */
    private void itemClic() {
        contactsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private TextView userId;
            private TextView imgUrl;
            private TextView userName;
            private CheckBox checkItem;

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                checkItem = ((CheckBox) view.findViewById(R.id.choose_contacts_item_cb));
                userName = ((TextView) view.findViewById(R.id.choose_contacts_item_name));
                imgUrl = ((TextView) view.findViewById(R.id.choose_contacts_item_imgUrl));
                userId = ((TextView) view.findViewById(R.id.choose_contacts_item_id));
                String name = userName.getText().toString();
                String img = imgUrl.getText().toString();
                String id = userId.getText().toString();
                Log.e("id", id);
                if (checkItem.isChecked()) {
                    checkItem.setChecked(false);
                    removeView(id);
                    chatUserIds.remove(id);
                    chatUserNames.remove(id);
                } else {
                    chatUserIds.add(id);
                    chatUserNames.put(id, name);
                    Log.e("names",chatUserNames.get(id));
                    checkItem.setChecked(true);
                    addView(name, img, id);
                }
            }
        });
    }

    /**
     * 动态添加控件
     */
    private void addView(String userName, String imgURl, String userId) {
        View view = LayoutInflater.from(this).inflate(R.layout.dynamic_add_contact_item, null);
        ImageView iv = (ImageView) view.findViewById(R.id.add_contact_img);
        TextView name = (TextView) view.findViewById(R.id.add_contact_name);
        x.image().bind(iv, imgURl);
        name.setText(userName);
        parent.addView(view);
        views.put(userId, view);
    }

    /**
     * 删除控件
     *
     * @param userId
     */
    private void removeView(String userId) {
        Toast.makeText(this, userId, Toast.LENGTH_SHORT).show();
        View view = views.get(userId);
        views.remove(view);
        parent.removeView(view);
    }

    /**
     * 参加讨论组的成员id
     */
    private void confirmChat() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chatUserIds.size() > 1) {
                    RongIM.getInstance().createDiscussionChat(getApplicationContext(), chatUserIds,mapToString() , new RongIMClient.CreateDiscussionCallback() {
                        @Override
                        public void onSuccess(String s) {
                            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {

                        }
                    });
                }else {
                    Toast.makeText(CreateGroup.this, "请选择至少两位联系人", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String mapToString() {
        StringBuilder userNames=new StringBuilder();
        for (int i = 0; i < chatUserNames.size()-1; i++) {
            userNames.append(chatUserNames.get(chatUserIds.get(i))+"、");
        }
        userNames.append(chatUserNames.get(chatUserIds.get(chatUserNames.size()-1)));
        return userNames.toString();
    }
}
