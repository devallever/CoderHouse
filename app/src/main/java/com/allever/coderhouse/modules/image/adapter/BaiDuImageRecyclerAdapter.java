package com.allever.coderhouse.modules.image.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.allever.coderhouse.R;
import com.allever.coderhouse.modules.image.bean.BaiDuImageItem;
import com.allever.coderhouse.modules.image.bean.BingImageItem;
import com.allever.coderhouse.modules.image.ui.ShowImageActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

/**
 * Created by allever on 17-5-28.
 */

public class BaiDuImageRecyclerAdapter extends RecyclerView.Adapter<BaiDuImageRecyclerAdapter.ImageRecyclerViewViewHolder> {
    private static final String TAG = "ImageRecyclerAdapter";
    private Context context;
    private List<BaiDuImageItem> baiDuImageItemList;

    public BaiDuImageRecyclerAdapter(Context context, List<BaiDuImageItem> baiDuImageItemList){
        this.context = context;
        this.baiDuImageItemList = baiDuImageItemList;
    }

    @Override
    public ImageRecyclerViewViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.baidu_image_item,viewGroup,false);
        ImageRecyclerViewViewHolder holder = new ImageRecyclerViewViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ImageRecyclerViewViewHolder holder, int position) {

        BaiDuImageItem baiDuImageItem = baiDuImageItemList.get(position);
        Glide.with(context).load(baiDuImageItem.getUrl()).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowImageActivity.class);
                intent.putExtra("url",baiDuImageItem.getUrl());
                intent.putExtra("description",baiDuImageItem.getDescription());
                context.startActivity(intent);
            }
        });

/*
        Glide.with(context).load(urlList.get(position)).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                int imageWidth = resource.getWidth();
                int imageHeight = resource.getHeight();
                Log.d(TAG, "onResourceReady: " + imageHeight );
                Log.d(TAG, "onResourceReady: " + imageWidth);
                ViewGroup.LayoutParams para = holder.imageView.getLayoutParams();
                para.height = imageHeight;
                para.width = imageWidth;
                holder.imageView.setImageBitmap(resource);
            }
        });
*/

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return baiDuImageItemList.size();
    }

    class ImageRecyclerViewViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public ImageRecyclerViewViewHolder(View view){
            super(view);
            imageView = (ImageView)view.findViewById(R.id.id_bai_du_image_item_iv);
        }
    }
}
