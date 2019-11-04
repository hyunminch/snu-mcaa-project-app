package io.github.snumcaa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Animation fromBottom, bgMoveUp, fadeOut;

    private ImageView vicon;
    private LinearLayout textSplash;
    private ConstraintLayout textHome, background;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_splash);

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom);
        bgMoveUp = AnimationUtils.loadAnimation(this, R.anim.bg_moveup);
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        background = (ConstraintLayout) findViewById(R.id.bgapp);
        vicon = (ImageView) findViewById(R.id.vicon);
        textSplash = (LinearLayout) findViewById(R.id.text_splash);
        textHome = (ConstraintLayout) findViewById(R.id.text_home);
        loginButton = (Button) findViewById(R.id.login_button);

        background.startAnimation(bgMoveUp);
        vicon.startAnimation(fadeOut);
        textSplash.startAnimation(fadeOut);

        textHome.startAnimation(fromBottom);

        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Currently the method is really naive.
        // By clicking on it, you just simply jump to MainActivity
        // Checking the correctness of username...etc needs to be implemented
        // in the future.

        Intent intent = new Intent(
                LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
