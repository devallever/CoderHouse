package com.allever.coderhouse.modules.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allever.coderhouse.R;
import com.allever.coderhouse.modules.main.bean.ArticleItem;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allever on 17-5-27.
 */

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder> {

    private List<ArticleItem> articleItemList = new ArrayList<>();
    private Context context;

    public MainRecyclerAdapter(Context context, List<ArticleItem> articleItems){
        this.context = context;
        this.articleItemList = articleItems;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_item_0,viewGroup,false);
        MainViewHolder viewHolder = new MainViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainViewHolder viewHolder, int position) {
        final ArticleItem articleItem = articleItemList.get(position);
        viewHolder.tv_title.setText(articleItem.getTitle());
        viewHolder.tv_user.setText(articleItem.getUser());
        viewHolder.tv_time.setText(articleItem.getTime());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return articleItemList.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title;
        TextView tv_user;
        TextView tv_time;
        public MainViewHolder(View view){
            super(view);
            tv_title = (TextView)view.findViewById(R.id.id_main_item_0_tv_title);
            tv_user = (TextView)view.findViewById(R.id.id_main_item_0_tv_user);
            tv_time = (TextView)view.findViewById(R.id.id_main_item_0_tv_time);

        }
    }
}
