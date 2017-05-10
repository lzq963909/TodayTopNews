package xunqaing.bwie.com.todaytopnews.fragment;


import android.content.Intent;
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

public class MenuRightFragment extends Fragment implements View.OnClickListener{

    private ImageView imageViewqq;
    private ImageView imageViewweibo;
    private ImageView imageViewtencent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.menuright_fragment,container,false);

        initView(view);


        return view;
    }

    private void initView(View view) {


        imageViewqq = (ImageView) view.findViewById(R.id.menuright_qq);
        imageViewqq.setOnClickListener(this);
        imageViewweibo = (ImageView) view.findViewById(R.id.menuright_weibo);
        imageViewweibo.setOnClickListener(this);
        imageViewtencent = (ImageView) view.findViewById(R.id.menuright_tencent);
        imageViewtencent.setOnClickListener(this);



    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menuright_qq:
                shareto();
                break;
            case R.id.menuright_weibo:
                shareto();
                break;
            case R.id.menuright_tencent:
                shareto();
                break;

        }

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

    public void share(){

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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);

    }
}
