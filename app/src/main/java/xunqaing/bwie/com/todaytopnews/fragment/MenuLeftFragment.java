package xunqaing.bwie.com.todaytopnews.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kyleduo.switchbutton.SwitchButton;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import xunqaing.bwie.com.todaytopnews.Constants.Constants;
import xunqaing.bwie.com.todaytopnews.R;
import xunqaing.bwie.com.todaytopnews.eventbean.SwitchButtonEvent;
import xunqaing.bwie.com.todaytopnews.activities.LoginActivity;
import xunqaing.bwie.com.todaytopnews.activities.SetActivity;
import xunqaing.bwie.com.todaytopnews.utils.PreferencesUtils;

/**
 * Created by : Xunqiang
 * 2017/5/8
 */

public class MenuLeftFragment extends Fragment {

    private ImageView menuleft_login_qq;
    private ImageView menuleft_login_txweibo;
    private ImageView menuleft_login_xlweibo;


    //得到登录用户信息的接口
    private IUiListener loginListener;
    //登录成功的回调
    private IUiListener userInfoListener;

    private SwitchButton switchButton;
    private View view;
//    private TextView tv_set;
    private LinearLayout linear_set;
    private LinearLayout OfflineDownld_layout;
    private Tencent mTencent;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.menuleft_fragment, container, false);

        //实例化Tencent 实例
        mTencent = Tencent.createInstance("1106165798",getActivity().getApplicationContext());



        menuleft_login_qq = (ImageView) view.findViewById(R.id.menuleft_login_qq);

        menuleft_login_txweibo = (ImageView) view.findViewById(R.id.menuleft_login_txweibo);

        menuleft_login_xlweibo = (ImageView) view.findViewById(R.id.menuleft_login_xlweibo);

        switchButton = (SwitchButton) view.findViewById(R.id.switch_btn);

//        tv_set = (TextView) view.findViewById(R.id.tv_set);

        linear_set = (LinearLayout) view.findViewById(R.id.linear_set);
        OfflineDownld_layout = (LinearLayout) view.findViewById(R.id.OfflineDownld_layout);
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

        OfflineDownld_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),SetActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in1,R.anim.out1);
            }
        });

        menuleft_login_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tentenctLogin();


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

        linear_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getActivity(),SetActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in1,R.anim.out1);

            }
        });
    }

    private void tentenctLogin() {

        loginListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                //登录成功后调用的方法
                JSONObject jo = (JSONObject) o;
                Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_SHORT).show();
                Log.e("COMPLETE:", jo.toString());
                String openID;
                try {
                    openID = jo.getString("openid");
                    String accessToken = jo.getString("access_token");
                    String expires = jo.getString("expires_in");
                    mTencent.setOpenId(openID);
                    mTencent.setAccessToken(accessToken, expires);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(UiError uiError) {
                //登录失败后调用的方法
                Toast.makeText(getActivity(), "登录失败", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancel() {
                //取消登录后调用的方法
            }
        };

        userInfoListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                if(o == null){
                    return;
                }
                try {
                    JSONObject jo = (JSONObject) o;
                    Log.e("JO:",jo.toString());
                    int ret = jo.getInt("ret");
                    String nickName = jo.getString("nickname");
                    String gender = jo.getString("gender");
                    Toast.makeText(getActivity(), "你好，" + nickName,Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                }
            }
            @Override
            public void onError(UiError uiError) {
            }
            @Override
            public void onCancel() {
            }
        };


        if (!mTencent.isSessionValid())
        {
            mTencent.login(this,"all", userInfoListener);
        }
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data,  loginListener);
        Tencent.handleResultData(data, loginListener);
        UserInfo info = new UserInfo(getActivity(), mTencent.getQQToken());
        info.getUserInfo(userInfoListener);

    }
}
