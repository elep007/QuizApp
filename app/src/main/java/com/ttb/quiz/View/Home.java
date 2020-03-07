package com.ttb.quiz.View;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ttb.quiz.Adapter.TopicAdapter;
import com.ttb.quiz.Models.Topics_model;
import com.ttb.quiz.Models.View_pager_model;
import com.ttb.quiz.R;
import com.ttb.quiz.Session;
import com.ttb.quiz.connections.RetrofitClient;
import com.ttb.quiz.connections.SharedPreferencesHandler;
import com.ttb.quiz.connections.Utils;
import com.ttb.quiz.services.Foreground;

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

public class Home extends AppCompatActivity {
    RecyclerView recyclerView_topic;
    List topiclist = new ArrayList();
    TopicAdapter topicAdapter;
    de.hdodenhof.circleimageview.CircleImageView profile_image;
    ImageView country_image;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    //    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    ArrayList<Integer> new_layout;
    private static int currentPage = 0;
    RecyclerView recyclerView_topic1;
    List topiclist1 = new ArrayList();
    TopicAdapter topicAdapter1;
    Button btn_all_topics;
    Button btn_full_leaderboard;
    FrameLayout btn_play, btn_invite_friends;
    LinearLayout ll_profile;
    FrameLayout fl_results_performance, fl_achievements_performance, fl_challenges;
    TextView txt_tittle;
    ImageView img_back, img_setting;
    SharedPreferencesHandler sharedPreferencesHandler;
    Session session;
    TextView txt_name, txt_country_name, txt_rank, txt_coins, txt_challanges;

    ProgressDialog pd;
    ArrayList<View_pager_model> view_pager_models;

    List<ArrayList> arrayLists;

    boolean challegnes = false;
    String token;

//    RelativeLayout view_pager_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        view_pager_layout=(RelativeLayout)findViewById(R.id.view_pager_layout);
        session = (Session) getApplicationContext();
        txt_challanges = (TextView) findViewById(R.id.txt_challanges);
        sharedPreferencesHandler = new SharedPreferencesHandler(this);
        token = sharedPreferencesHandler.ReadPreferences("token");
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_country_name = (TextView) findViewById(R.id.txt_country_name);
        txt_rank = (TextView) findViewById(R.id.txt_rank);
        txt_coins = (TextView) findViewById(R.id.txt_coins);
        profile_image = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.profile_image);
        country_image = (ImageView) findViewById(R.id.country_image);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_setting = (ImageView) findViewById(R.id.img_setting);
//        img_setting.setVisibility(View.GONE);
        img_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        txt_tittle = (TextView) findViewById(R.id.txt_tittle);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        btn_all_topics = (Button) findViewById(R.id.btn_all_topics);
        btn_all_topics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), All_Topic_Internal.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        ///////////////////////////////// invite button////////////
        btn_invite_friends = (FrameLayout) findViewById(R.id.btn_invite_friends);
        btn_invite_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "CELT English Quiz");
                String app_url = "I’m playing CELT English Quiz! Join me and we can challenge each other and practise English together! Download the app here https://play.google.com/store/apps/details?id=com.ttb.quiz";
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, app_url);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });
        btn_play = (FrameLayout) findViewById(R.id.btn_play);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), All_Topic_Internal.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });

        ///////performance layout///////////
        fl_results_performance = (FrameLayout) findViewById(R.id.fl_results_performance);
        fl_achievements_performance = (FrameLayout) findViewById(R.id.fl_achievements_performance);
        fl_challenges = (FrameLayout) findViewById(R.id.fl_challenges);
        btn_full_leaderboard = (Button) findViewById(R.id.btn_full_leaderboard);
        activity();      ////////////////////////next page data///////

    }


    public void AnimateBell() {
        Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_animation);
//        Button imgBell = (Button) findViewById(R.id.btn_play);
        btn_play.setAnimation(shake);
        fl_achievements_performance.setAnimation(shake);
