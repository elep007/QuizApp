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
import android.widget.TextView;
import android.widget.Toast;

import com.ttb.quiz.Adapter.LeaderAdapter;
import com.ttb.quiz.Models.View_pager_model;
import com.ttb.quiz.R;
import com.ttb.quiz.Session;
import com.ttb.quiz.connections.RetrofitClient;
import com.ttb.quiz.connections.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Leaderboard extends AppCompatActivity {

    TextView txt_tittle, txt_sub_tittle, txt_dayleft, txt_date, txt_country, txt_globle;
    ImageView img_back, img_setting, im_next;
    RecyclerView recyclerView;
    // List<LeaderboardModel> leaderboard;
    ProgressDialog pd;
    ArrayList<View_pager_model> view_pager_models;
    LeaderAdapter leaderAdapter;
    List<ArrayList> arrayLists;
    ImageView im_back;
    int pos;
    Session session;

    @Override
    protected void onResume() {
        super.onResume();
        txt_tittle.setText("Ranking");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        session = (Session) getApplicationContext();
        img_back = (ImageView) findViewById(R.id.img_back);
        im_back = (ImageView) findViewById(R.id.im_back);
        im_next = (ImageView) findViewById(R.id.im_next);
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
        txt_sub_tittle = (TextView) findViewById(R.id.txt_sub_tittle);
        txt_dayleft = (TextView) findViewById(R.id.txt_dayleft);
        txt_date = (TextView) findViewById(R.id.txt_date);
        txt_globle = (TextView) findViewById(R.id.txt_globle);
        txt_country = (TextView) findViewById(R.id.txt_country);
        txt_country.setText(session.getCountry());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_challenges);
        get_rankings_by_subtitle();
        txt_globle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_rankings_by_subtitle();
            }
        });
        txt_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_rankings_by_user_country();
            }
        });
        im_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pos < arrayLists.size() - 1) {
                    pos = pos + 1;
                    change_view(pos);
                }
            }
        });
        im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pos != 0) {
                    pos = pos - 1;
                    change_view(pos);
                }
            }
        });
    }


    public void get_rankings_by_user_country() {
        if (!Utils.isOnline(this)) {
            Utils.nointernet(this);
        } else {
            pd = new ProgressDialog(this);
            pd.setMessage("Please Wait..");
            pd.show();
            pd.setCancelable(true);
            Call<ResponseBody> call = RetrofitClient.getClient().get_rankings_by_user_country(session.getId());
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
                            arrayLists = new ArrayList<>();
                            view_pager_models = new ArrayList<>();
                            if (jo.getBoolean("success")) {
                                JSONArray jsonArray2 = jo.getJSONArray("category");
//                                achivement_list = new ArrayList();

                                for (int i = 0; i < jsonArray2.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray2.optJSONObject(i);
                                    JSONArray jsonArray1 = jsonObject1.getJSONArray("data");
                                    if (jsonArray1.length() >= 1) {

                                        for (int k = 0; k < jsonArray1.length(); k++) {
                                            JSONObject jsonObject2 = jsonArray1.getJSONObject(k);
                                            view_pager_models.add(new View_pager_model(
                                                    jsonObject2.optString("id"),
                                                    jsonObject2.optString("name"),
                                                    jsonObject2.optString("country"),
                                                    jsonObject2.optString("profile_pic"),
                                                    jsonObject2.optString("sub_topic_id"),
                                                    jsonObject2.optString("subtopic_points"),
                                                    jsonObject1.optString("topic_id")
                                                    , jsonObject1.optString("quiz_length"),
                                                    jsonObject1.optString("title"),
                                                    jsonObject1.optString("subtitle")
                                                    , jsonObject1.optString("desc")
                                                    , jsonObject1.optString("price"),
                                                    jsonObject1.optString("image")
                                                    , jsonObject1.optString("date"),
                                                    jsonObject1.optString("status"),
                                                    jsonObject2.optString("rank"),
                                                    jsonObject2.optString("country_flag")
                                            ));

                                        }
                                        arrayLists.add(view_pager_models);
                                    }
                                }


                                //////////////////////////////
                            } else {
                                arrayLists = new ArrayList<>();
                                view_pager_models = new ArrayList<>();
                                Toast.makeText(getApplicationContext(), jo.getString("message") + "", Toast.LENGTH_LONG).show();
                            }
                            change_view(0);
                        } catch (Exception e) {
                            e.printStackTrace();
                            recyclerView.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Not Working", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    pd.dismiss();
                    if (t.getMessage().equals("connect timed out")) {
                        Utils.nointernet(Leaderboard.this);
                    }
                }
            });
        }
    }

    public void get_rankings_by_subtitle() {
        if (!Utils.isOnline(this)) {
            Utils.nointernet(this);
        } else {
            pd = new ProgressDialog(this);
            pd.setMessage("Please Wait..");
            pd.show();
            pd.setCancelable(true);
            Call<ResponseBody> call = RetrofitClient.getClient().get_rankings_by_subtitle();
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
                            arrayLists = new ArrayList<>();
                            view_pager_models = new ArrayList<>();
                            if (jo.getBoolean("success")) {
                                JSONArray jsonArray2 = jo.getJSONArray("category");
//                                achivement_list = new ArrayList();
                                for (int i = 0; i < jsonArray2.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray2.optJSONObject(i);
                                    JSONArray jsonArray1 = jsonObject1.getJSONArray("data");
                                    if (jsonArray1.length() >= 1) {
                                        for (int k = 0; k < jsonArray1.length(); k++) {
                                            JSONObject jsonObject2 = jsonArray1.getJSONObject(k);
                                            view_pager_models.add(new View_pager_model(
                                                    jsonObject2.optString("id"),
                                                    jsonObject2.optString("name"),
                                                    jsonObject2.optString("country"),
                                                    jsonObject2.optString("profile_pic"),
                                                    jsonObject2.optString("sub_topic_id"),
                                                    jsonObject2.optString("subtopic_points"),
                                                    jsonObject1.optString("topic_id")
                                                    , jsonObject1.optString("quiz_length"),
                                                    jsonObject1.optString("title"),
                                                    jsonObject1.optString("subtitle")
                                                    , jsonObject1.optString("desc")
                                                    , jsonObject1.optString("price"),
                                                    jsonObject1.optString("image")
                                                    , jsonObject1.optString("date"),
                                                    jsonObject1.optString("status"),
                                                    jsonObject2.optString("rank"),
                                                    jsonObject2.optString("country_flag")
                                            ));
                                        }
                                        arrayLists.add(view_pager_models);
                                    }
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), jo.getString("message") + "", Toast.LENGTH_LONG).show();
                            }
                            change_view(0);
                        } catch (Exception e) {
                            recyclerView.setVisibility(View.GONE);
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
                        Utils.nointernet(Leaderboard.this);
                    }
                }
            });
        }
    }

    public void change_view(int position) {
        pos = position;
        recyclerView.setVisibility(View.VISIBLE);
        if (position == 0) {
            im_back.setVisibility(View.INVISIBLE);
        } else {
            im_back.setVisibility(View.VISIBLE);
            view_pager_models = arrayLists.get(position);
            txt_sub_tittle.setText(view_pager_models.get(position).getTitle());
        }
        ///////////////////expandable list/////////////////


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MMM-yyyy");
        String formattedDate = df.format(c);
        txt_date.setText(formattedDate);
        Calendar calendar = Calendar.getInstance();
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int daysLeft = lastDay - currentDay;
        txt_dayleft.setText(daysLeft + " Days Left");
        leaderAdapter = new LeaderAdapter(getApplicationContext(), arrayLists.get(position), Leaderboard.this,session);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(leaderAdapter);
    }
    ///////////////////////////////////////////////////
    public void add_challenge(String topic_id,String sub_topic,String competitor_id) {


//        pd = new ProgressDialog(this);
//        pd.setMessage("Please Wait..");
//        pd.show();
//        pd.setCancelable(true);
            if (!Utils.isOnline(this)) {
                Utils.nointernet(this);
            } else {
                Call<ResponseBody> call = RetrofitClient.getClient().add_challenge(session.getId(), topic_id, sub_topic, competitor_id);
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
                                    startActivity(new Intent(getApplicationContext(), Start_Quiz.class));
                                    overridePendingTransition(R.anim.enter, R.anim.exit);

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
                            Utils.nointernet(Leaderboard.this);
                        }
                    }
                });
            }
        }
}
