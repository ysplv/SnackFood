package com.example.administrator.mysnack.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TeMaiActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = TeMaiActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private String path = "http://api.ds.lingshi.cccwei.com/?cid=109&uid=0&tms=20160716050320&sig=db9ea4cdb25ca509&wssig=03f9f0756a5d59c7&os_type=3&version=24&channel_name=feibo&srv=2407&pg_cur=1&pg_size=20&subject_id=%d&since_id=0";
    private List<HeadDetailEntity> data;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_te_mai);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_teMai);
        ivBack = (ImageView) findViewById(R.id.iv_back_teMai);
        ivBack.setOnClickListener(this);//返回的监听
        data = new ArrayList<>();
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        initData(id);
    }

    private void initData(int id) {
        String path1 = String.format(path, id);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, path1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    // Log.e(TAG, "========onResponse: " + response);
                    try {
                        JSONObject jo1 = new JSONObject(response);
                        JSONObject jo2 = jo1.getJSONObject("data");
                        JSONArray ja1 = jo2.getJSONArray("items");
                        for (int i = 0; i < ja1.length(); i++) {
                            HeadDetailEntity entity = new HeadDetailEntity();
                            JSONObject jo3 = (JSONObject) ja1.get(i);
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
                    GridLayoutManager layoutManager = new GridLayoutManager(TeMaiActivity.this, 2);
                    recyclerView.setLayoutManager(layoutManager);
                    HeadRecyclerVeiwAdapter adapter = new HeadRecyclerVeiwAdapter(TeMaiActivity.this, data);
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
//返回的监听
    @Override
    public void onClick(View view) {
        finish();
    }
}
