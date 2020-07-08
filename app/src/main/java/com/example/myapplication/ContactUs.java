package com.example.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactUs extends AppCompatActivity {
    private ExpandableListView lvExp;
    private ExpandableListAdapater listAdapater;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHashMap;
    private int lastExpandedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        lvExp = findViewById(R.id.lvExp);
        initData();
        listAdapater = new ExpandableListAdapater(this, listDataHeader, listHashMap);
        lvExp.setAdapter(listAdapater);


        lvExp.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String selected = (String) listAdapater.getChild(i, i1);
                Toast.makeText(ContactUs.this, selected, Toast.LENGTH_SHORT).show();
                show_Dialog(view, selected);
                return true;
            }
        });

        lvExp.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {
                if (lastExpandedPosition != -1
                        && i != lastExpandedPosition) {
                    lvExp.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = i;
            }
        });
    }

    public void show_Dialog(View view, final String txt) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.customdialog, null);
        Button call_btn = alertLayout.findViewById(R.id.call_btn);
        Button mail_btn = alertLayout.findViewById(R.id.mail_btn);
        TextView alert_tv = alertLayout.findViewById(R.id.alert_tv);
        alert_tv.setText("Contact " + txt);
        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ContactUs.this, "Call Button Clicked", Toast.LENGTH_SHORT).show();
                Uri u = Uri.parse("tel:" + "1234567890");
                Intent i = new Intent(Intent.ACTION_DIAL, u);
                try {
                    startActivity(i);
                } catch (SecurityException s) {
                    Toast.makeText(ContactUs.this, s.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        mail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ContactUs.this, "Mail Button Clicked", Toast.LENGTH_SHORT).show();
                sendEmail(txt);
            }
        });
        AlertDialog.Builder alert = new AlertDialog.Builder(ContactUs.this);
        alert.setView(alertLayout);
        alert.setCancelable(true);
        AlertDialog dialog = alert.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogSlide;
        dialog.show();

    }

    private void sendEmail(String mailtowhom) {
        Log.i("Send email", "");
        String str = null;
        if (mailtowhom.matches("Prof.Vijaya Pinjarkar")) {
            str = "pvijaya@somaiya.edu";
        } else if (mailtowhom.matches("Dr. Radhika Kotecha")) {
            str = "rkotecha@somaiya.edu";
        } else if (mailtowhom.matches("Prof. Sarita Rathod")) {
            str = "saritar@somaiya.edu";
        } else {
            Toast.makeText(this, "String Did not fetch", Toast.LENGTH_SHORT).show();
        }
        String mailto = "mailto:" + str +
                "?cc=" + "" +
                "&subject=" + Uri.encode("Contacting") +
                "&body=" + Uri.encode("Hello I am Parent of XYZ student!");

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse(mailto));
        try {
            startActivity(emailIntent);
        } catch (ActivityNotFoundException e) {

        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slidein_left, R.anim.slideout_right);
    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHashMap = new HashMap<>();

        listDataHeader.add("Proctor");
        listDataHeader.add("Head Of Department");
        listDataHeader.add("Class teacher");

        List<String> proctor = new ArrayList<>();
        proctor.add("Prof.Vijaya Pinjarkar");
        proctor.add("+91 ");
        proctor.add("pvijaya@somaiya.edu");

        List<String> hod = new ArrayList<>();
        hod.add("Dr. Radhika Kotecha");
        hod.add("+91 7698558637");
        hod.add("rkotecha@somaiya.edu");

        List<String> ct = new ArrayList<>();
        ct.add("Prof. Sarita Rathod");
        ct.add("+91 982908378");
        ct.add("saritar@somaiya.edu");

        listHashMap.put(listDataHeader.get(0), proctor);
        listHashMap.put(listDataHeader.get(1), hod);
        listHashMap.put(listDataHeader.get(2), ct);
    }
}
