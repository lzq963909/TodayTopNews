package xunqaing.bwie.com.todaytopnews.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.kyleduo.switchbutton.SwitchButton;

import org.greenrobot.eventbus.EventBus;

import xunqaing.bwie.com.todaytopnews.Constants.Constants;
import xunqaing.bwie.com.todaytopnews.R;
import xunqaing.bwie.com.todaytopnews.SwitchButtonEvent;
import xunqaing.bwie.com.todaytopnews.utils.PreferencesUtils;

/**
 * Created by : Xunqiang
 * 2017/5/8
 */

public class MenuRightFragment extends Fragment {

    private SwitchButton switchButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.menuright_fragment,container,false);

        switchButton = (SwitchButton) view.findViewById(R.id.switch_btn);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                boolean mode = PreferencesUtils.getValueByKey(getContext(), Constants.isNightMode,isChecked);

                EventBus.getDefault().post(new SwitchButtonEvent(mode));

            }
        });
    }
}
