package xunqaing.bwie.com.todaytopnews.eventbean;

/**
 * Created by Administrator on 2017/5/16 0016.
 */

public class IsLoginEvent {
    private boolean isLogin;
    private String username;

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public IsLoginEvent(boolean isLogin, String username) {
        this.isLogin = isLogin;
        this.username = username;
    }
}
