package com.example.miwokapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView t = (TextView) findViewById(R.id.numbers);
        t.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this, activity_numbers.class);
                startActivity(i);
            }
        });
        TextView t1 = (TextView) findViewById(R.id.colors);
        t1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this, activity_colors.class);
                startActivity(i);/
            }
        });
        TextView t2 = (TextView) findViewById(R.id.phrases);
        t2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this, activity_phrases.class);
                startActivity(i);
            }
        });
        TextView t3 = (TextView) findViewById(R.id.family);
        t3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this, activity_family.class);
                startActivity(i);
            }
        });
    }

}
