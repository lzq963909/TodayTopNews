package xunqaing.bwie.com.todaytopnews.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.util.List;

import static android.R.attr.name;
import static com.igexin.push.core.g.S;

/**
 * Created by Administrator on 2017/5/14 0014.
 */
public class UserNewsCategory {

    private int id;
    private String username;
    private List<MyCateGory> newsCategoryList;

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


    private class MyCateGory {
        @Column(name = "category")
        private String category;
        @Column(name = "name")
        private String name;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
