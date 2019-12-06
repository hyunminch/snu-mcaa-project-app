package io.github.snumcaa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FriendSearchResultActivity extends AppCompatActivity {
    ArrayList<Search_result> friends = new ArrayList<Search_result>();
    ListView listView;
    FriendAdapter friendAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter_alpha, R.anim.exit_alpha);

        setContentView(R.layout.activity_friend_search_result);
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            // use the query to request user data from server

            friends = findUser(query);

            if (friends == null) {
                Context context = getApplicationContext();
                String text = "Didn't find user";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            else {
                listView = (ListView) findViewById(R.id.friend_search_result_list_view);
                friendAdapter = new FriendAdapter(this, friends);

                listView.setAdapter(friendAdapter);
            }
        }
    }

    private ArrayList<Search_result> findUser(String query) {
        // currently don't know how to use api
        return null;
    }
}
