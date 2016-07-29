package com.example.administrator.mysnack.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.mysnack.R;
import com.example.administrator.mysnack.adapter.HeadRecyclerVeiwAdapter;
import com.example.administrator.mysnack.app.MyApp;
import com.example.administrator.mysnack.entity.HeadDetailEntity;
import com.example.administrator.mysnack.utils.SpaceItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HeadDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = HeadDetailActivity.class.getSimpleName();
    private String path = "http://api.ds.lingshi.cccwei.com/?cid=109&uid=0&tms=20160716033019&sig=ee81e03d7332f8d6&wssig=78819cff549a86fd&os_type=3&version=24&channel_name=feibo&srv=2407&pg_cur=1&pg_size=20&subject_id=%d&since_id=0";
    private RecyclerView recyclerView;
    private List<HeadDetailEntity> data;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_detail);
        data = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_headDetail);
        ivBack = (ImageView) findViewById(R.id.iv_back_headDetail);
        ivBack.setOnClickListener(this);
        Intent intent = getIntent();
        String info = intent.getStringExtra("info");
        int info1 = Integer.parseInt(info);
        initData(info1);
    }

    private void initData(int info) {
        String path1 = String.format(path, info);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, path1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                   // Log.e(TAG, "========onResponse: " + response);
                    try {
                                JSONObject jo1=new JSONObject(response);
                                JSONObject jo2=jo1.getJSONObject("data");
                                JSONArray ja1=jo2.getJSONArray("items");
                                for (int i = 0; i <ja1.length() ; i++) {
                                    HeadDetailEntity entity=new HeadDetailEntity();
                                    JSONObject jo3= (JSONObject) ja1.get(i);
                            entity.setTitle(jo3.getString("title"));
                            entity.setImg_url(jo3.getJSONObject("img").getString("img_url"));
                            entity.setCurrent(jo3.getJSONObject("price").getDouble("current"));
                            entity.setPrime(jo3.getJSONObject("price").getDouble("prime"));
                            data.add(entity);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e(TAG, "============onResponse: " + data.toString());
                    GridLayoutManager layoutManager=new GridLayoutManager(HeadDetailActivity.this,2);
                    recyclerView.setLayoutManager(layoutManager);
                    HeadRecyclerVeiwAdapter adapter=new HeadRecyclerVeiwAdapter(HeadDetailActivity.this,data);
                    recyclerView.addItemDecoration(new SpaceItemDecoration(10));

                    recyclerView.setAdapter(adapter);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setTag("qx");//设置取消标记
        MyApp.getQueue().add(stringRequest);//加入缓存队列
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApp.getQueue().cancelAll("qx");//销毁
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
