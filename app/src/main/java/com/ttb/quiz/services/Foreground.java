package com.ttb.quiz.services;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Foreground implements Application.ActivityLifecycleCallbacks {

    private static Foreground instance;
    private boolean foreground;

    public static void init(Application app){
        if (instance == null){
            instance = new Foreground();
            app.registerActivityLifecycleCallbacks(instance);
        }
    }

    public boolean isForeground(){
        return foreground;
    }

    public boolean isBackground(){
        return !foreground;
    }

    public void onActivityPaused(Activity activity){
        foreground = false;
        startTimer();
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    public void onActivityResumed(Activity activity){
        foreground = true;
        //startTimer();
    }
    private Timer timer;
    private TimerTask timerTask;
    public void startTimer() {
        if(timer==null) {
            timer = new Timer();
            timerTask = new TimerTask() {
                public void run() {
                    MyService myService = new MyService();
                    if (isBackground()) {

                    }
                    myService.startTimer(isBackground());
                    //Log.i("isBackground",isBackground()+"");
                }
            };
            timer.schedule(timerTask, 1000, 3000); //
        }
    }
}