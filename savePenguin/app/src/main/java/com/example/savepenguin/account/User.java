package com.example.savepenguin.account;

public class User {
    private String userid;
    private String userpw;
    private String useremail;
    private String username;
    private int point;

    public User(String userid, String userpw, String useremail, String username, int point) {
        this.userid = userid;
        this.userpw = userpw;
        this.useremail = useremail;
        this.username = username;
        this.point = point;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserpw() {
        return userpw;
    }

    public void setUserpw(String userpw) {
        this.userpw = userpw;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public boolean isSameUser(User A) {
        if (A.userid.equals(this.userid) && A.userpw.equals(this.userpw)) {
            return true;
        } else {
            return false;
        }
    }
}
