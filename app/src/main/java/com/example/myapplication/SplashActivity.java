package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences pref;
    Intent intent;
    ImageView ivlogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ivlogo=findViewById(R.id.ivlogo);

        pref = getSharedPreferences("user_details",MODE_PRIVATE);

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Animation a= AnimationUtils.loadAnimation(SplashActivity.this,R.anim.frombottom);
                    ivlogo.startAnimation(a);
                    Thread.sleep(3000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                if(pref.contains("username")){
                    intent = new Intent(SplashActivity.this,DashBoardActivity.class);
                    startActivity(intent);
                    finish();
                }
    else {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }).start();








    }
}
