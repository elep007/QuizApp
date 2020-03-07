package com.ttb.quiz.View;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.ttb.quiz.R;
import com.ttb.quiz.connections.RetrofitClient;
import com.ttb.quiz.connections.SharedPreferencesHandler;
import com.ttb.quiz.connections.Utils;
import com.ttb.quiz.services.MyService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    LinearLayout ll_signup;
    EditText et_email, et_password;
    TextView txt_forgot, txt_terms, txt_policy;
    Button btn_login;
    Button iv_google, iv_fb;

    ProgressDialog pd;
    SharedPreferencesHandler sharedPreferencesHandler;
    //Social Login
    String social_full_name, social_email, social_password, socialid, user_account_type;
    boolean facbooklogin = false;
    com.facebook.login.LoginManager fbLoginManager;
    private GoogleApiClient mGoogleApiClient;
    private CallbackManager mFacebookCallbackManager;
    private ProgressDialog dialog;
    Uri social_image;
    private static final int RC_SIGN_IN = 9001;
    boolean social_login = false;
    private boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    String background_music;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferencesHandler=new SharedPreferencesHandler(this);
        background_music = sharedPreferencesHandler.ReadPreferences("background_music");
        ll_signup = (LinearLayout) findViewById(R.id.ll_signup);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        txt_forgot = (TextView) findViewById(R.id.txt_forgot);
        txt_terms = (TextView) findViewById(R.id.txt_terms);
        txt_policy = (TextView) findViewById(R.id.txt_policy);
        btn_login = (Button) findViewById(R.id.btn_login);
        iv_google = (Button) findViewById(R.id.iv_google);
        iv_fb = (Button) findViewById(R.id.iv_fb);
        iv_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                social_login = true;
                fblogin();
            }
        });
        iv_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                social_login = true;
                googlelogin();
            }
        });
        facebooklogin();
        checkpermission();
        login();
    }


    public void login() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validEmail(et_email.getText().toString())) {
                    et_email.setError("Check Your Email id");
                    et_email.requestFocus();
                } else if (et_password.getText().toString().length() == 0) {
                    et_password.setError("Password is not entered");
                    et_password.requestFocus();
                } else {
                login(et_email.getText().toString(),
                        et_password.getText().toString());

//                  Toast.makeText(Login.this, "Successfully done", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txt_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Forgot.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        ll_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Signup.class));
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
    }


    public void checkpermission() {
        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION))
                + (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION))
                + (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_WIFI_STATE))
                + (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA))
                + (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE))
                + (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
                != PackageManager.PERMISSION_GRANTED) {
            // Toast.makeText(getApplicationContext(), "545456", Toast.LENGTH_SHORT).show();
            // Check Permissions Now
            ActivityCompat.requestPermissions(Login.this,
                    new String[]{
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_WIFI_STATE,
                            android.Manifest.permission.CAMERA,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    },
                    101);

            // Check Permissions Now
        } else {
            login();
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == 101) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
            checkpermission();
        }
    }
    public void login(final String number,final String password) {
//        if(number.equals("elep007@hotmail.com")){
//            startActivity(new Intent(getApplicationContext(), Home.class));
//            overridePendingTransition(R.anim.enter, R.anim.exit);
//            Toast.makeText(Login.this, "Welcome XiangYi", Toast.LENGTH_SHORT).show();
//        }

        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait..");
        pd.show();
        pd.setCancelable(true);
        if (!Utils.isOnline(this)) {
            Utils.nointernet(this);
        } else {
            Call<ResponseBody> call = RetrofitClient.getClient().login(number, password);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        pd.dismiss();
                        try {
                            String result = response.body().string();
                            sharedPreferencesHandler.WritePreference("user_data",result);
                            JSONObject jsonObject;
                            JSONArray jsonArray;
                            jsonObject = new JSONObject(result);
                            jsonArray = jsonObject.getJSONArray("response");
                            JSONObject jo = jsonArray.getJSONObject(0);
                            if (jo.getBoolean("success")) {
                                sharedPreferencesHandler.WritePreference("number",number);
                                sharedPreferencesHandler.WritePreference("password",password);
                                JSONArray jsonArray2 = jo.getJSONArray("data");
                                JSONObject dataobj = jsonArray2.getJSONObject(0);
                                int status = dataobj.optInt("status");
                                if(status==1) {
                                    startActivity(new Intent(getApplicationContext(), Home.class));
                                    overridePendingTransition(R.anim.enter, R.anim.exit);
                                    Toast.makeText(Login.this, "Welcome " + dataobj.optString("name"), Toast.LENGTH_SHORT).show();
                                }else {
                                    AlertDialog alertbox = new AlertDialog.Builder(Login.this)
                                            .setMessage("Your Account is blocked, Contact to System Admin.")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                                // do something when the button is clicked
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    finishAffinity();
                                                }
                                            })
                                            .show();
                                }
                            } else {
                                et_password.setError(jo.optString("message"));
                                et_password.requestFocus();
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
                        Utils.nointernet(Login.this);
                    }
                }
            });
        }
    }
