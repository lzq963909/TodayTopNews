package xunqaing.bwie.com.todaytopnews.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Huangminghuan on 2017/5/10.
 */
@Table(name = "tuijian")
public class DbBean {
    @Column(name = "t_id",isId = true,autoGen = true)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "source")
    private String source;
    @Column(name = "comment_count")
    private int comment_count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }
}
