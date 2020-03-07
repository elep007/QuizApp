package com.ttb.quiz.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ttb.quiz.R;

public class Privacypolicy extends AppCompatActivity {

    TextView txt_name;
    ImageView iv_backpressed;

    @Override
    protected void onResume() {
        super.onResume();
        txt_name.setText("Privacy Policy");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacypolicy);
        iv_backpressed = (ImageView) findViewById(R.id.iv_backpressed);
        iv_backpressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        txt_name = (TextView) findViewById(R.id.txt_name);
    }
}
