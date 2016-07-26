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
import com.example.administrator.mysnack.entity.SubjectBody;
import com.example.administrator.mysnack.utils.MyImageCache;

import java.util.List;

/**
 * Created by Administrator on 2016/7/26.
 */
public class ListViewAdapterSubject extends BaseAdapter {
    private List<SubjectBody.DataBean.ItemsBean>list;
    private LayoutInflater inflater;
    private Context context;
    private ViewHolder viewHolder;

    public ListViewAdapterSubject(List<SubjectBody.DataBean.ItemsBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view==null){
            view=inflater.inflate(R.layout.listview_subject_item,viewGroup,false);
            viewHolder=new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.title.setText(list.get(i).getTitle());
        viewHolder.hotindex.setText(list.get(i).getHotindex()+"");
        SubjectBody.DataBean.ItemsBean.ImgBean imgBean = list.get(i).getImg();
        String img_url = imgBean.getImg_url();
//        if (TextUtils.isEmpty(img_url)){
//            return view;
//        }
        /**
         * 获取imageLoader对象
         */
        ImageLoader imageLoader = new ImageLoader(MyApp.getQueue(), new MyImageCache());
        /**
         * 获取ImageListner对象
         */
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(viewHolder.imageView_body, R.drawable.default_class_brand612_250, R.drawable.default_class_brand612_250);
        imageLoader.get(img_url, imageListener);
        return view;
    }
     public static class ViewHolder{
        TextView hotindex,title;
        ImageView imageView_body;

        public ViewHolder(View view) {
            hotindex= (TextView) view.findViewById(R.id.text_hotindex_subject_item);
            title= (TextView) view.findViewById(R.id.text_title_subject_item);
            imageView_body= (ImageView) view.findViewById(R.id.image_subject_item);
        }
    }
}
