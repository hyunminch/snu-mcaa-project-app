package io.github.snumcaa;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
public class Profile_ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_profile);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.exit_right);
    }
}
