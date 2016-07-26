package com.example.administrator.mysnack.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.mysnack.R;
import com.example.administrator.mysnack.app.MyApp;
import com.example.administrator.mysnack.url.Url;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private List<Fragment> fragments;//存放fragment的集合
    private ViewPager viewPager;
    private LinearLayout layout;
    private List<ImageView> imageViews;//小点的集合

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);//初始化控件
        initData();//初始化数据
        initDot();//初始化小点
        initFragment();//初始化Fragment
        return view;
    }

    //初始化Fragment
    private void initFragment() {
    }

    //初始化小点
    private void initDot() {
        imageViews=new ArrayList<>();//小点的集合
        for (int i = 0; i < 4; i++) {
            ImageView image = (ImageView) layout.getChildAt(i);
            image.setEnabled(true);
            image.setOnClickListener(this);//给每个小点设置监听
            imageViews.add(image);
        }
        imageViews.get(0).setEnabled(false);//默认第一个选中
    }

    //初始化数据
    private void initData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url.pathHead, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    Gson gson=new Gson();
                    Log.e(TAG, "=====onResponse=======: "+response );
//                    HeadEntity headEntity = gson.fromJson(response, HeadEntity.class);
//                    List<HeadEntity.DataBean.TopicsBean> topicsList = headEntity.getData().getTopics();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setTag("qx");//设置取消标记
        MyApp.getQueue().add(stringRequest);//放入请求队列

    }

    //初始化控件
    private void initView(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.viewPager_home);
        layout = (LinearLayout) view.findViewById(R.id.layout_dot_home);

    }

    @Override
    public void onClick(View view) {
        int index = layout.indexOfChild(view);//得到点击的小点的索引
        for (int i = 0; i < 4; i++) {
            imageViews.get(i).setEnabled(true);
        }
        imageViews.get(index).setEnabled(false);
        viewPager.setCurrentItem(index);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyApp.getQueue().cancelAll("qx");//销毁
    }
}
