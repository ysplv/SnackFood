package com.example.administrator.mysnack.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

/**
 * Created by Administrator on 2016/7/28 0028.
 */
public class DrawLayoutFragment extends Fragment {
    private static final String TAG = DrawLayoutFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private String path = "http://api.ds.lingshi.cccwei.com/?cid=109&uid=0&tms=20160716043753&sig=557eca9cb6300fd5&wssig=9572b8cf7ef3a816&os_type=3&version=24&channel_name=feibo&srv=2406&pg_cur=1&pg_size=20&sub_id=%d&parent_id=32&since_id=0";
    private List<HeadDetailEntity>data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawlayout, container, false);
        data = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_drawlayout);
        Bundle bundle = getArguments();
        int id = bundle.getInt("id");
        Log.e(TAG, "======onCreateView=====+DrawLayoutFragment: "+id );
        initData(id);//初始化数据
        return view;
    }

   private void initData(int id) {
       String  path1 = String.format(path, id);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, path1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    Log.e(TAG, "====DrawLayoutFragment=====onResponse: "+response );
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
                    Log.e(TAG, "============DrawLayoutFragmentonResponse: " + data.toString());
                    GridLayoutManager layoutManager=new GridLayoutManager(getActivity(),2);
                    recyclerView.setLayoutManager(layoutManager);
                    HeadRecyclerVeiwAdapter adapter=new HeadRecyclerVeiwAdapter(getActivity(),data);
                    recyclerView.setAdapter(adapter);

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyApp.getQueue().cancelAll("qx");
    }
}
