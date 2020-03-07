package com.ttb.quiz.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ttb.quiz.R;
import com.ttb.quiz.connections.RetrofitClient;
import com.ttb.quiz.connections.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class

Forgot extends AppCompatActivity {

    EditText et_email;
    Button btn_reset;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    TextView txt_name;
    ImageView iv_backpressed;
    ProgressDialog pd;

    @Override
    protected void onResume() {
        super.onResume();
        txt_name.setText("Forgot Password");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        iv_backpressed = (ImageView) findViewById(R.id.iv_backpressed);
        iv_backpressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //////////////////id calls//////////////////
        txt_name = (TextView) findViewById(R.id.txt_name);
        et_email = (EditText) findViewById(R.id.et_email);
        btn_reset = (Button) findViewById(R.id.btn_reset);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });
    }
        public void reset() {
        if (!et_email.getText().toString().matches(emailPattern)) {
            et_email.setError("Check your Email");
            et_email.requestFocus();
        } else {
            forgot_password(et_email.getText().toString());
//            Toast.makeText(this, "Link Successfully sent to Your Email id", Toast.LENGTH_SHORT).show();
        }
    }
            ///////////////////////retrofit email////////////////
    public void forgot_password(String email) {
        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait..");
        pd.show();
        pd.setCancelable(true);
        if (!Utils.isOnline(this)) {
            Utils.nointernet(this);
        } else {
            Call<ResponseBody> call = RetrofitClient.getClient().forgot_password(email);
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
                                Toast.makeText(Forgot.this, "New Password is " + jo.optString("message"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Login.class));
                                overridePendingTransition(R.anim.enter, R.anim.exit);
                                finish();

                            } else {
                                et_email.setError(jo.optString("message"));
                                et_email.requestFocus();
                            }
                            et_email.setText("");

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
                        Utils.nointernet(Forgot.this);
                    }
                }
            });
        }
    }

}
