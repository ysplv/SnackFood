package com.example.administrator.mysnack.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.example.administrator.mysnack.activity.HeadDetailActivity;
import com.example.administrator.mysnack.R;
import com.example.administrator.mysnack.app.MyApp;
import com.example.administrator.mysnack.utils.MyImageCache;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeadImageFragment extends Fragment implements View.OnClickListener {

    private ImageView iv;
    private String info;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_head_image, container, false);
        iv = (ImageView) view.findViewById(R.id.iv_head_fragment);
        Bundle bundle = getArguments();
        String url = bundle.getString("url");//图片网址
        info = bundle.getString("info");
        initImageLoader(url);
        iv.setOnClickListener(this);//点击图片的监听

        return view;
    }

    private void initImageLoader(String url) {
        //创建imageLoader对象
        ImageLoader imageLoader = new ImageLoader(MyApp.getQueue(), new MyImageCache());
        //imageLoader的监听
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(iv, R.drawable.default_class_brand612_250, R.drawable.default_class_brand612_250);
        imageLoader.get(url, listener, 0, 0);

    }

    //点击图片的监听
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(getActivity(), HeadDetailActivity.class);
        intent.putExtra("info",info);
        startActivity(intent);

    }
}
