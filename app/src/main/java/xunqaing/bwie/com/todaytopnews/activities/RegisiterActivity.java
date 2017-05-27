package xunqaing.bwie.com.todaytopnews.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
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

/**
 * 分别有 Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括)、
 * Spanned.SPAN_INCLUSIVE_EXCLUSIVE(前面包括，后面不包括)、
 * Spanned.SPAN_EXCLUSIVE_INCLUSIVE(前面不包括，后面包括)、
 * Spanned.SPAN_INCLUSIVE_INCLUSIVE(前后都包括)。
 *
 *
 *
 */

public class RegisiterActivity extends Activity {
    private Button regbtn;
    private EditText editUserName;
    private EditText editPassword;
    private TextView login_register_return;
    private TextView register_yonghuxieyi;
    private TextView register_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        initView(this);

        //TextView局部变色
        chageTextColor();

        //点击跳转百度页面
        register_yonghuxieyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Uri uri = Uri.parse("http://baidu.com");
                 Intent it = new Intent(Intent.ACTION_VIEW, uri);
                 startActivity(it);

            }
        });


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
        register_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(RegisiterActivity.this,LoginActivity.class);

                startActivity(intent);
            }
        });
    }

    private void chageTextColor() {

        SpannableStringBuilder spsbuilder = new SpannableStringBuilder(register_yonghuxieyi.getText().toString());

        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色

        ForegroundColorSpan jrttyhxy = new ForegroundColorSpan(Color.BLUE);

        spsbuilder.setSpan(jrttyhxy,3,11, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        register_yonghuxieyi.setText(spsbuilder);

    }

    private void initView(RegisiterActivity regisiterActivity) {

        editUserName = (EditText) regisiterActivity.findViewById(R.id.register_et_baliu);
        editPassword = (EditText) regisiterActivity.findViewById(R.id.register_password);
        regbtn = (Button) regisiterActivity.findViewById(R.id.register_next);
        register_login = (TextView) findViewById(R.id.register_login);
        login_register_return = (TextView) findViewById(R.id.login_register_return);
        register_yonghuxieyi = (TextView) findViewById(R.id.register_yonghuxieyi);

        login_register_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
