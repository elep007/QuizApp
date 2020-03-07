package com.ttb.quiz.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.ttb.quiz.Adapter.AchivementAdapter;
import com.ttb.quiz.Adapter.ListviewAdapter;
import com.ttb.quiz.Models.Achivement_model;
import com.ttb.quiz.Models.RankListModel;
import com.ttb.quiz.Models.View_pager_model;
import com.ttb.quiz.R;
import com.ttb.quiz.Session;
import com.ttb.quiz.connections.RetrofitClient;
import com.ttb.quiz.connections.SharedPreferencesHandler;
import com.ttb.quiz.connections.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {
    de.hdodenhof.circleimageview.CircleImageView profile_image, achivement_image_1;
    FrameLayout camera;
    ImageView img_country;
    TextView txt_username, txt_user_country, txt_dollor, txt_star, achivement_text_1, txt_profile_date, txt_overall_gobal_rank, txt_overall_india, txt_grammer_global_rank,
            txt_game_start, txt_loses, txt_ranking_percentage;
    Button btn_game_results, btn_topic;
    TextView txt_tittle;
    ImageView img_back, img_setting, img_camera;
    LinearLayout ll_ranking;
    com.ttb.quiz.customview.ExpandableHeightListView ranking_listview;
    List<RankListModel> ranklist;
    RecyclerView recyclerView_achivements;
    List achivement_list = new ArrayList();
    AchivementAdapter achivementAdapter;
    int i = 0;
    ProgressDialog pd;
    private Uri picUri;
    Bitmap bitmap = null;
    private String mCurrentPhotoPath, photo_name;
    private final int SELECT_PHOTO = 1;
    Uri outPutfileUri;
    Session session;
    SharedPreferencesHandler sharedPreferencesHandler;
    Context context = this;
    int REQUEST_LOCATION = 1023;

    ArrayList<View_pager_model> view_pager_models;
    List<ArrayList> arrayLists;
    TextView txt_wins_ranking_percentage, txt_loses_ranking_percentage1, txt_Waiting_percenatage, txt_draw_ranking_percentage1;
    TextView txt_global_rank, txt_country, txt_heading;
    int won = 0, lose = 0, draw = 0, waiting = 0;

    @Override
    protected void onResume() {
        super.onResume();
        txt_tittle.setText("Profile");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        session = (Session) getApplicationContext();
        sharedPreferencesHandler = new SharedPreferencesHandler(this);
        session = (Session) getApplicationContext();
        img_back = (ImageView) findViewById(R.id.img_back);
        img_setting = (ImageView) findViewById(R.id.img_setting);
        // img_setting.setVisibility(View.GONE);
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
        achivement_image_1 = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.achivement_image_1);
        camera = (FrameLayout) findViewById(R.id.camera);
        txt_username = (TextView) findViewById(R.id.txt_username);
        txt_username.setText(session.getName());
        txt_user_country = (TextView) findViewById(R.id.txt_user_country);
        txt_user_country.setText(session.getCountry());
        txt_dollor = (TextView) findViewById(R.id.txt_dollor);
        txt_dollor.setText(session.getCoins());
        txt_star = (TextView) findViewById(R.id.txt_star);
        achivement_text_1 = (TextView) findViewById(R.id.achivement_text_1);
        txt_profile_date = (TextView) findViewById(R.id.txt_profile_date);
        txt_overall_gobal_rank = (TextView) findViewById(R.id.txt_overall_gobal_rank);
        txt_overall_india = (TextView) findViewById(R.id.txt_overall_india);
        txt_game_start = (TextView) findViewById(R.id.txt_game_start);
        profile_image = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.profile_image);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagepickclick();

            }
        });
        img_country = (ImageView) findViewById(R.id.img_country);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagepickclick();

            }
        });
        img_camera = (ImageView) findViewById(R.id.img_camera);
        img_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkpermission();
            }
        });
        txt_country=(TextView)findViewById(R.id.txt_country);
        txt_country.setText(session.getCountry());

        btn_game_results = (Button) findViewById(R.id.btn_game_results);
        txt_wins_ranking_percentage = (TextView) findViewById(R.id.txt_wins_ranking_percentage);
        ////////////////////
