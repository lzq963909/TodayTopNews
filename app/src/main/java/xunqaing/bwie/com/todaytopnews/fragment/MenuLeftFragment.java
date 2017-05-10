package xunqaing.bwie.com.todaytopnews.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import xunqaing.bwie.com.todaytopnews.R;

/**
 * Created by : Xunqiang
 * 2017/5/8
 */

public class MenuLeftFragment extends Fragment {

    private ImageView menuleft_login_qq;
    private ImageView menuleft_login_txweibo;
    private ImageView menuleft_login_xlweibo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.menuleft_fragment,container,false);

        menuleft_login_qq = (ImageView) view.findViewById(R.id.menuleft_login_qq);

        menuleft_login_txweibo = (ImageView) view.findViewById(R.id.menuleft_login_txweibo);

        menuleft_login_xlweibo = (ImageView) view.findViewById(R.id.menuleft_login_xlweibo);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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



            }
        });

    }

    private void shareto() {

        new ShareAction(getActivity()).withText("hello")
                .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                    }
                }).open();


    }

    public void login(){

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
                String uid =  map.get("uid");
                String name =  map.get("name");
                String gender =  map.get("gender");
                String iconurl =  map.get("iconurl");


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
