package xunqaing.bwie.com.todaytopnews.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import xunqaing.bwie.com.todaytopnews.R;
import xunqaing.bwie.com.todaytopnews.cityview.CityAdapter;
import xunqaing.bwie.com.todaytopnews.cityview.CitySortModel;
import xunqaing.bwie.com.todaytopnews.cityview.EditTextWithDel;
import xunqaing.bwie.com.todaytopnews.cityview.PinyinComparator;
import xunqaing.bwie.com.todaytopnews.cityview.PinyinUtils;
import xunqaing.bwie.com.todaytopnews.cityview.SideBar;
import xunqaing.bwie.com.todaytopnews.cityview.SortAdapter;

public class CityActivity extends Activity {
    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog, mTvTitle;
    private SortAdapter adapter;
    private EditTextWithDel mEtCityName;
    private List<CitySortModel> SourceDateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        initViews();






    }

    private void initViews() {
        mEtCityName = (EditTextWithDel) findViewById(R.id.et_search);
        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        initDatas();
        initEvents();
        setAdapter();
    }

    private void setAdapter() {
        SourceDateList = filledData(getResources().getStringArray(R.array.provinces));
        Collections.sort(SourceDateList, new PinyinComparator());
        adapter = new SortAdapter(this, SourceDateList);
        sortListView.addHeaderView(initHeadView());
        sortListView.setAdapter(adapter);
    }

    private void initEvents() {
        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position + 1);
                }
            }
        });

        //ListView的点击事件
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mTvTitle.setText(((CitySortModel) adapter.getItem(position - 1)).getName());
                Toast.makeText(getApplication(), ((CitySortModel) adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        //根据输入框输入值的改变来过滤搜索
        mEtCityName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initDatas() {
        sideBar.setTextView(dialog);
    }

    private View initHeadView() {
        View headView = getLayoutInflater().inflate(R.layout.headview, null);
        GridView mGvCity = (GridView) headView.findViewById(R.id.gv_hot_city);
        String[] datas = getResources().getStringArray(R.array.city);
        ArrayList<String> cityList = new ArrayList<>();
        for (int i = 0; i < datas.length; i++) {
            cityList.add(datas[i]);
        }
        CityAdapter adapter = new CityAdapter(getApplicationContext(), R.layout.gridview_item, cityList);
        mGvCity.setAdapter(adapter);
        return headView;
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<CitySortModel> mSortList = new ArrayList<>();
        if (TextUtils.isEmpty(filterStr)) {
            mSortList = SourceDateList;
        } else {
            mSortList.clear();
            for (CitySortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.toUpperCase().indexOf(filterStr.toString().toUpperCase()) != -1 || PinyinUtils.getPingYin(name).toUpperCase().startsWith(filterStr.toString().toUpperCase())) {
                    mSortList.add(sortModel);
                }
            }
        }
        // 根据a-z进行排序
        Collections.sort(mSortList, new PinyinComparator());
        adapter.updateListView(mSortList);
    }

    private List<CitySortModel> filledData(String[] date) {
        //mSortList  有序的list
        List<CitySortModel> mSortList = new ArrayList<>();
        ArrayList<String> indexString = new ArrayList<>();

        for (int i = 0; i < date.length; i++) {
            CitySortModel sortModel = new CitySortModel();
            sortModel.setName(date[i]);
            String pinyin = PinyinUtils.getPingYin(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
                if (!indexString.contains(sortString)) {
                    indexString.add(sortString);
                }
            }
            mSortList.add(sortModel);
        }
        Collections.sort(indexString);
        sideBar.setIndexText(indexString);
        return mSortList;
    }
}



/*import android.app.Activity;
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

}*/
