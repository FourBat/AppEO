package com.liu.easyoffice.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liu.easyoffice.R;
import com.liu.easyoffice.pojo.Apply;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hui on 2016/9/19.
 */
public class MyApplyActivity extends Activity {
    public final String TAG = "MyApply";
    private ListView myApplyLv;
    public final static String MYAPPLY_URL = "http://10.40.5.50:8080/Xutils/myapply";
    private List<Apply> applies = new ArrayList<>();
    private SimpleAdapter simpleAdapter;
    List<Map<String, Object>> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_myapply_main);
        init();

        getJsonFromNet();

        String[] froms = {"applyKind", "applyTitle", "applyContent", "applyTime"};
        int[] ids = {R.id.apply_list_item_iv, R.id.apply_list_item_title, R.id.apply_list_item_content, R.id.apply_list_item_time};
        simpleAdapter = new SimpleAdapter(this, list, R.layout.apply_list_item, froms, ids);
        myApplyLv.setAdapter(simpleAdapter);
    }

    private void init() {
        myApplyLv = ((ListView) findViewById(R.id.apply_myapply_lv));
    }

    public void getJsonFromNet() {
        RequestParams params = new RequestParams(MYAPPLY_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                Gson gson = new Gson();
                applies = gson.fromJson(result, new TypeToken<List<Apply>>() {
                }.getType());
                for (Apply apply : applies) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("applyKind", getImg(apply.getApplyKind()));
                    map.put("applyTitle", apply.getApplyTitle());
                    map.put("applyContent", apply.getApplyContent());
                    map.put("applyTime", apply.getDate());
                    list.add(map);
                }
                simpleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(TAG, ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private int getImg(int applyKind) {
        int imgId = R.mipmap.emotion_003;
        switch (applyKind) {
            case 0:
                imgId = R.mipmap.emotion_003;
                break;
            case 1:
                imgId = R.mipmap.emotion_004;
                break;
            case 2:
                imgId = R.mipmap.emotion_005;
                break;
        }
        return imgId;
    }
}
