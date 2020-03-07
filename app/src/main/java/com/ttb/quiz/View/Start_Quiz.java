package com.ttb.quiz.View;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ttb.quiz.Models.Questions;
import com.ttb.quiz.R;
import com.ttb.quiz.Session;
import com.ttb.quiz.connections.RetrofitClient;
import com.ttb.quiz.connections.SharedPreferencesHandler;
import com.ttb.quiz.connections.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Start_Quiz extends AppCompatActivity {

    LinearLayout ll_correct_answer;
    de.hdodenhof.circleimageview.CircleImageView play_profile_image, c_image;
    TextView txt_vs;
    ImageView play_country, c_cuntry;
    ///////////// progress bar
    ProgressBar androidProgressBar;
    int progressStatusCounter = 0;
    TextView textView;
    Handler progressHandler = new Handler();

    ///////////// progress bar verticsl...
    ProgressBar mprogressBar;
    ProgressDialog pd;

    ProgressBar androidProgressBar_vs;
    int progressStatusCounter_vs = 0;
    TextView textView_vs, txt_username_start_quiz, txt_start_quiz_score, txt_progress_bar_top, txt_2nd_username_start_quiz, txt_2nd_user_start_quiz_score;
    Handler progressHandler_vs = new Handler();

    TextView txt_tittle, txt_correct_answer, txt_coin_start_quiz, txt_correct_ans_start_quiz, txt_question;
    ImageView img_back, img_setting, img_question, img_clock_start_quiz, img_chance_start_quiz, img_dollor_start_quiz, img_correct_ans_start_quiz, img_pints_start_quiz;
    Session session;
    TextView bt_one, bt_two, bt_three, bt_four;

    ArrayList<Questions> list = new ArrayList<>();
    boolean click = true;
    int count = 0, point = 0, coin = 0, maxque;
    CountDownTimer timer;
    CountDownTimer timer1;
    CountDownTimer timer2;
    int time;
    int user_progress = 0;
    int com_user_progress = 0;
    int progress;
    String points;
    int correct_answer = 0;
    int current_coins = 0;
    TextView txt_points;

    boolean extra_time = false;
    boolean chance = false;
    boolean double_point=false;
    boolean correct_anser=false;


    ImageView iv_bonus_point;
    LinearLayout ll_double_points;

    int correct_answer_n = 0;
    int coins;
    //    int current_coins=0;
    boolean twoxpoint = false;
    LinearLayout lay_que;
    SharedPreferencesHandler sharedPreferencesHandler;

    @Override
    protected void onResume() {
        super.onResume();
        txt_tittle.setText("Start Quiz");
    }

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to lose the game? ")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (timer != null) {
                            timer.cancel();
                        }
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(R.anim.enter, R.anim.exit);
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_quiz);
        session = (Session) getApplicationContext();
        sharedPreferencesHandler = new SharedPreferencesHandler(this);
        coins = Integer.parseInt(session.getCoins());
        lay_que = (LinearLayout) findViewById(R.id.lay_que);
        mprogressBar = (ProgressBar) findViewById(R.id.circular_progress_bar);
        iv_bonus_point = (ImageView) findViewById(R.id.iv_bonus_point);
        img_back = (ImageView) findViewById(R.id.img_back);
        txt_points = (TextView) findViewById(R.id.txt_points);
        txt_username_start_quiz = (TextView) findViewById(R.id.txt_username_start_quiz);
        txt_start_quiz_score = (TextView) findViewById(R.id.txt_start_quiz_score);
        ll_double_points = (LinearLayout) findViewById(R.id.ll_double_points);
