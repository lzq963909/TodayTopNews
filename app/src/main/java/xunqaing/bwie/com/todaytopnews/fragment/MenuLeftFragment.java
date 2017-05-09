package xunqaing.bwie.com.todaytopnews.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xunqaing.bwie.com.todaytopnews.R;

/**
 * Created by : Xunqiang
 * 2017/5/8
 */

public class MenuLeftFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.menuleft_fragment,container,false);

        return view;
    }
}
