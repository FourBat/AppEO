package com.liu.easyoffice.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;

import com.liu.easyoffice.Activity.ApplyAcitity;
import com.liu.easyoffice.MyView.MyViewFlipper;
import com.liu.easyoffice.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hui on 2016/9/14.
 */
public class WorkFragment extends Fragment {
    private static WorkFragment instance=null;
    public  static WorkFragment getInstance(){
        if (instance!=null){
            return instance;
        }else {
            return new WorkFragment();
        }
    }
    private MyViewFlipper workFlipper;
    private RadioGroup workRg;
    private GridView gridView;
    private int[] imgs = {R.mipmap.ad1, R.mipmap.ad2};
    private int[] icons = {R.mipmap.icon_qiandao, R.mipmap.icon_rizhi, R.mipmap.icon_shenpi, R.mipmap.icon_team_manager};
    private String[] names = {"签到", "任务", "审批", "团队管理"};

    /**
     * 初始化控件
     */
    private void init(View view) {
        workFlipper = ((MyViewFlipper) view.findViewById(R.id.work_flip));
        workRg = ((RadioGroup) view.findViewById(R.id.work_rg));
        gridView = ((GridView) view.findViewById(R.id.work_grid));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.work_fragment, null);
        init(view);
        for (int i = 0; i < imgs.length; i++) {
            ImageView iv = new ImageView(getActivity());
            iv.setBackgroundResource(imgs[i]);
            workFlipper.addView(iv);
            RadioButton rbtn = (RadioButton) LayoutInflater.from(getActivity()).inflate(R.layout.msg_lunbo_rbtn_style, workRg, false);
            workRg.addView(rbtn);
        }
        ((RadioButton) workRg.getChildAt(0)).setChecked(true);
        workFlipper.startFlipping();
//        设置监听事件
        workFlipper.setOnPageChangeListener(new MyViewFlipper.OnPagerChangeListener() {
            @Override
            public void onPageChanged(int index) {
                ((RadioButton) workRg.getChildAt(index)).setChecked(true);
            }
        });
        /**
         * gridView 设置
         */
        List<Map<String, Object>> grids = new ArrayList<>();
        for (int i = 0; i < icons.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("grid_item_icon", icons[i]);
            item.put("grid_item_name", names[i]);
            grids.add(item);
        }
        SimpleAdapter gridAdapter = new SimpleAdapter(getActivity(), grids, R.layout.work_grid_item, new String[]{"grid_item_icon", "grid_item_name"}, new int[]{R.id.work_grid_item_iv, R.id.work_grid_item_tv});
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        Intent intent=new Intent(getContext(), ApplyAcitity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        break;
                }
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("OnCreate++");
    }

    @Override
    public void onResume() {
        super.onResume();
        workFlipper.startFlipping();

    }

    @Override
    public void onStop() {
        workFlipper.stopFlipping();
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("ondestroy++");
        workFlipper.stopFlipping();
    }
}
