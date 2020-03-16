package com.example.maricosurvey;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {
    public static final String SECURITY_TAG = "Security Permission";
    private static final int REQUEST_Code = 0;
    private int PERMISSION_ALL = 1;
    public static String code = "", message = "", name = "", uid = "", empID = "", userTypeId = "";
    private static String[] PERMISSIONS_LIST = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.INTERNET
    };
    private ImageView iv;
    Button btnlogin;
    EditText edtid, edtpass;
    JSONObject jsonObject;
    JSONArray jsonArray;
    String Json_String;
    boolean networkAvailable = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //logo animation blink.
        Animation animation = new AlphaAnimation(1, (float) 0.70); //to change visibility from visible to invisible
        animation.setDuration(1000); //1 second duration for each animation cycle
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE); //repeating indefinitely
        animation.setRepeatMode(Animation.REVERSE); //animation will start from end point once ended.
        iv = (ImageView) findViewById(R.id.iv);
        iv.startAnimation(animation); //to start animation


        checkPermission();

        LoginButton();
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    private void checkPermission() {
        if (!hasPermissions(this, PERMISSIONS_LIST)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_LIST, PERMISSION_ALL);
        }
    }


    public void LoginButton() {
        btnlogin = (Button) findViewById(R.id.btnlogin);
        edtid = findViewById(R.id.edtid);
        edtpass = findViewById(R.id.edtpass);
        btnlogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        networkAvailable = com.example.maricosurvey.CustomUtility.haveNetworkConnection(LoginActivity.this);
                        //checking for
                        if (networkAvailable) {
                            String id  = edtid.getText().toString();
                            String pass = edtpass.getText().toString();
                            if(id.equals("") | pass.equals("")) {
                                AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                                alert.setTitle("Required!");
                                alert.setMessage("You can't leave any field blank.");
                                alert.setPositiveButton("ok", null);
                                alert.show();
                            }
                            else {
                                Intent intent = new Intent(getApplicationContext(), form.class);
                                startActivity(intent);
                               // new LoginTask().execute(id, pass);
                            }
                        }
                        else {
                            com.example.maricosurvey.CustomUtility.showAlert(LoginActivity.this, "Please Check your internet connection","Network Warning !!!");
                        }
                    }
                }
        );
    }

    private void parseJson() {
        if (Json_String == null) {
            Log.e("Json", "First Get Json\n" + Json_String);
        } else {
            try {

                jsonObject = new JSONObject(Json_String);
                jsonArray = jsonObject.getJSONArray("server_response");
                JSONObject jo = jsonArray.getJSONObject(0);
                code = jo.getString("code");
                message = jo.getString("message");

                if (code.equals("login_true")) {
                    name = jo.getString("name");
                    uid = jo.getString("uId");
                    empID = jo.getString("empId");
                    userTypeId = jo.getString("uTypeId");
                    finish();
                    Bundle IDbundle = new Bundle();
                    IDbundle.putString("name", name);
                    IDbundle.putString("empid", empID);
                    IDbundle.putString("uId", uid);
                    IDbundle.putString("uTypeId", userTypeId);
                    Intent intent = new Intent(getApplicationContext(), form.class);
                    intent.putExtras(IDbundle);
                    startActivity(intent);
                }
                else if (code.equals("login_false")) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                    alert.setTitle("Login Failed");
                    alert.setMessage("Your ID & Password Doesn't Match!!  ");
                    alert.setPositiveButton("Ok", null);
                    alert.show();
                    return;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    class LoginTask extends AsyncTask<String, Void, String> {

        String json_url;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            json_url = "http://imslpro.com/Android/android_login.php";
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setTitle("Please wait...");
            progressDialog.setMessage("");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String id, pass;
            id = args[0];
            pass = args[1];

            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&" +
                        URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8") + "&";
                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }

//                inputStream.close();
                httpURLConnection.disconnect();
                Thread.sleep(5000);
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            Json_String = result;
            //Toast.makeText(getApplicationContext(), "First Get Json\n"+Json_String, Toast.LENGTH_SHORT).show();
            parseJson();
        }
    }

}