//        txt_start_quiz_score.setText(session.g);
        txt_progress_bar_top = (TextView) findViewById(R.id.txt_progress_bar_top);
        txt_2nd_username_start_quiz = (TextView) findViewById(R.id.txt_2nd_username_start_quiz);
        txt_2nd_user_start_quiz_score = (TextView) findViewById(R.id.txt_2nd_user_start_quiz_score);
        txt_correct_answer = (TextView) findViewById(R.id.txt_correct_answer);
        txt_coin_start_quiz = (TextView) findViewById(R.id.txt_coin_start_quiz);
        txt_correct_ans_start_quiz = (TextView) findViewById(R.id.txt_correct_ans_start_quiz);
        txt_question = (TextView) findViewById(R.id.txt_question);

        img_question = (ImageView) findViewById(R.id.img_question);
        img_clock_start_quiz = (ImageView) findViewById(R.id.img_clock_start_quiz);
        img_chance_start_quiz = (ImageView) findViewById(R.id.img_chance_start_quiz);
        img_dollor_start_quiz = (ImageView) findViewById(R.id.img_dollor_start_quiz);
        img_correct_ans_start_quiz = (ImageView) findViewById(R.id.img_correct_ans_start_quiz);
        img_pints_start_quiz = (ImageView) findViewById(R.id.img_pints_start_quiz);

        bt_one = (TextView) findViewById(R.id.btn_answer1_sart_quiz);
        bt_two = (TextView) findViewById(R.id.btn_answer2_sart_quiz);
        bt_three = (TextView) findViewById(R.id.btn_answer3_sart_quiz);
        bt_four = (TextView) findViewById(R.id.btn_answer4_sart_quiz);

