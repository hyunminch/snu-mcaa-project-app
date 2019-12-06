package io.github.snumcaa;

import android.os.Bundle;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.r0adkll.slidr.Slidr;

public class SettingActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        Slidr.attach(this); // This is to enable sliding operations

        SwitchCompat notifSwitch = findViewById(R.id.notif_swtich);
        notifSwitch.setOnCheckedChangeListener(this);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.exit_right);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {}
}

