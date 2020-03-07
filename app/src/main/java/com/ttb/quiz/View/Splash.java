package com.ttb.quiz.View;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.animation.BounceInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ttb.quiz.R;
import com.ttb.quiz.Session;
import com.ttb.quiz.View.Login;
import com.ttb.quiz.connections.RetrofitClient;
import com.ttb.quiz.connections.SharedPreferencesHandler;
import com.ttb.quiz.connections.Utils;
import com.ttb.quiz.services.MyService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    TextView txt_welcome, txt_splash;
    LinearLayout ll_splash;
    SharedPreferencesHandler sharedPreferencesHandler;
    String background_music;
    String check;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        session=(Session)getApplicationContext();
        FirebaseApp.initializeApp(this);
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        sharedPreferencesHandler = new SharedPreferencesHandler(this);
        background_music = sharedPreferencesHandler.ReadPreferences("background_music");
        check = sharedPreferencesHandler.ReadPreferences("user_data") + "";
        try {
            if (background_music.equals("true")) {
                startService(new Intent(getApplicationContext(), MyService.class));
            }

        } catch (Exception e) {
            sharedPreferencesHandler.WritePreference("background_music", "true");
            startService(new Intent(getApplicationContext(), MyService.class));
            session.setCoins("0");
        }

        try{
            if(sharedPreferencesHandler.ReadPreferences("coins").isEmpty()){
                session.setCoins("0");
            }else
                session.setCoins(sharedPreferencesHandler.ReadPreferences("coins") + "");
        }catch (Exception e){}

        txt_welcome = (TextView) findViewById(R.id.txt_welcome);
        txt_splash = (TextView) findViewById(R.id.txt_splash);
        ll_splash = (LinearLayout) findViewById(R.id.ll_splash);
        ObjectAnimator moveAnim = ObjectAnimator.ofFloat(txt_welcome, "Y", 50);
        moveAnim.setDuration(2500);
        moveAnim.setInterpolator(new BounceInterpolator());
        moveAnim.start();
        new Handler().postDelayed(new Runnable() {
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if (check.equals("null")) {
                    Intent mainIntent = new Intent(Splash.this, Login.class);
                    Splash.this.startActivity(mainIntent);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                    Splash.this.finish();
                } else {
                    Intent i = new Intent(Splash.this, Home.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);


        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.ttb.quiz",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//                Toast.makeText(this, "   " +Base64.encodeToString(md.digest(), Base64.DEFAULT), Toast.LENGTH_SHORT).show();
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

//        AnimateBell();
    }
    ////////////////// for shake textview//////////
//    public void AnimateBell() {
//        Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_animation);
//        LinearLayout imgBell = (LinearLayout) findViewById(R.id.ll_splash);
//        imgBell.setAnimation(shake);
//    }


}

