package io.github.snumcaa;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;

public class UserInfo implements Parcelable {
    public String user_name;
    public String user_bio;
    public String user_password;
    public Bitmap user_avatar;

    public UserInfo(String name, String bio, String password){
        user_name = name;
        user_bio = bio;
        user_password = password;
        user_avatar = buildAvatar();
    }

    public UserInfo(){
        this("Locky","Non-existent debug user", "12345678");
    }

    public void update(String target, String value){
        if(target.contains("bio"))
            user_bio = value;
        else if(target.contains("password"))
            user_password = value;
    }

    public String getText(String target){
        if(target.contains("user_name"))
            return user_name;
        else if(target.contains("bio"))
            return user_bio;
        else if(target.contains("password"))
            return user_password;
        return "";
    }

    private Bitmap buildAvatar(){
        String text = user_name.substring(0, 1);
        float textSize = 300;
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(Color.BLACK);
        float baseline = -paint.ascent();
        int width = 400;
        int height = 400;
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, width/2-paint.measureText(text)/2, height/2+baseline/3, paint);
        return image;
    }

    protected UserInfo(Parcel in) {
//        user_id = in.readInt();
        user_name = in.readString();
//        user_whatsUp = in.readString();
        user_bio = in.readString();
        user_password = in.readString();
        user_avatar = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(user_name);
        parcel.writeString(user_bio);
        parcel.writeString(user_password);
        parcel.writeValue(user_avatar);
    }
}
