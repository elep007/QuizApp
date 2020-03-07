package com.ttb.quiz.View;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class Won_page extends AppCompatActivity {


    Button btn_new_game, btn_rematch;
    de.hdodenhof.circleimageview.CircleImageView play_profile_image, play_won_image;
    ImageView play_country,won_country;
    TextView txt_username_vs_play, txt_username_play;
    Button btn_leadboard;
    TextView txt_star_play, txt_gameanswer_play, txt_dollar_play, txt_play_winbonus;

    TextView txt_tittle;
    ImageView img_back, img_setting;
    Session session;
    ProgressDialog pd;
    String point,current_coins;
    ImageView img_home;


    @Override
    protected void onResume() {
        super.onResume();
        txt_tittle.setText("Result "+session.getGamae_result());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_won_page);
        session=(Session)getApplicationContext();
        point=getIntent().getExtras().getString("points");
        current_coins=getIntent().getExtras().getString("current_coins");
        img_back = (ImageView) findViewById(R.id.img_back);
        img_home=(ImageView)findViewById(R.id.img_home);
        img_setting = (ImageView) findViewById(R.id.img_setting);
        img_back.setVisibility(View.GONE);
        img_home.setVisibility(View.VISIBLE);
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Home.class));
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
        ///////////////////////////
        btn_new_game = (Button) findViewById(R.id.btn_new_game);
        btn_rematch = (Button) findViewById(R.id.btn_rematch);
        play_won_image = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.play_won_image);
        won_country = (ImageView) findViewById(R.id.won_country);
        play_profile_image = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.play_profile_image);
        play_country = (ImageView) findViewById(R.id.play_country);
        txt_username_vs_play = (TextView) findViewById(R.id.txt_username_vs_play);
        txt_username_play = (TextView) findViewById(R.id.txt_username_play);
        btn_leadboard = (Button) findViewById(R.id.btn_leadboard);
        btn_leadboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Leaderboard.class));
                overridePendingTransition(R.anim.enter,R.anim.exit);
            }
        });
        txt_star_play = (TextView) findViewById(R.id.txt_star_play);

        txt_gameanswer_play = (TextView) findViewById(R.id.txt_gameanswer_play);
        txt_dollar_play = (TextView) findViewById(R.id.txt_dollar_play);
        txt_play_winbonus = (TextView) findViewById(R.id.txt_play_winbonus);
        Picasso.with(this).load(Utils.user_image_url + session.getProfile_pic())
                .fit()
                .placeholder(R.drawable.ic_face)
                .into(play_profile_image);
        Picasso.with(this).load(Utils.country_flag_url + session.getCountry_flag())
                .fit()
                .placeholder(R.drawable.circle)
                .into(play_country);


        Picasso.with(this).load(Utils.user_image_url + session.getC_profile_pic())
                .fit()
                .placeholder(R.drawable.ic_face)
                .into(play_won_image);
        Picasso.with(this).load(Utils.country_flag_url + session.getC_country_flag())
                .fit()
                .placeholder(R.drawable.circle)
                .into(won_country);
        txt_username_play.setText(session.getName());
        txt_username_vs_play.setText(session.getC_name());

        next_screen();

    }

    public void next_screen() {
        btn_rematch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_challenge(session.getTopic_id(),"00","challenge",session.getC_id());

            }
        });
        btn_new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), All_Topic_Internal.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });

        txt_star_play.setText(point);
        txt_dollar_play.setText(current_coins);

    }
    ///////////////////////////////////////////////////
    public void add_challenge(String topic_id,String points,String result,String competitor_id) {
//        pd = new ProgressDialog(this);
//        pd.setMessage("Please Wait..");
//        pd.show();
//        pd.setCancelable(true);
        if (!Utils.isOnline(this)) {
            Utils.nointernet(this);
        } else {
            Call<ResponseBody> call = RetrofitClient.getClient().add_challenge(session.getId(),topic_id,session.getSub_topic_id(),competitor_id);
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
                                int game_id = jo.optInt("game_id");
                                session.setGame_id(game_id+"");
                                startActivity(new Intent(getApplicationContext(),Start_Quiz.class));
                                overridePendingTransition(R.anim.enter, R.anim.exit);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
//                        Toast.makeText(getApplicationContext(), "Not Working", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if (t.getMessage().equals("connect timed out")) {
                        Utils.nointernet(Won_page.this);
                    }
                }
            });
        }
    }




}
