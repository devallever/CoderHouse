package com.allever.coderhouse.modules.image.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allever.coderhouse.R;
import com.allever.coderhouse.modules.image.adapter.BaiDuImageRecyclerAdapter;
import com.allever.coderhouse.modules.image.bean.BaiDuImageItem;
import com.allever.coderhouse.modules.image.bean.BaiDuImageRoot;
import com.allever.coderhouse.modules.image.bean.Imgs;
import com.allever.coderhouse.modules.image.network.BaiDuRetrofitUtil;
import com.allever.coderhouse.modules.main.listener.RecyclerViewScrollListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by allever on 17-5-28.
 */

public class BaiDuImageFragment extends Fragment implements RecyclerViewScrollListener.OnRecycleRefreshListener {
    private ProgressDialog progressDialog;
    private static final String TAG = "BaiduImageFragment";
    private RecyclerView recyclerView;
    private RecyclerViewScrollListener recyclerViewScrollListener;
    private BaiDuImageRecyclerAdapter baiDuImageRecyclerAdapter;
    //private List<String> urlList;
    private List<BaiDuImageItem> baiDuImageItemList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int startIndex = 0;

    private String col;
    private String tag;
    private String url;


    public BaiDuImageFragment(){

    }

    @SuppressLint("ValidFragment")
    public BaiDuImageFragment(String col, String tag){
        this.col = col;
        this.tag = tag;
        url = "imgs?col=" + col +"&tag=" + tag +"&sort=0&pn=" + (startIndex*10+1) +"&rn=10&p=channel&from=1";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.baidu_image_fragment_layout,container,false);

        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.id_image_fragment_swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        //urlList = new ArrayList<>();
        baiDuImageItemList = new ArrayList<>();
        //initData();


        recyclerView = (RecyclerView)view.findViewById(R.id.id_baidu_image_fragment_recycler_view);
        baiDuImageRecyclerAdapter = new BaiDuImageRecyclerAdapter(getActivity(),baiDuImageItemList);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        //layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        //recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(baiDuImageRecyclerAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //防止第一行到顶部有空白区域
                layoutManager.invalidateSpanAssignments();
            }
        });

        recyclerViewScrollListener = new RecyclerViewScrollListener(this);
        recyclerView.addOnScrollListener(recyclerViewScrollListener);
        swipeRefreshLayout.setOnRefreshListener(recyclerViewScrollListener);

        getData();



        return view;
    }

    private void getData(){
        BaiDuRetrofitUtil.getInstance().getImageList(new Subscriber<BaiDuImageRoot>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
                dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                dismissProgressDialog();
                Log.d(TAG, "onError: ");
                e.printStackTrace();
            }

            @Override
            public void onNext(BaiDuImageRoot baiDuImageRoot) {
                Gson gson = new Gson();
                Log.d(TAG, "onNext: result = " + gson.toJson(baiDuImageRoot));

                if (startIndex == 0) {
                    baiDuImageItemList.clear();
                    if (swipeRefreshLayout.isRefreshing())
                        swipeRefreshLayout.setRefreshing(false);
                }

                for(Imgs imgs: baiDuImageRoot.getImgs()){

                    if (imgs.getDownloadUrl()!= null){
                        BaiDuImageItem baiDuImageItem = new BaiDuImageItem();
                        baiDuImageItem.setUrl(imgs.getDownloadUrl());
                        baiDuImageItem.setDescription(imgs.getDesc());
                        baiDuImageItemList.add(baiDuImageItem);
                    }

                }
                recyclerViewScrollListener.setLoadDataStatus(false);
                baiDuImageRecyclerAdapter.notifyDataSetChanged();
            }
        },col,tag,startIndex + "");
    }

    @Override
    public void loadMore() {
        startIndex = startIndex + 10;
        showProgressDialog("Loading...");
        getData();
    }

    @Override
    public void refresh() {
        startIndex = 0;
        getData();
    }

    private void showProgressDialog(String message){
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    private void dismissProgressDialog(){
        if (progressDialog!=null){
            if (progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }

    }
}
