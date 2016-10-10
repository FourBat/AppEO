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
    MsgFragment msgFragment=MsgFragment.getInstance();
    WorkFragment workFragment=WorkFragment.getInstance();
    ContactsFragment contactsFragment=ContactsFragment.getInstance();
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return msgFragment;
            case 1:
                return workFragment;
            case 2:
                return contactsFragment;
        }
      return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

}
