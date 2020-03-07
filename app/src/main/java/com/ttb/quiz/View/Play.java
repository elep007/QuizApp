package com.ttb.quiz.View;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ttb.quiz.R;
import com.ttb.quiz.Session;
import com.ttb.quiz.connections.RetrofitClient;
import com.ttb.quiz.connections.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Play extends AppCompatActivity {

    Button btn_cancel;
    de.hdodenhof.circleimageview.CircleImageView  play_profile_image;
    TextView txt_username_play, txt_country_play, txt_trophy_play, txt_level_play;
    de.hdodenhof.circleimageview.CircleImageView play_profile_image_vs_team;
    ImageView play_country_image_vs_team ,play_country;
    TextView txt_level_vs_team, txt_trophy_vs_team, txt_country_vs_team, txt_username_vs_team;
    FrameLayout fl_bottom, fl_top;
    ImageView iv_trophy, iv_vs_trophy;
    LinearLayout ll_level, ll_vs_level;
    TextView txt_tittle;
    ImageView img_back, img_setting;
    Session session;
    ProgressBar search_pbar;
    boolean change=true;


    @Override
    protected void onResume() {
        super.onResume();
        txt_tittle.setText("Play");
        Picasso.with(this).load(Utils.user_image_url + session.getProfile_pic())
                .fit()
                .placeholder(R.drawable.ic_face)
                .into(play_profile_image);

        Picasso.with(this).load(Utils.country_flag_url + session.getCountry_flag())
                .fit()
                .placeholder(R.drawable.circle)
                .into(play_country);

        txt_username_play.setText(session.getName());
        txt_country_play.setText(session.getCountry());
        txt_trophy_play.setText(session.getRanking());
        //txt_level_play.setText(session.getUser_level());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        session=(Session)getApplicationContext();
        img_back = (ImageView) findViewById(R.id.img_back);
        search_pbar=(ProgressBar)findViewById(R.id.search_pbar);
        img_setting = (ImageView) findViewById(R.id.img_setting);
        img_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Settings.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);

            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        txt_tittle = (TextView) findViewById(R.id.txt_tittle);
        //////////
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        txt_username_play = (TextView) findViewById(R.id.txt_username_play);
        txt_country_play = (TextView) findViewById(R.id.txt_country_play);
        txt_trophy_play = (TextView) findViewById(R.id.txt_trophy_play);
        txt_level_play = (TextView) findViewById(R.id.txt_level_play);
        play_country = (ImageView) findViewById(R.id.play_country);
        play_country_image_vs_team = (ImageView) findViewById(R.id.play_country_image_vs_team);
        play_profile_image_vs_team = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.play_profile_image_vs_team);
        play_profile_image = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.play_profile_image);
        txt_level_vs_team = (TextView) findViewById(R.id.txt_level_vs_team);
        txt_trophy_vs_team = (TextView) findViewById(R.id.txt_trophy_vs_team);
        txt_country_vs_team = (TextView) findViewById(R.id.txt_country_vs_team);
        txt_username_vs_team = (TextView) findViewById(R.id.txt_username_vs_team);
        fl_top = (FrameLayout) findViewById(R.id.fl_top);
        fl_bottom = (FrameLayout) findViewById(R.id.fl_bottom);
        ll_level = (LinearLayout) findViewById(R.id.ll_level);
        ll_vs_level = (LinearLayout) findViewById(R.id.ll_vs_level);
        iv_trophy = (ImageView) findViewById(R.id.iv_trophy);
        iv_vs_trophy = (ImageView) findViewById(R.id.iv_vs_trophy);
        next();
        ObjectAnimator moveAnim_vs_team = ObjectAnimator.ofFloat(fl_top, "Y", 20);
        moveAnim_vs_team.setDuration(3000);
        moveAnim_vs_team.setInterpolator(new BounceInterpolator());
        moveAnim_vs_team.start();
        ObjectAnimator moveAnim = ObjectAnimator.ofFloat(fl_bottom, "Y", 20);
        moveAnim.setDuration(3000);
        moveAnim.setInterpolator(new BounceInterpolator());
        moveAnim.start();
