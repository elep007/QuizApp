package com.ttb.quiz.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.ttb.quiz.R;
import com.ttb.quiz.connections.SharedPreferencesHandler;
import com.ttb.quiz.services.MyService;

public class Settings extends AppCompatActivity {
    LinearLayout ll_logout, ll_send_feedback, ll_sound, ll_profile;
    TextView txt_tittle;
    ImageView img_back, img_setting;
    ToggleButton toggleButton1, toggleButton2, toggleButton3;
    SharedPreferencesHandler sharedPreferencesHandler;
    String background_music;
    String music;

    @Override
    protected void onResume() {
        super.onResume();
        txt_tittle.setText("Settings");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sharedPreferencesHandler = new SharedPreferencesHandler(this);
        background_music = sharedPreferencesHandler.ReadPreferences("background_music");
        img_back = (ImageView) findViewById(R.id.img_back);
        img_setting = (ImageView) findViewById(R.id.img_setting);
        img_setting.setVisibility(View.GONE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        txt_tittle = (TextView) findViewById(R.id.txt_tittle);
        //////////////////////////
        ll_send_feedback = (LinearLayout) findViewById(R.id.ll_send_feedback);
        ll_profile = (LinearLayout) findViewById(R.id.ll_profile);
        ll_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        ll_send_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:karimov.e@celt.az")));
            }
        });
        ll_logout = (LinearLayout) findViewById(R.id.ll_logout);
        ll_sound = (LinearLayout) findViewById(R.id.ll_sound);
        toggleButton1 = (ToggleButton) findViewById(R.id.toggleButton1);
        if (background_music.equals("true")) {
            toggleButton1.setChecked(true);
            music="true";
        } else {
            music="false";
            toggleButton1.setChecked(false);
        }
        toggleButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toggleButton1.isChecked()) {
                    music="true";
                    sharedPreferencesHandler.WritePreference("background_music", "true");
                    startService(new Intent(getApplicationContext(), MyService.class));
                } else {
                    music="false";
                    sharedPreferencesHandler.WritePreference("background_music", "false");
                    stopService(new Intent(getApplicationContext(), MyService.class));
                }
            }
        });
        ll_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferencesHandler.ClearPreferences();
                sharedPreferencesHandler.WritePreference("background_music", music);
                startActivity(new Intent(getApplicationContext(), Login.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
                finishAffinity();

            }
        });
    }
}
