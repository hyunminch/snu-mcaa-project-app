package io.github.snumcaa;

import android.graphics.Bitmap;

public class Search_result {
    public Search_result(){
        user_name = "debug_user";
        user_icon = null;
    }
    public Search_result(String name, Bitmap icon) {
        user_name = name;
        user_icon = icon;
    }
    public int id;
    public String user_name;
    public Bitmap user_icon;
}
