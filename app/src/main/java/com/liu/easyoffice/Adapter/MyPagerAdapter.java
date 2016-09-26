package com.liu.easyoffice.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.liu.easyoffice.Fragment.MsgFragment;
import com.liu.easyoffice.Fragment.ContactsFragment;
import com.liu.easyoffice.Fragment.WorkFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hui on 2016/9/14.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList=new ArrayList<>();
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentList.add(new MsgFragment());
        fragmentList.add(new WorkFragment());
        fragmentList.add(new ContactsFragment());
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

}
