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

    private ImageView bgapp, vicon;
    private LinearLayout textsplash;
    private ConstraintLayout texthome;
    private Animation frombottom;
    private Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_splash);

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);

        bgapp = (ImageView) findViewById(R.id.bgapp);
        vicon = (ImageView) findViewById(R.id.vicon);
        textsplash = (LinearLayout) findViewById(R.id.textsplash);
        texthome = (ConstraintLayout) findViewById(R.id.texthome);
        loginButton = (Button) findViewById(R.id.login_button);



        bgapp.animate()
                .translationY(-1700).setDuration(800).setStartDelay(800);
        vicon.animate()
                .alpha(0).setDuration(800).setStartDelay(800);
        textsplash.animate()
                .translationY(140).alpha(0).setDuration(800).setStartDelay(800);

        texthome.startAnimation(frombottom);

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
    }
}
