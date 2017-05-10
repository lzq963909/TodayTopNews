package xunqaing.bwie.com.todaytopnews.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import xunqaing.bwie.com.todaytopnews.R;
import xunqaing.bwie.com.todaytopnews.bean.LoginBean;

/**
 * Created by Administrator on 2017/5/10 0010.
 */

public class RegisiterActivity extends Activity {
    private Button regbtn;
    private EditText editUserName;
    private EditText editPassword;
    private TextView login_register_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        initView(this);
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams requestParams = new RequestParams("http://qhb.2dyt.com/Bwei/register");
                requestParams.addBodyParameter("phone", editUserName.getText().toString().trim());
                requestParams.addBodyParameter("password", editPassword.getText().toString().trim());
                requestParams.addBodyParameter("postkey","1503d");

                x.http().post(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        LoginBean loginBean = JSON.parseObject(result,LoginBean.class);
                        if (loginBean.getRet_code() == 200){
                            startActivity(new Intent(RegisiterActivity.this,LoginActivity.class));
                        }else {
                            Toast.makeText(RegisiterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
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
    }

    private void initView(RegisiterActivity regisiterActivity) {

        editUserName = (EditText) regisiterActivity.findViewById(R.id.register_et_baliu);
        editPassword = (EditText) regisiterActivity.findViewById(R.id.register_password);
        regbtn = (Button) regisiterActivity.findViewById(R.id.register_next);
        login_register_return = (TextView) findViewById(R.id.login_register_return);
        login_register_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
