package com.example.administrator.mysnack.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.mysnack.HeadDetailActivity;
import com.example.administrator.mysnack.R;
import com.example.administrator.mysnack.TeMaiActivity;
import com.example.administrator.mysnack.adapter.HeadFragmentAdapter;
import com.example.administrator.mysnack.app.MyApp;
import com.example.administrator.mysnack.entity.HeadEntity;
import com.example.administrator.mysnack.url.Url;
import com.example.administrator.mysnack.utils.MyImageCache;
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
    private TextView tvTeMai;
    private TextView tvUpDate;
    private TextView tvZheKou;
    private TextView tvTime;
    private TextView tvTitle;
    private ImageView ivTeMai;
    private ImageView ivSpecials1, ivSpecials2, ivSpecials3, ivSpecials4;
    private TextView newTitleBig;
    protected TextView newTiteSml;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);//初始化控件
        initData();//初始化数据
        initDot();//初始化小点
        return view;
    }

    //初始化Fragment
    private void initFragment(String imgUrl, String info) {
        HeadImageFragment fragment = new HeadImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", imgUrl);
        bundle.putString("info", info);
        fragment.setArguments(bundle);
        fragments.add(fragment);

    }

    //初始化小点
    private void initDot() {
        imageViews = new ArrayList<>();//小点的集合
        for (int i = 0; i < 4; i++) {
            ImageView image = (ImageView) layout.getChildAt(i);
            image.setSelected(true);
            image.setOnClickListener(this);//给每个小点设置监听
            imageViews.add(image);
        }
        imageViews.get(0).setSelected(false);//默认第一个选中
    }

    //初始化数据
    private void initData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url.pathHead, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    Gson gson = new Gson();
                    // Log.e(TAG, "=====onResponse=======: "+response );
                    HeadEntity headEntity = gson.fromJson(response, HeadEntity.class);
                    List<HeadEntity.DataBean.TopicsBean> topicsList = headEntity.getData().getTopics();//头集合
                    fragments = new ArrayList<>();//fragment集合
                    for (int i = 0; i < 4; i++) {
                        String imgUrl = topicsList.get(i).getImg().getImg_url();
                        String info = topicsList.get(i).getAction().getInfo();
                        initFragment(imgUrl, info);
                    }
                    initViewPager();
                    //特卖
                    String brands_title = headEntity.getData().getBrands_title_big();
                    tvTeMai.setText(brands_title);
                    String brands_sml = headEntity.getData().getBrands_title_sml();
                    tvUpDate.setText(brands_sml);
                    List<HeadEntity.DataBean.BrandsBean> brands = headEntity.getData().getBrands();
                    String discount = brands.get(0).getDiscount();
                    tvZheKou.setText(discount);
                    int time = brands.get(0).getTime();
                    tvTime.setText("仅剩" + time);
                    String title = brands.get(0).getTitle();
                    tvTitle.setText(title);
                    String img_url = brands.get(0).getImg().getImg_url();
                    final int id = brands.get(0).getId();
                    ivTeMai.setOnClickListener(new View.OnClickListener() {//图片的监听
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), TeMaiActivity.class);
                            intent.putExtra("id", id);
                            startActivity(intent);

                        }
                    });

                    //下载图片
                    ImageLoader imageLoader = new ImageLoader(MyApp.getQueue(), new MyImageCache());
                    ImageLoader.ImageListener listener = ImageLoader.getImageListener(ivTeMai, R.drawable.default_projects_small300_150, R.drawable.default_projects_small300_150);
                    imageLoader.get(img_url, listener, 0, 0);
                    //特别
                    List<HeadEntity.DataBean.SpecialsBean> specials = headEntity.getData().getSpecials();
                    String img_url1 = specials.get(0).getImg().getImg_url();
                    final String info1 = specials.get(0).getAction().getInfo();
                    initImageLoader(ivSpecials1, img_url1);
                    ivSpecials1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), HeadDetailActivity.class);
                            intent.putExtra("info", info1);
                            startActivity(intent);
                        }
                    });
                    String img_url2 = specials.get(1).getImg().getImg_url();
                    final String info2 = specials.get(1).getAction().getInfo();
                    initImageLoader(ivSpecials2, img_url2);
                    ivSpecials2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), HeadDetailActivity.class);
                            intent.putExtra("info", info2);
                            startActivity(intent);
                        }
                    });
                    String img_url3 = specials.get(2).getImg().getImg_url();
                    final String info3= specials.get(2).getAction().getInfo();
                    initImageLoader(ivSpecials3, img_url3);
                    ivSpecials3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), HeadDetailActivity.class);
                            intent.putExtra("info", info3);
                            startActivity(intent);
                        }
                    });
                    String img_url4 = specials.get(3).getImg().getImg_url();
                    final String info4 = specials.get(3).getAction().getInfo();
                    initImageLoader(ivSpecials4, img_url4);
                    ivSpecials4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), HeadDetailActivity.class);
                            intent.putExtra("info", info4);
                            startActivity(intent);
                        }
                    });
                    //好吃到爆
                    String new_title_big = headEntity.getData().getNew_title_big();
                    newTitleBig.setText(new_title_big);
                    String new_title_sml = headEntity.getData().getNew_title_sml();
                    newTiteSml.setText(new_title_sml);
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

    //图片下载
    private void initImageLoader(ImageView ivSpecials, String img_url) {
        ImageLoader imageLoader = new ImageLoader(MyApp.getQueue(), new MyImageCache());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(ivSpecials, R.drawable.default_projects_small300_150, R.drawable.default_projects_small300_150);
        imageLoader.get(img_url, listener, 0, 0);
    }

    private void initViewPager() {
        HeadFragmentAdapter adapter = new HeadFragmentAdapter(getActivity().
                getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < 4; i++) {
                    imageViews.get(i).setSelected(true);
                }
                imageViews.get(position).setSelected(false);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //初始化控件
    private void initView(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.viewPager_home);
        layout = (LinearLayout) view.findViewById(R.id.layout_dot_home);
        tvTeMai = (TextView) view.findViewById(R.id.tv_temai_home);
        tvUpDate = (TextView) view.findViewById(R.id.tv_update_home);
        tvZheKou = (TextView) view.findViewById(R.id.tv_zhekou_home);
        tvTime = (TextView) view.findViewById(R.id.tv_time_home);
        tvTitle = (TextView) view.findViewById(R.id.tv_title_home);
        ivTeMai = (ImageView) view.findViewById(R.id.iv_temai_home);
        ivSpecials1 = (ImageView) view.findViewById(R.id.iv_specials1_home);
        ivSpecials2 = (ImageView) view.findViewById(R.id.iv_specials2_home);
        ivSpecials3 = (ImageView) view.findViewById(R.id.iv_specials3_home);
        ivSpecials4 = (ImageView) view.findViewById(R.id.iv_specials4_home);
        newTitleBig = (TextView)view.findViewById(R.id.tv_newTitleBig_home);
        newTiteSml = (TextView) view.findViewById(R.id.tv_newTitleSml_home);

    }

    //小点的监听事件
    @Override
    public void onClick(View view) {
        int index = layout.indexOfChild(view);//得到点击的小点的索引
        for (int i = 0; i < 4; i++) {
            imageViews.get(i).setSelected(true);
        }
        imageViews.get(index).setSelected(false);
        viewPager.setCurrentItem(index);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyApp.getQueue().cancelAll("qx");//销毁
    }
}
