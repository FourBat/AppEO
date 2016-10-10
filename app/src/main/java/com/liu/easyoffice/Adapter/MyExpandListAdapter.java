package com.liu.easyoffice.Adapter;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liu.easyoffice.R;
import com.liu.easyoffice.Utils.MySharePreference;
import com.liu.easyoffice.pojo.Group;

import java.util.List;

/**
 * Created by hui on 2016/9/27.
 */

public class MyExpandListAdapter extends BaseExpandableListAdapter {
    private Context context;
    List<Group> groups;
    public MyExpandListAdapter(Context context, List<Group> groups) {
        this.context=context;
        this.groups=groups;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int i) {
        return groups.size()+1;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        View group=view.inflate(context, R.layout.contacts_expand_group_item,null);
        ImageView iv= ((ImageView) group.findViewById(R.id.contacts_expand_icon));
        TextView tv=((TextView) group.findViewById(R.id.contacts_expand_tv_comName));
        tv.setText(MySharePreference.getCurrentCompany(context).getTcName());
        if(b){
            iv.setBackgroundResource(R.mipmap.pulldown);
        }else {
            iv.setBackgroundResource(R.mipmap.enter);
        }
        return group;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        Log.e("child", "getChildView:"+i1);
        Log.e("child", "getChildView:"+groups.size());
        View child=view.inflate(context,R.layout.contacts_expand_child_item,null);
        TextView gId= (TextView) child.findViewById(R.id.contacts_expand_child_item_gid);
        TextView gName= (TextView) child.findViewById(R.id.contacts_expand_child_item_name);
        if(i1!=0){
            gId.setText(groups.get(i1-1).getTgId()+"");
            gName.setText(groups.get(i1-1).getTgName()+"");
        }
        return child;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