//        Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
//        img.startAnimation(animFadeOut);
        play_profile_image = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.play_profile_image);
        play_country = (ImageView) findViewById(R.id.play_country);
        c_image = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.c_image);
        c_cuntry = (ImageView) findViewById(R.id.c_cuntry);
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
                .into(c_image);
        Picasso.with(this).load(Utils.country_flag_url + session.getC_country_flag())
                .fit()
                .placeholder(R.drawable.circle)
                .into(c_cuntry);
        txt_username_start_quiz.setText(session.getName());
        txt_2nd_username_start_quiz.setText(session.getC_name());
        getQuestion(session.getSub_topic_id());
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
        ////////////////////////////////
        txt_vs = (TextView) findViewById(R.id.txt_vs);

        ll_double_points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (coins < 60 && current_coins < 60) {
//                    txt_points.setText(session.getCoins());
                    Toast.makeText(getApplicationContext(), "Coins are not Available", Toast.LENGTH_SHORT).show();
                } else if (list.get(count).getAnswer().equals("1")) {
                    point = point + point;
                    txt_points.setText(point + "");
                }
            }
        });

        ll_correct_answer = (LinearLayout) findViewById(R.id.ll_correct_answer);
        ll_correct_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(correct_anser){
                    correct_anser=false;


                if (coins < 100 && current_coins < 40) {

                    txt_coin_start_quiz.setText(session.getCoins());
                    Toast.makeText(getApplicationContext(), "Coins are not Available", Toast.LENGTH_SHORT).show();
                } else if (list.get(count).getAnswer().equals("1")) {
                    coins = coins - 100;
                    session.setCoins(coins + "");
                    txt_coin_start_quiz.setText(session.getCoins());
                    bt_one.setBackgroundResource(R.drawable.button_border_green);

                } else if (list.get(count).getAnswer().equals("2")) {
                    bt_two.setBackgroundResource(R.drawable.button_border_green);
                    coins = coins - 100;
                    session.setCoins(coins + "");
                    txt_coin_start_quiz.setText(session.getCoins());
                } else if (list.get(count).getAnswer().equals("3")) {
                    bt_three.setBackgroundResource(R.drawable.button_border_green);
                    coins = coins - 100;
                    session.setCoins(coins + "");
                    txt_coin_start_quiz.setText(session.getCoins());
                } else if (list.get(count).getAnswer().equals("4")) {
                    bt_four.setBackgroundResource(R.drawable.button_border_green);
                    coins = coins - 100;
                    session.setCoins(coins + "");
                    txt_coin_start_quiz.setText(session.getCoins());
                }
                if (twoxpoint) {
                    twoxpoint = false;
                    point = point + Integer.parseInt(list.get(count).getPoints());
                    point = point + point;
                } else {
                    point = point + Integer.parseInt(list.get(count).getPoints());
                }
                user_progress = user_progress + progress;
                correct_answer = correct_answer + 1;
                correct_answer_n = correct_answer_n + 1;
                coins = coins + 3;
                current_coins = current_coins + 3;
                session.setCoins(coins + "");
                next_que();
                }
            }
        });
        androidProgressBar = (ProgressBar) findViewById(R.id.horizontal_progress_bar);
        textView = (TextView) findViewById(R.id.textView);
        androidProgressBar_vs = (ProgressBar) findViewById(R.id.horizontal_progress_bar_vs);
        textView_vs = (TextView) findViewById(R.id.textView_vs);
        img_pints_start_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        img_clock_start_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (extra_time) {
                        extra_time = false;
                        if (coins < 20 && current_coins < 20) {
                            txt_coin_start_quiz.setText(session.getCoins());
                            img_clock_start_quiz.setImageResource(R.drawable.timer_grey);
                            Toast.makeText(getApplicationContext(), "Coins are not Available", Toast.LENGTH_SHORT).show();
                        } else {
                            coins = coins - 20;
                            session.setCoins(coins + "");
                            txt_coin_start_quiz.setText(session.getCoins());
                            isPaused = true;
                            time_pause();
                        }
                }

            }
        });
        img_chance_start_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click) {
                    if (coins < 40 && current_coins < 40) {
                        txt_coin_start_quiz.setText(session.getCoins());
                        img_chance_start_quiz.setImageResource(R.drawable.chance_50_grey);
                        Toast.makeText(getApplicationContext(), "Coins are not Available", Toast.LENGTH_SHORT).show();
                    } else {
                        if (chance) {
                            chance = false;
                            coins = coins - 40;
                            session.setCoins(coins + "");
                            txt_coin_start_quiz.setText(session.getCoins());
                            if (list.get(count).getAnswer().equals("1")) {
                                bt_three.setVisibility(View.INVISIBLE);
                                bt_four.setVisibility(View.INVISIBLE);
                            } else if (list.get(count).getAnswer().equals("2")) {
                                bt_one.setVisibility(View.INVISIBLE);
                                bt_four.setVisibility(View.INVISIBLE);
                            } else if (list.get(count).getAnswer().equals("3")) {
                                bt_one.setVisibility(View.INVISIBLE);
                                bt_two.setVisibility(View.INVISIBLE);
                            } else if (list.get(count).getAnswer().equals("4")) {
                                bt_three.setVisibility(View.INVISIBLE);
                                bt_two.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                }
            }
        });
        bt_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click) {
                    if (list.get(count).getAnswer().equals("1")) {
                        bt_one.setBackgroundResource(R.drawable.button_border_green);
                        if (twoxpoint) {
                            twoxpoint = false;
                            point = point + Integer.parseInt(list.get(count).getPoints());
                            point = point + point;
                        } else {
                            point = point + Integer.parseInt(list.get(count).getPoints());
                        }
                        user_progress = user_progress + progress;
                        correct_answer = correct_answer + 1;
                        correct_answer_n = correct_answer_n + 1;
                        coins = coins + 3;
                        current_coins = current_coins + 3;
                        session.setCoins(coins + "");

                    } else if (list.get(count).getAnswer().equals("2")) {
                        bt_one.setBackgroundResource(R.drawable.button_border_red);
                        bt_two.setBackgroundResource(R.drawable.button_border_green);
                        correct_answer_n = 0;
                    } else if (list.get(count).getAnswer().equals("3")) {
                        bt_one.setBackgroundResource(R.drawable.button_border_red);
                        bt_three.setBackgroundResource(R.drawable.button_border_green);
                        correct_answer_n = 0;
                    } else if (list.get(count).getAnswer().equals("4")) {
                        bt_one.setBackgroundResource(R.drawable.button_border_red);
                        bt_four.setBackgroundResource(R.drawable.button_border_green);
                        correct_answer_n = 0;
                    }
                    click = false;
                    count = count + 1;
                    next_que();
                }
            }
        });
        bt_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click) {
                    if (list.get(count).getAnswer().equals("1")) {
                        bt_one.setBackgroundResource(R.drawable.button_border_green);
                        bt_two.setBackgroundResource(R.drawable.button_border_red);
                        correct_answer_n = 0;
                    } else if (list.get(count).getAnswer().equals("2")) {
                        user_progress = user_progress + progress;
                        if (twoxpoint) {
                            twoxpoint = false;
                            point = point + Integer.parseInt(list.get(count).getPoints()) + Integer.parseInt(list.get(count).getPoints());
                            point = point + point;
                        } else {
                            point = point + Integer.parseInt(list.get(count).getPoints());
                        }
                        bt_two.setBackgroundResource(R.drawable.button_border_green);
                        correct_answer = correct_answer + 1;
                        correct_answer_n = correct_answer_n + 1;
                        coins = coins + 3;
                        current_coins = current_coins + 3;
                        session.setCoins(coins + "");

                    } else if (list.get(count).getAnswer().equals("3")) {
                        bt_two.setBackgroundResource(R.drawable.button_border_red);
                        bt_three.setBackgroundResource(R.drawable.button_border_green);
                        correct_answer_n = 0;
                    } else if (list.get(count).getAnswer().equals("4")) {
                        bt_two.setBackgroundResource(R.drawable.button_border_red);
                        bt_four.setBackgroundResource(R.drawable.button_border_green);
                        correct_answer_n = 0;
                    }
                    click = false;
                    count = count + 1;
                    next_que();
                }
            }
        });
        bt_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click) {
                    if (list.get(count).getAnswer().equals("1")) {
                        bt_one.setBackgroundResource(R.drawable.button_border_green);
                        bt_three.setBackgroundResource(R.drawable.button_border_red);
                        correct_answer_n = 0;
                    } else if (list.get(count).getAnswer().equals("2")) {
                        bt_two.setBackgroundResource(R.drawable.button_border_green);
                        bt_three.setBackgroundResource(R.drawable.button_border_red);
                        correct_answer_n = 0;
                    } else if (list.get(count).getAnswer().equals("3")) {
                        user_progress = user_progress + progress;
                        if (twoxpoint) {
                            twoxpoint = false;
                            point = point + Integer.parseInt(list.get(count).getPoints()) + Integer.parseInt(list.get(count).getPoints());
                            point = point + point;
                        } else {
                            point = point + Integer.parseInt(list.get(count).getPoints());
                        }
                        bt_three.setBackgroundResource(R.drawable.button_border_green);
                        correct_answer = correct_answer + 1;
                        correct_answer_n = correct_answer_n + 1;
                        coins = coins + 3;
                        current_coins = current_coins + 3;
                        session.setCoins(coins + "");

                    } else if (list.get(count).getAnswer().equals("4")) {
                        bt_three.setBackgroundResource(R.drawable.button_border_red);
                        bt_four.setBackgroundResource(R.drawable.button_border_green);
                        correct_answer_n = 0;
                    }
                    click = false;
                    count = count + 1;
                    next_que();
                }
            }
        });
        bt_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click) {
                    if (list.get(count).getAnswer().equals("1")) {
                        bt_one.setBackgroundResource(R.drawable.button_border_green);
                        bt_four.setBackgroundResource(R.drawable.button_border_red);
                        correct_answer_n = 0;
                    } else if (list.get(count).getAnswer().equals("2")) {
                        bt_two.setBackgroundResource(R.drawable.button_border_green);
                        bt_four.setBackgroundResource(R.drawable.button_border_red);
                        correct_answer_n = 0;
                    } else if (list.get(count).getAnswer().equals("3")) {
                        bt_three.setBackgroundResource(R.drawable.button_border_green);
                        bt_four.setBackgroundResource(R.drawable.button_border_red);
                        correct_answer_n = 0;

                    } else if (list.get(count).getAnswer().equals("4")) {
                        user_progress = user_progress + progress;
                        if (twoxpoint) {
                            twoxpoint = false;
                            point = point + Integer.parseInt(list.get(count).getPoints()) + Integer.parseInt(list.get(count).getPoints());
                            point = point + point;
                        } else {
                            point = point + Integer.parseInt(list.get(count).getPoints());
                        }
                        bt_four.setBackgroundResource(R.drawable.button_border_green);
                        correct_answer = correct_answer + 1;
                        correct_answer_n = correct_answer_n + 1;
                        coins = coins + 3;
                        current_coins = current_coins + 3;
                        session.setCoins(coins + "");

                    }
                    click = false;
                    count = count + 1;
                    next_que();
                }
            }
        });
        //////////////////gaurav


    }

    public void nextQuenm() {
        //  Toast.makeText(getApplicationContext(),currentno +"  "+maxque,Toast.LENGTH_LONG).show();
        if (count == maxque) {
            calculate_result();
            correct_answer_n = 0;
        } else if (correct_answer_n == 5) {
            int coin_count = coin - current_coins + 20;
            coin = coin_count;
            current_coins = 20;
            lay_que.setVisibility(View.GONE);
            img_question.setVisibility(View.GONE);
            txt_question.setText("");
            twoxpoint = true;
            correct_answer_n = 0;
            iv_bonus_point.setVisibility(View.VISIBLE);
//            Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
//            iv_bonus_point.startAnimation(animFadeIn);
            next_que_bo();
        } else {
            try {
                lay_que.setVisibility(View.VISIBLE);
                if (coins < 20 && current_coins < 20) {
                    extra_time = false;
                    img_clock_start_quiz.setImageResource(R.drawable.timer_grey);
                } else {
                    extra_time = true;
                    img_clock_start_quiz.setImageResource(R.drawable.timer);
                }
                if (coins < 40 && current_coins < 40) {
                    chance = false;
                    img_chance_start_quiz.setImageResource(R.drawable.chance_50_grey);
                } else {
                    chance = true;
                    img_chance_start_quiz.setImageResource(R.drawable.chance_50);
                }
                if (coins < 60 && current_coins < 60) {
                    img_pints_start_quiz.setImageResource(R.drawable.star_points_grey);
                } else {
                    img_pints_start_quiz.setImageResource(R.drawable.star_points);
                }
                if (coins < 100 && current_coins < 40) {
                    correct_anser=true;
                    img_correct_ans_start_quiz.setImageResource(R.drawable.successful_grey);
                } else {
                    correct_anser=false;
                    img_correct_ans_start_quiz.setImageResource(R.drawable.sucessful_green); ////change image
                }



                txt_coin_start_quiz.setText(session.getCoins());
                txt_points.setText(point + "");
                txt_start_quiz_score.setText(point + "");
                androidProgressBar.setProgress(user_progress);
                bt_one.setVisibility(View.VISIBLE);
                bt_two.setVisibility(View.VISIBLE);
                bt_three.setVisibility(View.VISIBLE);
                bt_four.setVisibility(View.VISIBLE);
                bt_one.setBackgroundResource(R.drawable.button_border);
                bt_two.setBackgroundResource(R.drawable.button_border);
                bt_three.setBackgroundResource(R.drawable.button_border);
                bt_four.setBackgroundResource(R.drawable.button_border);
                if (count != maxque) {
//                calculate_result();
                    txt_question.setText("" + list.get(count).getQuestion());
                }
                if (list.get(count).getQuestion_image().equals("")) {
                    img_question.setVisibility(View.GONE);
                } else {
                    img_question.setVisibility(View.VISIBLE);
                    Picasso.with(this).load(Utils.question_images + list.get(count).getQuestion_image())
                            .fit()
                            .placeholder(R.drawable.ic_face)
                            .into(img_question);
                }
                bt_one.setText(list.get(count).getOption1());
                bt_two.setText(list.get(count).getOption2());
                bt_three.setText(list.get(count).getOption3());
                bt_four.setText(list.get(count).getOption4());
                txt_correct_answer.setText(correct_answer + "");
                // Toast.makeText(getApplicationContext(), list.get(count).getAnswer(), Toast.LENGTH_LONG).show();
                time = list.get(count).getTime() * 1000;
                click = true;
//                chance = true;
//                extra_time=true;
                add_points();
            } catch (Exception e) {
            }

        }

    }

    public void calculate_result() {
        if (!Utils.isOnline(this)) {
            Utils.nointernet(this);
        } else {
            sharedPreferencesHandler.WritePreference("coins", coins + "");
            Call<ResponseBody> call = RetrofitClient.getClient().calculate_result(session.getGame_id());
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
                                JSONObject game = jsonArray2.getJSONObject(0);
                                String game_result = "";
                                if (session.getGame_player_type().equals("challenger")) {
                                    game_result = game.optString("competitor_result");
                                } else {
                                    game_result = game.optString("result");
                                }
                                session.setGamae_result(game_result);
                                Toast.makeText(getApplicationContext(), "GOOD EFFORT...", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), Won_page.class).putExtra("points", point + "").putExtra("current_coins", current_coins + ""));
                                overridePendingTransition(R.anim.enter, R.anim.exit);
                                finishAffinity();
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
                    if (t.getMessage().equals("connect timed out")) {
                        Utils.nointernet(Start_Quiz.this);
                    }
                }
            });
        }
    }

    public void time_pause() {
        timer.cancel();
        mprogressBar.setVisibility(View.GONE);
        isPaused = false;
        isCanceled = false;
        timeRemaining = timeRemaining + 5000;
        //tx_timer.setText("seconds remaining: " + millisUntilFinished / 1000);
        mprogressBar = (ProgressBar) findViewById(R.id.circular_progress_bar);
        mprogressBar.setVisibility(View.VISIBLE);
        ObjectAnimator anim = ObjectAnimator.ofInt(mprogressBar, "progress", 0, 100);
        anim.setDuration(timeRemaining);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.start();
        timer = new CountDownTimer(timeRemaining, 1000) {

            public void onTick(long millisUntilFinished) {
                txt_progress_bar_top.setText("" + millisUntilFinished / 1000);

            }

            public void onFinish() {
                count = count + 1;
                nextQuenm();
            }
        }.start();


    }

    public void add_points() {
        if (!Utils.isOnline(this)) {
            Utils.nointernet(this);
        } else {
            // Toast.makeText(getApplicationContext(),game_id+"  "+session.getId(),Toast.LENGTH_LONG).show();
            session.setCoins(coins + "");
            Call<ResponseBody> call = RetrofitClient.getClient().add_points(session.getId(), session.getGame_id(), session.getGame_player_type(), session.getCoins(), point + "");
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
                                JSONObject game = jsonArray2.getJSONObject(0);
                                int mypoint = game.optInt("points");
                                txt_2nd_user_start_quiz_score.setText(mypoint + "");
                                androidProgressBar_vs.setProgress(mypoint);
                                nextQue();
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
                    if (t.getMessage().equals("connect timed out")) {
                        Utils.nointernet(Start_Quiz.this);
                    }
                }
            });
        }
    }

    private boolean isPaused = false;
    private boolean isCanceled = false;
    private long timeRemaining = 0;

    public void nextQue() {

        mprogressBar.setVisibility(View.VISIBLE);
        ObjectAnimator anim = ObjectAnimator.ofInt(mprogressBar, "progress", 0, 100);
        anim.setDuration(time);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.start();
        timer = new CountDownTimer(time, 1000) {

            public void onTick(long millisUntilFinished) {

                if (isPaused || isCanceled) {
                    //If the user request to cancel or paused the
                    //CountDownTimer we will cancel the current instance
                    cancel();
                } else {
                    timeRemaining = millisUntilFinished;
                    txt_progress_bar_top.setText("" + millisUntilFinished / 1000);
                }
            }

            public void onFinish() {
                count = count + 1;
                nextQuenm();
            }
        }.start();
    }

    public void next_que() {
        if (timer != null) {
            timer.cancel();
        }
        mprogressBar.setVisibility(View.GONE);
        timer = new CountDownTimer(500, 1000) {
            public void onTick(long millisUntilFinished) {
                //tx_timer.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                iv_bonus_point.setVisibility(View.GONE);
                nextQuenm();
            }
        }.start();
    }

    public void next_que_bo() {
        if (timer != null) {
            timer.cancel();
        }
        mprogressBar.setVisibility(View.GONE);
        timer = new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {
                //tx_timer.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                iv_bonus_point.setVisibility(View.GONE);
                nextQuenm();
            }
        }.start();
    }

    /////////////////////////////////////////////////
    public void getQuestion(final String sub_topic_id) {
        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait..");
        pd.show();
        pd.setCancelable(true);
        if (!Utils.isOnline(this)) {
            Utils.nointernet(this);
        } else {
            Call<ResponseBody> call = RetrofitClient.getClient().get_questions(sub_topic_id);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        pd.dismiss();
                        try {
                            String result = response.body().string();
                            JSONObject jsonObject;
                            JSONArray jsonArray;
                            jsonObject = new JSONObject(result);
                            jsonArray = jsonObject.getJSONArray("response");
                            JSONObject jo = jsonArray.getJSONObject(0);
                            if (jo.getBoolean("success")) {
                                JSONArray jsonArray2 = jo.getJSONArray("data");
                                maxque = jsonArray2.length();
                                progress = 100 / maxque;
                                for (int i = 0; i < jsonArray2.length(); i++) {
                                    JSONObject dataobj = jsonArray2.getJSONObject(i);
                                    list.add(new Questions(dataobj.optString("question"),
                                            dataobj.optString("question_image"),
                                            dataobj.optString("option1"),
                                            dataobj.optString("option2"),
                                            dataobj.optString("option3"),
                                            dataobj.optString("option4"),
                                            dataobj.optString("answer"),
                                            dataobj.optInt("time"),
                                            dataobj.optString("points")));
                                    points = dataobj.optString("points");
                                }
                                nextQuenm();
                            } else {

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
                        Utils.nointernet(Start_Quiz.this);
                    }
                }
            });
        }
    }


}
