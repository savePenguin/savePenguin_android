package com.example.savepenguin.model;

public class Point {
    private int point;
    private String pointDate;
    private String pointFrom;
    private String tumbler;

    public Point(int point, String pointDate, String pointFrom, String tumbler) {
        this.point = point;
        this.pointDate = pointDate;
        this.pointFrom = pointFrom;
        this.tumbler = tumbler;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getPointDate() {
        return pointDate;
    }

    public void setPointDate(String pointDate) {
        this.pointDate = pointDate;
    }

    public String getPointFrom() {
        return pointFrom;
    }

    public void setPointFrom(String pointFrom) {
        this.pointFrom = pointFrom;
    }

    public String getTumbler() {
        return tumbler;
    }

    public void setTumbler(String tumbler) {
        this.tumbler = tumbler;
    }
}
