package io.github.snumcaa;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.r0adkll.slidr.Slidr;

public class ProfileActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_profile);
        Slidr.attach(this);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.exit_right);
    }
}
