package xunqaing.bwie.com.todaytopnews.utils;

import java.util.Date;

/** * 
 * @author  ����Trydimire E-mail: 867133009@qq.com
 * @date ����ʱ�䣺2015-8-7 ����11:31:52 
 * @version 1.0 
 * @parameter 
 * @since 
 * @return  */
public class MyUrl {
	
	/**�ȴʵĽӿڵ�ַ **/
	public static final String HOT_WORDS_URL= "http://toutiao.com/hot_words/?_=";
	/**�Ƽ����� �ĵ�ַ **/
	public static final String ALL_NEWS_URL= "http://toutiao.com/api/article/recent/?source=2&category=__all__&max_behot_time=0&_=";
	/**�ȵ����� �ĵ�ַ **/
	public static final String HOT_NEWS_URL= "http://toutiao.com/api/article/recent/?source=2&category=news_hot&max_behot_time=0&_=";
	/**������ŵĵ�ַ **/
	public static final String SOCIETY_NEWS_URL= "http://toutiao.com/api/article/recent/?source=2&category=news_society&max_behot_time=0&_=";
	/**�������ŵĽӿڵ�ַ **/
	public static final String ENTERTAINMENT_NEWS_URL= "http://toutiao.com/api/article/recent/?source=2&category=news_entertainment&max_behot_time=0&_=";
	/**�Ƽ����ŵĽӿڵ�ַ **/
	public static final String TECH_NEWS_URL= "http://toutiao.com/api/article/recent/?source=2&category=news_car&max_behot_time=0&_=";
	/**�������ŵĽӿڵ�ַ**/
	public static final String NEWS_SPORTS = "http://toutiao.com/api/article/recent/?source=2&category=news_sports&max_behot_time=0&_=";
	
	/**����ѡ��Ľӿڵ�ַ**/
	public static final String NEWS_CATEGORY = "http://ic.snssdk.com/article/category/get/v2/?iid=2939228904";
	/**���г��еĽӿڵ�ַ(����ĸ��ʽ����)**/
	public static final String CITY_ADDRESS = "http://ic.snssdk.com/2/article/city/";
	/**�����м������µĽӿ�**/
	public static final String UPDATE_COUNTS="http://ic.snssdk.com/2/article/v27/refresh_tip/?min_behot_time=1439292102&iid=2939228904";
	
	private static  String urlone = "http://toutiao.com/api/article/recent/?source=2&category=";
	private static String urltwo = "&max_behot_time=0&_=";
	
	/**
	 * ����Ƶ�������ؽӿڵ�ַ
	 * �÷���ƴ�ӿ��Ի�ȡʵʱ����
	 * newstype ������ѡ��Ľӿ��л��
	 * */
	public   static String getUrl(String newstype) {
		String url = "";
		Date dt= new Date();
		Long time= dt.getTime();
		url = urlone + newstype + urltwo + time;
		return url;
	}
}
