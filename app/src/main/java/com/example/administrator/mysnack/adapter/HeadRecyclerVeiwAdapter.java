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
 * Created by Administrator on 2016/7/26 0026.
 */
public class HeadRecyclerVeiwAdapter extends RecyclerView.Adapter<HeadRecyclerVeiwAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<HeadDetailEntity> data;

    public HeadRecyclerVeiwAdapter(Context context, List<HeadDetailEntity> data) {
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.head_detail_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvTitle.setText(data.get(position).getTitle());
        holder.tvCurrent.setText("￥"+data.get(position).getCurrent() + "");
        holder.tvPrime.setText("￥"+data.get(position).getPrime()+"");
        String imgUrl=data.get(position).getImg_url();
        //创建imageLoader对象
        ImageLoader imageLoader = new ImageLoader(MyApp.getQueue(), new MyImageCache());
        //imageLoader的监听
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(holder.ivImg, R.drawable.default_class_brand612_250, R.drawable.default_class_brand612_250);
        imageLoader.get(imgUrl, listener, 0, 0);
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvCurrent, tvPrime;
        private ImageView ivImg;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title_headDetail);
            tvCurrent = (TextView) itemView.findViewById(R.id.tv_current_headDetail);
            tvPrime = (TextView) itemView.findViewById(R.id.tv_prime_headDetail);
            ivImg = (ImageView) itemView.findViewById(R.id.iv_img_headDetail);
        }
    }
}
