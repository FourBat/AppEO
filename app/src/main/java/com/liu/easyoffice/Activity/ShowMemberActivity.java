package com.liu.easyoffice.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.liu.easyoffice.R;
import com.liu.easyoffice.Utils.MySharePreference;
import com.liu.easyoffice.Utils.Utils;
import com.liu.easyoffice.pojo.Group;
import com.liu.easyoffice.pojo.GroupMember;
import com.liu.easyoffice.pojo.User;

import org.w3c.dom.Text;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowMemberActivity extends AppCompatActivity {

    private ListView groupLv;
    private ListView memberLv;
    private SimpleAdapter groupAdapter;
    List<Map<String,Object>> glists=new ArrayList<>();
    List<Map<String,Object>>  mLists=new ArrayList<>();
    private SimpleAdapter menberAdapter;
    private TextView currentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_member);
        init();
    }

    /**
     * 控件初始化
     */
    private void init(){
        groupLv = ((ListView) findViewById(R.id.show_member_lv_group));
        memberLv = ((ListView) findViewById(R.id.show_member_lv_member));
        currentName = ((TextView) findViewById(R.id.show_member_tv_cname));
        currentName.setText(getIntent().getStringExtra("currentName"));
        setAdapter();
    }
    private void setAdapter(){

        String gform[]={"gname","gid"};
        int gto[]={R.id.show_group_tv_name,R.id.show_group_id};
        groupAdapter=new SimpleAdapter(this,glists,R.layout.show_group_item,gform,gto);
        groupLv.setAdapter(groupAdapter);

        String mForm[]={"mname","mtel","mimgUrl"};
        int to[]={R.id.show_member_item_tv_name,R.id.show_member_tv_tel,R.id.show_member_iv};
        menberAdapter=new SimpleAdapter(this,mLists,R.layout.show_member_item,mForm,to);
        memberLv.setAdapter(menberAdapter);
        loadMsg();
    }
    /**
     * 加载值
     */
    private void loadMsg(){
        RequestParams params=new RequestParams(Utils.GET_GROUP_MEMBER);
        params.addParameter("companyId",MySharePreference.getCurrentCompany(getApplicationContext()).getTcId());
        Long parentId=getIntent().getLongExtra("parentId",-1L);
        params.addParameter("parentId",parentId);
        Log.e("parent", "loadMsg: "+parentId );
        params.addParameter("currentGId",getIntent().getLongExtra("currentGId",0));
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("group",result);
                Gson gson=new Gson();
                GroupMember groupMember=gson.fromJson(result, GroupMember.class);
                List<Group> groups=groupMember.getGroups();
                for (Group group : groups) {
                    Map<String,Object> maps=new HashMap<String, Object>();
                    maps.put("gname",group.getTgName());
                    maps.put("gid",group.getTgId());
                    glists.add(maps);
                }
                groupAdapter.notifyDataSetChanged();
                List<User> users=groupMember.getUsers();
                for (User user : users) {
                    Map<String,Object> maps=new HashMap<String, Object>();
                    menberAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
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
                    maps.put("mname",user.getUserName());
                    maps.put("mtel",user.getUserId());
                    maps.put("mimgUrl",user.getImgUrl());
                    mLists.add(maps);
                }
                menberAdapter.notifyDataSetChanged();
                itemClick();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("group", "onError: "+ex.toString() );
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
     * group点击与member点击
     */
    private void itemClick(){
        groupLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv= (TextView) view.findViewById(R.id.show_group_id);
                TextView name= (TextView) view.findViewById(R.id.show_group_tv_name);
                Long id=Long.parseLong(tv.getText().toString());
                Intent intent=new Intent(ShowMemberActivity.this,ShowMemberActivity.class);
                intent.putExtra("currentName",name.getText().toString());
                intent.putExtra("parentId",id);
                intent.putExtra("currentGId",id);
                startActivity(intent);
            }
        });
    }
}
