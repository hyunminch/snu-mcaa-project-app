package io.github.snumcaa;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collection;

public class FriendAdapter extends BaseAdapter {
    private ArrayList<Search_result> data;
    private Context context;
    FriendAdapter(Context context, Collection<Search_result> data){
        this.data = new ArrayList<Search_result>(data);
        this.context = context;
    }
    @Override
    public int getCount(){
        return 0;
    }

    @Override
    public Object getItem(int position){
        return null;
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        return null;
    }
}
