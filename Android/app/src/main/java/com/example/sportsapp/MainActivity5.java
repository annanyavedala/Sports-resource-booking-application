package com.example.sportsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity5 extends AppCompatActivity {
    JSONObject change_pwd;
    private RequestQueue queueD;
    TextView roll;
    TextView oldpwd;
    TextView newpwd;
    String id, pwd0, pwd1;
    Button button;
    JsonObjectRequest changepwdrequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        roll = (TextView) findViewById(R.id.roll);
        oldpwd = (TextView) findViewById(R.id.oldpwd);
        newpwd = (TextView) findViewById(R.id.newpwd);
        change_pwd = new JSONObject();

        id = roll.getText().toString();
        pwd0 = oldpwd.getText().toString();
        pwd1 = newpwd.getText().toString();
        button = (Button) findViewById(R.id.button);
        queueD = Volley.newRequestQueue(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    change_pwd.put("id", "160119733082");
                    change_pwd.put("password", "abc321");
                    change_pwd.put("new_password","abc123");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String URLD = "https://sport-resources-booking-api.herokuapp.com/changePassword";
                //queueD = Volley.newRequestQueue(this);

                changepwdrequest = new JsonObjectRequest(Request.Method.POST,
                        URLD,
                        change_pwd,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // toast = new Toast(getApplicationContext(), "Password changed successfully", Toast.LENGTH_LONG);
                                Toast.makeText(MainActivity5.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Authorization", "Bearer " + MainActivity2.accessTkn);
                        return params;
                    }

                    @Override
                    public Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("id", MainActivity.sroll);
                        return params;
                    }
                };
                queueD.add(changepwdrequest);

            }
        });
    }
}

//        try {
//            change_pwd.put("id",id);
//            change_pwd.put("password",pwd0);
//            change_pwd.put("new_password",pwd1);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        String URLD= "https://sport-resources-booking-api.herokuapp.com/changePassword";
//        queueD= Volley.newRequestQueue(this);
//
//        JsonObjectRequest changepwdrequest = new JsonObjectRequest(Request.Method.POST,
//                URLD,
//                change_pwd,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // toast = new Toast(getApplicationContext(), "Password changed successfully", Toast.LENGTH_LONG);
//                        Toast.makeText(MainActivity5.this, "Cancel Successful!", Toast.LENGTH_SHORT).show();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
//                    }
//                }){
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "Bearer " + MainActivity2.accessTkn);
//                return params;
//            }
//            @Override
//            public Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("id", MainActivity.sroll);
//                return params;
//            }
//        };
//        queueD.add(changepwdrequest);
//
//    }
//
//}