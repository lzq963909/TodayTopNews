package xunqaing.bwie.com.todaytopnews.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.liaoinstan.springview.container.AcFunFooter;
import com.liaoinstan.springview.container.AcFunHeader;
import com.liaoinstan.springview.widget.SpringView;

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
import xunqaing.bwie.com.todaytopnews.SwitchButtonEvent;
import xunqaing.bwie.com.todaytopnews.activities.CityActivity;
import xunqaing.bwie.com.todaytopnews.adapter.NewsListAdapter;
import xunqaing.bwie.com.todaytopnews.bean.TuijianBean;
import xunqaing.bwie.com.todaytopnews.utils.MyUrl;
import xunqaing.bwie.com.todaytopnews.utils.NetUtil;
import xunqaing.bwie.com.todaytopnews.utils.SteamTools;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public class NewsMainFragment extends Fragment{
    private ListView listView;
    private String newsType;
    private List<TuijianBean.DataBean> list;

    private List<TuijianBean.DataBean> listAll = new ArrayList<>();

    private NewsListAdapter adapter;
    private IApplication application;
    private SpringView springView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_news, null);

        springView = (SpringView) view.findViewById(R.id.news_springview);

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

        springView.setType(SpringView.Type.FOLLOW);//不重叠

        springView.setHeader(new AcFunHeader(getActivity(),R.drawable.acfun_header));

        springView.setFooter(new AcFunFooter(getActivity(),R.drawable.acfun_footer));

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {//刷新

                listAll.clear();

                initData(true);

                springView.onFinishFreshAndLoad();

            }

            @Override
            public void onLoadmore() {//加载更多

                initData(false);
                springView.onFinishFreshAndLoad();

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

                } else {
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


        if (newsType.equals("news_local")) {

            Intent i = new Intent(getActivity(), CityActivity.class);
            startActivity(i);

        }

    }

    private void findDatasFromDB() {
        String result = SteamTools.readSdcardFile();
        TuijianBean tuijianBean = JSON.parseObject(result, TuijianBean.class);
        list = tuijianBean.getData();
        adapter = new NewsListAdapter(getActivity(), list);
        listView.setAdapter(adapter);

    }

    /*//shuaxin
    @Override
    public void onRefresh() {

        listAll.clear();

//        adapter.notifyDataSetChanged();

        initData(true);

    }*/
}
