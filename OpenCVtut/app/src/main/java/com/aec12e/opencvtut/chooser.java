package com.aec12e.opencvtut;

import android.animation.Animator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class chooser extends AppCompatActivity {

    //private int matchNum;
    private int player_assign;
    private Animation show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);

        Intent intent = getIntent();
        //matchNum = intent.getIntExtra("matchNum",0);
        show = AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_grow_fade_in_from_bottom);

        setPlayers();
    }

    public void setPlayers() {
        Random ran = new Random();
        player_assign = ran.nextInt(2);
        ImageView p1badge = (ImageView) findViewById(R.id.p1badge);
        ImageView p2badge = (ImageView) findViewById(R.id.p2badge);
        Button go = (Button) findViewById(R.id.chooser_go);
        switch (player_assign) {
            case 0:
                p1badge.setImageResource(R.mipmap.evenbadge);
                p2badge.setImageResource(R.mipmap.oddbadge);
                break;
            case 1:
                p1badge.setImageResource(R.mipmap.oddbadge);
                p2badge.setImageResource(R.mipmap.evenbadge);
                break;
        }

        show.setStartOffset(600);
        p1badge.setVisibility(View.VISIBLE);
        p2badge.setVisibility(View.VISIBLE);

        p1badge.startAnimation(show);
        p2badge.startAnimation(show);

        go.setVisibility(View.VISIBLE);
        go.startAnimation(show);

        return;
    }

    public void startCamera(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("player_assign",player_assign);
        this.startActivity(intent);
    }
}
