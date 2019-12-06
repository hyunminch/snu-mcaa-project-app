package io.github.snumcaa.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.IOException;

import io.github.snumcaa.MainActivity;
import io.github.snumcaa.R;
import io.github.snumcaa.ui.notifications.NotificationsViewModel;

public class ProfileFragment extends Fragment {
    SharedPreferences user_info;
    View root;
    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        root = inflater.inflate(R.layout.fragment_profile, container, false);

        MainActivity main = (MainActivity) getContext();
        user_info = main.getUserInfo();
        upDateAvatar();
        TextView user_name = root.findViewById(R.id.user_name);
        user_name.setText(user_info.getString("user name_show", getString(R.string.blank)));
        return root;
    }

    private void upDateAvatar(){
        String imageUriString = user_info.getString(getResources().getString(R.string.user_avatar_image), null);
        if(imageUriString==null)
            return;
        Uri imageUri = Uri.parse(imageUriString);
        Bitmap avatar;
        try {
            avatar = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
        }catch (IOException e){
            e.printStackTrace();
            return;
        }
        if(avatar==null)
            return;
        CircularImageView avatarView = root.findViewById(R.id.profile_image);
        avatarView.setImageBitmap(avatar);

    }

    @Override
    public void onResume(){
        super.onResume();
        upDateAvatar();
    }
}
