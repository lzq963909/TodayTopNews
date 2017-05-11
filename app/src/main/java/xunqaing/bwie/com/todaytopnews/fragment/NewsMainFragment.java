package xunqaing.bwie.com.todaytopnews.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import xunqaing.bwie.com.todaytopnews.IApplication;
import xunqaing.bwie.com.todaytopnews.R;
import xunqaing.bwie.com.todaytopnews.RefreshLayout;
import xunqaing.bwie.com.todaytopnews.SwitchButtonEvent;
import xunqaing.bwie.com.todaytopnews.adapter.NewsListAdapter;
import xunqaing.bwie.com.todaytopnews.bean.TuijianBean;
import xunqaing.bwie.com.todaytopnews.utils.MyUrl;
import xunqaing.bwie.com.todaytopnews.utils.NetUtil;
import xunqaing.bwie.com.todaytopnews.utils.SteamTools;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public class NewsMainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private ListView listView;
    private String newsType;
    private List<TuijianBean.DataBean> list;

    private List<TuijianBean.DataBean> listAll = new ArrayList<>();

    private NewsListAdapter adapter;
    private IApplication application;
    private RefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_news, null);

        swipeRefreshLayout = (RefreshLayout) view.findViewById(R.id.news_swiperefreshlayout);

        listView = (ListView) view.findViewById(R.id.news_listview);
        newsType = getArguments().getString("newstype");

        application = (IApplication) getActivity().getApplication();

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {

            @Override
            public void onLoad() {
                swipeRefreshLayout.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        initData(false);
                    }
                }, 1000);
            }
        });

        initData(true);

    }

//    public void setAdapterFlush(boolean flag) {
//
//        if (flag) {
//
//            adapter = new NewsListAdapter(getActivity(), listAll);
//            listView.setAdapter(adapter);
//
//        } else {
//
//            adapter.notifyDataSetChanged();
//
//        }
//
//    }

    public void initData(boolean flag) {

        //判断是否WIFI网络
        if (NetUtil.GetNetype(getActivity()).equals("WIFI")) {
            findDatasFromIntentle(flag);
        } else {
            findDatasFromDB();
        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setListViewTextView(SwitchButtonEvent event) {

        setBackground(event.isWhite());


    }

    public void setBackground(boolean isWhite) {

        //白天模式
        if (isWhite) {

            listView.setBackgroundColor(Color.WHITE);

        } else {

            listView.setBackgroundColor(Color.BLACK);

        }

    }

    private void findDatasFromIntentle(final boolean flag) {

        RequestParams requestParams = new RequestParams(MyUrl.getUrl(newsType));

        x.http().get(requestParams, new Callback.CommonCallback<String>() {

            private DbManager manager;

            @Override
            public void onSuccess(String result) {
                TuijianBean tuijianBean = JSON.parseObject(result, TuijianBean.class);
                list = tuijianBean.getData();

                listAll.addAll(list);

                if (flag) {
                    adapter = new NewsListAdapter(getActivity(), listAll);
                    listView.setAdapter(adapter);

                }else{
                    adapter.notifyDataSetChanged();
                }


                SteamTools.WriteToFile(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void findDatasFromDB() {
        String result = SteamTools.readSdcardFile();
        TuijianBean tuijianBean = JSON.parseObject(result, TuijianBean.class);
        list = tuijianBean.getData();
        adapter = new NewsListAdapter(getActivity(), list);
        listView.setAdapter(adapter);

    }

    //shuaxin
    @Override
    public void onRefresh() {

        listAll.clear();

//        adapter.notifyDataSetChanged();

        initData(true);


        swipeRefreshLayout.setRefreshing(false);

    }
}
