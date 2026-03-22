package com.siam.mealcraft;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private final int FALLBACK_DELAY = 3000;
    private final Handler handler = new Handler();

    private final Runnable fallback = new Runnable() {
        @Override
        public void run() {
            goMain();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        LottieAnimationView lottie = findViewById(R.id.lottieSplash);
        TextView title = findViewById(R.id.splashTitle);
        TextView subtitle = findViewById(R.id.splashSubtitle);

        title.setAlpha(0f);
        title.setScaleX(0.95f);
        title.setScaleY(0.95f);
        title.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(650).setStartDelay(350).start();
        subtitle.setAlpha(0f);
        subtitle.animate().alpha(0.85f).setDuration(650).setStartDelay(500).start();


        lottie.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                goMain();
            }
        });


        handler.postDelayed(fallback, FALLBACK_DELAY);
    }

    private void goMain() {
        if (isFinishing()) return;
        handler.removeCallbacks(fallback);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(fallback);
    }
}
