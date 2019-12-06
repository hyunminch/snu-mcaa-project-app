package io.github.snumcaa;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.r0adkll.slidr.Slidr;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class ProfileActivity extends FragmentActivity {
    SharedPreferences user_info;
    //SharedPreferences.Editor user_info_editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_profile);
        Slidr.attach(this);
        this.user_info = this.getSharedPreferences(
                getString(R.string.user_info_storage_id),
                Context.MODE_PRIVATE
        );
        upDateText_all();
        upDateAvatar();
        CircularImageView image = (CircularImageView)findViewById(R.id.profile_profile_image);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.exit_right);
    }

    public void onClick_text(View v){
        String v_tag = v.getTag().toString();
        final String tag_show = v_tag + "_show";
        final SharedPreferences.Editor editor = user_info.edit();
        final TextView showView = v.findViewWithTag(tag_show);
        final Context context = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("input new "+v_tag);
        final EditText edit = new EditText(context);
        builder.setView(edit);
        builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String input = edit.getText().toString();
                editor.putString(tag_show, input);
                editor.commit();
                upDateText(showView);
                Toast.makeText(context, "successful", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "canceled", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public void pickNewAvatar(View v){
        CropImage.ActivityBuilder cropper = CropImage.activity();
        cropper.setAspectRatio(1, 1);
        cropper.setFixAspectRatio(true);
        cropper.start(this);
    }
    public void onSelectImageClick(View view){
        CropImage.startPickImageActivity(this);
    }

    private void upDateText_all(){
        TextView user_name_top = findViewById(R.id.profile_username_top);
        user_name_top.setText(user_info.getString("user name_show", getString(R.string.blank)));
        TextView user = findViewById(R.id.profile_username_show);
        upDateText(user);
        TextView whatsUp = findViewById(R.id.profile_whatsUp_show);
        upDateText(whatsUp);
        TextView bio = findViewById(R.id.profile_bio_show);
        upDateText(bio);
    }

    private void upDateText(TextView text){
        text.setText(user_info.getString((String)text.getTag(), getString(R.string.blank)));
    }

    private void setupNewAvatar(Uri imageUri){
        Bitmap newAvatar;
        try{
            Bitmap pic_ori = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            newAvatar = Bitmap.createScaledBitmap(pic_ori, 400, 400, true);
            FileOutputStream out = new FileOutputStream(getFilesDir() + File.separator + getResources().getString(R.string.user_avatar_image_file_name));
            newAvatar.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            Toast.makeText(this, "Failed, "+e.toString(), Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences.Editor editor = user_info.edit();
        editor.putString(getResources().getString(R.string.user_avatar_image), imageUri.toString());
        editor.commit();
        CircularImageView avatarView = findViewById(R.id.profile_profile_image);
        avatarView.setImageBitmap(newAvatar);
    }

    private void upDateAvatar(){
        String imageUriString = user_info.getString(getString(R.string.user_avatar_image), null);
        if(imageUriString==null)
            return;
        Uri imageUri = Uri.parse(imageUriString);
        Bitmap avatar;
        try {
            avatar = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        }catch (IOException e){
            e.printStackTrace();
            return;
        }
        CircularImageView avatarView = findViewById(R.id.profile_profile_image);
        avatarView.setImageBitmap(avatar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                setupNewAvatar(resultUri);
            }else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
            }
        }
        super.onActivityResult(requestCode, requestCode, data);
    }
}
