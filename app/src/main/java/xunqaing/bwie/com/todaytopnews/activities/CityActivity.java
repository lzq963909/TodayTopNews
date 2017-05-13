package xunqaing.bwie.com.todaytopnews.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import xunqaing.bwie.com.todaytopnews.R;
import xunqaing.bwie.com.todaytopnews.bean.WhichCity;

public class CityActivity extends Activity {

    private ListView lv;
    private List<WhichCity.DataBean> listcityz;
    private MyAdapterCity adapter;
    //城市名
    List<String> listname = new ArrayList<>();
    //所有城市
    List<List<String>> listcity = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout .activity_city);

        lv = (ListView) findViewById(R.id.activity_city_listview);

        initData();


        //加头
        View v = View.inflate(this,R.layout.activity_city_listhead,null);
        lv.addHeaderView(v);

        adapter = new MyAdapterCity();
        lv.setAdapter(adapter);
    }

    private void initData() {

        RequestParams requestparams = new RequestParams("http://ic.snssdk.com/2/article/city/");

        x.http().get(requestparams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("MSGG",result+"========");
                if (result != null){

                WhichCity whichCity = JSON.parseObject(result, WhichCity.class);
                listcityz = whichCity.getData();

                    for (WhichCity.DataBean wd : listcityz
                         ) {

                    listname.add(wd.getName());
                    listcity.add(wd.getCities());
                    }
                    adapter.notifyDataSetChanged();

                }else{

                    Toast.makeText(CityActivity.this, "空", Toast.LENGTH_SHORT).show();
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

    class MyAdapterCity extends BaseAdapter{


        @Override
        public int getCount() {
            return listcityz!=null?listcityz.size():0;
        }

        @Override
        public Object getItem(int position) {
            return listcityz.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewholder;

            if (convertView == null){

                viewholder = new ViewHolder();

                convertView = View.inflate(CityActivity.this,R.layout.activity_city_list,null);

                viewholder.tv1 = (TextView) convertView.findViewById(R.id.activity_city_tv1);
                viewholder.tv2 = (TextView) convertView.findViewById(R.id.activity_city_tv2);

                convertView.setTag(viewholder);

            }else{

                viewholder = (ViewHolder) convertView.getTag();

            }

            viewholder.tv1.setText(listname.get(position));
            viewholder.tv1.setBackgroundColor(Color.GRAY);

            List<String> strings = listcity.get(position);
            String result = "";
            for (String s:strings) {
                result +=s+"\n";

            }
            viewholder.tv2.setText(result);

            return convertView;
        }

        class ViewHolder {

            TextView tv1;
            TextView tv2;
        }

    }

}
