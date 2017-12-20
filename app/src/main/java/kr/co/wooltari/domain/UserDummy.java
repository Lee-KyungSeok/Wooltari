package kr.co.wooltari.domain;

import java.io.File;

/**
 * Created by Kyung on 2017-11-28.
 */

public class UserDummy {
    public static Dummy data;

    static {
        data = new Dummy();
    }

    public static class Dummy {
        public String token = "881e720f84555bcef513049e5a2373c7eb81bd3c";
        public int pk = 6;
        public String user_type = "d";
        public String email = "leeseok8347@naver.com";
//        public String password = "dummy";
        public String nickname = "KyungseokNaver";
        public boolean is_active = true;
        public String data_joined = "2017-12-17T16:37:30.158187+09:00";
        public String image = "https://s3.ap-northeast-2.amazonaws.com/fastcampus-wooltari-files/static/placeholder/placeholder_human.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAJIMDKNNYFVXNQIYA%2F20171220%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Date=20171220T152240Z&X-Amz-Expires=3600&X-Amz-SignedHeaders=host&X-Amz-Signature=849a7daa5e3546bf047878952c1f092e9996d5988923038f4ffac2dbe26dbee3";
        public String device_token="e8AiPo-cMOo:APA91bHJIdZFhzCYlTFuW_XPV-F0vgE3Nb9_XEk2BdfnmagqhWbDDba-_rbs9q3WM0ctS862tyc0UuTr9M3rLndNjw9qmcXd9gyZS7ZkR8SLnZ5Af0d__YtmYG1mexLsrAAB_uAEb18s";
    }
}
