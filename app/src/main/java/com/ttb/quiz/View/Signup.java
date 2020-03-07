package com.ttb.quiz.View;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ttb.quiz.R;
import com.ttb.quiz.connections.RetrofitClient;
import com.ttb.quiz.connections.SharedPreferencesHandler;
import com.ttb.quiz.connections.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends AppCompatActivity  {

    TextView txt_terms, txt_policy;
    LinearLayout ll_login;
    Button btn_signup;
    EditText et_password, et_email, et_name;


    com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner spinner_country,celt_student;
    ArrayList<String> country_type,celt_student_list;
    ProgressDialog pd;
    SharedPreferencesHandler sharedPreferencesHandler;

    TextView txt_name;
    ImageView iv_backpressed;
    LinearLayout ly_celt_student;
    String cte="";

    @Override
    protected void onResume() {
        super.onResume();
        txt_name.setText("Sign Up");
    }

    private boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        sharedPreferencesHandler=new SharedPreferencesHandler(this);
        iv_backpressed = (ImageView) findViewById(R.id.iv_backpressed);
        iv_backpressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_policy = (TextView) findViewById(R.id.txt_policy);
        txt_terms = (TextView) findViewById(R.id.txt_terms);
        ll_login = (LinearLayout) findViewById(R.id.ll_login);
        btn_signup = (Button) findViewById(R.id.btn_signup);
        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        ly_celt_student=(LinearLayout)findViewById(R.id.ly_celt_student);
        spinner_country = (com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner) findViewById(R.id.spinner_country);
        celt_student = (com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner) findViewById(R.id.celt_student);
        celt_student_list=new ArrayList<>();
        celt_student_list.add("YES");
        celt_student_list.add("NO");
        ArrayAdapter aa = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, celt_student_list);
        celt_student.setAdapter(aa);
        getcountrymodel();
        et_email = (EditText) findViewById(R.id.et_email);
        signup();
    }

    public void signup() {
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_name.getText().toString().isEmpty()) {
                    et_name.setError("Name can't be empty");
                    et_name.requestFocus();
                } else if (!validEmail(et_email.getText().toString())) {
                    et_email.setError("Check your Email");
                    et_email.requestFocus();
                } else if (et_password.getText().toString().isEmpty()) {
                    et_password.setError("Password can't be Blank");
                    et_password.requestFocus();
                } else {
                    sign_up(et_name.getText().toString(),
                            et_email.getText().toString(), et_password.getText().toString(),spinner_country.getText().toString());
                }
            }
        });

        ll_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });



        txt_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Privacypolicy.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });

        spinner_country.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(spinner_country.getText().toString().equals("Azerbaijan") || spinner_country.getText().toString().equals("Turkey") ){
                    //ly_celt_student.setVisibility(View.VISIBLE);
                    ly_celt_student.setVisibility(View.GONE);

                }else {
                    cte="";
                    ly_celt_student.setVisibility(View.GONE);
                }

            }

            public void onNothingSelected(AdapterView parent) {
                // Do nothing.
            }
        });
        celt_student.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                cte=celt_student.getText().toString();
            }

            public void onNothingSelected(AdapterView parent) {
                // Do nothing.
            }
        });
    }
//
//    //Performing action onItemSelected and onNothing selected
//
//    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
//        // Toast.makeText(getApplicationContext(), country[position], Toast.LENGTH_LONG).show();
//    }
//
//    public void onNothingSelected(AdapterView<?> arg0) {
//        // TODO Auto-generated method stub
//    }


    public void sign_up(final String name, final String email, final String password, String country) {
        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait..");
        pd.show();
        pd.setCancelable(true);
        if (!Utils.isOnline(this)) {
            Utils.nointernet(this);
        } else {
            Call<ResponseBody> call = RetrofitClient.getClient().user_register(name, email, password, country);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        pd.dismiss();
                        try {
                            String result = response.body().string();
                            sharedPreferencesHandler.WritePreference("user_data",result);
                            JSONObject jsonObject;
                            JSONArray jsonArray, jsonArray1;
                            jsonObject = new JSONObject(result);
                            jsonArray = jsonObject.getJSONArray("response");
                            JSONObject jo = jsonArray.getJSONObject(0);
//                            Toast.makeText(Signup.this, ""+result, Toast.LENGTH_SHORT).show();
                            if (jo.getBoolean("success")) {
                                JSONArray jsonArray2 = jo.getJSONArray("data");
                                JSONObject dataobj = jsonArray2.getJSONObject(0);
                                int status = dataobj.optInt("status");
                                if(status==1) {
                                    sharedPreferencesHandler.WritePreference("number",email);
                                    sharedPreferencesHandler.WritePreference("password",password);
                                    startActivity(new Intent(getApplicationContext(), Home.class));
                                    overridePendingTransition(R.anim.enter, R.anim.exit);
                                    Toast.makeText(Signup.this, "Welcome " + name, Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(Signup.this, "" + name, Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    AlertDialog alertbox = new AlertDialog.Builder(Signup.this)
                                            .setMessage("Your Account is blocked, Contact to System Admin.")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                                // do something when the button is clicked
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    finishAffinity();
                                                }
                                            })
                                            .show();
                                }
//
                            } else {
                                et_password.setError(jo.optString("message"));
                                et_password.requestFocus();
                            }

                        } catch (Exception e) {
//                            Toast.makeText(Signup.this, "fff", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    } else {
//                        Toast.makeText(getApplicationContext(), "Not Working", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if (t.getMessage().equals("connect timed out")) {
                        Utils.nointernet(Signup.this);
                    }
                }
            });
        }
    }


    public void getcountrymodel() {
        if (!Utils.isOnline(this)) {
            Utils.nointernet(this);
        } else {
            Call<ResponseBody> call = RetrofitClient.getClient().countries_list();
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            String result = response.body().string();
                            Log.i("result", result);
                            JSONObject jsonObject;
                            JSONArray jArray;
                            jsonObject = new JSONObject(result);
                            jArray = jsonObject.getJSONArray("response");
                            JSONObject jo = jArray.getJSONObject(0);
                            if (jo.getBoolean("success")) {
                                // Toast.makeText(getApplicationContext(),jsonObject.optString("message")+"",Toast.LENGTH_LONG).show();
                                JSONArray jsonArray = jo.getJSONArray("data");
                                country_type = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    country_type.add(jsonObject1.optString("country_name"));
                                }
                                ArrayAdapter aa = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, country_type);
                                spinner_country.setAdapter(aa);

//                                Toast.makeText(getApplicationContext(), "Successfully Signup",  Toast.LENGTH_SHORT).show();
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
                    //  Toast.makeText(getApplicationContext(), ""+t.getMessage(), Toast.LENGTH_LONG).show();
                    if (t.getMessage().equals("connect timed out")) {
                        Utils.nointernet(Signup.this);
                    }
                }
            });
        }
    }

}
