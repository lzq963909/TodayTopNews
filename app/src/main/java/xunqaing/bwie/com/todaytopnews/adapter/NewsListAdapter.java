package xunqaing.bwie.com.todaytopnews.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import xunqaing.bwie.com.todaytopnews.R;
import xunqaing.bwie.com.todaytopnews.bean.TuijianBean;

public class NewsListAdapter extends BaseAdapter{

	List<TuijianBean.DataBean> newsList;
	ImageLoader imageLoader = ImageLoader.getInstance();
	Context mcontext;
	private PopupWindow popupWindow;
	private PhotoView photoViewimageview;

	public NewsListAdapter(Context mcontext, List<TuijianBean.DataBean> newsList) {
		this.newsList = newsList;
		this.mcontext = mcontext;

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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder mHolder;
		View view = convertView;
		if (view == null) {
			view = View.inflate(mcontext,R.layout.list_news_item, null);
			mHolder = new ViewHolder();

			mHolder.item_title = (TextView) view.findViewById(R.id.item_title);
			mHolder.item_lable = (TextView) view.findViewById(R.id.item_lable);
			mHolder.item_media_name = (TextView) view.findViewById(R.id.item_media_name);
			mHolder.item_comment_count = (TextView) view.findViewById(R.id.item_comment_count);
			mHolder.right_image = (ImageView) view.findViewById(R.id.item_middle_image);
			mHolder.item_image_0 = (PhotoView) view.findViewById(R.id.item_image01);
			mHolder.item_image_1 = (PhotoView) view.findViewById(R.id.item_image02);
			mHolder.item_image_2 = (PhotoView) view.findViewById(R.id.item_image03);
			mHolder.item_image_layout =(LinearLayout) view.findViewById(R.id.item_image_layout);
			mHolder.textViewDel = (TextView) view.findViewById(R.id.del_id);

			view.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) view.getTag();
		}

		//
		mHolder.item_image_0.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				View inflate = LayoutInflater.from(mcontext).inflate(R.layout.pop_phoneview, null);

				photoViewimageview = (PhotoView) inflate.findViewById(R.id.item_image011);

				popupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.MATCH_PARENT);
				ColorDrawable dw = new ColorDrawable(0x10ab82ff);
				popupWindow.setBackgroundDrawable(dw);
				popupWindow.setOutsideTouchable(true);
				// 获取窗体显示的布局的长宽高,然后设置偏移量就能显示在指定控件的上方了   测量出布局的宽高
				inflate.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
				popupWindow.showAsDropDown(mHolder.item_image_0, 0, (int) -(inflate.getMeasuredHeight() + mHolder.textViewDel.getHeight()));
				//Glide.with(mcontext).load(newsList.get(position).getMiddle_image().getUrl()).centerCrop().into(photoViewimageview);
				Glide.with(mcontext).load(getItem(position).getImage_list().get(0).getUrl()).centerCrop().into(photoViewimageview);

			}
		});




		//获取position对应的数据
		TuijianBean.DataBean news = getItem(position);

		mHolder.item_title.setText(news.getTitle()+"");
		mHolder.item_lable.setText(news.getSource());
		mHolder.item_comment_count.setText("评论" + news.getComment_count());
		List<TuijianBean.DataBean.ImageBean> imgUrlList = news.getImage_list();
		mHolder.item_comment_count.setVisibility(View.VISIBLE);

		if(imgUrlList !=null && imgUrlList.size() !=0){
			if(imgUrlList.size() == 3){
				mHolder.right_image.setVisibility(View.GONE);
				mHolder.item_image_layout.setVisibility(View.VISIBLE);
				Glide.with(mcontext).load(imgUrlList.get(0).getUrl()).override(250, 180).centerCrop().into(mHolder.item_image_0);
				Glide.with(mcontext).load(imgUrlList.get(1).getUrl()).override(250, 180).centerCrop().into(mHolder.item_image_1);
				Glide.with(mcontext).load(imgUrlList.get(2).getUrl()).override(250, 180).centerCrop().into(mHolder.item_image_2);
			}
		}else if (newsList.get(position).getMiddle_image()!=null){
			mHolder.right_image.setVisibility(View.VISIBLE);
			mHolder.item_image_layout.setVisibility(View.GONE);
			Glide.with(mcontext).load(newsList.get(position).getMiddle_image().getUrl()).centerCrop().into(mHolder.right_image);

		}


		mHolder.textViewDel.setVisibility(View.VISIBLE);

		mHolder.textViewDel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				View inflate = LayoutInflater.from(mcontext).inflate(R.layout.pop, null);
				TextView textView = (TextView) inflate.findViewById(R.id.buganxingqu);

				popupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);
				ColorDrawable dw = new ColorDrawable(0x10ab82ff);
				popupWindow.setBackgroundDrawable(dw);
				popupWindow.setOutsideTouchable(true);
				// 获取窗体显示的布局的长宽高,然后设置偏移量就能显示在指定控件的上方了   测量出布局的宽高
				inflate.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
				popupWindow.showAsDropDown(mHolder.textViewDel, 0, (int) -(inflate.getMeasuredHeight() + mHolder.textViewDel.getHeight()));
				textView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						newsList.remove(position);
						notifyDataSetChanged();
						popupWindow.dismiss();
					}
				});
			}
		});
		return view;
	}

	static class ViewHolder {
		//title
		TextView item_title;
		//新闻源
		TextView item_media_name;
		//类似推广之类的标签
		TextView item_lable;
		//评论数量
		TextView item_comment_count;
		//右边图片
		ImageView right_image;
		//3张图片布局
		LinearLayout item_image_layout; //3张图片时候的布局
		ImageView item_image_0;
		ImageView item_image_1;
		ImageView item_image_2;
		TextView textViewDel ;
		//大图的图片的话布局
		ImageView large_image;
		//评论布局
		RelativeLayout comment_layout;
		TextView comment_content;
	}
}