///////////Socila Login//
// Social Login //
public void googlelogin() {
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build();
    mGoogleApiClient = new GoogleApiClient.Builder(this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build();
    final Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
    startActivityForResult(signInIntent, RC_SIGN_IN);
}

    public void fblogin() {
        facbooklogin = true;
        facebooklogin();
        fbLoginManager.logInWithReadPermissions(Login.this, Arrays.asList("email", "public_profile"));

    }

    /////////////////////////Register with Facebook/////////////////////
    public void facebooklogin() {
        FacebookSdk.sdkInitialize(this);
        fbLoginManager = com.facebook.login.LoginManager.getInstance();
        mFacebookCallbackManager = CallbackManager.Factory.create();
        fbLoginManager.registerCallback(mFacebookCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        final AccessToken accessToken = loginResult.getAccessToken();
                        GraphRequest request = GraphRequest.newMeRequest(
                                accessToken,
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        Log.i("resultobject", object + "");
                                        try {
                                            user_account_type = "facebook";
                                            social_email = object.optString("email");
                                            social_password = object.optString("id");
                                            social_full_name = object.optString("name");
                                            String gender = object.optString("gender");
                                            social_image = Uri.parse("https://graph.facebook.com/" + social_password + "/picture?type=large&width=720&height=720");
                                            socialid = object.optString("id");
                                           // user_login(social_full_name, social_email, "", social_password, "Facebook");
                                            sharedPreferencesHandler.WritePreference("user_image", social_image + "");
                                            Toast.makeText(getApplicationContext(),"Welcome "+ social_full_name, Toast.LENGTH_LONG).show();
                                        } catch (Exception e) {
                                            Toast.makeText(getApplicationContext(), e.getMessage() + "1", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,gender,email,picture.type(large)");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("fberror", error.getMessage());
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    ////google logout code////////////
    public void logout() {
        mGoogleApiClient.connect();
        mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {
                //FirebaseAuth.getInstance().signOut();
                if (mGoogleApiClient.isConnected()) {

                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            if (status.isSuccess()) {

                            }
                        }
                    });
                }
            }

            @Override
            public void onConnectionSuspended(int i) {
                //Log.d(TAG, "Google API Client Connection Suspended");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInAccount acct = result.getSignInAccount();
            try {
                if (acct.getEmail().equals("")) {

                } else {
                    user_account_type = "google";
                    social_email = acct.getEmail();
                    social_password = acct.getId();
                    socialid = acct.getId();
                    social_full_name = acct.getDisplayName();
                    // google_familyname = acct.getGivenName();
                    social_image = acct.getPhotoUrl();
                    final String givenname = "", familyname = "", displayname = "", birthday = "";
                    sharedPreferencesHandler.WritePreference("user_image", social_image + "");
                    Toast.makeText(getApplicationContext(),"Welcome "+ social_full_name, Toast.LENGTH_LONG).show();
                    //user_login(social_full_name, social_email, "", social_password, "Google");
                    logout();
                }
            } catch (Exception e) {
                Log.i("error", e.getMessage());
                Toast.makeText(this, "Someting went wrong with server. Please try diffrent login method", Toast.LENGTH_LONG).show();
            }

        } else if (facbooklogin) {
            facbooklogin = false;
            mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }


}