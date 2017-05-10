package xunqaing.bwie.com.todaytopnews.adapter;

import java.util.List;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import xunqaing.bwie.com.todaytopnews.R;
import xunqaing.bwie.com.todaytopnews.bean.TuijianBean;

public class NewsListAdapter extends BaseAdapter{
	List<TuijianBean.DataBean> newsList;
	Activity activity;
	LayoutInflater inflater = null;
	ImageLoader imageLoader = ImageLoader.getInstance();
	public NewsListAdapter(List<TuijianBean.DataBean> newsList) {
		this.newsList = newsList;
	}
	


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return newsList == null ? 0 : newsList.size();
	}

	@Override
	public TuijianBean.DataBean getItem(int position) {
		if (newsList != null && newsList.size() != 0) {
			return newsList.get(position);
		}
		return null;
	}




	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder mHolder;
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.list_item, null);
			mHolder = new ViewHolder();
			view.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) view.getTag();
		}
		//图文混排的操作
		return view;
	}

	static class ViewHolder {
		//第一个布局下的
		LinearLayout linearLayoutOne;

		TextView item_one_title;
		TextView item_one_title;
		TextView item_one_title;
		TextView item_one_title;
		TextView item_one_title;
		TextView item_one_title;
		TextView item_one_title;
		TextView item_one_title;
		TextView item_one_title;
		TextView item_one_title;
		TextView item_one_title;
		TextView item_one_title;
		TextView item_one_title;
		TextView item_one_title;









	}
	
}
