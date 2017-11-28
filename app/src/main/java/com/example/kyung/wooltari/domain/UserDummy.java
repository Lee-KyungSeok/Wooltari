package com.example.kyung.wooltari.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyung on 2017-11-28.
 */

public class UserDummy {
    public static Dummy data;

    static {
        data = new Dummy();
    }

    public static class Dummy {
        public int pk = 0;
        public String user_type = "d";
        public String email = "dummy@d.gmail.com";
        public String password = "dummy";
        public String nickname = "dummy";
        public boolean is_active = true;
        public String data_joined = "2017-11-27T00:04:56.435930+09:00";
        public String profile = "http://bike.seoul.go.kr/data/data/1_3/c0109c.jpg";
    }
}
