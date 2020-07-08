package com.example.myapplication;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

public class Attendance extends AppCompatActivity {
    private static String ATTENDANCE_URL = "";

    //    private static final String ATTENDANCE_URL = "http://192.168.0.107/api.php";
    public Adapater adapter, adapter2;
    RelativeLayout r1;
    TextView th_avg, txt_theory, txt_practical, pr_avg, avg_attendance;
    private RecyclerView recycler, recyclerViewpractical;
    private List<Subject> sub_list = new ArrayList<>();
    private List<Subject> sub_list2 = new ArrayList<>();

    private ArrayList<Long> percentage_list1 = new ArrayList<>();
    private ArrayList<Long> percentage_list2 = new ArrayList<>();
    private ImageView sucess_icon;
    private SharedPreferences prf;
    private String roll,sem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        recycler = findViewById(R.id.recyclerView);
        recyclerViewpractical = findViewById(R.id.recyclerViewpractical);

        recycler.setHasFixedSize(true);
        recyclerViewpractical.setHasFixedSize(true);
        th_avg = findViewById(R.id.th_avg);
        pr_avg = findViewById(R.id.pr_avg);
        avg_attendance = findViewById(R.id.avg_attendance);
        txt_theory = findViewById(R.id.txt_theory);
        txt_practical = findViewById(R.id.txt_practical);
        sucess_icon = findViewById(R.id.sucess_icon);
        ATTENDANCE_URL = URL_Class.getUrl()+"api.php";
        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        roll = prf.getString("username",null);
        sem = prf.getString("sem",null);
        Toast.makeText(this, "Sem:"+sem, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "username:"+roll, Toast.LENGTH_SHORT).show();

        boolean isConnected = checkNetworkConnection();
        if (!isConnected) {
            Toast.makeText(this, "No Internet", Toast.LENGTH_LONG).show();
            Intent i = new Intent(Attendance.this, NoInternetActivity.class);
            i.putExtra("ActivityName", "Attendance");
            startActivity(i);
            overridePendingTransition(R.anim.slidein_right, R.anim.slideout_left);
        } else {
            Toast.makeText(this, "Connected to Internet", Toast.LENGTH_LONG).show();
        }

        int len = getAttendance();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4, GridLayoutManager.HORIZONTAL, false);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getApplicationContext(), 4, GridLayoutManager.HORIZONTAL, false);
        recycler.setLayoutManager(gridLayoutManager);
        recyclerViewpractical.setLayoutManager(gridLayoutManager2);

    }

    public int getAttendance() {
        final int[] len = new int[1];
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ATTENDANCE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray json_subjects = new JSONArray(response);
                    JSONArray json_theory = new JSONArray();
                    JSONArray json_practical = new JSONArray();
                    json_theory = json_subjects.getJSONArray(0);
                    json_practical = json_subjects.getJSONArray(1);
                    len[0] = json_theory.length();
                    Subject hader1 = new Subject("");
                    sub_list.add(hader1);
                    sub_list2.add(hader1);
//                    attendance_list.add(hader1);
                    Subject hader2 = new Subject("Total");
                    sub_list.add(hader2);
                    sub_list2.add(hader2);
//                    attendance_list.add(hader2);
                    Subject hader3 = new Subject("Attended");
                    sub_list.add(hader3);
                    sub_list2.add(hader3);
//                    attendance_list.add(hader3);
                    Subject hader4 = new Subject("Percentage");
                    sub_list.add(hader4);
                    sub_list2.add(hader4);
//                    attendance_list.add(hader4);
                    for (int i = 0; i < json_theory.length(); i++) {
                        JSONObject subject_object = json_theory.getJSONObject(i);
                        String subject_name = subject_object.getString("subjects");
                        String total_lec = subject_object.getString("total_lec");
                        String attended_lec = subject_object.getString("attend_lec");
                        Subject subject = new Subject(subject_name);
                        Subject total = new Subject(total_lec);
                        Double t = Double.parseDouble(total_lec);
                        Double a = Double.parseDouble(attended_lec);
                        long rad = Math.round((a / t) * 100);
                        percentage_list1.add(rad);
                        Subject attend = new Subject(attended_lec);
                        Subject hader5 = new Subject(rad + "%");
                        sub_list.add(subject);
                        sub_list.add(total);
                        sub_list.add(attend);
                        sub_list.add(hader5);

                    }

                    for (int i = 0; i < json_practical.length(); i++) {
                        JSONObject practical_object = json_practical.getJSONObject(i);
                        String subject_name = practical_object.getString("subjects_pracs");
                        String total_lec = practical_object.getString("total_practical");
                        String attended_lec = practical_object.getString("attend_pracs");
                        Subject subject = new Subject(subject_name);
                        Subject total = new Subject(total_lec);
                        Double t = Double.parseDouble(total_lec);
                        Double a = Double.parseDouble(attended_lec);
                        Toast.makeText(Attendance.this, attended_lec, Toast.LENGTH_SHORT).show();
                        long rad = Math.round((a / t) * 100);
                        percentage_list2.add(rad);
                        Subject attend = new Subject(attended_lec);
                        Subject hader5 = new Subject(rad + "%");
                        sub_list2.add(subject);
                        sub_list2.add(total);
                        sub_list2.add(attend);
                        sub_list2.add(hader5);

                    }

                    long sum = 0, sum2 = 0;
                    for (int i = 0; i < percentage_list1.size(); i++) {
                        sum += percentage_list1.get(i);
                    }
                    long rad = Math.round((sum / json_theory.length()));
                    th_avg.setText(rad + "%");
                    for (int i = 0; i < percentage_list2.size(); i++) {
                        sum2 += percentage_list2.get(i);
                    }
                    long rad2 = Math.round((sum2 / json_practical.length()));
                    pr_avg.setText(rad2 + "%");
                    long avg = ((rad + rad2) / 2);
                    avg_attendance.setText(avg + "%");
                    if (avg >= 75) {
                        sucess_icon.setImageResource(R.drawable.ic_tick);
                    } else {
                        sucess_icon.setImageResource(R.drawable.ic_quit);
                    }
                    adapter = new Adapater(sub_list, Attendance.this);
                    recycler.setAdapter(adapter);
                    adapter2 = new Adapater(sub_list2, Attendance.this);
                    recyclerViewpractical.setAdapter(adapter2);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Attendance.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
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
