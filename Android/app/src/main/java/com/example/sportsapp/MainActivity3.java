package com.example.sportsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class MainActivity3 extends AppCompatActivity {
    JsonArrayRequest arrayRequestB;
    private RequestQueue queueB;
    LinearLayout bookedhistorylist;
    BookedResource[] currentUserBookedHistory, bookedResources;
    TextView tv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        bookedhistorylist = (LinearLayout) findViewById(R.id.bookedhistorylist);
        tv4 = (TextView) findViewById(R.id.textView4);
        queueB = Volley.newRequestQueue(this);
        String URLB = "https://sport-resources-booking-api.herokuapp.com/allBookings";
        arrayRequestB = new JsonArrayRequest(Request.Method.GET,
                URLB,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

//                        String newResponse = response.toString();
//                        GsonBuilder gsonBuilder = new GsonBuilder();
//                        Gson gson = gsonBuilder.create();
//                        bookedResources = gson.fromJson(newResponse, BookedResource[].class);
                        int len = response.length();
//                        int currLen = 0;
//                        currentUserBookedHistory = new BookedResource[len];
                        for (int i = 0; i < len; i++) {
                            try {
                                Log.d("History", response.getJSONObject(i).getString("user_id"));
                                if (response.getJSONObject(i).getString("user_id").trim().equals(MainActivity.sroll)) {
                                    //tv4.setText(tv4.getText().toString() + "\n" + response.getJSONObject(i).getString("resource_name") + " " + response.getJSONObject(i).getString("day"));
                                    add(bookedhistorylist,
                                            response.getJSONObject(i).getString("resource_name"),
                                            response.getJSONObject(i).getString("day"),
                                            response.getJSONObject(i).getString("status"),
                                            response.getJSONObject(i).getString("return_time").toString());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
//                        for(int i=0;i<len;i++) {
////                            if(bookedResources[i].getUserId() == MainActivity.sroll){
////                                currentUserBookedHistory[currLen] = bookedResources[i];
////                                currLen+=1;
////                            }
////                        }
//                        for (int k=0;k<currLen;k++){
//                            add(bookedhistorylist);
//                        }
//                        add(bookedhistorylist, bookedResources, len);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast toast = Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG);
                        toast.show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + MainActivity2.accessTkn);
                return params;
            }
        };
        queueB.add(arrayRequestB);


    }


    private void add(LinearLayout bookedhistorylist,String resourcename,String date,String status,String rtime) {
        String r = resourcename;
        String c = date;
        LinearLayout bookedset = new LinearLayout(this);
        bookedset.setOrientation(LinearLayout.HORIZONTAL);
        bookedset.setBackground(getResources().getDrawable(R.drawable.lay_bg));
        bookedset.setPadding(18,32,18,32);
        LinearLayout bookedE1 = new LinearLayout(this);
        bookedE1.setOrientation(LinearLayout.VERTICAL);
        LinearLayout bookedE2 = new LinearLayout(this);
        //resourceE1.setBackgroundColor(Color.RED);
        //resourceE2.setBackgroundColor(Color.GREEN);
        bookedE1.setPadding(40, 30, 0, 20);
        bookedE2.setPadding(0, 40, 30, 40);
        bookedE1.setMinimumWidth(600);
        TextView tvResourse = new TextView(this);
        TextView tvDate = new TextView(this);
        Space space = new Space(this);
        tvResourse.setText(r);
        tvResourse.setTextColor(Color.WHITE);
        tvResourse.setTextSize(18);
        tvResourse.setTypeface(Typeface.SERIF);
        tvDate.setText(c);
        tvDate.setTextColor(Color.WHITE);
        tvDate.setTextSize(12);
        TextView returnbtn = new TextView(this);
        //returnbtn.setText("Book");
        returnbtn.setPadding(40, 20, 40, 20);
        if (status.equals("0")) {
            returnbtn.setText("Not collected");
            returnbtn.setBackground(getResources().getDrawable(R.drawable.btn_bg));
            returnbtn.setTextColor(getResources().getColor(R.color.layoutbg));
        }
        else if(status.equals("1")) {
            if (rtime.equals("null")) {
                returnbtn.setText("Not Returned");
                returnbtn.setBackground(getResources().getDrawable(R.drawable.status_bg));
                returnbtn.setTextColor(getResources().getColor(R.color.black));
                returnbtn.setTextSize(14);

            }
            else {
                returnbtn.setText("Returned");
                //tv4.setText(rtime);
                returnbtn.setBackground(getResources().getDrawable(R.drawable.status_bg_1));
                returnbtn.setTextColor(getResources().getColor(R.color.white));
                returnbtn.setTextSize(18);
            }
        }
        //returnbtn.setBackground(getResources().getDrawable(R.drawable.btn_bg));
        //bookbtn.setBackgroundColor(Color.WHITE);
        //returnbtn.setTextColor(getResources().getColor(R.color.layoutbg));
        bookedE1.addView(tvResourse);
        bookedE1.addView(tvDate);
        bookedE2.addView(returnbtn);
        bookedset.addView(bookedE1);
        bookedset.addView(bookedE2);
        bookedhistorylist.addView(bookedset);
        bookedhistorylist.addView(space, 0, 60);
        bookedhistorylist.setPadding(50, 0, 50, 0);

    }

}




