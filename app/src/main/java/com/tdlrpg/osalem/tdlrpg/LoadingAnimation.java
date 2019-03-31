package com.tdlrpg.osalem.tdlrpg;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class LoadingAnimation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_animation);
        Thread thread= new Thread();
        load();
        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                openHome();
            }
        }.start();

    }

    private void load()
    {
        View v = findViewById(R.id.imageView2);
        v.setVisibility(View.VISIBLE);
        Animation a = AnimationUtils.loadAnimation(this, R.anim.blink);
        v.setAnimation(a);
        v.animate();
    }

    public void openHome()
    {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }
}
