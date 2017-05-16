package xunqaing.bwie.com.todaytopnews.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

import xunqaing.bwie.com.todaytopnews.R;
import xunqaing.bwie.com.todaytopnews.bean.LoginBean;
import xunqaing.bwie.com.todaytopnews.eventbean.IsLoginEvent;
import xunqaing.bwie.com.todaytopnews.utils.PreferencesUtils;

/**
 * Created by Administrator on 2017/5/10 0010.
 */

public class LoginActivity extends Activity {
    private Button logBtn;
    private EditText editUserName;
    private EditText editPassword;
    private TextView text_regist;
    private TextView login_register_return;
    private TextView tv_wangji;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weibo_login);

        initView(this);

        tv_wangji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginActivity.this,ForgetActivity.class);
                startActivity(i);
            }
        });

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams requestParams = new RequestParams("http://qhb.2dyt.com/Bwei/login");
                requestParams.addBodyParameter("username", editUserName.getText().toString().trim());
                requestParams.addBodyParameter("password", editPassword.getText().toString().trim());
                requestParams.addBodyParameter("postkey", "1503d");

                x.http().post(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        LoginBean loginBean = JSON.parseObject(result, LoginBean.class);
                        Log.d("msg",result.toString());
                        if (loginBean.getRet_code() == 200) {
                            Map<String,Object> users = new HashMap<String, Object>();
                            users.put("username",editUserName.getText().toString().trim());
                            users.put("isLogin",true);
                            PreferencesUtils.setMapKey(LoginActivity.this,users);
                            EventBus.getDefault().post(new IsLoginEvent(true));
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "账号密码错误请重新输入!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        });
        text_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisiterActivity.class));
            }
        });
    }

    private void initView(LoginActivity loginActivity) {
        editUserName = (EditText) loginActivity.findViewById(R.id.login_phone);
        editPassword = (EditText) loginActivity.findViewById(R.id.login_password);
        logBtn = (Button) loginActivity.findViewById(R.id.login);
        text_regist = (TextView) loginActivity.findViewById(R.id.login_register);
        login_register_return = (TextView) findViewById(R.id.login_register_return);
        tv_wangji = (TextView) findViewById(R.id.login_unremeber);
        login_register_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(LoginActivity.this,RegisiterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
