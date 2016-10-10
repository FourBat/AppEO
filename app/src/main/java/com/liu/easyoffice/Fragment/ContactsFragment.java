package com.liu.easyoffice.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liu.easyoffice.Activity.NewFriend;
import com.liu.easyoffice.Activity.ShowMemberActivity;
import com.liu.easyoffice.Adapter.MyExpandListAdapter;
import com.liu.easyoffice.R;
import com.liu.easyoffice.Utils.MySharePreference;
import com.liu.easyoffice.Utils.Utils;
import com.liu.easyoffice.pojo.Group;
import com.liu.easyoffice.pojo.User;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;

/**
 * Created by hui on 2016/9/14.
 */
public class ContactsFragment extends Fragment {
    private static ContactsFragment instance = null;

    private ListView contactsLv;
    private List<User> users = new ArrayList<>();
    private SimpleAdapter adapter;
    private List<Map<String, Object>> list = new ArrayList<>();
    String imgURl;
    private RelativeLayout newFriend;
    private ExpandableListView expandLv;

    public static ContactsFragment getInstance() {
        if (instance != null) {
            return instance;
        } else {
            return new ContactsFragment();
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("onCreate");
        View view = inflater.inflate(R.layout.contacts_fragment, container, false);
        initView(view);
        clickContacts();
        return view;
    }
    private void initView(View view) {
        newFriend = ((RelativeLayout) view.findViewById(R.id.contacts_layout_newfriend));
        contactsLv = ((ListView) view.findViewById(R.id.contacts_lv));
        expandLv = ((ExpandableListView) view.findViewById(R.id.contacts_expand));
        expandLv.setGroupIndicator(null);
        getGroupsByTel();
        String froms[] = {"imgUrl", "userName", "userId"};
        int to[] = {R.id.contacts_lv_img, R.id.contacts_lv_name, R.id.contacts_lv_id};
        adapter = new SimpleAdapter(getActivity(), list, R.layout.contacts_list, froms, to);
        contactsLv.setAdapter(adapter);
        initMsg();
        enterNewFriend();
        enterCompanMsg();
    }
    /**
     * 初始化信息 加载用户列表信息
     */
    private void initMsg() {
        String userId = getActivity().getApplication().getSharedPreferences("user", Context.MODE_PRIVATE).getString("userId", "");
        String userName = getActivity().getApplication().getSharedPreferences("user", Context.MODE_PRIVATE).getString("userName", "");
        imgURl = getActivity().getApplication().getSharedPreferences("user", Context.MODE_PRIVATE).getString("imgUrl", "");
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
                    Log.e("img", user.getImgUrl());
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
    //点击每个用户
    private void clickContacts() {
        contactsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String userId = ((TextView) view.findViewById(R.id.contacts_lv_id)).getText().toString();
                final String userName = ((TextView) view.findViewById(R.id.contacts_lv_name)).getText().toString();
                if (RongIM.getInstance() != null) {
                    RongIM.getInstance().startPrivateChat(getActivity(), userId, userName);
                }

            }
        });
    }
    private void enterNewFriend() {
        newFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewFriend.class);
                startActivity(intent);
            }
        });
    }
    /**
     * 跳转到显示公司成员界面
     */
    private void enterCompanMsg() {
        Log.e("msg", "测试");
        expandLv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Intent intent = new Intent(getActivity(), ShowMemberActivity.class);
                TextView name= (TextView) view.findViewById(R.id.contacts_expand_child_item_name);
                TextView id= (TextView) view.findViewById(R.id.contacts_expand_child_item_gid);
                if(i1==0){
                    intent.putExtra("currentName",MySharePreference.getCurrentCompany(getActivity()).getTcName());
                    intent.putExtra("parentId",MySharePreference.getCurrentCroup(getActivity()).getTgId());
                    intent.putExtra("currentGId", MySharePreference.getCurrentCroup(getActivity()).getTgId());
                    Log.e("id", MySharePreference.getCurrentCroup(getActivity()).getTgId()+"");
                    startActivity(intent);
                }else {
                    intent.putExtra("currentName",name.getText().toString());
                    intent.putExtra("currentGId", Long.parseLong(id.getText().toString()));
                    startActivity(intent);
                }
                return true;
            }
        });

    }
    /**
     * 根據当前的手机号查找当前的所在的组
     */
    private void getGroupsByTel(){
        RequestParams params=new RequestParams(Utils.GET_GROUPS);
        params.addParameter("tel",MySharePreference.getCurrentUser(getActivity()).getUserId());
        params.addParameter("companyId",MySharePreference.getCurrentCompany(getActivity()).getTcId());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                List<Group> groups=gson.fromJson(result,new TypeToken<List<Group>>(){}.getType());
                expandLv.setAdapter(new MyExpandListAdapter(getActivity(),groups));
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
