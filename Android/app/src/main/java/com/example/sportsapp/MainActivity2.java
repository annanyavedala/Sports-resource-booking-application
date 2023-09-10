package com.example.sportsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import  com.example.sportsapp.BookedResource;

public class MainActivity2 extends AppCompatActivity {
    static String accessTkn;
    String aT;
    static TextView text;
    static JSONObject idBookingLog;
    private RequestQueue queue;
    JsonArrayRequest arrayRequest;
    LinearLayout availablelist;
    LinearLayout bookedlist;
    TextView bookedmsg;
    JSONObject data;
    private RequestQueue bookqueue;
    TextView text3;
    TextView text5;
    Boolean value;
    int bookPress;
    BookedResource userBookedData;
    //TextView textE2;
    LinearLayout bookedE2;
    Button optionE2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bookedlist = (LinearLayout) findViewById(R.id.bookedlist);
        text = (TextView) findViewById(R.id.bookedmsg);
        availablelist=(LinearLayout) findViewById(R.id.availablelist);
        userBookedData = new BookedResource();




        text3 = (TextView) findViewById(R.id.textView3);
        text5 = (TextView) findViewById(R.id.textView5);

        optionE2 = (Button) findViewById(R.id.optionE2);
        bookedE2=(LinearLayout) findViewById(R.id.bookedE2);
        bookedE2.setVisibility(View.INVISIBLE);

        Booked();





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.history:
                openActivity3();
                return true;
            case R.id.usericon:
                openActivity4();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

        //return super.onOptionsItemSelected(item);
    }
    public void openActivity3() {
        Intent intent1 = new Intent(this,MainActivity3.class);
        startActivity(intent1);
    }
    public  void openActivity4() {
        Intent intent2 = new Intent(this,MainActivity4.class);
        startActivity(intent2);
    }
    int c=1;



    public void DisplayResources(){


        String URL = "https://sport-resources-booking-api.herokuapp.com/ResourcesPresent";
        queue = Volley.newRequestQueue(this);
        arrayRequest = new JsonArrayRequest(Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String newResponse = response.toString();
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        Resource[] resources = gson.fromJson(newResponse,Resource[].class);
                        int len = resources.length;
                        if (c==1){
                        for(int i=0;i<len;i++){
                            Resource unit = resources[i];
                            add(availablelist,unit);

                        } }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast toast = Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+accessTkn);
                return params;
            }
        };

        queue.add(arrayRequest);

    }

    private void cancel() {

        //c=1;
        //DisplayResources();

        RequestQueue Cqueue;
        Cqueue = Volley.newRequestQueue(this);

        JSONObject cid=new JSONObject();
        try {
            cid.put("id",MainActivity.sroll);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String CURL = "https://sport-resources-booking-api.herokuapp.com/cancelBooking";

        JsonObjectRequest CobjectRequest = new JsonObjectRequest(Request.Method.POST,
                CURL,
                cid,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(MainActivity2.this, "Cancel Successful!", Toast.LENGTH_SHORT).show();
                        Booked();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Cancel Failed! Check terms and conditions",Toast.LENGTH_LONG).show();
                        Booked();

                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + accessTkn);
                return params;
            }
            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", MainActivity.sroll);
                return params;
            }
        };
        Cqueue.add(CobjectRequest);

    }

    public void Booked() {
        RequestQueue queueB;
        queueB = Volley.newRequestQueue(this);
        idBookingLog = new JSONObject();

        try {
            idBookingLog.put("id",MainActivity.sroll);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String URL_bookingLog = "https://sport-resources-booking-api.herokuapp.com/userBookingslog";

        JsonObjectRequest objectRequest_bookingLog = new JsonObjectRequest(Request.Method.POST,
                URL_bookingLog,
                idBookingLog,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        value = true;
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        userBookedData = gson.fromJson(String.valueOf(response),BookedResource.class);

                        if (userBookedData.getStatus()==0){
                            bookedE2.setVisibility(View.VISIBLE);
                            text.setText(userBookedData.getResourceName());
                            text.setTextSize(20);
                            optionE2.setEnabled(true);
                            optionE2.setText("Cancel");
                            optionE2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    cancel();
                                }
                            });
                        }

                        else if (userBookedData.getStatus()==1){
                            bookedE2.setVisibility(View.INVISIBLE);
                            text.setText("You have already booked a resource !");
                            text.setTextSize(19);

                        }
                        c=2;
                        DisplayResources();



                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        value = false;
                        text.setText("No Bookings");
                        bookedE2.setVisibility(View.INVISIBLE);
                        text.setTextSize(25);
                        DisplayResources();


                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + accessTkn);
                return params;
            }
            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", MainActivity.sroll);
                return params;
            }
        };
        queueB.add(objectRequest_bookingLog);



    }

    public void add(LinearLayout list, final Resource unit) {
//        String r = unit.getResourceName();
//        String c = "Count: " + unit.getResourcesAvailable().toString() + "/" + unit.getCount().toString();
//        LinearLayout resourceset = new LinearLayout(this);
//        resourceset.setOrientation(LinearLayout.HORIZONTAL);
//        resourceset.setBackground(getResources().getDrawable(R.drawable.lay_bg));
//        resourceset.setPadding(18,32,18,32);
//        LinearLayout resourceE1 = new LinearLayout(this);
//        resourceE1.setOrientation(LinearLayout.VERTICAL);
//        LinearLayout resourceE2 = new LinearLayout(this);
//        //resourceE1.setBackgroundColor(Color.RED);
//        //resourceE2.setBackgroundColor(Color.GREEN);
//        resourceE1.setPadding(40, 30, 0, 20);
//        resourceE2.setPadding(0, 40, 30, 40);
//        resourceE1.setMinimumWidth(500);
//        TextView tvResourse = new TextView(this);
//        TextView tvCount = new TextView(this);
//        Space space = new Space(this);
//        tvResourse.setText(r);
//        tvResourse.setTextColor(Color.WHITE);
//        tvResourse.setTextSize(18);
//        tvResourse.setTypeface(Typeface.SERIF);
//        tvCount.setText(c);
//        tvCount.setTextColor(Color.WHITE);
//        tvCount.setTextSize(12);
////        TextView bookbtn = new TextView(this);
     // Button bookbtn = new Button(this);
//        bookbtn.setText("Book");
        if (value){
            Button bookbtn = new Button(this);
            bookbtn.setEnabled(false);
        }
        else{
            String r = unit.getResourceName();
            String c = "Count: " + unit.getResourcesAvailable().toString() + "/" + unit.getCount().toString();
            LinearLayout resourceset = new LinearLayout(this);
            resourceset.setOrientation(LinearLayout.HORIZONTAL);
            resourceset.setBackground(getResources().getDrawable(R.drawable.lay_bg));
            resourceset.setPadding(18,32,18,32);
            LinearLayout resourceE1 = new LinearLayout(this);
            resourceE1.setOrientation(LinearLayout.VERTICAL);
            LinearLayout resourceE2 = new LinearLayout(this);
            //resourceE1.setBackgroundColor(Color.RED);
            //resourceE2.setBackgroundColor(Color.GREEN);
            resourceE1.setPadding(40, 30, 0, 20);
            resourceE2.setPadding(0, 40, 30, 40);
            resourceE1.setMinimumWidth(500);
            TextView tvResourse = new TextView(this);
            TextView tvCount = new TextView(this);
            Space space = new Space(this);
            tvResourse.setText(r);
            tvResourse.setTextColor(Color.WHITE);
            tvResourse.setTextSize(18);
            tvResourse.setTypeface(Typeface.SERIF);
            tvCount.setText(c);
            tvCount.setTextColor(Color.WHITE);
            tvCount.setTextSize(12);
//        TextView bookbtn = new TextView(this);
            Button bookbtn = new Button(this);
            bookbtn.setText("Book");
            bookbtn.setPadding(40, 20, 40, 20);
            bookbtn.setBackground(getResources().getDrawable(R.drawable.btn_bg));
            //bookbtn.setBackgroundColor(Color.WHITE);
            bookbtn.setTextColor(getResources().getColor(R.color.layoutbg));
            resourceE1.addView(tvResourse);
            resourceE1.addView(tvCount);
            resourceE2.addView(bookbtn);
            resourceset.addView(resourceE1);
            resourceset.addView(resourceE2);
            list.addView(resourceset);
            list.addView(space, 0, 60);
            list.setPadding(50, 0, 50, 0);
            bookedmsg = (TextView) findViewById(R.id.bookedmsg);
            bookbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    book(MainActivity.sroll, unit.getResourceName());
                }
            });
        }
