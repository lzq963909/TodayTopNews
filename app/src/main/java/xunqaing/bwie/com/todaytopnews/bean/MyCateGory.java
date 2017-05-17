package xunqaing.bwie.com.todaytopnews.bean;

/**
 * Created by Administrator on 2017/5/16 0016.
 */
public class MyCateGory {
    private String name;
    private String category;

    public MyCateGory(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public MyCateGory() {

    }
}
