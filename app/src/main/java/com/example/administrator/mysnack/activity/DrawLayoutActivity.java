package com.example.administrator.mysnack.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.mysnack.R;
import com.example.administrator.mysnack.adapter.DrawlayoutFragmentPagerAdapter;
import com.example.administrator.mysnack.app.MyApp;
import com.example.administrator.mysnack.entity.DrawTitelEntity;
import com.example.administrator.mysnack.fragment.DrawLayoutFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DrawLayoutActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = DrawLayoutActivity.class.getSimpleName();
    protected TextView title;
    protected String title1;
    protected int id1;
    private String pathTitle = "http://api.ds.lingshi.cccwei.com/?cid=109&uid=0&tms=20160716043752&sig=afe38cbc78d23d9a&wssig=bc1fa44ee0cf7367&os_type=3&version=24&channel_name=feibo&srv=2402&classify_id=%d";
    protected ImageView ivBack;
    private String path;
    private List<DrawTitelEntity> data;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_layout);
        data = new ArrayList<>();
        fragments=new ArrayList<>();
        initVeiw();//初始化控件
        initIntent();//Intent传过来的值
        initData();//初始化数据
    }

    //初始化数据
    private void initData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, path, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    //Log.e(TAG, "=====onResponse===pathTitle===: " + response);
                    try {
                        JSONObject jo1 = new JSONObject(response);
                        JSONObject jo2 = jo1.getJSONObject("data");
                        JSONArray ja1 = jo2.getJSONArray("items");
                        DrawTitelEntity entity = new DrawTitelEntity();
                        entity.setId(0);
                        entity.setTitle("全部");
                        data.add(entity);
                        for (int i = 0; i < ja1.length(); i++) {
                            DrawTitelEntity entity1 = new DrawTitelEntity();
                            JSONObject jo3 = ja1.getJSONObject(i);
                            entity1.setId(jo3.getInt("id"));
                            entity1.setTitle(jo3.getString("title"));
                            data.add(entity1);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    initTitle();    //添加页卡标题
                    initFragment();//添加页视图
                    DrawlayoutFragmentPagerAdapter adapter = new DrawlayoutFragmentPagerAdapter(getSupportFragmentManager(), fragments, data);//Fragment适配器
                    viewPager.setAdapter(adapter);
                    tabLayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来
                    tabLayout.setTabsFromPagerAdapter(adapter);//给Tabs设置适配器
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setTag("qx");
        MyApp.getQueue().add(stringRequest);

    }

    //添加页视图
    private void initFragment() {
        for (int i = 0; i < data.size(); i++) {
          DrawLayoutFragment  fragment = new DrawLayoutFragment();
            int id = data.get(i).getId();
            Log.e(TAG, "===id====initFragment===: "+id );
            Bundle bundle1 = new Bundle();
            bundle1.putInt("id", id);
            fragment.setArguments(bundle1);
            fragments.add(fragment);
        }
    }

    //添加页卡标题
    private void initTitle() {
        for (int i = 0; i < data.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(data.get(i).getTitle()));
        }
    }

    //Intent传过来的值
    private void initIntent() {
        Intent intent = getIntent();
        title1 = intent.getStringExtra("title1");
        title.setText(title1);
        id1 = intent.getIntExtra("id1", 0);
        path = String.format(pathTitle, id1);

    }

    //初始化控件
    private void initVeiw() {
        title = (TextView) findViewById(R.id.tv_titleName_drawlayout);
        ivBack = (ImageView) findViewById(R.id.iv_back_drawLayout);
        ivBack.setOnClickListener(this);//返回的监听
        tabLayout = (TabLayout) findViewById(R.id.tablayout_drawlayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager_drawlayout);
    }

    @Override
    public void onClick(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApp.getQueue().cancelAll("qx");
    }
}
