package com.allever.coderhouse.modules.image.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.allever.coderhouse.R;
import com.allever.coderhouse.modules.image.adapter.BingImageRecyclerAdapter;
import com.allever.coderhouse.modules.image.bean.BingData;
import com.allever.coderhouse.modules.image.bean.BingImageItem;
import com.allever.coderhouse.modules.image.bean.BingRoot;
import com.allever.coderhouse.modules.image.network.BingRetrofitUtil;
import com.allever.coderhouse.modules.main.listener.RecyclerViewScrollListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by allever on 17-5-29.
 */

public class BingImageFragment extends Fragment implements RecyclerViewScrollListener.OnRecycleRefreshListener{

    private static final String TAG = "BingImageFragment";

    private RecyclerView recyclerView;
    private BingImageRecyclerAdapter bIngImageRecyclerAdapter;
    private List<BingImageItem> bingImageItemList;
    private int page = 1;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerViewScrollListener recyclerViewScrollListener;
    private ProgressDialog progressDialog;
    private boolean isNothingData = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bing_image_fragment_layout,container,false);

        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.id_bing_image_fragment_swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));


        bingImageItemList = new ArrayList<>();
        recyclerView = (RecyclerView)view.findViewById(R.id.id_bing_image_fragment_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bIngImageRecyclerAdapter = new BingImageRecyclerAdapter(getActivity(),bingImageItemList);
        recyclerView.setAdapter(bIngImageRecyclerAdapter);


        recyclerViewScrollListener = new RecyclerViewScrollListener(this);
        recyclerView.addOnScrollListener(recyclerViewScrollListener);
        swipeRefreshLayout.setOnRefreshListener(recyclerViewScrollListener);

        getData();




        return view;
    }

    private void getData(){
        BingRetrofitUtil.getInstance().getBingImageList(new Subscriber<BingRoot>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
                dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
                dismissProgressDialog();
            }

            @Override
            public void onNext(BingRoot bingRoot) {
                Log.d(TAG, "onNext: ");
                Gson gson = new Gson();
                String result = gson.toJson(bingRoot);
                Log.d(TAG, "onNext: result =" + result);

                if (page == 1) {
                    bingImageItemList.clear();
                    if (swipeRefreshLayout.isRefreshing())
                        swipeRefreshLayout.setRefreshing(false);
                }

                if (bingRoot.getData() != null){
                    if (bingRoot.getData().size()==0) {
                        Toast.makeText(getActivity(),"没有更多了",Toast.LENGTH_LONG).show();
                        isNothingData = true;
                        recyclerViewScrollListener.setLoadDataStatus(false);
                        return;
                        //bIngImageRecyclerAdapter.notifyDataSetChanged();
                    }
                    BingImageItem bingImageItem;
                    for (BingData bingData: bingRoot.getData()){
                        bingImageItem = new BingImageItem();
                        bingImageItem.setTitle(bingData.getTitle());
                        bingImageItem.setCopyRight(bingData.getCopyright());
                        bingImageItem.setDescription(bingData.getDescription());
                        bingImageItem.setDate(bingData.getDate());
                        bingImageItem.setUrl(bingData.getUrl());
                        bingImageItem.setDetailUrl(bingData.getDetailUrl());
                        bingImageItemList.add(bingImageItem);
                    }
                    recyclerViewScrollListener.setLoadDataStatus(false);
                    bIngImageRecyclerAdapter.notifyDataSetChanged();

                }

            }
        },page+"");
    }

    @Override
    public void loadMore() {
        if (isNothingData){
            Toast.makeText(getActivity(),"没有更多了",Toast.LENGTH_LONG).show();
            recyclerViewScrollListener.setLoadDataStatus(false);
            //return;
        }else {
            page++;
            showProgressDialog("Loading...");
            getData();
        }

    }

    @Override
    public void refresh() {
        isNothingData = false;
        page = 1;
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
