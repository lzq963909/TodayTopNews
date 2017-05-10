package xunqaing.bwie.com.todaytopnews;

import android.app.Application;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;


public class IApplication extends Application {

    public DbManager.DaoConfig config;
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        init();

       initData();
        UMShareAPI.get(this);
        Config.DEBUG = true;
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
    }
    public void init() {
        try {

            File cacheDir = StorageUtils.getOwnCacheDirectory(this, "/SD");
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                    .threadPriority(Thread.NORM_PRIORITY - 2)
                    .denyCacheImageMultipleSizesInMemory()
                    .threadPoolSize(3)//线程池内加载的数量
                    .discCacheFileNameGenerator(new Md5FileNameGenerator())//diskCache() and diskCacheFileNameGenerator()调用相互重叠
                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                    .memoryCache(new LruMemoryCache((int) (6 * 1024 * 1024)))
                    .diskCache(new UnlimitedDiskCache(cacheDir))
                    .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000))//设置超时时间
                    .build();
            ImageLoader.getInstance().init(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     *圆角图片
     * */
    public static DisplayImageOptions MyDisplayImageOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.ic_launcher)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.ic_launcher)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
                .displayer(new RoundedBitmapDisplayer(360))//是否设置为圆角，弧度为多少
                //      .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                .build();//构建完成

        return options;
    }

    public void initData() {
                 config = new DbManager.DaoConfig();
                 config.setDbName("tj.db");
                 config.setDbVersion(1);
                 config.setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                        @Override
                         public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

             }
                    });
            }

}