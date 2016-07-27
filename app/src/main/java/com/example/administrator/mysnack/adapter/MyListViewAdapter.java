package com.example.administrator.mysnack.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.administrator.mysnack.R;
import com.example.administrator.mysnack.app.MyApp;
import com.example.administrator.mysnack.entity.HeadDetailEntity;
import com.example.administrator.mysnack.utils.MyImageCache;

import java.util.List;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class MyListViewAdapter extends BaseAdapter {
    private List<HeadDetailEntity>data;
    private LayoutInflater inflater;

    public MyListViewAdapter(Context context,List<HeadDetailEntity> data) {
        this.data = data;
       this. inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data!=null?data.size():0;
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view==null) {
            viewHolder=new ViewHolder();
            view=inflater.inflate(R.layout.home_haochi_item,viewGroup,false);
            viewHolder.tvCurrent= (TextView) view.findViewById(R.id.tv_current_item);
            viewHolder.tvTitle= (TextView) view.findViewById(R.id.tv_title_item);
            viewHolder.ivHaochi= (ImageView) view.findViewById(R.id.iv_haochi_item);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        HeadDetailEntity headDetailEntity=data.get(i);
        viewHolder.tvCurrent.setText("￥"+headDetailEntity.getCurrent()+"");
        viewHolder.tvTitle.setText(headDetailEntity.getTitle());
        String ivUrl=headDetailEntity.getImg_url();
        //下载图片
        ImageLoader imageLoader = new ImageLoader(MyApp.getQueue(), new MyImageCache());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(viewHolder.ivHaochi, R.drawable.default_projects_small300_150, R.drawable.default_projects_small300_150);
        imageLoader.get(ivUrl, listener, 0, 0);

        return view;
    }
    class ViewHolder{
        private TextView tvTitle,tvCurrent;
        private ImageView ivHaochi;

    }
}