//        ObjectAnimator moveAnim_level = ObjectAnimator.ofFloat(ll_level, "X", 10);
//        moveAnim_level.setDuration(3000);
//        moveAnim_level.setInterpolator(new BounceInterpolator());
//        moveAnim_level.start();
//        ObjectAnimator moveAnim_vs_level = ObjectAnimator.ofFloat(ll_vs_level, "X", 10);
//        moveAnim_vs_level.setDuration(2000);
//        moveAnim_vs_level.setInterpolator(new BounceInterpolator());
//        moveAnim_vs_level.start();
        search_users();
      }

    public void next() {
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change=false;
             onBackPressed();
            }
        });
    }
    public void search_users() {
        if (!Utils.isOnline(this)) {
            Utils.nointernet(this);
        } else {
            Call<ResponseBody> call = RetrofitClient.getClient().search_users(session.getId());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            String result = response.body().string();
                            JSONObject jsonObject;
                            JSONArray jsonArray;
                            jsonObject = new JSONObject(result);
                            jsonArray = jsonObject.getJSONArray("response");
                            JSONObject jo = jsonArray.getJSONObject(0);
                            if (jo.getBoolean("success")) {

                                JSONArray jsonArray2 = jo.getJSONArray("data");
                                JSONObject dataobj = jsonArray2.getJSONObject(0);
                                session.setC_id(dataobj.optString("id"));
                                session.setC_name(dataobj.optString("name"));
                                session.setC_country_flag(dataobj.optString("country_flag"));
                                session.setC_country(dataobj.optString("country"));
                                session.setC_profile_pic(dataobj.optString("profile_pic"));
                                Picasso.with(Play.this).load(Utils.user_image_url + dataobj.optString("profile_pic"))
                                        .fit()
                                        .placeholder(R.drawable.ic_face)
                                        .into(play_profile_image_vs_team);
                                Picasso.with(Play.this).load(Utils.country_flag_url + dataobj.optString("country_flag"))
                                        .fit()
                                        .placeholder(R.drawable.circle)
                                        .into(play_country_image_vs_team);
                                txt_username_vs_team.setText(dataobj.optString("name"));
                                txt_country_vs_team.setText(dataobj.optString("country"));
                                txt_trophy_vs_team.setText(dataobj.optString("ranking"));
                                add_results(session.getTopic_id(),"00","play",session.getC_id());
                             //   txt_level_vs_team.setText(dataobj.optString("user_level"));
                            }else {
                                search_users();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Not Working", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if (t.getMessage().equals("connect timed out")) {
                        Utils.nointernet(Play.this);
                    }
                }
            });
        }
    }
    ///////////////////////////////////////////////////
    public void add_results(String topic_id,String points,String result,String competitor_id) {
//        pd = new ProgressDialog(this);
//        pd.setMessage("Please Wait..");
//        pd.show();
//        pd.setCancelable(true);
        if (!Utils.isOnline(this)) {
            Utils.nointernet(this);
        } else {
            Call<ResponseBody> call = RetrofitClient.getClient().add_results(session.getId(),topic_id,session.getSub_topic_id(),points,result,competitor_id);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        //    pd.dismiss();
                        try {
                            String result = response.body().string();
                            JSONObject jsonObject;
                            JSONArray jsonArray;
                            jsonObject = new JSONObject(result);
                            jsonArray = jsonObject.getJSONArray("response");
                            JSONObject jo = jsonArray.getJSONObject(0);

                            if (jo.getBoolean("success")) {
                                String game_id = jo.optString("game_id");
                                session.setGame_id(game_id);
                                session.setGame_player_type("user");

                                CountDownTimer  timer = new CountDownTimer(2000, 1000) {
                                    public void onTick(long millisUntilFinished) {
                                        //tx_timer.setText("seconds remaining: " + millisUntilFinished / 1000);
                                    }

                                    public void onFinish() {
                                        if(change) {
                                            search_pbar.setVisibility(View.GONE);
                                            startActivity(new Intent(getApplicationContext(), Start_Quiz.class));
                                            overridePendingTransition(R.anim.enter, R.anim.exit);
                                            finish();
                                        }
                                    }
                                }.start();


                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Not Working", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if (t.getMessage().equals("connect timed out")) {
                        Utils.nointernet(Play.this);
                    }
                }
            });
        }
    }

}
