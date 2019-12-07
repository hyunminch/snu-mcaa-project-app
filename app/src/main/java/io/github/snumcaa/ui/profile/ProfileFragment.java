package io.github.snumcaa.ui.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.mikhaellopez.circularimageview.CircularImageView;

import io.github.snumcaa.UserInfo;
import io.github.snumcaa.ui.MainActivity;
import io.github.snumcaa.R;

public class ProfileFragment extends Fragment {
    private UserInfo userInfo;
    private View root;
    private Context context;
    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        root = inflater.inflate(R.layout.fragment_profile, container, false);
        context = getContext();
        MainActivity main = (MainActivity) getActivity();
        userInfo = main.getUserInfo();
        setupAll();
        return root;
    }

    public void onClick_text(View v){
        final String v_tag = v.getTag().toString();
        final String tag_show = v_tag + "_show";
        final TextView showView = v.findViewWithTag(tag_show);
        final Context context = getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("input new "+v_tag);
        final EditText edit = new EditText(context);
        builder.setView(edit);
        final UserInfo userInfo_temp = userInfo;
        builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String input = edit.getText().toString();
                if(!verifyChange(v_tag, input)){
                    Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
                    return;
                }
                userInfo.update(v_tag, input);
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

    private boolean verifyChange(String type, String value){
        //TODO: to verify changes wih the server
        return true;
    }

    private void setupAll(){
        upDateText_all();
        View bio = root.findViewWithTag("bio");
        bio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick_text(view);
            }
        });
        View password = root.findViewWithTag("password");
        password.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                onClick_text(view);
            }
        });
        upDateAvatar();
    }

    private void upDateText_all(){
        TextView user = root.findViewWithTag("user_name");
        upDateText(user);
        TextView bio = root.findViewById(R.id.profile_bio_show);
        upDateText(bio);
        TextView password = root.findViewById(R.id.profile_password_show);
        upDateText(password);
    }

    private void upDateText(TextView text){
        text.setText(userInfo.getText((String)text.getTag()));
    }

    private void upDateAvatar(){
        Bitmap avatar = userInfo.user_avatar;
        CircularImageView avatarView = root.findViewById(R.id.profile_profile_image);
        avatarView.setImageBitmap(avatar);
    }
}
