package com.example.administrator.mysnack.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.mysnack.R;
import com.example.administrator.mysnack.adapter.ListViewAdapterSubject;
import com.example.administrator.mysnack.app.MyApp;
import com.example.administrator.mysnack.entity.HeaderImage;
import com.example.administrator.mysnack.entity.SubjectBody;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubjectFragment extends Fragment {
    private int images[] = {R.id.image_left_top_subject, R.id.image_right_top_subject, R.id.image_left_bottom_subject, R.id.image_right_bottom_subject};
    private List<HeaderImage.DataBean.ItemsBean> headerImages;
    private String header_url = "http://api.ds.lingshi.cccwei.com/?cid=109&uid=0&tms=20160716034330&sig=60d1116c1a6206ed&wssig=f6ffa509d8352f5c&os_type=3&version=24&channel_name=feibo&srv=2401http://api.ds.lingshi.cccwei.com/?cid=109&uid=0&tms=20160716034330&sig=60d1116c1a6206ed&wssig=f6ffa509d8352f5c&os_type=3&version=24&channel_name=feibo&srv=2401";
    private List<SubjectBody.DataBean.ItemsBean>list;
    private ListViewAdapterSubject adapter;
    private String body_url="http://api.ds.lingshi.cccwei.com/?cid=109&uid=0&tms=20160716034738&sig=2d6fdfe037ec4ebe&wssig=225e4c862bf99fbf&os_type=3&version=24&channel_name=feibo&srv=2405&pg_cur=1&pg_size=20&since_id=0";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_subject, container, false);
        ScrollView scrollView = (ScrollView) view.findViewById(R.id.scrollView_subject);
        scrollView.smoothScrollTo(0,0);
        ListView listView = (ListView) view.findViewById(R.id.listView_subject);

        list=new ArrayList<>();
        adapter=new ListViewAdapterSubject(list,getActivity());
        listView.setAdapter(adapter);
        initData(view);
        return view;
    }

    private void initData(final View view) {
        headerImages = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, header_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                List<HeaderImage.DataBean.ItemsBean> items = new Gson().fromJson(response, HeaderImage.class).getData().getItems();
                headerImages.addAll(items);
                initView(view);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setTag("qx");
        MyApp.getQueue().add(stringRequest);
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, body_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<SubjectBody.DataBean.ItemsBean> items = new Gson().fromJson(response, SubjectBody.class).getData().getItems();
                list.addAll(items);
                adapter.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest1.setTag("qx");
        MyApp.getQueue().add(stringRequest1);

    }

    private void initView(View view) {
        if (headerImages == null || headerImages.size() < 4) {
            return;
        }
        for (int i = 0; i < 4; i++) {
            final ImageView imageView = (ImageView) view.findViewById(images[i]);
            ImageRequest imageRequest = new ImageRequest(headerImages.get(i).getImg().getImg_url(), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    imageView.setImageBitmap(response);
                }
            }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            imageRequest.setTag("qx");
            MyApp.getQueue().add(imageRequest);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyApp.getQueue().cancelAll("qx");
    }
}
