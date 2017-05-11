package xunqaing.bwie.com.todaytopnews.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import xunqaing.bwie.com.todaytopnews.R;

/**
 * Created by : Xunqiang
 * 2017/5/8
 */

public class MenuRightFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.menuright_fragment,container,false);

        ImageView iv_qq = (ImageView) view.findViewById(R.id.menuright_qq);
        ImageView iv_weibo = (ImageView) view.findViewById(R.id.menuright_weibo);
        ImageView iv_tencent = (ImageView) view.findViewById(R.id.menuright_tencent);

        iv_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareto();
            }
        });

        iv_weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareto();
            }
        });

        iv_tencent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareto();
            }
        });



        return view;
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


}
