package kr.co.wooltari.domain;

/**
 * Created by Kyung on 2017-11-28.
 */

public class UserDummy {
    public static Dummy data;

    static {
        data = new Dummy();
    }

    public static class Dummy {
        public String token = "0ff8d1bf221571083ab2dd59f116e986360f4316";
        public int pk = 14;
        public String user_type = "d";
        public String email = "leeseok8347@hanmail.net";
//        public String password = "dummy";
        public String nickname = "TestKyungSeok";
        public boolean is_active = true;
        public String data_joined = "2017-12-08T13:37:26.552140+09:00";
        public String profile = "http://bike.seoul.go.kr/data/data/1_3/c0109c.jpg";

        public String device_token="e8AiPo-cMOo:APA91bHJIdZFhzCYlTFuW_XPV-F0vgE3Nb9_XEk2BdfnmagqhWbDDba-_rbs9q3WM0ctS862tyc0UuTr9M3rLndNjw9qmcXd9gyZS7ZkR8SLnZ5Af0d__YtmYG1mexLsrAAB_uAEb18s";
    }
}
