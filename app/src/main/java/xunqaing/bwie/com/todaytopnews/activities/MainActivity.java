package xunqaing.bwie.com.todaytopnews.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.alibaba.fastjson.JSON;
import com.bwei.slidingmenu.SlidingMenu;
import com.bwei.slidingmenu.app.SlidingFragmentActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import xunqaing.bwie.com.todaytopnews.R;
import xunqaing.bwie.com.todaytopnews.adapter.MyAdapter;
import xunqaing.bwie.com.todaytopnews.bean.NewsCategory;
import xunqaing.bwie.com.todaytopnews.utils.MyUrl;

public class MainActivity extends SlidingFragmentActivity {

    private SlidingMenu slidingMenu;
    private TabLayout tabLayout;
    private ViewPager viewpager;
    private MyAdapter adapter;
    private List<NewsCategory.DataBeanX.DataBean> categoryList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewpager = (ViewPager) findViewById(R.id.viewpage);

        //设置TabLayout
        setTabLayout();


        //加载左右侧滑Fragment
        initLeftRight();

        initData();

    }

    private void initData() {
        RequestParams params = new RequestParams(MyUrl.NEWS_CATEGORY);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                NewsCategory newsCategory = JSON.parseObject(result, NewsCategory.class);
                categoryList = newsCategory.getData().getData();
                adapter = new MyAdapter(getSupportFragmentManager(), categoryList);
                viewpager.setAdapter(adapter);
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


    private void setTabLayout() {

        //TabLayout 和 ViewPager 关联
        tabLayout.setupWithViewPager(viewpager);

        // 设置 选中 未选中 字的颜色
        tabLayout.setTabTextColors(Color.GRAY, Color.BLACK);

        // 设置 指示器的颜色
        tabLayout.setSelectedTabIndicatorColor(Color.YELLOW);

        //设置滚动模式
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        //        设置正常模式
//        tabLayout.setTabMode(TabLayout.MODE_FIXED);


    }


    private void initLeftRight() {

        Fragment menuLeftFragment = new MenuLeftFragment();
        //左侧布局加载哪个xml中
        setBehindContentView(R.layout.left_menu_fragment);

        getSupportFragmentManager().beginTransaction().replace(R.id.left_fragment, menuLeftFragment).commit();

        slidingMenu = getSlidingMenu();

        // 设置slidingmenu滑动的方式(左右)
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);

        // 设置触摸屏幕的模式（边缘触摸）
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

        // 设置阴影的宽度
        slidingMenu.setShadowWidthRes(R.dimen.shadow_width);

        // 设置slidingmenu边界的阴影图片
        slidingMenu.setShadowDrawable(R.drawable.shadow);

        // 设置滑动菜单视图的宽度
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);

        // 设置渐入渐出效果的值
        slidingMenu.setFadeDegree(0.35f);

        //设置右边（二级）侧滑菜单
        MenuRightFragment menuRightFragment = new MenuRightFragment();
        //加载右侧Fragment
        slidingMenu.setSecondaryMenu(R.layout.right_menu_fragment);
        getSupportFragmentManager().beginTransaction().replace(R.id.id_frame_rightmenu, menuRightFragment).commit();

    }
}
