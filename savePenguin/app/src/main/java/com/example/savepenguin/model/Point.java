package com.example.savepenguin.model;

public class Point {
    private int cuppoint;
    private String pointDate;
    private String pointLocation;
    private String qrname;
    private String username;

    public Point(int cuppoint, String pointDate, String pointLocation, String qrname, String username) {
        this.cuppoint = cuppoint;
        this.pointDate = pointDate;
        this.pointLocation = pointLocation;
        this.qrname = qrname;
        this.username = username;
    }

    public int getCuppoint() {
        return cuppoint;
    }

    public void setCuppoint(int cuppoint) {
        this.cuppoint = cuppoint;
    }

    public String getPointDate() {
        return pointDate;
    }

    public void setPointDate(String pointDate) {
        this.pointDate = pointDate;
    }

    public String getPointLocation() {
        return pointLocation;
    }

    public void setPointLocation(String pointLocation) {
        this.pointLocation = pointLocation;
    }

    public String getQrname() {
        return qrname;
    }

    public void setQrname(String qrname) {
        this.qrname = qrname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
