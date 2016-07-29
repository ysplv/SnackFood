package com.example.administrator.mysnack.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.administrator.mysnack.R;
import com.example.administrator.mysnack.app.MyApp;
import com.example.administrator.mysnack.entity.HeadDetailEntity;
import com.example.administrator.mysnack.utils.MyImageCache;

import java.util.List;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class MoreTeMaiRecyclerViewAdapter extends RecyclerView.Adapter<MoreTeMaiRecyclerViewAdapter.ViewHolder> {
    private List<HeadDetailEntity> data;
    private LayoutInflater inflater;

    public MoreTeMaiRecyclerViewAdapter(Context context, List<HeadDetailEntity> data) {
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.more_temai_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvCurrent.setText("￥" + data.get(position).getCurrent());
        holder.tvPrime.setText("￥" + data.get(position).getPrime());
        String imgUrl = data.get(position).getImg_url();
        //创建imageLoader对象
        ImageLoader imageLoader = new ImageLoader(MyApp.getQueue(), new MyImageCache());
        //imageLoader的监听
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(holder.ivImg, R.drawable.default_hone_class84_84, R.drawable.default_hone_class84_84);
        imageLoader.get(imgUrl, listener, 0, 0);

    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivImg;
        private TextView tvCurrent, tvPrime;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImg = (ImageView) itemView.findViewById(R.id.iv_moretemai_item);
            tvCurrent = (TextView) itemView.findViewById(R.id.tv_current_moretemai_item);
            tvPrime = (TextView) itemView.findViewById(R.id.tv_origin_moretemai_item);
        }
    }
}
