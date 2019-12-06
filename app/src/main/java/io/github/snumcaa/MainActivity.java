package io.github.snumcaa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences user_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_profile, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        this.user_info = this.getSharedPreferences(
                getString(R.string.user_info_storage_id),
                Context.MODE_PRIVATE
        );
    }

    public void profileClicked(View view){
        startActivity(new Intent(this, ProfileActivity.class));
        overridePendingTransition(R.anim.enter_right, R.anim.stay);
    }

    public SharedPreferences getUserInfo(){
        return user_info;
    }

    public void settingClicked(View view){
        startActivity(new Intent(this, SettingActivity.class));
        overridePendingTransition(R.anim.enter_right, R.anim.stay);
    }
}
