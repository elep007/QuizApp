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

public class Games_Results extends AppCompatActivity {

    RecyclerView result_page_RecyclerView;
    List resultlist = new ArrayList();
    GameAdapter gameAdapter;
    TextView txt_tittle;
    ImageView img_back, img_setting;
    Session session;
    ProgressDialog pd;
    RelativeLayout rl_no_data;

    @Override
    protected void onResume() {
        super.onResume();
        txt_tittle.setText("Games Results");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games__results);
        session = (Session) getApplicationContext();
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
        rl_no_data = (RelativeLayout) findViewById(R.id.rl_no_data);
        get_results();
    }

    public void get_results() {
        if (!Utils.isOnline(this)) {
            Utils.nointernet(this);
        } else {
            pd = new ProgressDialog(this);
            pd.setMessage("Please Wait..");
            pd.show();
            pd.setCancelable(true);
            Call<ResponseBody> call = RetrofitClient.getClient().get_results(session.getId());
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
                                for (int i = 0; i < jsonArray2.length(); i++) {
                                    JSONObject dataobj = jsonArray2.getJSONObject(i);
                                    resultlist.add(new Result_model(
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
                                result_page_RecyclerView = (RecyclerView) findViewById(R.id.recyclerView_result);
                                gameAdapter = new GameAdapter(getApplicationContext(), resultlist);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false);
                                result_page_RecyclerView.setLayoutManager(linearLayoutManager);
                                result_page_RecyclerView.setItemAnimator(new DefaultItemAnimator());
                                result_page_RecyclerView.setAdapter(gameAdapter);
                                //   txt_level_vs_team.setText(dataobj.optString("user_level"));
                            } else {
                                rl_no_data.setVisibility(View.VISIBLE);
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
                        Utils.nointernet(Games_Results.this);
                    }
                }
            });
        }
    }
    //////////Game calculate result api///////

}

