package xunqaing.bwie.com.todaytopnews.activities;

import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bwei.slidingmenu.SlidingMenu;
import com.bwei.slidingmenu.app.SlidingFragmentActivity;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;
import java.util.Map;

import xunqaing.bwie.com.todaytopnews.IApplication;
import xunqaing.bwie.com.todaytopnews.R;
import xunqaing.bwie.com.todaytopnews.SwitchButtonEvent;
import xunqaing.bwie.com.todaytopnews.adapter.MyAdapter;
import xunqaing.bwie.com.todaytopnews.bean.NewsCategory;
import xunqaing.bwie.com.todaytopnews.fragment.MenuLeftFragment;
import xunqaing.bwie.com.todaytopnews.fragment.MenuRightFragment;
import xunqaing.bwie.com.todaytopnews.utils.MyUrl;

public class MainActivity extends SlidingFragmentActivity implements UMAuthListener {

    private SlidingMenu slidingMenu;
    private TabLayout tabLayout;
    private ViewPager viewpager;
    private MyAdapter adapter;
    private List<NewsCategory.DataBeanX.DataBean> categoryList;
    private IApplication application;
    private WindowManager manager;
    private View view;
    private WindowManager.LayoutParams params;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewpager = (ViewPager) findViewById(R.id.viewpage);

        ImageView iv_left = (ImageView) findViewById(R.id.pub_title_left_imageview);
        ImageView iv_right = (ImageView) findViewById(R.id.pub_title_right_imageview);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);

        }


        //设置TabLayout
        setTabLayout();


        //加载左右侧滑Fragment
        initLeftRight();


        initData();
        initGrayBackground();
        //点击出现侧滑
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                slidingMenu.showMenu(true);

            }
        });

        //右侧图片点击
        iv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                slidingMenu.showSecondaryMenu(true);

            }
        });

        application = (IApplication) getApplication();
    }

    public void initGrayBackground() {

        manager = (WindowManager) getSystemService(WINDOW_SERVICE);

        /**
         * 应用程序窗口。WindowManager.LayoutParams.TYPE_APPLICATION
         * 所有程序窗口的"基地"窗口，其他应用程序窗口都显示在它上面
         * 普通应用功能程序窗口。token必须设置为Activity的token，以指出该窗口属谁
         */
        //1.应用程序的窗口，
        //2. (1)设置此窗口不抢焦点   (2)设置此窗口没有点击事件
        //3.设置窗口时透明的
        params = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_APPLICATION, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                , PixelFormat.TRANSPARENT);

        view = new View(this);

        view.setBackgroundResource(R.color.color_window);

    }

    //日夜切换
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainActivityEvent(SwitchButtonEvent event) {

        if (event.isWhite()) {
            //    白
            manager.removeViewImmediate(view);

        } else {

            //晚
            manager.addView(view, params);

        }

        //对所有的空间取出，设置对应图片
        setView();

        //更改字体颜色
        switchTextViewColor((ViewGroup) getWindow().getDecorView(),event.isWhite());

        MainActivity activity = new MainActivity();
        activity.changeMode(event.isWhite());
    }

    private void changeMode(boolean white) {

        if (white) {

            tabLayout.setBackgroundColor(Color.GRAY);

            setWhiteMode();
        } else {
            tabLayout.setBackgroundColor(Color.BLACK);

            setNightMode();
        }


    }

    /**
     * 遍历出所有的textView设置对应的颜色 （递归）
     */
    public void switchTextViewColor(ViewGroup view,boolean white) {
//        getChildCount 获取ViewGroup下view的个数
//        view.getChildAt(i) 根据下标获取对应的子view
        for (int i = 0; i < view.getChildCount(); i++) {
            if (view.getChildAt(i) instanceof ViewGroup) {
                switchTextViewColor((ViewGroup) view.getChildAt(i),white);
            } else if (view.getChildAt(i) instanceof TextView) {
                //设置颜色
                int resouseId ;
                TextView textView = (TextView) view.getChildAt(i);
                if(white){
                    resouseId = Color.BLACK;
                }else {
                    resouseId = Color.WHITE;
                }
                textView.setTextColor(resouseId);
            }
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
        tabLayout.setSelectedTabIndicatorColor(Color.RED);

        setWhiteMode();
        //设置滚动模式
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        //        设置正常模式
//        tabLayout.setTabMode(TabLayout.MODE_FIXED);


    }

    // 更改 控件 背景
    private void setView() {


    }

    // 改变tablayout 字颜色 下标颜色
    private void setWhiteMode(){
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.title_color));
        tabLayout.setTabTextColors(getResources().getColor(R.color.iblack),getResources().getColor(R.color.title_color));
    }
    private void setNightMode(){
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.title_color));
        tabLayout.setTabTextColors(getResources().getColor(R.color.iblack),getResources().getColor(R.color.title_color));
    }


    private void initLeftRight() {

        Fragment menuLeftFragment = new MenuLeftFragment();
        //左侧布局加载哪个xml中
        setBehindContentView(R.layout.left_menu_fragment);

        getSupportFragmentManager().beginTransaction().replace(R.id.id_left_fragment, menuLeftFragment).commit();

        slidingMenu = getSlidingMenu();

        // 设置slidingmenu滑动的方式(左)
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


    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

    }

    @Override
    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(SHARE_MEDIA share_media, int i) {

    }
}
