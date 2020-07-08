package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {




    private static String notification_url="";

    //    private static String notification_url="http://192.168.0.107/notification.php";
    RecyclerView recyclerView;
    NotificationAdapter adapter;
   /* Toolbar toolbar;*/

    List<Notification> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);


        notificationList=new ArrayList<>();

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
       // toolbar=findViewById(R.id.toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notification_url = URL_Class.getUrl()+"notification.php";

        /*notificationList.add(new Notification(
                "Dr. Radhika Kotecha",
                "Urgent: Students interested to participate in Smart India Hackathon 2020 (except those who have already been nominated) should meet me on Monday with their project titles.",
                "23/02/2020 || 18:15"));*/

        //,R.drawable.woman



        load_notification();

        adapter=new NotificationAdapter(this,notificationList);
        recyclerView.setAdapter(adapter);
    }

    private void load_notification(){

        StringRequest stringRequest= new StringRequest(Request.Method.GET, notification_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONArray jsonArray= new JSONArray(response);

                    for(int i=0; i<jsonArray.length();i++){

                        JSONObject jsonObject=jsonArray.getJSONObject(i);

                        String faculty_name=jsonObject.getString("faculty_name");
                        String title=jsonObject.getString("title");
                        String date= jsonObject.getString("date");
                        String images= jsonObject.getString("images");

                        //Toast.makeText(NotificationActivity.this, ""+faculty_name+title+date, Toast.LENGTH_SHORT).show();

                        Notification notification= new Notification(faculty_name, title, date,images);
                        notificationList.add(notification);


                    }

                    adapter=new NotificationAdapter(NotificationActivity.this,notificationList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NotificationActivity.this, "Error for notification"+error.getMessage(), Toast.LENGTH_SHORT).show();



            }
        });


        Volley.newRequestQueue(this).add(stringRequest);

    }
}
