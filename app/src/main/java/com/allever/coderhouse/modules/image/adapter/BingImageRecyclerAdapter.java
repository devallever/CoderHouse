package com.allever.coderhouse.modules.image.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allever.coderhouse.R;
import com.allever.coderhouse.modules.image.bean.BingImageItem;
import com.allever.coderhouse.modules.image.ui.ShowImageActivity;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by allever on 17-5-29.
 */

public class BingImageRecyclerAdapter extends RecyclerView.Adapter<BingImageRecyclerAdapter.ImageRecyclerViewViewHolder> {

    private Context context;
    private List<BingImageItem> bingImageItemList;

    public BingImageRecyclerAdapter(Context context, List<BingImageItem> bingImageItems){
        this.context = context;
        this.bingImageItemList = bingImageItems;
    }

    @Override
    public ImageRecyclerViewViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.bing_image_item,viewGroup,false);
        ImageRecyclerViewViewHolder imageRecyclerViewViewHolder = new ImageRecyclerViewViewHolder(view);

        return imageRecyclerViewViewHolder;
    }

    @Override
    public void onBindViewHolder(ImageRecyclerViewViewHolder holder, int position) {
        BingImageItem bingImageItem = bingImageItemList.get(position);
        holder.tv_copy_right.setText(bingImageItem.getCopyRight());
        Glide.with(context).load(bingImageItem.getUrl()).into(holder.iv_image);
        holder.iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowImageActivity.class);
                intent.putExtra("url",bingImageItem.getUrl());
                intent.putExtra("description",bingImageItem.getDecription());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bingImageItemList.size();
    }

    class ImageRecyclerViewViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_image;
        TextView tv_copy_right;
        public ImageRecyclerViewViewHolder(View view){
            super(view);
            iv_image = (ImageView)view.findViewById(R.id.id_bing_image_item_iv);
            tv_copy_right = (TextView)view.findViewById(R.id.id_bing_image_item_tv_copy_right);
        }

    }
}
