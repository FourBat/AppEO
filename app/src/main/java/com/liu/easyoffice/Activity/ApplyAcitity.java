package com.liu.easyoffice.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewParent;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;

import com.liu.easyoffice.Fragment.WorkFragment;
import com.liu.easyoffice.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hui on 2016/9/18.
 */
public class ApplyAcitity extends Activity implements View.OnClickListener{
    private GridView applyGv;
    private int[] icons={R.mipmap.icon_qiandao,R.mipmap.icon_rizhi,R.mipmap.icon_shenpi,R.mipmap.icon_team_manager};
    private String[] names={"请假","报销","出差","补签"};
    private ImageView applyIvBack;
    private RadioButton rbtnMyApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_main);
        init();
        initGridView();
        onclick();
        backToWork();
    }

    private void initGridView() {
        List<Map<String,Object>> grids=new ArrayList<>();
        for (int i = 0; i <icons.length ; i++) {
            Map<String,Object> item=new HashMap<>();
            item.put("grid_item_icon",icons[i]);
            item.put("grid_item_name",names[i]);
            grids.add(item);
        }
        SimpleAdapter gridAdapter=new SimpleAdapter(this,grids,R.layout.work_grid_item,new String[]{"grid_item_icon","grid_item_name"},new int[]{R.id.work_grid_item_iv,R.id.work_grid_item_tv});
        applyGv.setAdapter(gridAdapter);
    }

    /**
     * 初始化控件
     */
    private void init() {
        applyGv = ((GridView) findViewById(R.id.apply_grid));
        applyIvBack = ((ImageView) findViewById(R.id.apply_iv_back));
        rbtnMyApply = ((RadioButton) findViewById(R.id.apply_rb_myApply));
    }

    /**
     * 各个空间点击事件
     */
    private void onclick(){
        rbtnMyApply.setOnClickListener(this);
    }
    /**
     * 退回到上一个页面
     */
    private void backToWork(){
        applyIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        System.out.println(v.getId());
        switch (v.getId()){
            case R.id.apply_rb_myApply:
                Intent intent=new Intent(this,MyApplyActivity.class);
                startActivity(intent);
                break;
            case R.id.apply_rb_receive:
                break;
        }
    }
}
