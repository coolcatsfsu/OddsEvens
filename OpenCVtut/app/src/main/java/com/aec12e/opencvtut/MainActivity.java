package com.aec12e.opencvtut;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, CameraBridgeViewBase.CvCameraViewListener2 {

    private CameraBridgeViewBase cameraFeed;
    private Mat mRgba;

    //UI shit
    private ImageView badge;
    private TextView turnTitle;
    private Animation show_badge;
    private Animation show_top;
    private Animation show_bottom;
    private Animation switch_turntitle;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                    cameraFeed.enableView();
                    cameraFeed.setOnTouchListener(MainActivity.this);
                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        cameraFeed = (CameraBridgeViewBase) findViewById(R.id.opencvsurface);
        cameraFeed.setMaxFrameSize(720,1280);
        cameraFeed.setVisibility(SurfaceView.VISIBLE);
        cameraFeed.setCvCameraViewListener(this);

        badge = (ImageView) findViewById(R.id.badge);
        show_badge = AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_grow_fade_in_from_bottom);
        show_top = AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_slide_in_top);
        show_bottom = AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_slide_in_bottom);

        turnTitle = (TextView) findViewById(R.id.turntitle);
        turnTitle.setText(R.string.player1);
        turnTitle.setTag("1");

        ImageView upper = (ImageView) findViewById(R.id.upper);
        ImageView lower = (ImageView) findViewById(R.id.lower);

        show_top.setStartOffset(100);
        show_bottom.setStartOffset(100);

        show_top.setDuration(800);
        show_bottom.setDuration(800);
        upper.setVisibility(View.VISIBLE);
        lower.setVisibility(View.VISIBLE);

        upper.startAnimation(show_top);
        lower.startAnimation(show_bottom);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (cameraFeed != null)
            cameraFeed.disableView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_1_0, this, mLoaderCallback);
        }
        else {
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cameraFeed != null)
            cameraFeed.disableView();
    }



    @Override
    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat();
    }

    @Override
    public void onCameraViewStopped() {
        mRgba.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();
        return mRgba;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    public void showBadge(View v) {
        badge.setVisibility(View.INVISIBLE);
        int val = Integer.parseInt((String)v.getTag());

        switch (val) {
            case 1:
                badge.setImageResource(R.mipmap.onebadge);
                break;
            case 2:
                badge.setImageResource(R.mipmap.twobadge);
                break;
        }

        badge.clearAnimation();
        badge.setVisibility(View.VISIBLE);
        badge.startAnimation(show_badge);

    }

    public void switchUser(View v) {
        turnTitle.setVisibility(View.INVISIBLE);
        int turn = Integer.parseInt((String)turnTitle.getTag());
        turnTitle.clearAnimation();

        switch (turn) {
            case 1:
                turnTitle.setText(R.string.player2);
                turnTitle.setTag("2");
                break;
            case 2:
                turnTitle.setText(R.string.player1);
                turnTitle.setTag("1");
                break;
            default:
                turnTitle.setText("what");
                break;
        }
        turnTitle.setVisibility(View.VISIBLE);
        turnTitle.startAnimation(show_badge);
    }

    public void showWinner(View v) {
        int winner = Integer.parseInt((String) v.getTag());
        Intent intent = new Intent(getBaseContext(), winner_screen.class);
        intent.putExtra("winner", winner);
        this.startActivity(intent);
    }

}
