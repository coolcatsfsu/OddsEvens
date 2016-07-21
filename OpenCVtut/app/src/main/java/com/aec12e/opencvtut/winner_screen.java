package com.aec12e.opencvtut;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class winner_screen extends AppCompatActivity {

    private int winner;
    private Animation show;
    private TextView winner_text;
    private ImageView winner_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_screen);
        Intent intent = getIntent();
        winner = intent.getIntExtra("winner",0);
        winner_text = (TextView) findViewById(R.id.winner_text);
        winner_icon = (ImageView) findViewById(R.id.winner_icon);
        winner_icon.setImageResource(R.mipmap.logo);
        if (winner == 0)
        {
            winner_text.setText(R.string.p1winner);

        }
        else
        {
            winner_text.setText(R.string.p2winner);
        }

        show = AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_grow_fade_in_from_bottom);
        show.setStartOffset(600);

        winner_text.setVisibility(View.VISIBLE);
        winner_text.startAnimation(show);
    }

    public void restartGame(View v) {
        Intent intent = new Intent(getBaseContext(),chooser.class);
        this.startActivity(intent);
    }


}
