package xunqaing.bwie.com.todaytopnews.eventbean;

/**
 * Created by Administrator on 2017/5/16 0016.
 */

public class IsLoginEvent {
    private boolean isLogin;

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public IsLoginEvent(boolean isLogin) {
        this.isLogin = isLogin;
    }
}
