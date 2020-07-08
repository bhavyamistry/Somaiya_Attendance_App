package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class DashBoardActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvroll,tvbatch,tvsem,tvwelcome;
    private CardView cv_notification , cv_event, cv_attendance,cv_result,cv_contactus ;
    SharedPreferences prf;
  //  Intent intent;
   ImageButton btnLogOut;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvwelcome=findViewById(R.id.tvwelcome);
        tvbatch=findViewById(R.id.tvbatch);
        tvroll=findViewById(R.id.tvroll);
        tvsem=findViewById(R.id.tvsem);
        btnLogOut=findViewById(R.id.btnLogOut);




        cv_notification=findViewById(R.id.cv_notification);
        cv_event=findViewById(R.id.cv_event);
        cv_attendance=findViewById(R.id.cv_attendance);
        cv_result=findViewById(R.id.cv_result);
        cv_contactus=findViewById(R.id.cv_contactus);




        prf = getSharedPreferences("user_details",MODE_PRIVATE);


        tvwelcome.setText(""+prf.getString("M_name",null)+" "+prf.getString("L_name",null));
        tvbatch.setText("Batch No- "+prf.getString("batch",null));
        tvroll.setText("Roll No- "+prf.getString("roll",null));
        tvsem.setText("Current "+prf.getString("sem",null));
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prf.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(DashBoardActivity.this,MainActivity.class);

                startActivity(intent);
                finish();
            }
        });





       cv_notification.setOnClickListener(this);
       cv_event.setOnClickListener(this);
       cv_attendance.setOnClickListener(this);
       cv_result.setOnClickListener(this);
       cv_contactus.setOnClickListener(this);




    }
/*

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id= item.getItemId();
        if (id==R.id.logout);
        {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
*/

    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId()){
            case R.id.cv_notification:
                intent=new Intent(this,NotificationActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Notification", Toast.LENGTH_SHORT).show();
                break;

           case  R.id.cv_event:
                intent=new Intent(this,EventActivity.class);
                startActivity(intent);
                //Toast.makeText(this, "Event", Toast.LENGTH_SHORT).show();
                break;

            case  R.id.cv_attendance:
//                Toast.makeText(this, "Attendance", Toast.LENGTH_SHORT).show();
                intent=new Intent(DashBoardActivity.this,Attendance.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.slidein_right, R.anim.slideout_left);
                break;


            case  R.id.cv_result:
                Intent i = new Intent(this, ResultActivity.class);
                startActivity(i);
//                Toast.makeText(this, "Result", Toast.LENGTH_SHORT).show();
//                overridePendingTransition(R.anim.slidein_right, R.anim.slideout_left);
                break;
            case  R.id.cv_contactus:
                intent=new Intent(this,ContactUs.class);
                startActivity(intent);
                Toast.makeText(this, "Result", Toast.LENGTH_SHORT).show();
                overridePendingTransition(R.anim.slidein_right, R.anim.slideout_left);
                break;

            default: break;
        }

    }


}
