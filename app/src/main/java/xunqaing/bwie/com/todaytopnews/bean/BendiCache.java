package xunqaing.bwie.com.todaytopnews.bean;

import org.xutils.db.annotation.Column;

import static android.R.attr.name;
import static android.os.Build.VERSION_CODES.N;

/**
 * Created by Administrator on 2017/5/11 0011.
 */

public class BendiCache {

    @Column(name="id",isId = true,autoGen = true)
    private int id;
    @Column(name = "data")
    private String datas;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatas() {
        return datas;
    }

    public void setDatas(String datas) {
        this.datas = datas;
    }
}
