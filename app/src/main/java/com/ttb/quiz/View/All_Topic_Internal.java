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

import com.ttb.quiz.Adapter.TopicAdapter;
import com.ttb.quiz.Adapter.TopicAdapterInternal;
import com.ttb.quiz.Models.Topics_model;
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

public class All_Topic_Internal extends AppCompatActivity {


    RecyclerView recyclerView_all_topic_internal;
    List topiclist = new ArrayList();
    TopicAdapterInternal topicAdapter;

    TextView txt_tittle;
    ImageView img_back, img_setting;
    ProgressDialog pd;
    Session session;

    @Override
    protected void onResume() {
        super.onResume();
        txt_tittle.setText("All Topics");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__topic__internal);
        session=(Session)getApplicationContext();
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
        txt_tittle = (TextView) findViewById(R.id.txt_tittle);
        recyclerView_all_topic_internal = (RecyclerView) findViewById(R.id.recyclerView_all_topic_internal);
        get_topics();
    }

    public void get_topics(){
        if (!Utils.isOnline(this)) {
            Utils.nointernet(this);
        } else {
            pd = new ProgressDialog(this);
            pd.setMessage("Please Wait..");
            pd.show();
            pd.setCancelable(true);
            Call<ResponseBody> call = RetrofitClient.getClient().get_topic_data(session.getId());
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
                                JSONArray jsonArray2 = jo.getJSONArray("category");
                                topiclist=new ArrayList();
                                for (int i=0;i<jsonArray2.length();i++) {
                                        JSONObject dataobj = jsonArray2.optJSONObject(i);
                                    try {
                                        JSONArray jsonArray1 = dataobj.optJSONArray("data");
                                        for (int j=0;j<jsonArray1.length();j++) {
                                            JSONObject jsonObject1 = jsonArray1.optJSONObject(j);
                                            topiclist.add(new Topics_model(
                                                    dataobj.optString("id"),
                                                    jsonObject1.optString("id"),
                                                    dataobj.optString("title"),
                                                    jsonObject1.optString("title"),
                                                    jsonObject1.optString("desc"),
                                                    jsonObject1.optString("sub_topic_rank"),
                                                    jsonObject1.optString("image"),
                                                    j
                                            ));
                                        }
                                    }catch (Exception e){}
                                }
                                ///////////////recycler view///////
                                topicAdapter = new TopicAdapterInternal(getApplicationContext(), topiclist,All_Topic_Internal.this);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false);
                                recyclerView_all_topic_internal.setLayoutManager(linearLayoutManager);
                                recyclerView_all_topic_internal.setItemAnimator(new DefaultItemAnimator());
                                recyclerView_all_topic_internal.setAdapter(topicAdapter);
                            }else {
                                Toast.makeText(getApplicationContext(),jo.getString("message")+"",Toast.LENGTH_LONG).show();
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
                        Utils.nointernet(All_Topic_Internal.this);
                    }
                }
            });
        }
    }
    public void play_game(String id,String s_id){
        session.setTopic_id(id);
        session.setSub_topic_id(s_id);
        startActivity(new Intent(getApplicationContext(), Play.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
        finish();
    }
}
