package com.allever.coderhouse.modules.image.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.allever.coderhouse.R;
import com.allever.coderhouse.modules.image.ui.ShowImageActivity;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by allever on 17-5-30.
 */

public class GanHuoImageRecyclerAdapter extends RecyclerView.Adapter<GanHuoImageRecyclerAdapter.ImageRecyclerViewViewHolder> {
    private static final String TAG = "ImageRecyclerAdapter";
    private Context context;
    private List<String> urlList;

    public GanHuoImageRecyclerAdapter(Context context, List<String> urlList){
        this.context = context;
        this.urlList = urlList;
    }

    @Override
    public ImageRecyclerViewViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.gan_huo_image_item,viewGroup,false);
        ImageRecyclerViewViewHolder holder = new ImageRecyclerViewViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ImageRecyclerViewViewHolder holder, int position) {
        Glide.with(context).load(urlList.get(position)).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowImageActivity.class);
                intent.putExtra("url",urlList.get(position));
                intent.putExtra("description","");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return urlList.size();
    }

    class ImageRecyclerViewViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public ImageRecyclerViewViewHolder(View view){
            super(view);
            imageView = (ImageView)view.findViewById(R.id.id_gan_huo_image_item_iv);
        }
    }
}
