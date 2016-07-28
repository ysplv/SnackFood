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
import com.example.administrator.mysnack.entity.HeadEntity;
import com.example.administrator.mysnack.utils.MyImageCache;

import java.util.List;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class ListViewAdapterDrawlayout extends BaseAdapter {
    private List<HeadEntity.DataBean.ClassifiesBean>data;
    private LayoutInflater inflater;

    public ListViewAdapterDrawlayout( Context context,List<HeadEntity.DataBean.ClassifiesBean> data) {
        this.data = data;
        this.inflater=LayoutInflater.from(context);
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
            view=inflater.inflate(R.layout.drawlayout_item,viewGroup,false);
            viewHolder.ivdrawlayout= (ImageView) view.findViewById(R.id.iv_drawlayout);
            viewHolder.tvTitel= (TextView) view.findViewById(R.id.tv_title_drawlayout);
            viewHolder.tvDesc= (TextView) view.findViewById(R.id.tv_desc_drawlayout);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }

        viewHolder.tvTitel.setText(data.get(i).getTitle());
        viewHolder.tvDesc.setText(data.get(i).getDesc());
        String imgUrl=data.get(i).getImg().getImg_url();
        ImageLoader imageLoader = new ImageLoader(MyApp.getQueue(), new MyImageCache());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(viewHolder.ivdrawlayout, R.drawable.default_projects_small300_150, R.drawable.default_projects_small300_150);
        imageLoader.get(imgUrl, listener, 0, 0);

        return view;
    }
    class ViewHolder{
        private ImageView ivdrawlayout;
        private TextView tvTitel,tvDesc;

    }
}
