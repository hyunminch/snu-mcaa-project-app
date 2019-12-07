package io.github.snumcaa.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.github.snumcaa.FriendActivity;
import io.github.snumcaa.ProfileActivity;
import io.github.snumcaa.R;
import io.github.snumcaa.SettingActivity;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_users, R.id.navigation_profile, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        this.userInfo = this.getSharedPreferences(
                getString(R.string.user_info_storage_id),
                Context.MODE_PRIVATE
        );
    }

    public void profileClicked(View view) {
        startActivity(new Intent(this, ProfileActivity.class));
        overridePendingTransition(R.anim.enter_right, R.anim.stay);
    }

    public SharedPreferences getUserInfo(){
        return userInfo;
    }

    public void settingClicked(View view){
        startActivity(new Intent(this, SettingActivity.class));
        overridePendingTransition(R.anim.enter_right, R.anim.stay);
    }

    public void friendClicked(View view) {
        startActivity(new Intent(this, FriendActivity.class));
        overridePendingTransition(R.anim.enter_right, R.anim.stay);
    }
}