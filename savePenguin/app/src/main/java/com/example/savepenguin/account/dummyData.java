package com.example.savepenguin.account;

import java.util.ArrayList;

public class dummyData {
    public ArrayList<User> users;

    public dummyData() {
        users = new ArrayList<>();
        users.add(new User("jobum", "0923", "brian@naver.com", "조범수", 100));
        users.add(new User("bum", "0923", "brian@naver.com", "조범수", 0));
        users.add(new User("jo", "0923", "brian@naver.com", "조범수", 0));
        users.add(new User("1", "1", "brian@naver.com", "조범수", 1000));
    }

}
