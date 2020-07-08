package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ResultActivity extends AppCompatActivity {
        private static String ATTENDANCE_URL = "";
    //    private static final String ATTENDANCE_URL = "http://192.168.0.107/result.php";
    public Adapter2 adapter, adapter2,adapater3;
    private TextView txt_tt1,txt_tt2,txt_avg;
    private RecyclerView recyclerViewtt1,recyclerViewtt2,recyclerViewavg;
    private List<Subject> rs_list = new ArrayList<>();
    private List<Subject> rs_list2 = new ArrayList<>();
    private List<Subject> rs_list3 = new ArrayList<>();
    private ArrayList<Long> avg_list = new ArrayList<>();
    private SharedPreferences prf;
    private String roll,sem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        txt_tt1 = (TextView)findViewById(R.id.txt_tt1);
        txt_tt2 = (TextView)findViewById(R.id.txt_tt2);
        txt_avg = (TextView)findViewById(R.id.txt_avg);

        recyclerViewtt1 = (RecyclerView)findViewById(R.id.recyclerViewtt1);
        recyclerViewtt2 = (RecyclerView)findViewById(R.id.recyclerViewtt2);
        recyclerViewavg = (RecyclerView)findViewById(R.id.recyclerViewavg);
        ATTENDANCE_URL = URL_Class.getUrl()+"result.php";
        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        roll = prf.getString("username",null);
        sem = prf.getString("sem",null);
        Toast.makeText(this, "Sem:"+sem, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Roll:"+roll, Toast.LENGTH_SHORT).show();

        boolean isConnected = checkNetworkConnection();
        if (!isConnected) {
            Toast.makeText(this, "No Internet", Toast.LENGTH_LONG).show();
            Intent i = new Intent(ResultActivity.this, NoInternetActivity.class);
            i.putExtra("ActivityName", "Attendance");
            startActivity(i);
            overridePendingTransition(R.anim.slidein_right, R.anim.slideout_left);
        } else {
            Toast.makeText(this, "Connected to Internet", Toast.LENGTH_LONG).show();
        }

        int len = getResult();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3, GridLayoutManager.HORIZONTAL, false);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getApplicationContext(), 3, GridLayoutManager.HORIZONTAL, false);
        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(getApplicationContext(), 3, GridLayoutManager.HORIZONTAL, false);
        recyclerViewtt1.setLayoutManager(gridLayoutManager);
        recyclerViewtt2.setLayoutManager(gridLayoutManager2);
        recyclerViewavg.setLayoutManager(gridLayoutManager3);
    }

    public int getResult() {
        final int[] len = new int[1];
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ATTENDANCE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray json_subjects = new JSONArray(response);
                    JSONArray json_tt1 = new JSONArray();
                    JSONArray json_tt2 = new JSONArray();
                    JSONArray subjects_display = new JSONArray();
                    subjects_display = json_subjects.getJSONArray(0);
                    json_tt1 = json_subjects.getJSONArray(1);
                    json_tt2 = json_subjects.getJSONArray(2);
                    len[0] = subjects_display.length();
                    Subject hader1 = new Subject("");
                    rs_list.add(hader1);
                    rs_list2.add(hader1);
                    rs_list3.add(hader1);
                    Subject hader2 = new Subject("Obtained");
                    rs_list.add(hader2);
                    rs_list2.add(hader2);
                    rs_list3.add(hader2);
                    Subject hader3 = new Subject("Total");
                    rs_list.add(hader3);
                    rs_list2.add(hader3);
                    rs_list3.add(hader3);

                    for (int i = 0; i < subjects_display.length(); i++) {
                        String subject_name = subjects_display.getString(i);
                        String obtained_t1 = json_tt1.getString(i);
                        String obtained_t2 = json_tt2.getString(i);
                        Subject subject = new Subject(subject_name);
                        Subject obt1 = new Subject(obtained_t1);
                        Subject obt2 = new Subject(obtained_t2);
                        rs_list.add(subject);
                        rs_list2.add(subject);
                        rs_list3.add(subject);
                        String avg = "",t_avg = "";
                        Subject total,total2 = null;
                        Subject average,total_avg;
                        Double t;
                        rs_list.add(obt1);
                        rs_list2.add(obt2);
                        if(obtained_t1.matches("-"))
                        {
                            total = new Subject("-");
                        }
                        else
                        {
                            total = new Subject("20");
                        }
                        if(obtained_t2.matches("-"))
                        {
                            total2 = new Subject("-");
                        }
                        else
                        {
                            total2 = new Subject("20");
                        }
                        if(obtained_t1.matches("-") && obtained_t2.matches("-"))
                        {
                            avg = "-";
                            t_avg = "-";
                        }
                        else if(!obtained_t1.matches("-") && obtained_t2.matches("-"))
                        {
                            long a = Long.parseLong(obtained_t1);
                            long r = Math.round((a / 2));
                            avg = Long.toString(r);
                            t_avg = "20";
                        }
                        else
                        {
                            long a = Long.parseLong(obtained_t1);
                            long b = Long.parseLong(obtained_t2);
                            long r = Math.round(((a+b) / 2));
                            avg = Long.toString(r);
                            t_avg = "20";
                        }
                        average = new Subject(avg);
                        total_avg = new Subject(t_avg);



                        rs_list3.add(average);
                        rs_list3.add(total_avg);

                        rs_list.add(total);

                        rs_list2.add(total2);

                    }

                    adapter = new Adapter2(rs_list, ResultActivity.this);
                    recyclerViewtt1.setAdapter(adapter);
                    adapter2 = new Adapter2(rs_list2, ResultActivity.this);
                    recyclerViewtt2.setAdapter(adapter2);
                    adapater3 = new Adapter2(rs_list3, ResultActivity.this);
                    recyclerViewavg.setAdapter(adapater3);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ResultActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                prf = getSharedPreferences("user_details",MODE_PRIVATE);
                params.put("sem", sem);
                params.put("roll_no", roll);
                return params;
            }
        };
        Mysingleton.getInstance(getApplicationContext()).addToRequestque(stringRequest);
        return len[0];
    }

    private boolean checkNetworkConnection() {
        boolean wifi;
        boolean mobile;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connectivityManager.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            wifi = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobile = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if (wifi) {
            } else if (mobile) {

            }
            return true;
        } else {
            return false;
        }
    }
}

