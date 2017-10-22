package com.allever.coderhouse.modules.image.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allever.coderhouse.R;
import com.allever.coderhouse.modules.image.adapter.BingImageRecyclerAdapter;
import com.allever.coderhouse.modules.image.adapter.GanHuoImageRecyclerAdapter;
import com.allever.coderhouse.modules.image.bean.BingImageItem;
import com.allever.coderhouse.modules.main.bean.Results;
import com.allever.coderhouse.modules.main.bean.Root;
import com.allever.coderhouse.modules.main.listener.RecyclerViewScrollListener;
import com.allever.coderhouse.modules.main.network.GanHuoRetrofitUtil;
import com.allever.coderhouse.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by allever on 17-5-30.
 */

public class GanHuoImageFragment extends Fragment implements RecyclerViewScrollListener.OnRecycleRefreshListener{

    private static final String TAG = "GanHuoImageFragment";

    private ProgressDialog progressDialog;


    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private GanHuoImageRecyclerAdapter ganHuoImageRecyclerAdapter;
    private List<String> urlList;
    private int page = 1;

    private RecyclerViewScrollListener recyclerViewScrollListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gan_huo_image_fragment_layout,container,false);


        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.id_gan_huo_image_fragment_swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));


        urlList = new ArrayList<>();
        recyclerView = (RecyclerView)view.findViewById(R.id.id_gan_huo_image_fragment_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        ganHuoImageRecyclerAdapter = new GanHuoImageRecyclerAdapter(getActivity(),urlList);
        recyclerView.setAdapter(ganHuoImageRecyclerAdapter);

        recyclerViewScrollListener = new RecyclerViewScrollListener(this);
        recyclerView.addOnScrollListener(recyclerViewScrollListener);
        swipeRefreshLayout.setOnRefreshListener(recyclerViewScrollListener);

        getData();




        return view;
    }

    private void getData(){
        GanHuoRetrofitUtil.getInstance().getDataList(new Subscriber<Root>() {
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
            public void onNext(Root root) {
                Log.d(TAG, "onNext: ");

                if (page == 0) {
                    urlList.clear();
                    if (swipeRefreshLayout.isRefreshing())
                        swipeRefreshLayout.setRefreshing(false);
                }

                if (root.getResults().size()>0){
                    for (Results results: root.getResults()){
                        urlList.add(results.getUrl());
                    }
                    recyclerViewScrollListener.setLoadDataStatus(false);
                    ganHuoImageRecyclerAdapter.notifyDataSetChanged();
                }
            }
        },"福利", Constants.DATA_ITEM_COUNT,page);
    }

    @Override
    public void loadMore() {
        page++;
        showProgressDialog("Loading...");
        getData();
    }

    @Override
    public void refresh() {
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
