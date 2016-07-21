package com.aec12e.opencvtut;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void startNewMatch(View v) {
        Intent newMatch = new Intent(this, chooser.class);
        newMatch.putExtra("matchNum",0);
        this.startActivity(newMatch);
    }
}