//        country.setAnimation(shake);
    }

    public void activity() {
        fl_results_performance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Games_Results.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        fl_achievements_performance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Achievements.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        fl_challenges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (challegnes) {
                    AlertDialog alertbox = new AlertDialog.Builder(Home.this)
                            .setTitle("No Challenges")
                            .setMessage("You don't Have any challenges at the moment. Try Challenging or inviting a friend")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                // do something when the button is clicked
                                public void onClick(DialogInterface arg0, int arg1) {
                                    get_challenges();
//                                    finishAffinity();
                                }
                            })
//                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
//
//                                // do something when the button is clicked
//                                public void onClick(DialogInterface arg0, int arg1) {
//                                }
//                            })
                            .show();
                } else {


                    startActivity(new Intent(getApplicationContext(), Challenges.class));
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }
            }
        });
    }

    ///////view pager coding//////////
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int i, float v, int i1) {
        }

        @Override
        public void onPageSelected(int position) {
            currentPage = position;
        }

        @Override
        public void onPageScrollStateChanged(int i) {
        }
    };

    int i = 0;

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(new_layout.get(position), container, false);

            try {
                ArrayList<View_pager_model> models = arrayLists.get(position);

//            Toast.makeText(getApplicationContext(), models.get(0).getId(), Toast.LENGTH_LONG).show();
                TextView first_winner = (TextView) view.findViewById(R.id.first_winner);
                TextView second_winner = (TextView) view.findViewById(R.id.second_winner);
                TextView third_winner = (TextView) view.findViewById(R.id.third_winner);
                TextView first_winner_country = (TextView) view.findViewById(R.id.first_winner_country);
                TextView txt_first_winner_coins = (TextView) view.findViewById(R.id.txt_first_winner_coins);
                TextView second_winner_rank = (TextView) view.findViewById(R.id.second_winner_rank);
                TextView second_winner_country = (TextView) view.findViewById(R.id.second_winner_country);
                TextView txt_profile_date = (TextView) view.findViewById(R.id.txt_profile_date);
                TextView txt_profile_days = (TextView) view.findViewById(R.id.txt_profile_days);
//          TextView txt_second_winner_coins = (TextView) view.findViewById(R.id.txt_second_winner_coins);
                TextView third_winner_rank = (TextView) view.findViewById(R.id.third_winner_rank);
                TextView third_winner_country = (TextView) view.findViewById(R.id.third_winner_country);
//            TextView txt_third_winner_coins = (TextView) view.findViewById(R.id.txt_third_winner_coins);
                de.hdodenhof.circleimageview.CircleImageView profile_image_home = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.profile_image_home);
                ImageView home_country_first = (ImageView) view.findViewById(R.id.home_country_first);
                ImageView iv_country_second = (ImageView) view.findViewById(R.id.iv_country_second);
                ImageView iv_country_third = (ImageView) view.findViewById(R.id.iv_country_third);
                de.hdodenhof.circleimageview.CircleImageView profile_image_second = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.profile_image_second);
                de.hdodenhof.circleimageview.CircleImageView home_profile_third = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.home_profile_third);
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("MMM-yyyy");
                String formattedDate = df.format(c);
                txt_profile_date.setText(formattedDate);
                Calendar calendar = Calendar.getInstance();
                int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
                int daysLeft = lastDay - currentDay;
                txt_profile_days.setText(daysLeft + " Days Left");
              //  view_pager_layout.setVisibility(View.VISIBLE);

                if (models.size() < 3) {
                    if (models.size() < 2) {
                        first_winner.setText(models.get(0).getName());
                        first_winner_country.setText(models.get(0).getCountry());
                        txt_first_winner_coins.setText(models.get(0).getRank());
                        Picasso.with(getApplicationContext()).load(Utils.user_image_url + models.get(0).getProfile_pic())
                                .fit()
                                .placeholder(R.drawable.ic_face)
                                .into(profile_image_home);

                        Picasso.with(getApplicationContext()).load(Utils.country_flag_url + models.get(0).getCountry_flag())
                                .fit()
                                .placeholder(R.drawable.rectangle)
                                .into(home_country_first);

                        second_winner.setText("");
                        second_winner_country.setText("");
                        third_winner_country.setText("");
                        third_winner.setText("");
                        second_winner_rank.setText("");
                        third_winner_rank.setText("");


//                  first_winner_country.setText(models.getc);

                    } else {

                        first_winner.setText(models.get(0).getName());
                        second_winner.setText(models.get(1).getName());
                        first_winner_country.setText(models.get(0).getCountry());
                        second_winner_country.setText(models.get(1).getCountry());
                        txt_first_winner_coins.setText(models.get(0).getRank());
                        second_winner_rank.setText(models.get(1).getSubtopic_points());
                        Picasso.with(getApplicationContext()).load(Utils.user_image_url + models.get(0).getProfile_pic())
                                .fit()
                                .placeholder(R.drawable.ic_face)
                                .into(profile_image_home);
                        Picasso.with(getApplicationContext()).load(Utils.country_flag_url + models.get(0).getCountry_flag())
                                .fit()
                                .placeholder(R.drawable.rectangle)
                                .into(home_country_first);
                        ////
                        Picasso.with(getApplicationContext()).load(Utils.user_image_url + models.get(1).getProfile_pic())
                                .fit()
                                .placeholder(R.drawable.ic_face)
                                .into(profile_image_second);
                        Picasso.with(getApplicationContext()).load(Utils.country_flag_url + models.get(1).getCountry_flag())
                                .fit()
                                .placeholder(R.drawable.rectangle)
                                .into(iv_country_second);
//                        Toast.makeText(getApplicationContext(), models.get(0).getCountry_flag(), Toast.LENGTH_LONG).show();
                        // txt_profile_date.setText(models.get(0).getDate());
                    }

                } else {
                    first_winner.setText(models.get(0).getName());
                    first_winner_country.setText(models.get(0).getCountry());
                    second_winner_country.setText(models.get(1).getCountry());
                    third_winner_country.setText(models.get(2).getCountry());
                    second_winner.setText(models.get(1).getName());
                    third_winner.setText(models.get(2).getName());
                    txt_first_winner_coins.setText(models.get(0).getSubtopic_points());
                    second_winner_rank.setText(models.get(1).getSubtopic_points());
                    third_winner_rank.setText(models.get(2).getSubtopic_points());
                    Picasso.with(getApplicationContext()).load(Utils.user_image_url + models.get(0).getProfile_pic())
                            .fit()
                            .placeholder(R.drawable.ic_face)
                            .into(profile_image_home);
                    Picasso.with(getApplicationContext()).load(Utils.country_flag_url + models.get(0).getCountry_flag())
                            .fit()
                            .placeholder(R.drawable.rectangle)
                            .into(home_country_first);

                    ////

                    Picasso.with(getApplicationContext()).load(Utils.user_image_url + models.get(1).getProfile_pic())
                            .fit()
                            .placeholder(R.drawable.ic_face)
                            .into(profile_image_second);
                    Picasso.with(getApplicationContext()).load(Utils.country_flag_url + models.get(1).getCountry_flag())
                            .fit()
                            .placeholder(R.drawable.rectangle)
                            .into(iv_country_second);

                    ////

                    Picasso.with(getApplicationContext()).load(Utils.user_image_url + models.get(2).getProfile_pic())
                            .fit()
                            .placeholder(R.drawable.ic_face)
                            .into(home_profile_third);
                    Picasso.with(getApplicationContext()).load(Utils.country_flag_url + models.get(2).getCountry_flag())
                            .fit()
                            .placeholder(R.drawable.rectangle)
                            .into(iv_country_third);
                    //   txt_profile_date.setText(models.get(0).getDate());


                }
            } catch (Exception e) {
                Log.i("exp", e.getMessage());
            }

            Button btn_full_leaderboard = (Button) view.findViewById(R.id.btn_full_leaderboard);
            btn_full_leaderboard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Home.this, Leaderboard.class));
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return new_layout.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {

            return view == obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        txt_tittle.setText("Home");
        login(sharedPreferencesHandler.ReadPreferences("number"),sharedPreferencesHandler.ReadPreferences("password"));
    }
    public void login(final String number, String password) {
        if (!Utils.isOnline(this)) {
            Utils.nointernet(this);
        } else {
            Call<ResponseBody> call = RetrofitClient.getClient().login(number, password);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            String result = response.body().string();
                            sharedPreferencesHandler.WritePreference("user_data",result);
                            JSONObject jsonObject;
                            JSONArray jsonArray;
                            jsonObject = new JSONObject(result);
                            jsonArray = jsonObject.getJSONArray("response");
                            JSONObject jo = jsonArray.getJSONObject(0);
                            if (jo.getBoolean("success")) {
                                JSONArray jsonArray2 = jo.getJSONArray("data");
                                JSONObject dataobj = jsonArray2.getJSONObject(0);

                                session.setId(dataobj.optString("id"));
                                session.setName(dataobj.optString("name"));
                                session.setEmail(dataobj.optString("email"));
                                session.setCountry(dataobj.optString("country"));
                                session.setRole(dataobj.optString("role"));
                                session.setProfile_pic(dataobj.optString("profile_pic"));
                                session.setLoginby(dataobj.optString("loginby"));
                                if(dataobj.optString("ranking").equals("null")){
                                    session.setRanking("0");
                                }else {
                                    session.setRanking(dataobj.optString("ranking"));
                                }
                                if(dataobj.optString("ranking").equals("1")){
                                    txt_rank.setText(dataobj.optString("ranking") + "st");
                                }else if(dataobj.optString("ranking").equals("2")){
                                    txt_rank.setText(dataobj.optString("ranking") + "nd");
                                } else if(dataobj.optString("ranking").equals("3")){
                                    txt_rank.setText(dataobj.optString("ranking")+ "rd");
                                } else if(dataobj.optString("ranking").equals("4")){
                                    txt_rank.setText(dataobj.optString("ranking") + "th");
                                }
                                session.setUser_level(dataobj.optString("user_level"));
                                session.setCountry_flag(dataobj.optString("country_flag"));
                                if (session.getCoins().equals("0")) {
                                    if(!dataobj.optString("coins").equals("null"))
                                    session.setCoins(dataobj.optString("coins"));
                                }
                                session.setTotal_points(dataobj.optString("total_points"));
                                txt_name.setText(session.getName());
                                txt_country_name.setText(session.getCountry());

                                txt_coins.setText(session.getCoins());
                                Picasso.with(Home.this).load(Utils.user_image_url + session.getProfile_pic())
                                        .fit()
                                        .placeholder(R.drawable.ic_face)
                                        .into(profile_image);
                                Picasso.with(Home.this).load(Utils.country_flag_url + session.getCountry_flag())
                                        .fit()
                                        .placeholder(R.drawable.circle)
                                        .into(country_image);
                                get_challenges();
                                get_topics();
                                get_rankings_by_subtitle();
                                add_fcm();
                                /////User data ends//
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
                        Utils.nointernet(Home.this);
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to exit Quiz application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();
    }

    ///////////////////// topic receycleview /////////////////////////
    public void get_topics() {
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
                                topiclist = new ArrayList();
                                int k = 3;
                                if (jsonArray2.length() < 3) {
                                    k = jsonArray2.length();
                                }
                                for (int i = 0; i < k; i++) {
                                    try {
                                        JSONObject dataobj = jsonArray2.optJSONObject(i);
                                        JSONArray jsonArray1 = dataobj.optJSONArray("data");
                                        JSONObject jsonObject1 = jsonArray1.optJSONObject(0);
                                        topiclist.add(new Topics_model(
                                                dataobj.optString("id"),
                                                jsonObject1.optString("id"),
                                                dataobj.optString("title"),
                                                jsonObject1.optString("title"),
                                                jsonObject1.optString("desc"),
                                                jsonObject1.optString("sub_topic_rank"),
                                                jsonObject1.optString("image"),
                                                i
                                        ));

                                    } catch (Exception e) {
                                    }
                                }

                                ///////////////recycler view///////
                                recyclerView_topic = (RecyclerView) findViewById(R.id.recyclerView_topic);
                                topicAdapter = new TopicAdapter(getApplicationContext(), topiclist, Home.this);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false);
                                recyclerView_topic.setLayoutManager(linearLayoutManager);
                                recyclerView_topic.setItemAnimator(new DefaultItemAnimator());
                                recyclerView_topic.setAdapter(topicAdapter);
                            } else {
                                Toast.makeText(getApplicationContext(), jo.getString("message") + "", Toast.LENGTH_LONG).show();
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
                        Utils.nointernet(Home.this);
                    }
                }
            });
        }
    }

    public void play_game(String id) {
        session.setSub_topic_id(id);
        startActivity(new Intent(getApplicationContext(), Play.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }


    public void get_challenges() {
        if (!Utils.isOnline(this)) {
            Utils.nointernet(this);
        } else {
            Call<ResponseBody> call = RetrofitClient.getClient().get_challenge(session.getId());
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
                                if (jsonArray2.length() != 0) {
                                    challegnes = false;
                                    txt_challanges.setText(jsonArray2.length() + "");
                                    txt_challanges.setVisibility(View.VISIBLE);
                                }
                            } else {
                                challegnes = true;
                                txt_challanges.setText(0 + "");
                                txt_challanges.setVisibility(View.GONE);
                                //Toast.makeText(getApplicationContext(),jo.getString("message")+"",Toast.LENGTH_LONG).show();
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
                        Utils.nointernet(Home.this);
                    }
                }
            });
        }
    }


    public void get_rankings_by_subtitle() {
        if (!Utils.isOnline(this)) {
            Utils.nointernet(this);
        } else {
//            pd = new ProgressDialog(this);
//            pd.setMessage("Please Wait..");
//            pd.show();
//            pd.setCancelable(true);
            Call<ResponseBody> call = RetrofitClient.getClient().get_rankings_by_subtitle();
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    pd.dismiss();
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
//                                achivement_list = new ArrayList();
                                arrayLists = new ArrayList<>();
                                int pager_layout = 0;
                                new_layout = new ArrayList<>();
                                for (int i = 0; i < jsonArray2.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray2.optJSONObject(i);
                                    JSONArray jsonArray1 = jsonObject1.getJSONArray("data");
                                    if (jsonArray1.length() >= 1) {
                                        new_layout.add(R.layout.activity_home_slider);
//                                        layouts[i] = R.layout.activity_home_slider;
//                                        layouts[i] = R.layout.activity_home_slider;
                                        view_pager_models = new ArrayList<>();
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

//                                        Log.i("error", jsonObject2.optString("name"));
                                            String id = jsonObject2.optString("id");
                                            String first_winner = jsonObject2.optString("name");
                                            String second_winner = jsonObject2.optString("name");
//                                        Toast.makeText(Home.this, "" + jsonObject2.optString("name"), Toast.LENGTH_SHORT).show();
                                        }
                                        arrayLists.add(view_pager_models);
                                    }
                                }


                                ////////////////////////////////view pager///////////
                                //      dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
                                TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
                                tabLayout.setupWithViewPager(viewPager, true);
                                myViewPagerAdapter = new MyViewPagerAdapter();
                                viewPager.setAdapter(myViewPagerAdapter);
                                viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
                            } else {
                                Toast.makeText(getApplicationContext(), jo.getString("message") + "", Toast.LENGTH_LONG).show();
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
                        Utils.nointernet(Home.this);
                    }
                }
            });
        }
    }


    public void add_fcm() {
        if (!Utils.isOnline(this)) {
            Utils.nointernet(this);
        } else {
            Call<ResponseBody> call = RetrofitClient.getClient().add_fcm(session.getId(), token);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {

                    } else {
                        Toast.makeText(getApplicationContext(), "Not Working", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if (t.getMessage().equals("connect timed out")) {
                        Utils.nointernet(Home.this);
                    }
                }
            });
        }
    }


}