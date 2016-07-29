package com.example.administrator.mysnack.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.mysnack.R;
import com.example.administrator.mysnack.activity.DrawLayoutActivity;
import com.example.administrator.mysnack.activity.HaochiWebViewActivity;
import com.example.administrator.mysnack.activity.HeadDetailActivity;
import com.example.administrator.mysnack.activity.TeMaiActivity;
import com.example.administrator.mysnack.adapter.HeadFragmentAdapter;
import com.example.administrator.mysnack.adapter.ListViewAdapterDrawlayout;
import com.example.administrator.mysnack.adapter.MyListViewAdapter;
import com.example.administrator.mysnack.app.MyApp;
import com.example.administrator.mysnack.entity.HeadDetailEntity;
import com.example.administrator.mysnack.entity.HeadEntity;
import com.example.administrator.mysnack.url.Url;
import com.example.administrator.mysnack.utils.MyImageCache;
import com.example.administrator.mysnack.utils.TiemTool;
import com.example.administrator.mysnack.view.InnerListView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    protected InnerListView lvHaoChi;
    private String path = "http://api.ds.lingshi.cccwei.com/?cid=109&uid=0&tms=20160716043427&sig=e0860e43ba43e817&wssig=f8082da2737d5a0d&os_type=3&version=24&channel_name=feibo&srv=2206&since_id=0&pg_cur=1&pg_size=20";
    private List<HeadDetailEntity> data;
    protected ScrollView srollView;
    private int time;
    private Fragment[]fragmentArray=new Fragment[2];

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                   time-=1000;
                    String difference = TiemTool.getDifference(time);//倒计时
                    tvTime.setText("仅剩" + difference);
                    break;
                case 2:
                    tvTime.setText("倒计时结束");
                    break;
                case 1:
                    int j = msg.arg1;
                    if (imageViews.size() == 4) {
                        viewPager.setCurrentItem(j);
                        imageViews.get(j).setEnabled(false);
                    }

                    break;
            }

        }

        ;
    };
    protected ImageView ivDrawBack;
    protected DrawerLayout drawerLayout;
    protected LinearLayout layout1;
    protected ListView lvDrawlayout;
    private FrameLayout frameLayout;
    private LinearLayout reMaiLayout1;
    private LinearLayout reMaiLayout2;
    private TextView tvReMail;
    private TextView tvReMai2;
    private TextView tvLine1;
    private TextView tvLine2;
    private Fragment currentFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);//初始化控件
        initData();//初始化数据
        initDot();//初始化小点
        initData1();//初始化ListView数据

        new Thread() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                super.run();
                int i = 1;
                while (true) {
                    i = i % 2;

                    Message msg = new Message();
                    msg.what = 1;
                    msg.arg1 = i;

                    handler.sendMessage(msg);
                    try {
                        Thread.sleep(3000);
                        i++;

                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            }
        }.start();
        return view;
    }

    private void initData1() {
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, path, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    // Log.e(TAG, "========onResponse====2===: " + response);
                    data = new ArrayList<>();
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
                            entity.setId(jo3.getInt("id"));
                            data.add(entity);
                        }
                        Log.e(TAG, "========onResponse====data===: " + data.size() + "=====" + data.toString());
                        MyListViewAdapter adapter = new MyListViewAdapter(getActivity(), data);
                        lvHaoChi.setAdapter(adapter);
                        lvHaoChi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(getActivity(), HaochiWebViewActivity.class);
                                HeadDetailEntity headDetatilEntity = data.get(i);
                                int id = headDetatilEntity.getId();
                                Log.e(TAG, "========onItemClick: " + id);
                                intent.putExtra("id", id);
                                startActivity(intent);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest1.setTag("quxiao");
        MyApp.getQueue().add(stringRequest1);
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
                    Log.e(TAG, "=====onResponse====A====: " + topicsList.size());
                    for (int i = 0; i < topicsList.size(); i++) {

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
                    time = brands.get(0).getTime();
//                    time=7000;
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            while (true) {
                                Message msg = handler.obtainMessage();
                                if (time==0){
                                    msg.what=2;
                                    handler.sendMessage(msg);
                                    break;
                                }
                                msg.what = 0;
                                handler.sendMessage(msg);
                                try {
                                    Thread.sleep(1000);


                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }

                        }
                    }.start();

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
                    final String info3 = specials.get(2).getAction().getInfo();
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
                    //抽屉
                    final List<HeadEntity.DataBean.ClassifiesBean> classifies = headEntity.getData().getClassifies();
                    ListViewAdapterDrawlayout adapterDrawlayout = new ListViewAdapterDrawlayout(getActivity(), classifies);
                    lvDrawlayout.setAdapter(adapterDrawlayout);
                    lvDrawlayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            int id1 = classifies.get(i).getId();
                            String title1 = classifies.get(i).getTitle();
                            Intent intent = new Intent(getActivity(), DrawLayoutActivity.class);
                            intent.putExtra("id1", id1);
                            intent.putExtra("title1", title1);
                            startActivity(intent);
                        }
                    });
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
        newTitleBig = (TextView) view.findViewById(R.id.tv_newTitleBig_home);
        newTiteSml = (TextView) view.findViewById(R.id.tv_newTitleSml_home);
        lvHaoChi = (InnerListView) view.findViewById(R.id.lv_haochi_home);
        srollView = (ScrollView) view.findViewById(R.id.scrollView_home);
        frameLayout = (FrameLayout)view.findViewById(R.id.frameLayout_home);//放碎片
        for (int i = 0; i < 2; i++) {
            MoreTeMaiFragment fragment=new MoreTeMaiFragment();
            Bundle  bundle=new Bundle();
            bundle.putInt("type",1-i);
            fragment.setArguments(bundle);
            fragmentArray[i]=fragment;
        }
        currentFragment=fragmentArray[0];
        //默认显示第一个
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_home,fragmentArray[0]).commit();
        tvReMail = (TextView)view.findViewById(R.id.tv_remai1_home);
        tvReMail.setTextColor(0xffff2d4b);
        tvReMai2 = (TextView)view.findViewById(R.id.tv_remai2_home);
        tvLine1 = (TextView)view.findViewById(R.id.tv_line1_home);
        tvLine1.setBackgroundColor(0xffff2d4b);
        tvLine2 = (TextView)view.findViewById(R.id.tv_line2_home);
        reMaiLayout1= (LinearLayout)view.findViewById(R.id.liner1_remai_home);
        reMaiLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTvTeMai();
                tvReMail.setTextColor(0xffff2d4b);
                tvLine1.setBackgroundColor(0xffff2d4b);
                switchFragment(currentFragment,fragmentArray[0]);


            }
        });
        reMaiLayout2 = (LinearLayout)view.findViewById(R.id.liner2_remai_home);
        reMaiLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTvTeMai();
                tvReMai2.setTextColor(0xffff2d4b);
                tvLine2.setBackgroundColor(0xffff2d4b);
                switchFragment(currentFragment,fragmentArray[1]);
            }
        });
        srollView.smoothScrollTo(0, 0);
        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawlayout_home);
        layout1 = (LinearLayout) view.findViewById(R.id.linearlayout_drawlayout_home);
        ivDrawBack = (ImageView) view.findViewById(R.id.iv_launch_homeFragment);//抽屉按钮
        ivDrawBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(layout1);

            }
        });
        lvDrawlayout = (ListView) view.findViewById(R.id.listView_drawlayout_home);
    }
    //fragment切换
    public void switchFragment(Fragment from,Fragment to){
        if (from==to){
            return;
        }
        if (!to.isAdded()){
            getActivity().getSupportFragmentManager().beginTransaction().hide(from).add(R.id.frameLayout_home,to).commit();
        }else {
            getActivity().getSupportFragmentManager().beginTransaction().hide(from).show(to).commit();
        }
        currentFragment=to;
    }
//更多热卖
    public void changeTvTeMai(){
        tvReMail.setTextColor(0xff000000);
        tvReMai2.setTextColor(0xff000000);
        tvLine1.setBackgroundColor(0xffffffff);
        tvLine2.setBackgroundColor(0xffffffff);
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
        MyApp.getQueue().cancelAll("quxiao");
    }
}
