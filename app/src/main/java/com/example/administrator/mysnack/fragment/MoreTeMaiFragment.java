package com.example.administrator.mysnack.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
import com.example.administrator.mysnack.adapter.MoreTeMaiRecyclerViewAdapter;
import com.example.administrator.mysnack.app.MyApp;
import com.example.administrator.mysnack.entity.HeadDetailEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreTeMaiFragment extends Fragment {
    private static final String TAG = MoreTeMaiFragment.class.getSimpleName();
    private String path="http://api.ds.lingshi.cccwei.com/?cid=802877&uid=194747&tms=20160729001143&sig=5145d04e10e87e9a&wssig=1c6c6c19148f6ad6&os_type=3&version=23&channel_name=qihoo&srv=2301&special_type=%d&since_id=0&pg_cur=1&pg_size=20";
    private RecyclerView recyclerView;
    private List<HeadDetailEntity>data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        data=new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_more_te_mai, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_moreTeMai);
        Bundle bundle = getArguments();
        int type = bundle.getInt("type");
        Log.e(TAG, "=======MoreTeMaiFragment===onCreateView: "+type );
        initData(type);
        return view;
    }

    private void initData(int type) {
        path=String .format(path,type);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, path, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    Log.e(TAG, "=========MoreTeMaiFragment====onResponse: "+response );
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
                    Log.e(TAG, "========MoreTeMaiFragment====onResponse: " + data.toString());
                    LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    recyclerView.setLayoutManager(layoutManager);
                    MoreTeMaiRecyclerViewAdapter adapter=new MoreTeMaiRecyclerViewAdapter(getActivity(),data);
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
