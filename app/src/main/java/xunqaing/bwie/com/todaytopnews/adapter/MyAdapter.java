package xunqaing.bwie.com.todaytopnews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import xunqaing.bwie.com.todaytopnews.bean.NewsCategory;
import xunqaing.bwie.com.todaytopnews.fragment.NewsMainFragment;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public class MyAdapter extends FragmentPagerAdapter {
    private List<NewsCategory.DataBeanX.DataBean> list;

    public MyAdapter(FragmentManager fm,List<NewsCategory.DataBeanX.DataBean> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        NewsMainFragment f = new  NewsMainFragment();
        return  f;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getName();
    }
}
