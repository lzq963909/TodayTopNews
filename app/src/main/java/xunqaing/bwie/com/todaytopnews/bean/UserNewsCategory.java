package xunqaing.bwie.com.todaytopnews.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.util.List;

import static android.R.attr.name;
import static com.igexin.push.core.g.S;

/**
 * Created by Administrator on 2017/5/14 0014.
 */
@Table(name = "UserNewsCategory")
public class UserNewsCategory {

    @Column(name = "id" ,isId = true , autoGen = true)
    private int id;
    private String username;
    private List<String> newsCategoryList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getNewsCategoryList() {
        return newsCategoryList;
    }

    public void setNewsCategoryList(List<String> newsCategoryList) {
        this.newsCategoryList = newsCategoryList;
    }
}
