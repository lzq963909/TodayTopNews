package xunqaing.bwie.com.todaytopnews.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import xunqaing.bwie.com.todaytopnews.R;

public class ForgetActivity extends Activity {

    private EditText et;
    private Button bt;
    private Button bt_suiji;
    private TextView tv;
    private String suijishu;
    private TextView login_register_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wangjimima_activity);

        login_register_return = (TextView) findViewById(R.id.login_register_return);
        et = (EditText) findViewById(R.id.wangjimima_et);
        bt = (Button) findViewById(R.id.zhaohuimima_next_button);
        bt_suiji = (Button) findViewById(R.id.wangjimima_bt);

        login_register_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bt_suiji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv = (TextView) v.findViewById(R.id.wangjimima_bt);
                suijishu = suijishu(3);
                tv.setText(suijishu);

            }
        });


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String num = et.getText().toString().trim();

                if (!TextUtils.isEmpty(num)){

                    if (num.equals(suijishu)){

                        Intent i = new Intent(ForgetActivity.this,MainActivity.class);
                        startActivity(i);
                        finish();

                    }else{

                        Toast.makeText(ForgetActivity.this, "输入不对，请重输", Toast.LENGTH_SHORT).show();
                        suijishu = suijishu(3);
                        tv.setText(suijishu);
                        et.setText("");

                    }

                }else{

                    Toast.makeText(ForgetActivity.this, "请输入生成随机数", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private String suijishu(int strLength) {

        Random random = new Random();
        // 获得随机数
        double pross = (1+random.nextDouble())*Math.pow(10,strLength);
        // 将获得的获得随机数转化为字符串
        String strNum = String .valueOf(pross);
        // 返回固定的长度的随机数
        return strNum.substring(1,strLength+1);
    }
}
