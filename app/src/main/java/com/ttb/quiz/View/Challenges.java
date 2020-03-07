package com.ttb.quiz.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ttb.quiz.Adapter.ChallengesAdapter;
import com.ttb.quiz.Adapter.GameAdapter;
import com.ttb.quiz.Models.Result_model;
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

public class Challenges extends AppCompatActivity {


    RecyclerView recyclerView_challenges;
    List challengeslist;
    ChallengesAdapter challengesAdapter;


    TextView txt_tittle;
    ImageView img_back, img_setting;
    Session session;
    ProgressDialog pd;
    @Override
    protected void onResume() {
        super.onResume();
        txt_tittle.setText("Challenges");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);
        session=(Session)getApplicationContext();
        txt_tittle = (TextView) findViewById(R.id.txt_tittle);
        img_back = (ImageView) findViewById(R.id.img_back);
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
        get_challenges();
        ///////////////////////////////////////////




    }

    public void get_challenges() {
        if (!Utils.isOnline(this)) {
            Utils.nointernet(this);
        } else {
            pd = new ProgressDialog(this);
            pd.setMessage("Please Wait..");
            pd.show();
            pd.setCancelable(true);
            challengeslist = new ArrayList();
            Call<ResponseBody> call = RetrofitClient.getClient().get_challenge(session.getId());
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
                            if (jo.getBoolean("success")) {
                                JSONArray jsonArray2 = jo.getJSONArray("data");
                                for (int i=0;i<jsonArray2.length();i++) {
                                    JSONObject dataobj = jsonArray2.getJSONObject(i);
                                    challengeslist.add(new Result_model(
                                            dataobj.optString("user_id"),
                                            dataobj.optString("id"),
                                            dataobj.optString("topicid"),
                                            dataobj.optString("subtopicid"),
                                            dataobj.optString("title"),
                                            dataobj.optString("name"),
                                            dataobj.optString("country"),
                                            dataobj.optString("ranking"),
                                            dataobj.optString("profile_pic"),
                                            dataobj.optString("country_flag"),
                                            dataobj.optString("result"),
                                            "undefine"
                                    ));
                                }


                                //   txt_level_vs_team.setText(dataobj.optString("user_level"));
                            }
                            recyclerView_challenges = (RecyclerView)findViewById(R.id.recyclerView_challenges);
                            challengesAdapter = new ChallengesAdapter(getApplicationContext(), challengeslist,Challenges.this);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false);
                            recyclerView_challenges.setLayoutManager(linearLayoutManager);
                            recyclerView_challenges.setItemAnimator(new DefaultItemAnimator());
                            recyclerView_challenges.setAdapter(challengesAdapter);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Not Working", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    pd.dismiss();
                    if (t.getMessage().equals("connect timed out")) {
                        Utils.nointernet(Challenges.this);
                    }
                }
            });
        }
    }
    public void accept_challenge(String game_id,String topic_id,String sub_topic_id,String
            u_id,String name,String country_flag,String country , String profile_pic ){
        session.setGame_id(game_id);
        session.setTopic_id(topic_id);
        session.setSub_topic_id(sub_topic_id);
        session.setC_id(u_id);
        session.setC_name(name);
        session.setCountry_flag(country_flag);
        session.setCountry(country);
        session.setC_profile_pic(profile_pic);
        if (!Utils.isOnline(this)) {
            Utils.nointernet(this);
        } else {
            pd = new ProgressDialog(this);
            pd.setMessage("Please Wait..");
            pd.show();
            pd.setCancelable(true);
           // Toast.makeText(getApplicationContext(),game_id+"  "+session.getId(),Toast.LENGTH_LONG).show();
            Call<ResponseBody> call = RetrofitClient.getClient().accept_challenge(session.getId(),game_id);
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
                            if (jo.getBoolean("success")) {
                                if(jo.optString("message").equals("Game Started")){
                                    session.setGame_player_type("challenger");
                                    startActivity(new Intent(getApplicationContext(),Start_Quiz.class));
                                    overridePendingTransition(R.anim.enter,R.anim.exit);
                                    finish();
                                }else {
                                    Toast.makeText(getApplicationContext(),"Game Not Started",Toast.LENGTH_LONG).show();
                                }
                                //   txt_level_vs_team.setText(dataobj.optString("user_level"));
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
                    pd.dismiss();
                    if (t.getMessage().equals("connect timed out")) {
                        Utils.nointernet(Challenges.this);
                    }
                }
            });
        }
    }
    public void deny_challenge(String game_id,String topic_id,String sub_topic_id){
        session.setGame_id(game_id);
        session.setTopic_id(topic_id);
        session.setSub_topic_id(sub_topic_id);
        if (!Utils.isOnline(this)) {
            Utils.nointernet(this);
        } else {
            pd = new ProgressDialog(this);
            pd.setMessage("Please Wait..");
            pd.show();
            pd.setCancelable(true);
            // Toast.makeText(getApplicationContext(),game_id+"  "+session.getId(),Toast.LENGTH_LONG).show();
            Call<ResponseBody> call = RetrofitClient.getClient().deny_challenge(session.getId(),game_id);
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
                            if (jo.getBoolean("success")) {
                                get_challenges();
                                //   txt_level_vs_team.setText(dataobj.optString("user_level"));
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
                    pd.dismiss();
                    if (t.getMessage().equals("connect timed out")) {
                        Utils.nointernet(Challenges.this);
                    }
                }
            });
        }
    }


}
