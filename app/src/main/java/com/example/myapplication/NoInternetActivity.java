package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class NoInternetActivity extends AppCompatActivity {
    SwipeRefreshLayout Swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
        String activity = "";
        final Intent intent = getIntent();
        activity = intent.getStringExtra("ActivityName");
        Swipe = findViewById(R.id.Swipe);
        Swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Swipe.isRefreshing()) {
                    boolean isConnected = checkNetworkConnection();
                    if (!isConnected) {
                        Toast.makeText(NoInternetActivity.this, "No Internet", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(NoInternetActivity.this, "Connected To Internet", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(NoInternetActivity.this, intent.getClass());
                        startActivity(i);
                        overridePendingTransition(R.anim.slidein_right, R.anim.slideout_left);
                    }
                }
            }
        });
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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slidein_left, R.anim.slideout_right);
    }

}