//        bookbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (value){
//                    Toast.makeText(getApplicationContext(),"BOOKING UNSUCCESSFUL",Toast.LENGTH_LONG).show();
//
//                }
//                else {
//
//
//                    book(MainActivity.sroll,unit.getResourceName());
//
//                }
//
//                book(MainActivity.sroll,unit.getResourceName());
//
//            }
//
//
//
//
//
//        });
//        bookbtn.setPadding(40, 20, 40, 20);
//        bookbtn.setBackground(getResources().getDrawable(R.drawable.btn_bg));
//        //bookbtn.setBackgroundColor(Color.WHITE);
//        bookbtn.setTextColor(getResources().getColor(R.color.layoutbg));
//        resourceE1.addView(tvResourse);
//        resourceE1.addView(tvCount);
//        resourceE2.addView(bookbtn);
//        resourceset.addView(resourceE1);
//        resourceset.addView(resourceE2);
//        list.addView(resourceset);
//        list.addView(space, 0, 60);
//        list.setPadding(50, 0, 50, 0);
//        bookedmsg = (TextView) findViewById(R.id.bookedmsg);
    }



    private void book(final String sroll, final String resourceName) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String date = sdf.format(new Date());
        SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
        String time = sd.format(new Date());



        JSONObject bookingDetails = new JSONObject();




        try {
            bookingDetails.put("id",sroll);
            bookingDetails.put("name",resourceName);
            bookingDetails.put("day",date);
            bookingDetails.put("reservation_time", "12:16:00");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String URL1= "https://sport-resources-booking-api.herokuapp.com/bookResource";
        bookqueue=Volley.newRequestQueue(this);




        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.POST,
                URL1,
                bookingDetails,
                new Response.Listener<JSONObject>() {



                    @Override
                    public void onResponse(JSONObject response) {


                        text.setText("Booking Successful1");


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                if (value || bookPress==1) {
//
//                    Toast.makeText(getApplicationContext(),"BOOKING UNSUCCESSFUL",Toast.LENGTH_LONG).show();
//                    if (bookPress==0){
//                        bookPress+=1;
//                    }
//                    }
//                else {
//                    Toast.makeText(getApplicationContext(),"BOOKING SUCCESSFUL",Toast.LENGTH_LONG).show();
//                }

                //Toast.makeText(getApplicationContext(),"BOOKING SUCCESSFUL",Toast.LENGTH_LONG).show();
                Booked();
                Toast.makeText(getApplicationContext(),"BOOKING SUCCESSFUL",Toast.LENGTH_LONG).show();



            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+accessTkn);
                return params;
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> map = new HashMap<String, String>();
                map.put("id",sroll);
                map.put("name",resourceName);
                map.put("day",date);
                map.put("reservation_time","12:10:00");
                return map;



            }


        };
        bookqueue.add(objectRequest);




    }


}
