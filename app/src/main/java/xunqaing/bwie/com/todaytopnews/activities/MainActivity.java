package xunqaing.bwie.com.todaytopnews.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.bwei.slidingmenu.SlidingMenu;
import com.bwei.slidingmenu.app.SlidingFragmentActivity;

import java.util.ArrayList;
import java.util.List;

import xunqaing.bwie.com.todaytopnews.R;
import xunqaing.bwie.com.todaytopnews.fragment.ActivitysFragment;
import xunqaing.bwie.com.todaytopnews.fragment.CollectFragment;
import xunqaing.bwie.com.todaytopnews.fragment.FriendsFragment;
import xunqaing.bwie.com.todaytopnews.fragment.IndexFragment;
import xunqaing.bwie.com.todaytopnews.fragment.MenuLeftFragment;
import xunqaing.bwie.com.todaytopnews.fragment.MenuRightFragment;
import xunqaing.bwie.com.todaytopnews.fragment.MyTalkFragment;
import xunqaing.bwie.com.todaytopnews.fragment.ShopFragment;

public class MainActivity extends SlidingFragmentActivity {

    private SlidingMenu slidingMenu;

    private List<Fragment> list = new ArrayList<Fragment>();
    private TabLayout tabLayout;
    private ViewPager viewpager;
    private MyAdapter adapter;
    private ImageView ivleft;
    private ImageView ivright;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewpager = (ViewPager) findViewById(R.id.viewpage);

        ivleft = (ImageView) findViewById(R.id.pub_title_left_imageview);
        ivright = (ImageView) findViewById(R.id.pub_title_right_imageview);


        //设置TabLayout
        setTabLayout();


        //加载左右侧滑Fragment
        initLeftRight();

        //创建Fragment
        initFragment(savedInstanceState);

        //设置一开始显示的Fragment
        switchFragment(0);

        adapter = new MyAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);


        //点击左右头像出现左右侧滑

        ivleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                slidingMenu.showMenu(true);

            }
        });

        ivright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                slidingMenu.showSecondaryMenu(true);

            }
        });
    }

    class MyAdapter extends FragmentPagerAdapter {

        String [] title = {"推荐","热点","杭州","时尚","科技","体育"} ;

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

//            IndexFragment indexFragment = null ;
//            FriendsFragment friendsFragment = null;
//            MyTalkFragment myTalkFragment = null ;
//            CollectFragment collectFragment = null;
//            ActivitysFragment activitysFragment = null;
//            ShopFragment shopFragment = null;

            Fragment f = null;

            switch (position){

                case 0:

                    f = new IndexFragment();

                    break;


                case 1:

                    f = new FriendsFragment();

                    break;

                case 2:

                    f = new MyTalkFragment();

                    break;

                case 3:

                    f = new CollectFragment();

                    break;

                case 4:

                    f = new ActivitysFragment();

                    break;

                case 5:

                    f = new ShopFragment();

                    break;

            }
                return  f;

        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }

    private void setTabLayout() {

        //TabLayout 和 ViewPager 关联
        tabLayout.setupWithViewPager(viewpager);

        // 设置 选中 未选中 字的颜色
        tabLayout.setTabTextColors(Color.GRAY,Color.BLACK);

        // 设置 指示器的颜色
        tabLayout.setSelectedTabIndicatorColor(Color.YELLOW);

        //设置滚动模式
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        //        设置正常模式
//        tabLayout.setTabMode(TabLayout.MODE_FIXED);



    }

    private void switchFragment(int pos) {


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (!list.get(pos).isAdded()){

            transaction.add(R.id.viewpage,list.get(pos),list.get(pos).getClass().getSimpleName());

        }

        for (int i = 0; i <list.size() ; i++) {

            if (i == pos){

                transaction.show(list.get(pos));
            }else{

                transaction.hide(list.get(i));
            }

        }

        transaction.commit();

    }

    private void initFragment(Bundle savedInstanceState) {


        if (list!=null && list.size()>0){

            list.clear();
        }

        IndexFragment indexFragment = null ;
        FriendsFragment friendsFragment = null;
        MyTalkFragment myTalkFragment = null ;
        CollectFragment collectFragment = null;
        ActivitysFragment activitysFragment = null;
        ShopFragment shopFragment = null;

        if(savedInstanceState != null){

            indexFragment = (IndexFragment)getSupportFragmentManager().findFragmentByTag("IndexFragment");
            friendsFragment = (FriendsFragment)getSupportFragmentManager().findFragmentByTag("FriendsFragment");
            myTalkFragment = (MyTalkFragment)getSupportFragmentManager().findFragmentByTag("MyTalkFragment");
            collectFragment = (CollectFragment)getSupportFragmentManager().findFragmentByTag("CollectFragment");
            activitysFragment = (ActivitysFragment)getSupportFragmentManager().findFragmentByTag("ActivitysFragment");
            shopFragment = (ShopFragment)getSupportFragmentManager().findFragmentByTag("ShopFragment");
        }

        if(indexFragment == null){
            indexFragment = new IndexFragment();
        }
        if(friendsFragment == null){
            friendsFragment = new FriendsFragment();
        }
        if(myTalkFragment == null){
            myTalkFragment = new MyTalkFragment();
        }
        if(collectFragment == null){
            collectFragment = new CollectFragment();
        }
        if(activitysFragment == null){
            activitysFragment = new ActivitysFragment();
        }
        if(shopFragment == null){
            shopFragment = new ShopFragment();
        }

        list.add(indexFragment);
        list.add(friendsFragment);
        list.add(myTalkFragment);
        list.add(collectFragment);
        list.add(activitysFragment);
        list.add(shopFragment);
    }

    private void initLeftRight() {

        Fragment menuLeftFragment = new  MenuLeftFragment();
        //左侧布局加载哪个xml中

        setBehindContentView(R.layout.left_menu_fragment);

        getSupportFragmentManager().beginTransaction().replace(R.id.id_left_fragment,menuLeftFragment).commit();

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

        getSupportFragmentManager().beginTransaction().replace(R.id.id_frame_rightmenu,menuRightFragment).commit();


    }
}
