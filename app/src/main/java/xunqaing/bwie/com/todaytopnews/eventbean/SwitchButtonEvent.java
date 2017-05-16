package xunqaing.bwie.com.todaytopnews.eventbean;

/**
 * Created by
 * Chenxin
 * on 2017/5/10.
 */

public class SwitchButtonEvent {


    //true 代表白天模式  false为夜间
    private boolean isWhite;

    public SwitchButtonEvent() {
    }

    public SwitchButtonEvent(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void setWhite(boolean white) {
        isWhite = white;
    }
}
