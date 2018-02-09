package com.allever.coderhouse.modules.main.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.allever.coderhouse.R;
import com.allever.coderhouse.modules.main.adapter.MainRecyclerAdapter;
import com.allever.coderhouse.modules.main.bean.ArticleItem;
import com.allever.coderhouse.modules.main.bean.GuessResults;
import com.allever.coderhouse.modules.main.bean.GuessRoot;
import com.allever.coderhouse.modules.main.bean.Results;
import com.allever.coderhouse.modules.main.bean.Root;
import com.allever.coderhouse.modules.main.bean.Type;
import com.allever.coderhouse.modules.main.listener.RecyclerItemClickListener;
import com.allever.coderhouse.modules.main.listener.RecyclerViewScrollListener;
import com.allever.coderhouse.modules.main.network.GanHuoRetrofitUtil;
import com.allever.coderhouse.utils.Constants;

import org.litepal.crud.DataSupport;
import org.w3c.dom.ProcessingInstruction;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by allever on 17-5-27.
 */

public class MainFragment extends Fragment implements RecyclerViewScrollListener.OnRecycleRefreshListener {
    private ProgressDialog progressDialog;
    private static final String TAG = "MainFragment";
    private RecyclerView recyclerView;
    private RecyclerViewScrollListener recyclerViewScrollListener;
    private MainRecyclerAdapter mainRecyclerAdapter;
    private List<ArticleItem> articleItemList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String type = "Android";
    private int page = 1;

    private boolean isGuessLike = false;


    public MainFragment(){
        isGuessLike = true;

    }

    @SuppressLint("ValidFragment")
    public MainFragment(String type){
        this.type = type;
        isGuessLike = false;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_layout,container,false);
        articleItemList = new ArrayList<>();

        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.id_main_fragment_swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        /*
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (swipeRefreshLayout.isRefreshing()){
                                        swipeRefreshLayout.setRefreshing(false);
                                    }
                                }
                            });
                        }catch (InterruptedException ie){
                            ie.printStackTrace();
                        }

                    }
                }).start();

            }
        });
        */

        recyclerView = (RecyclerView)view.findViewById(R.id.id_main_fragment_recycle_view);
        mainRecyclerAdapter = new MainRecyclerAdapter(getActivity(),articleItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mainRecyclerAdapter);

        recyclerViewScrollListener = new RecyclerViewScrollListener(this);

        recyclerView.addOnScrollListener(recyclerViewScrollListener);
        swipeRefreshLayout.setOnRefreshListener(recyclerViewScrollListener);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                List<Type> typeList = DataSupport.where("typeName=?",articleItemList.get(position).getType()).find(Type.class);
                Type type;
                if (typeList.size()>0){
                     type = typeList.get(0);
                    int clickCount = type.getClickCount();
                    clickCount++;
                    type.setClickCount(clickCount);
                    type.update(type.getId());
                }else {
                    type = new Type();
                    type.setClickCount(1);
                    type.setTypeName(articleItemList.get(position).getType());
                    type.save();
                }
               /* Toast.makeText(getActivity(),"After click: "
                        + articleItemList.get(position).getType()
                        + "count = " + type.getClickCount(),Toast.LENGTH_LONG).show();
                */
                Intent intent = new Intent(getActivity(),ArticleDetailActivity.class);
                intent.putExtra("url",articleItemList.get(position).getArticleUrl());
                intent.putExtra("title",articleItemList.get(position).getTitle());
                startActivity(intent);

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));


        //showProgressDialog("Loading...");
        getData();


        return view;
    }

    private void getData(){
        Subscriber<Root> rootSubscriber = new Subscriber<Root>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
                dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e.toString());
            }

            @Override
            public void onNext(Root root) {
                Log.d(TAG, "onNext: ");
                if (root.getResults()!=null){
                    ArticleItem articleItem;
                    if (page == 1) {
                        articleItemList.clear();
                        if (swipeRefreshLayout.isRefreshing())
                            swipeRefreshLayout.setRefreshing(false);
                    }
                    for (Results results: root.getResults()){
                        articleItem = new ArticleItem();
                        articleItem.setTitle(results.getDesc());
                        articleItem.setTime(results.getCreatedAt().substring(0,10));
                        articleItem.setUser(results.getWho());
                        articleItem.setType(results.getType());
                        if (results.getString() != null) articleItem.setImgUrl(results.getString().get(0));
                        articleItem.setArticleUrl(results.getUrl());
                        articleItemList.add(articleItem);
                        Log.d(TAG, "desc = " + results.getDesc());
                    }
                    recyclerViewScrollListener.setLoadDataStatus(false);
                    mainRecyclerAdapter.notifyDataSetChanged();
                }

                //Toast.makeText(getActivity(),root.getResults().get(0).getDesc(),Toast.LENGTH_LONG).show();
            }
        };

        Subscriber<GuessRoot> guessRootSubscriber = new Subscriber<GuessRoot>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
                dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e.toString());
            }

            @Override
            public void onNext(GuessRoot guessRoot) {
                Log.d(TAG, "onNext: ");
                if (guessRoot.getResults()!=null){
                    ArticleItem articleItem;
                    if (page == 1) {
                        articleItemList.clear();
                        if (swipeRefreshLayout.isRefreshing())
                            swipeRefreshLayout.setRefreshing(false);
                    }
                    for (GuessResults results: guessRoot.getResults()){
                        articleItem = new ArticleItem();
                        articleItem.setTitle(results.getDesc());
                        articleItem.setTime(results.getPublishedAt().substring(0,10));
                        articleItem.setUser(results.getWho());
                        articleItem.setType(results.getType());
                        //if (results.getString() != null) articleItem.setImgUrl(results.getString().get(0));
                        articleItem.setArticleUrl(results.getUrl());
                        articleItemList.add(articleItem);
                        Log.d(TAG, "desc = " + results.getDesc());
                    }
                    recyclerViewScrollListener.setLoadDataStatus(false);
                    mainRecyclerAdapter.notifyDataSetChanged();
                }
            }
        };
        if (isGuessLike){
           // DataSupport.order("clickCount");
            List<Type> typeList = DataSupport.order("clickCount desc").find(Type.class);
            if (typeList.size()>0) this.type = typeList.get(0).getTypeName();
            else this.type = Constants.DATA_TYPE_ANDROID;
            GanHuoRetrofitUtil.getInstance().getGessLikeList(guessRootSubscriber,type,Constants.DATA_ITEM_COUNT,page);
        }else {
            GanHuoRetrofitUtil.getInstance().getDataList(rootSubscriber,type, Constants.DATA_ITEM_COUNT,page);
        }


    }

    @Override
    public void loadMore() {
        showProgressDialog("Loading...");
        page++;
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
