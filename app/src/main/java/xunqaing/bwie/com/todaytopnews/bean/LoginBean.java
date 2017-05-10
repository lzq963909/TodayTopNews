package xunqaing.bwie.com.todaytopnews.bean;

/**
 * Created by Administrator on 2017/5/10 0010.
 */

public class LoginBean {

    /**
     * ret_code : 200
     * ret_msg : 登录成功
     * result : {"id":4,"password":"123456","username":"13353910788"}
     */

    private int ret_code;
    private String ret_msg;
    private ResultBean result;

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public String getRet_msg() {
        return ret_msg;
    }

    public void setRet_msg(String ret_msg) {
        this.ret_msg = ret_msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 4
         * password : 123456
         * username : 13353910788
         */

        private int id;
        private String password;
        private String username;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
