package io.github.snumcaa.ui;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.github.snumcaa.R;
import io.github.snumcaa.UserInfo;

public class MainActivity extends AppCompatActivity {
    private UserInfo userInfo;

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

        this.userInfo = buildUserInfo();
    }

    private UserInfo buildUserInfo(){
        SharedPreferences sharedPreferences = getSharedPreferences("VIDEOSHAREX_PREFS", MODE_PRIVATE);
        String userName = sharedPreferences.getString("username", null);
        if(userName == null) {
            return new UserInfo();
        }
        String password = sharedPreferences.getString("password", null);
        if(password == null)
            password = "12345678";
        //UserRepository userRepository = new BasicAuthClient<UserRepository>().createAuth(UserRepository.class, this);
        //String bio = userRepository.getProfile();
        // TODO: get bio
        String bio = "This is a nonexistent bio message for debug usage";
        return new UserInfo(
                userName,
                bio,
                password);
    }

    public UserInfo getUserInfo(){return userInfo;}


    // To communicate with the server about the changes
    private boolean verifyChange(String type, String value){
        return true;
    }
}
