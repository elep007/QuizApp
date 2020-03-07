package com.ttb.quiz.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ttb.quiz.Adapter.Your_achivementsAdapter;
import com.ttb.quiz.Models.Achivement_model;
import com.ttb.quiz.R;
import com.ttb.quiz.Session;
import com.ttb.quiz.connections.RetrofitClient;
import com.ttb.quiz.connections.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Achievements extends AppCompatActivity {

    RecyclerView recyclerView_achivement;
    List achivementlist = new ArrayList();
    Your_achivementsAdapter your_achivementsAdapter;
    TextView txt_tittle;
    ImageView img_back, img_setting;
    ProgressDialog pd;
    List topiclist;
    Session session;
    RelativeLayout rl_no_data_achievements;

    @Override
    protected void onResume() {
        super.onResume();
        txt_tittle.setText("Achievements");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);
        session = (Session) getApplicationContext();
        //////title bar///////////////
        img_back = (ImageView) findViewById(R.id.img_back);
        img_setting = (ImageView) findViewById(R.id.img_setting);
        img_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Settings.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
//        img_setting.setVisibility(View.GONE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        txt_tittle = (TextView) findViewById(R.id.txt_tittle);
        rl_no_data_achievements=(RelativeLayout)findViewById(R.id.rl_no_data_achievements);

        //////////////////
        recyclerView_achivement = (RecyclerView) findViewById(R.id.recyclerView_achivement);
        get_achivements();
    }

    public void get_achivements() {
        if (!Utils.isOnline(this)) {
            Utils.nointernet(this);
        } else {
            pd = new ProgressDialog(this);
            pd.setMessage("Please Wait..");
            pd.show();
            pd.setCancelable(true);
            Call<ResponseBody> call = RetrofitClient.getClient().get_acheivements(session.getId());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    pd.dismiss();
                    if (response.isSuccessful()) {
                        try {
                            String result = response.body().string();
                            JSONObject jsonObject;
                            JSONArray jsonArray;
                            jsonObject = new JSONObject(result);
                            jsonArray = jsonObject.getJSONArray("response");
                            JSONObject jo = jsonArray.getJSONObject(0);
                            String win = jo.optString("total_winning");
                            String lose = jo.optString("total_loose");
                            String total_game_played = jo.optString("total_game_played");
                            if (jo.getBoolean("success")) {
                                JSONArray jsonArray2 = jo.getJSONArray("category");
                                topiclist = new ArrayList();
                                int gameplayed = jo.optInt("total_game_played");
                                for (int i = 0; i < jsonArray2.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray2.optJSONObject(i);
                                    int check = jsonObject1.optInt("games_to_play");
                                    int per = (gameplayed * 100) / check;
                                    if (check < gameplayed) {
                                        gameplayed = gameplayed - check;
                                    }
                                    achivementlist.add(new Achivement_model(
                                            jsonObject1.optString("title"),
                                            jsonObject1.optString("image"),
                                            jsonObject1.optString("games_to_play"),
                                            jsonObject1.optString("coins"),
                                            per
                                    ));
                                    your_achivementsAdapter = new Your_achivementsAdapter(getApplicationContext(), achivementlist);
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false);
                                    recyclerView_achivement.setLayoutManager(linearLayoutManager);
                                    recyclerView_achivement.setItemAnimator(new DefaultItemAnimator());
                                    recyclerView_achivement.setAdapter(your_achivementsAdapter);
                                }
                            } else {
                                rl_no_data_achievements.setVisibility(View.VISIBLE);
//                                Toast.makeText(getApplicationContext(), jo.getString("message") + "", Toast.LENGTH_LONG).show();
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
                    pd.dismiss();
                    if (t.getMessage().equals("connect timed out")) {
                        Utils.nointernet(Achievements.this);
                    }
                }
            });
        }
    }


}