//        Toast.makeText(getApplicationContext(),""+session.getWin(),Toast.LENGTH_SHORT).show();
//        txt_wins_ranking_percentage.setText(session.getWin());
        txt_loses_ranking_percentage1 = (TextView) findViewById(R.id.txt_loses_ranking_percentage1);
        txt_Waiting_percenatage = (TextView) findViewById(R.id.txt_Waiting_percenatage);
        txt_draw_ranking_percentage1 = (TextView) findViewById(R.id.txt_draw_ranking_percentage1);
        txt_global_rank = (TextView) findViewById(R.id.txt_global_rank);
        recyclerView_achivements = (RecyclerView) findViewById(R.id.recyclerView_achivements);
        /////////////////////////// mail function /////////////////
        btn_topic = (Button) findViewById(R.id.btn_topic);
        btn_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:to@gmail.com")));
            }
        });
        ///////////////////////////////////end //////////////////////
        Picasso.with(this).load(Utils.user_image_url + session.getProfile_pic())
                .fit()
                .placeholder(R.drawable.ic_face)
                .into(profile_image);
        Picasso.with(this).load(Utils.country_flag_url + session.getCountry_flag())
                .fit()
                .placeholder(R.drawable.circle)
                .into(img_country);

        result();
        get_rankings_by_user();
        get_achivements();
        get_results();
    }

    public void get_results() {
        if (!Utils.isOnline(this)) {
            Utils.nointernet(this);
        } else {
            Call<ResponseBody> call = RetrofitClient.getClient().get_results(session.getId());
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
                                int gameplayed = 0;
                                for (int i = 0; i < jsonArray2.length(); i++) {
                                    gameplayed++;
                                    JSONObject dataobj = jsonArray2.getJSONObject(i);
                                    if (dataobj.optString("result").equals("Won")) {
                                        won++;
                                    } else if (dataobj.optString("result").equals("waiting")) {
                                        waiting++;
                                    } else if (dataobj.optString("result").equals("Draw")) {
                                        draw++;
                                    } else {
                                        lose++;

                                    }
                                }
                                txt_wins_ranking_percentage.setText(won + " ");
                                txt_loses_ranking_percentage1.setText(lose + " ");
                                txt_Waiting_percenatage.setText(waiting + " ");
                                txt_draw_ranking_percentage1.setText(draw + " ");
                                txt_game_start.setText(gameplayed + "\n" +"Games");
                                //   txt_level_vs_team.setText(dataobj.optString("user_level"));
                            } else {
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
                    if (t.getMessage().equals("connect timed out")) {
                        Utils.nointernet(Profile.this);
                    }
                }
            });
        }
    }

    public void get_rankings_by_user() {
        if (!Utils.isOnline(this)) {
            Utils.nointernet(this);
        } else {
            Call<ResponseBody> call = RetrofitClient.getClient().get_rankings_by_user(session.getId());
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
                                JSONArray jsonArray2 = jo.getJSONArray("category");
//                                achivement_list = new ArrayList();
                                arrayLists = new ArrayList<>();
                                ranklist = new ArrayList<>();
                                for (int i = 0; i < jsonArray2.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray2.optJSONObject(i);
                                    JSONArray jsonArray1 = jsonObject1.getJSONArray("data");
                                    if (jsonArray1.length() >= 1) {
                                        view_pager_models = new ArrayList<>();
                                        for (int k = 0; k < jsonArray1.length(); k++) {
                                            JSONObject jsonObject2 = jsonArray1.getJSONObject(k);
//                                            view_pager_models.add(new View_pager_model(
//                                                    jsonObject2.optString("id"),
//                                                    jsonObject2.optString("name"),
//                                                    jsonObject2.optString("country"),
//                                                    jsonObject2.optString("profile_pic"),
//                                                    jsonObject1.optString("topic_id")
//                                                    , jsonObject1.optString("quiz_length"),
//                                                    jsonObject1.optString("title"),
//                                                    jsonObject1.optString("subtitle")
//                                                    , jsonObject1.optString("desc")
//                                                    , jsonObject1.optString("price"),
//                                                    jsonObject1.optString("image")
//                                                    , jsonObject1.optString("date"),
//                                                    jsonObject1.optString("status"),
//                                                    jsonObject2.optString("rank"),
//                                                    jsonObject2.optString("country_flag")
//                                            ));
                                            ranklist.add(new RankListModel(jsonObject1.optString("title"), jsonObject2.optString("rank"), jsonObject2.optString("sub_topic_rank")));
                                        }
                                        arrayLists.add(view_pager_models);
                                    }
                                }
                                /////////////// expandable listview //////////////////
                                ll_ranking = (LinearLayout) findViewById(R.id.ll_ranking);
                                ranking_listview = (com.ttb.quiz.customview.ExpandableHeightListView) findViewById(R.id.ranking_listview);
                                ListviewAdapter adapter = new ListviewAdapter(Profile.this, R.layout.exapandable_list_item, ranklist);
                                ranking_listview.setAdapter(adapter);
                                ranking_listview.setExpanded(true);
                                //////////////////////////////
                            } else {
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
                    if (t.getMessage().equals("connect timed out")) {
                        Utils.nointernet(Profile.this);
                    }
                }
            });
        }
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
                                achivement_list = new ArrayList();
                                int gameplayed = jo.optInt("total_game_played");
                                for (int i = 0; i < jsonArray2.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray2.optJSONObject(i);
                                    int check = jsonObject1.optInt("games_to_play");
                                    int per = (gameplayed * 100) / check;
                                    if (check < gameplayed) {
                                        gameplayed = gameplayed - check;
                                    }
                                    // Toast.makeText(Profile.this, ""+ jsonObject1.optString("title"), Toast.LENGTH_SHORT).show();
                                    achivement_list.add(new Achivement_model(
                                            jsonObject1.optString("title"),
                                            jsonObject1.optString("image"),
                                            jsonObject1.optString("games_to_play"),
                                            jsonObject1.optString("coins"),
                                            per
                                    ));
                                    achivementAdapter = new AchivementAdapter(getApplicationContext(), achivement_list);
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), OrientationHelper.HORIZONTAL, false);
                                    recyclerView_achivements.setLayoutManager(linearLayoutManager);
                                    recyclerView_achivements.setItemAnimator(new DefaultItemAnimator());
                                    recyclerView_achivements.setAdapter(achivementAdapter);
                                }

                            } else {
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
                        Utils.nointernet(Profile.this);
                    }
                }
            });
        }
    }


    public void checkpermission() {
        if ((ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA))
                + (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
                + (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE))
                != PackageManager.PERMISSION_GRANTED) {
            // Toast.makeText(getApplicationContext(), "545456", Toast.LENGTH_SHORT).show();
            // Check Permissions Now
            ActivityCompat.requestPermissions(Profile.this,
                    new String[]{
                            android.Manifest.permission.CAMERA,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                            ,
                    },
                    REQUEST_LOCATION);

            // Check Permissions Now
        } else {
            imagepickclick();
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
            checkpermission();
        }
    }

    public void imagepickclick() {
        picUri = null;
        CropImage.activity(picUri)
                //  .setMinCropResultSize(850,500)
                .setAspectRatio(100, 132)
                //.setMaxCropResultSize(850,500)
                .start(this);
    }

    //////////////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    picUri = result.getUri();
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result.getUri());
                    // Saving image to mobile internal memory for sometime  //  bitmap = getResizedBitmap(bitmap, 100);
                    String root = getApplicationContext().getFilesDir().toString();
                    File myDir = new File(root + "/quiz");
                    myDir.mkdirs();
                    Random generator = new Random();
                    int n = 1000000;
                    n = generator.nextInt(n);
                    String fname = "Img" + n + ".jpg";
                    mCurrentPhotoPath = root + "/quiz/" + fname;
                    File file1 = new File(myDir, fname);
                    saveFile(bitmap, file1);
                    File file = new File(mCurrentPhotoPath);
                    Log.i("Name", "File" + file.getName());
                    photo_name = file.getName();
                    Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
                    Bitmap resized = Bitmap.createScaledBitmap(bitmap, 370, 459, true);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    resized.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    String imgageBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    String imageString = "data:image/png;base64," + imgageBase64;
                    Log.i("Resized_Image", "imageString" + imageString);
                    profile_image.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));
                    //Image Upload to server Here
                    upload_image();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    //    Saving file to the mobile internal memory
    private void saveFile(Bitmap sourceUri, File destination) {
        if (destination.exists()) destination.delete();
        try {
            FileOutputStream out = new FileOutputStream(destination);
            sourceUri.compress(Bitmap.CompressFormat.JPEG, 60, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void result() {
        btn_game_results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Games_Results.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });

        txt_star.setText(session.getTotal_points());
   }

    public void upload_image() {
        pd = new ProgressDialog(Profile.this);
        pd.setMessage("Please Wait..");
        pd.setCancelable(false);
        pd.show();
        File file_photo = new File(String.valueOf(mCurrentPhotoPath));
        RequestBody photoFile = RequestBody.create(MediaType.parse("image"), file_photo);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("file", file_photo.getName(), photoFile);
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), session.getId());
        Call<ResponseBody> call = RetrofitClient.getClient().profile_image(user_id, photo);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                pd.dismiss();
                if (response.isSuccessful()) {
                    // Snackbar.make(et_name, "Details Updated Successfully", BaseTransientBottomBar.LENGTH_SHORT).show();
                    try {
                        String result = response.body().string();
                        sharedPreferencesHandler.WritePreference("user_data", result);
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
                            session.setRanking(dataobj.optString("ranking"));
                            session.setUser_level(dataobj.optString("user_level"));
                            session.setCountry_flag(dataobj.optString("country_flag"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        pd.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                pd.dismiss();
                Log.i("ResponseBody", t.getMessage() + "");
            }
        });
    }


}