package com.ttb.quiz.services;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ttb.quiz.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MyService extends Service {
    //creating a mediaplayer object
    public static MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (player==null) {

            //getting systems default ringtone
            player = MediaPlayer.create(this, R.raw.yaariringtone);
            //setting loop play to true-
            //this will make the ringtone continuously playing
            player.setLooping(true);

            //staring the player
            player.start();
            //we have some options for service
            //start sticky means service will be explicity started and stopped
        }
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player!=null) {
            if (player.isPlaying()) {
                player.stop();
                player=null;
            }
        }
        //stopping the player when service is destroyed
    }


    public void startTimer(boolean b) {
        if(player==null){

        }else {
            if (b) {
                if (player.isPlaying()) {
                    player.pause();
                }
            } else {
                if (!player.isPlaying()) {
                    player.start();
                }
            }
        }
       // Log.i("isBackground",b+"");
    }


}
