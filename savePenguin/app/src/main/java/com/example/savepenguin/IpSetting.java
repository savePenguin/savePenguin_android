package com.example.savepenguin;

public class IpSetting {
    private String ipv4 = "192.168.0.4";

    public String getIpv4() {
        return ipv4;
    }

    public String getBaseUrl() {
        return "http://" + ipv4 + ":8060";
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }
}
