package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
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

public class EventActivity extends AppCompatActivity {



    private static String event_url="";

    //    private static final String event_url="http://192.168.0.107/event.php";

    RecyclerView recyclerView;
    EventAdapter adapter;
    List<Event> eventList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        eventList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
       recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        event_url = URL_Class.getUrl()+"event.php";
        load_events();

        adapter = new EventAdapter(this, eventList);
        recyclerView.setAdapter(adapter);


    }

    private void load_events(){




        StringRequest stringRequest= new StringRequest(Request.Method.GET, event_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONArray jsonArray= new JSONArray(response);

                    for(int i=0; i<jsonArray.length();i++){

                        JSONObject jsonObject=jsonArray.getJSONObject(i);

                        String title=jsonObject.getString("title");
                        String category=jsonObject.getString("type");
                        String date= jsonObject.getString("date");
                        String description= jsonObject.getString("description");
                        String images= jsonObject.getString("images");

                      //  Toast.makeText(EventActivity.this, ""+title+category+date+description, Toast.LENGTH_SHORT).show();

                        Event event= new Event(title,category,date,description,images);
                        eventList.add(event);


                    }

                    adapter=new EventAdapter(EventActivity.this,eventList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EventActivity.this, "Error for event"+error.getMessage(), Toast.LENGTH_SHORT).show();



            }
        });


        Volley.newRequestQueue(this).add(stringRequest);

    }
}
