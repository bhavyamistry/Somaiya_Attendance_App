package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.view.DragAndDropPermissions;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText etusername,etpassword;
    private Button btnLogin;
//    private String url_POST="http://192.168.0.107/post.php";
    private String url_POST="";
    private TextInputLayout toggle;

   /* Animation anim,frombottom;
    ImageView I1,I2,bg,logo;
    LinearLayout L1,L2;
    CardView cv1;*/
    //private String url_event = "http://192.168.0.103/event.php";


    SharedPreferences pref;
    Intent intent;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etusername=findViewById(R.id.etusername);
        etpassword=findViewById(R.id.etpassword);
        btnLogin=findViewById(R.id.btnLogin);
        toggle=findViewById(R.id.toggle);
        url_POST = URL_Class.getUrl()+"post.php";







        pref = getSharedPreferences("user_details",MODE_PRIVATE);
        intent = new Intent(MainActivity.this,DashBoardActivity.class);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etusername.length()!=10){

                    etusername.setError("Invalid ID Number");

                }
                else if(etpassword.length()!=10){

                    Toast.makeText(MainActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();


                }
                else {

                    InsertSV();
                    //loadevents();
                }

            }
        });


        /*cv1=findViewById(R.id.cv1);
        I1=findViewById(R.id.iv1);
        I2=findViewById(R.id.iv2);
        bg=findViewById(R.id.bg);
        cv1=findViewById(R.id.cv1);
        L1=findViewById(R.id.ll1);
        logo=findViewById(R.id.logo);

        frombottom= AnimationUtils.loadAnimation(this,R.anim.frombottom);
        anim=AnimationUtils.loadAnimation(this,R.anim.animation);
        bg.animate().translationY(-1000).setDuration(800).setStartDelay(800);
        logo.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(600);
        I1.startAnimation(frombottom);
        I2.startAnimation(frombottom);
        cv1.startAnimation(frombottom);*/
    }


    private void InsertSV(){
//        Toast.makeText(MainActivity.this, url_POST, Toast.LENGTH_SHORT).show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url_POST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject= new JSONObject(response);
                    String success=jsonObject.getString("success");
                    int success1=Integer.parseInt(success);
                    JSONArray jsonArray=jsonObject.getJSONArray("login");

                    if (success1==1){



                        String username=etusername.getText().toString();
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("username", username);
                        editor.commit();
                        //Toast.makeText(getApplicationContext(), "Login Successful"+username, Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();


                        for (int i=0; i<jsonArray.length();i++){

                            JSONObject object= jsonArray.getJSONObject(i);

                            String L_name=object.getString("L_name").trim(); //lastname
                            String M_name=object.getString("M_name").trim(); //middle name
                            String  roll= object.getString("roll").trim();
                            String  batch= object.getString("batch").trim();
                            String  sem= object.getString("sem").trim();


                            SharedPreferences.Editor editor1=pref.edit();
                            editor1.putString("L_name",L_name);
                            editor1.putString("M_name",M_name);
                            editor1.putString("batch",batch);
                            editor1.putString("roll",roll);
                            editor1.putString("sem",sem);

                            editor1.commit();



/*                            editor.putString("L_name",L_name);
                            editor.putString("M_name",M_name);
                            editor.putString("roll",roll);
                            editor.putString("batch",batch);
                            editor.putString("sem",sem);*/





/*

*/

                            // sessionManager.createSession(L_name,M_name,batch,roll,sem);


                        }


                    }

                    else{

                        Intent i= new Intent(MainActivity.this, DashBoardActivity.class);
                        startActivity(i);
                    }





                } catch (JSONException e) {
                    etusername.setText("");
                    etpassword.setText("");
                    Toast.makeText(MainActivity.this, "Invalid Details", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        })

        {
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> params = new HashMap<String, String>();
                String username=etusername.getText().toString();
                String password=etpassword.getText().toString();
                params.put("username",username);
                params.put("password",password);

//                Toast.makeText(MainActivity.this, ""+params, Toast.LENGTH_SHORT).show();
                return params;
            }
        };


        Mysingleton.getInstance(getApplicationContext()).addToRequestque(stringRequest);

    }










}
