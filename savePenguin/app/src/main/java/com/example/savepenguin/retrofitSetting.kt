package com.example.savepenguin

class retrofitSetting {

    companion object {

        private var ipv4 = "192.168.0.3"
        private var baseUrl = "http://" + ipv4 + ":8060/"

        fun getBaseurl(): String {
            return baseUrl.toString()
        }

    }
}