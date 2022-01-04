package com.example.codewithandroid.uploadvideovolley;

import android.content.Context;
import android.content.SharedPreferences;

public class VideoModal {

    Context context;
private String videourl;



    SharedPreferences sharedPreferences;



    public VideoModal(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("video_Details",Context.MODE_PRIVATE);
    }



    public String getVideourl() {
        videourl= sharedPreferences.getString("videourl","");
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    sharedPreferences.edit().putString("videourl",videourl).commit();
    }
}
