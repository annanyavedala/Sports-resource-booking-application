package com.example.sportsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity6 extends AppCompatActivity {
    EditText roll;
    Button submit;
    JSONObject id;
    JsonObjectRequest forgot_password;
    private RequestQueue queueE;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        roll = findViewById(R.id.roll);
        final String rollno = roll.toString();
        submit = findViewById(R.id.button2);
        final String URLE = "https://sport-resources-booking-api.herokuapp.com/forgot_password";
        queueE = Volley.newRequestQueue(this);
        id = new JSONObject();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    id.put("id",rollno);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                forgot_password = new JsonObjectRequest(Request.Method.POST,
                        URLE,
                        id,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast toast = Toast.makeText(getApplicationContext(), "check mail", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast toast = Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG);
                                toast.show();
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
                        params.put("id",rollno);
                        return params;
                    }
                };
                queueE.add(forgot_password);
            }
        });

    }
}