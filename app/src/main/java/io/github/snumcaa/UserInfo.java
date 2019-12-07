package io.github.snumcaa;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaMetadataRetriever;
import android.os.Parcel;
import android.os.Parcelable;

public class UserInfo implements Parcelable {
    public int user_id;
    public String user_name;
    public String user_whatsUp;
    public String user_bio;
    public Bitmap user_avatar;

    public UserInfo(int id, String name, String whatsUp, String bio ){
        user_id = id;
        user_name = name;
        user_whatsUp = whatsUp;
        user_bio = bio;
        user_avatar = buildAvatar();
    }

    public UserInfo(){
        this(8353, "Locky", "Busy for the final season[sign]", "Non-existent debug user");
    }

    public void update(String target, String value){
        if(target.equals("user_name")){
            user_name = value;
            user_avatar = buildAvatar();
        }else if(target.equals("What's Up message"))
            user_whatsUp = value;
        else if(target.equals("bio"))
            user_bio = value;
    }

    public String getText(String target){
        if(target.contains("user_name"))
            return user_name;
        else if(target.contains("What's Up message"))
            return user_whatsUp;
        else if(target.contains("bio"))
            return user_bio;
        return "";
    }

    private Bitmap buildAvatar(){
        String text = user_name.substring(0, 1);
        float textSize = 300;
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(Color.BLACK);
        //paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = 400;//(int) (paint.measureText(text) + 0.5f);
        int height = 400;//(int) (baseline + paint.descent() + 0.5f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, width/2-paint.measureText(text)/2, height/2+baseline/3, paint);
        //image = Bitmap.createScaledBitmap(image, 400, 400, false);
        return image;
    }

    protected UserInfo(Parcel in) {
        user_id = in.readInt();
        user_name = in.readString();
        user_whatsUp = in.readString();
        user_bio = in.readString();
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
        parcel.writeInt(user_id);
        parcel.writeString(user_name);
        parcel.writeString(user_whatsUp);
        parcel.writeString(user_bio);
        parcel.writeValue(user_avatar);
    }
}
