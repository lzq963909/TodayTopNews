package xunqaing.bwie.com.todaytopnews.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import xunqaing.bwie.com.todaytopnews.R;

import static xunqaing.bwie.com.todaytopnews.R.id.set_com;

/**
 * Created by Huangminghuan on 2017/5/12.
 */

public class SetActivity extends Activity implements View.OnClickListener{

    private TextView set_foot;
    private TextView set_comment;
    private TextView set_off_wlan;
    private TextView set_tv_jingCaiTuiJian;
    private TextView set_tv_help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set);

        set_foot = (TextView) findViewById(set_com);
        set_comment = (TextView) findViewById(R.id.set_comment);
        set_off_wlan = (TextView) findViewById(R.id.set_off_wlan);
        set_tv_jingCaiTuiJian = (TextView) findViewById(R.id.set_tv_jingCaiTuiJian);
        set_tv_help = (TextView) findViewById(R.id.set_tv_help);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.set_com:

                AlertDialog.Builder builder=new AlertDialog.Builder(this);

            break;

            case R.id.set_comment:

            break;

            case R.id.set_off_wlan:

            break;

            case R.id.set_tv_jingCaiTuiJian:

            break;
            case R.id.set_tv_help:

                break;
        }
    }
}
