package xunqaing.bwie.com.todaytopnews.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import xunqaing.bwie.com.todaytopnews.IApplication;
import xunqaing.bwie.com.todaytopnews.R;
import xunqaing.bwie.com.todaytopnews.SwitchButtonEvent;
import xunqaing.bwie.com.todaytopnews.adapter.NewsListAdapter;
import xunqaing.bwie.com.todaytopnews.bean.TuijianBean;
import xunqaing.bwie.com.todaytopnews.utils.MyUrl;
import xunqaing.bwie.com.todaytopnews.utils.NetUtil;

import static org.xutils.x.getDb;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public class NewsMainFragment extends Fragment {
    private ListView listView;
    private String newsType;
    private List<TuijianBean.DataBean> list;
    private NewsListAdapter adapter;
    private IApplication application;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_news,null);
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

        //判断是否有网络
        if (NetUtil.GetNetype(getActivity()).equals("WIFI")){
            findDatasFromIntentle();
        }else {
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

    private void findDatasFromIntentle() {

        RequestParams requestParams = new RequestParams(MyUrl.getUrl(newsType));

        x.http().get(requestParams, new Callback.CommonCallback<String>() {

            private DbManager manager;

            @Override
            public void onSuccess(String result) {
                TuijianBean tuijianBean = JSON.parseObject(result,TuijianBean.class);
                list = tuijianBean.getData();
                adapter = new NewsListAdapter(getActivity(),list);
                listView.setAdapter(adapter);

                manager = getDb(application.config);

                try {
                    for (TuijianBean.DataBean dataBean: list) {
                        manager.save(dataBean);
                    }


                } catch (DbException e) {
                    e.printStackTrace();
                }
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
        try {
            List<TuijianBean.DataBean> mlist= x.getDb(application.config).selector(TuijianBean.DataBean.class).findAll();
            list =mlist;
            adapter = new NewsListAdapter(getActivity(),list);
            listView.setAdapter(adapter);
        } catch (DbException e) {
            e.printStackTrace();
        }

    }
}
