package io.github.snumcaa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.Collection;

public class FriendAdapter extends BaseAdapter {
    private ArrayList<Search_result> data;
    private Context context;
    FriendAdapter(Context context, Collection<Search_result> data){
        super();
        this.data = new ArrayList<Search_result>(data);
        this.context = context;
    }
    @Override
    public int getCount(){
        return data.size();
    }

    @Override
    public Object getItem(int position){
        return data.get(position);
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);
        View entry = inflater.inflate(R.layout.friend_search_result_single, null);
        CircularImageView iconView = entry.findViewWithTag("search_result_icon");
        TextView usernameView = entry.findViewWithTag("search_result_user_name");
        entry.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(context, "pressed", Toast.LENGTH_SHORT);
            }
        });
        Search_result info = data.get(position);
        if(info==null)
            return entry;

        if(info.user_icon!=null)
            iconView.setImageBitmap(info.user_icon);
        if(info.user_name!=null)
            usernameView.setText(info.user_name);
        return entry;
    }
}
