package xunqaing.bwie.com.todaytopnews.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
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
import xunqaing.bwie.com.todaytopnews.activities.CityActivity;
import xunqaing.bwie.com.todaytopnews.activities.WebViewActivity;
import xunqaing.bwie.com.todaytopnews.adapter.NewsListAdapter;
import xunqaing.bwie.com.todaytopnews.bean.TuijianBean;
import xunqaing.bwie.com.todaytopnews.eventbean.SwitchButtonEvent;
import xunqaing.bwie.com.todaytopnews.eventbean.SwitchCity;
import xunqaing.bwie.com.todaytopnews.utils.MyUrl;
import xunqaing.bwie.com.todaytopnews.utils.NetUtil;
import xunqaing.bwie.com.todaytopnews.utils.SteamTools;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public class NewsMainFragment extends Fragment implements SpringView.OnFreshListener{
    private ListView listView;
    private String newsType;
    private List<TuijianBean.DataBean> list;

    private List<TuijianBean.DataBean> listAll = new ArrayList<>();

    private NewsListAdapter adapter;
    private IApplication application;
    private SpringView springView;
    private LinearLayout linearLayout;
    private String userCity = "北京";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_news, null);
        View headerView = inflater.inflate(R.layout.listview_header_item,null);

        linearLayout = (LinearLayout) headerView.findViewById(R.id.linearlayout_city);


        springView = (SpringView) view.findViewById(R.id.news_springview);

        listView = (ListView) view.findViewById(R.id.news_listview);
        listView.addHeaderView(headerView);
        newsType = getArguments().getString("newstype");

        application = (IApplication) getActivity().getApplication();

        if (newsType.equals("news_local")) {
            linearLayout.setVisibility(View.VISIBLE);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), CityActivity.class);
                    getActivity().overridePendingTransition(R.anim.in1,R.anim.out1);
                    startActivity(i);
                }
            });


        }

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

        springView.setListener(this);

        initData(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent=new Intent(getActivity(),WebViewActivity.class);
                String url = listAll.get(i-1).getUrl();
                intent.putExtra("url",url);

                getActivity().startActivity(intent);

                getActivity().overridePendingTransition(R.anim.in1,R.anim.out1);
            }
        });

    }


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
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshView(SwitchCity event) {
        userCity = event.getCity();
        findDatasFromIntentle(true);
        onRefresh();
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

        RequestParams requestParams = new RequestParams(MyUrl.getUrl(newsType,userCity));

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


                SteamTools.WriteToFile(result,"data.txt");
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
        String result = SteamTools.readSdcardFile("data.txt");
        TuijianBean tuijianBean = JSON.parseObject(result, TuijianBean.class);
        list = tuijianBean.getData();
        adapter = new NewsListAdapter(getActivity(), list);
        listView.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {

        listAll.clear();

        initData(true);

        springView.onFinishFreshAndLoad();
    }

    @Override
    public void onLoadmore() {

        initData(false);
        springView.onFinishFreshAndLoad();
    }
}
