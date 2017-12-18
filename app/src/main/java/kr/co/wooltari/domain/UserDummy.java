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
        public String token = "d5bf8f541404c1a86ab5f97a2a499f90a89b8c56";
        public int pk = 7;
        public String user_type = "d";
        public String email = "leeseok8347@naver.com";
//        public String password = "dummy";
        public String nickname = "KyungseokNaver";
        public boolean is_active = true;
        public String data_joined = "2017-12-17T16:37:30.158187+09:00";
        public String image = "http://www.doglost.co.uk/images/7000-dogs-2015.jpg";
        public String device_token="e8AiPo-cMOo:APA91bHJIdZFhzCYlTFuW_XPV-F0vgE3Nb9_XEk2BdfnmagqhWbDDba-_rbs9q3WM0ctS862tyc0UuTr9M3rLndNjw9qmcXd9gyZS7ZkR8SLnZ5Af0d__YtmYG1mexLsrAAB_uAEb18s";
    }
}
