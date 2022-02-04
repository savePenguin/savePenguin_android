package com.example.savepenguin.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class QR {
    private String qrName;
    private String about;
    private Bitmap profile;

    public QR(String qrName, String about, Bitmap profile) {
        this.qrName = qrName;
        this.about = about;
        this.profile = profile;
    }

    public String getQrName() {
        return qrName;
    }

    public void setQrName(String qrName) {
        this.qrName = qrName;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Bitmap getProfile() {
        return profile;
    }

    public void setProfile(Bitmap profile) {
        this.profile = profile;
    }
}
