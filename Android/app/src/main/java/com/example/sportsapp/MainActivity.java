package com.example.sportsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    private RequestQueue queue;
    JsonObjectRequest objectRequest;
    JsonArrayRequest arrayRequest;
    JSONObject data;
    EditText roll;
    EditText pwd;
    static String sroll;
    String spwd;
    TextView forgot_pwd;
    String URL = "https://sport-resources-booking-api.herokuapp.com/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        forgot_pwd = findViewById(R.id.forgot_pwd);
        Button logbtn = (Button) findViewById(R.id.logbtn);
         roll=(EditText) findViewById(R.id.roll);
         pwd=(EditText) findViewById(R.id.pwd);


        //String URL = "https://sport-resources-booking-api.herokuapp.com/login";
       /* data = new JSONObject();
        try {
            data.put("id",sroll);
            data.put("password",spwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/


        queue = Volley.newRequestQueue(this);
        /*objectRequest = new JsonObjectRequest(Request.Method.POST,
                            URL,
                            data,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        MainActivity2.accessTkn = response.getString("access_token");
                                        textView4.setText(sroll);
                                        textView6.setText(data.toString());

                                        openActivity2();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                        textView4.setText(sroll);
                        textView6.setText(data.toString());
                        Toast toast = Toast.makeText(getApplicationContext(),"Enter Valid Credentials",Toast.LENGTH_LONG);
                        toast.show();
                    }
                });*/

        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sroll=roll.getText().toString();
                spwd=pwd.getText().toString();
                data = new JSONObject();
                try {
                    data.put("id",sroll);
                    data.put("password",spwd);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                objectRequest = new JsonObjectRequest(Request.Method.POST,
                        URL,
                        data,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    MainActivity2.accessTkn = response.getString("access_token");


                                    openActivity2();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {


                                Toast toast = Toast.makeText(getApplicationContext(),"Enter Valid Credentials",Toast.LENGTH_LONG);
                                toast.show();
                            }
                        });

                queue.add(objectRequest);
            }
        });
        forgot_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity6();
            }
        });
    }

    private void openActivity2() {

        Intent intent = new Intent(this,MainActivity2.class);
        startActivity(intent);
    }
    private  void openActivity6() {
        Intent intent = new Intent(this,MainActivity6.class);
        startActivity(intent);
    }



}