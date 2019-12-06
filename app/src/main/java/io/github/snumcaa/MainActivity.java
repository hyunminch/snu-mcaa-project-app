package io.github.snumcaa;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_profile, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public void profileClicked(View view) {
        startActivity(new Intent(this, ProfileActivity.class));
        overridePendingTransition(R.anim.enter_right, R.anim.stay);
    }

    public void settingClicked(View view) {
        startActivity(new Intent(this, SettingActivity.class));
        overridePendingTransition(R.anim.enter_right, R.anim.stay);
    }

    public void friendClicked(View view) {
        startActivity(new Intent(this, FriendActivity.class));
        overridePendingTransition(R.anim.enter_right, R.anim.stay);
    }


}
