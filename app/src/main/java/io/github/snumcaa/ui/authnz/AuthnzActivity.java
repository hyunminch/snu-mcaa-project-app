package io.github.snumcaa.ui.authnz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import io.github.snumcaa.ui.MainActivity;
import io.github.snumcaa.R;

import kotlin.Unit;

public class AuthnzActivity extends AppCompatActivity implements View.OnClickListener {
    // Animation related fields
    private Animation fromBottom, backgroundMoveUp, fadeOut;
    private ImageView icon;
    private LinearLayout textSplash;
    private ConstraintLayout textHome, background;

    private Button signUpButton;
    private EditText username;
    private EditText password;

    private AuthnzViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(AuthnzViewModel.class);

        checkSignedIn();

        setContentView(R.layout.activity_login_splash);

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom);
        backgroundMoveUp = AnimationUtils.loadAnimation(this, R.anim.bg_moveup);
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        background = findViewById(R.id.bgapp);
        icon = findViewById(R.id.vicon);
        textSplash = findViewById(R.id.text_splash);
        textHome = findViewById(R.id.text_home);
        signUpButton = findViewById(R.id.login_button);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        background.startAnimation(backgroundMoveUp);
        icon.startAnimation(fadeOut);
        textSplash.startAnimation(fadeOut);
        textHome.startAnimation(fromBottom);

        signUpButton.setOnClickListener(this);
    }

    public void signUp() {
        final String usernameText = username.getText().toString();
        final String passwordText = password.getText().toString();

        viewModel
                .signUp(usernameText, passwordText)
                .observe(this, new Observer<SignUpResult>() {
                    @Override
                    public void onChanged(SignUpResult result) {
                        if (result.getFailed()) {
                            // TODO: do something
                        } else {
                            onSignedUp(usernameText, passwordText);
                        }
                    }
                });
    }

    public void onSignedUp(String usernameText, String passwordText) {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("VIDEOSHAREX_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("username", usernameText);
        editor.putString("password", passwordText);

        editor.apply();

        proceedToMain();
    }

    public void checkSignedIn() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("VIDEOSHAREX_PREFS", MODE_PRIVATE);
        String username = preferences.getString("username", null);
        String password = preferences.getString("password", null);

        if (username != null && password != null) {
            viewModel
                    .signIn(getApplicationContext())
                    .observe(this, new Observer<SignInResult>() {
                        @Override
                        public void onChanged(SignInResult result) {
                            if (!result.getFailed()) {
                                onSignedIn();
                            } else {
                                Log.i("AuthnzActivity", "SignIn failed.");
                            }
                        }
                    });
        }
    }

    public void onSignedIn() {
        proceedToMain();
    }

    public void proceedToMain() {
        Intent intent = new Intent(AuthnzActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        signUp();
    }
}
