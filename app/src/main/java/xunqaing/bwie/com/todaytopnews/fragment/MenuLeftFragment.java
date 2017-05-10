package xunqaing.bwie.com.todaytopnews.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.kyleduo.switchbutton.SwitchButton;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import xunqaing.bwie.com.todaytopnews.Constants.Constants;
import xunqaing.bwie.com.todaytopnews.R;
import xunqaing.bwie.com.todaytopnews.SwitchButtonEvent;
import xunqaing.bwie.com.todaytopnews.activities.LoginActivity;
import xunqaing.bwie.com.todaytopnews.utils.PreferencesUtils;

/**
 * Created by : Xunqiang
 * 2017/5/8
 */

public class MenuLeftFragment extends Fragment {

    private ImageView menuleft_login_qq;
    private ImageView menuleft_login_txweibo;
    private ImageView menuleft_login_xlweibo;

    private SwitchButton switchButton;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.menuleft_fragment, container, false);

        menuleft_login_qq = (ImageView) view.findViewById(R.id.menuleft_login_qq);

        menuleft_login_txweibo = (ImageView) view.findViewById(R.id.menuleft_login_txweibo);

        menuleft_login_xlweibo = (ImageView) view.findViewById(R.id.menuleft_login_xlweibo);

        switchButton = (SwitchButton) view.findViewById(R.id.switch_btn);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                boolean mode = PreferencesUtils.getValueByKey(getContext(), Constants.isNightMode, isChecked);

                setMode(isChecked);

                EventBus.getDefault().post(new SwitchButtonEvent(isChecked));

                setBackground(isChecked);

            }
        });

        menuleft_login_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                login();


            }
        });

        menuleft_login_txweibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                login();
            }
        });

        menuleft_login_xlweibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到登录注册页面
                loginOrRegisiter();

            }
        });

    }

    public void setBackground(boolean isWhite) {

        //白天模式
        if (isWhite) {

            view.setBackgroundColor(Color.WHITE);

        } else {

            view.setBackgroundColor(Color.BLACK);

        }

    }

    public void setMode(boolean mode) {

        PreferencesUtils.addConfigInfo(getContext(), Constants.isNightMode, mode);

    }


    private void loginOrRegisiter() {

        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    public void login() {

        UMShareAPI.get(getActivity()).getPlatformInfo(getActivity(), SHARE_MEDIA.SINA, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                System.out.println("onStart share_media = " + share_media);
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                System.out.println("onComplete share_media = " + share_media);
//                uid	openid	unionid	id	用户唯一标识	如果需要做跨APP用户打通，QQ需要使用unionID实现
//                name	screen_name	screen_name	screen_name	用户昵称
//                gender	gender	gender	gender	用户性别	该字段会直接返回男女
//                iconurl

                //获取资料
                String uid = map.get("uid");
                String name = map.get("name");
                String gender = map.get("gender");
                String iconurl = map.get("iconurl");


            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                System.out.println("onError SHARE_MEDIA share_media = " + share_media);

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                System.out.println("onCancel SHARE_MEDIA share_media = " + share_media);

            }
        });

    }
}
