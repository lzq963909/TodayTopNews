package xunqaing.bwie.com.todaytopnews.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.util.List;

import static android.R.attr.id;
import static android.R.attr.name;
import static com.igexin.push.core.g.S;

/**
 * Created by Administrator on 2017/5/14 0014.
 */
public class UserNewsCategory {

    private String username;
    private List<MyCateGory> newsCategoryList;

    public UserNewsCategory( String username, List<MyCateGory> newsCategoryList) {
        this.username = username;
        this.newsCategoryList = newsCategoryList;
    }

    public UserNewsCategory() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<MyCateGory> getNewsCategoryList() {
        return newsCategoryList;
    }

    public void setNewsCategoryList(List<MyCateGory> newsCategoryList) {
        this.newsCategoryList = newsCategoryList;
    }
}
