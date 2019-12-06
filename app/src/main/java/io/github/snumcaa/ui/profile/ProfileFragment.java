package io.github.snumcaa.ui.profile;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.IOException;

import io.github.snumcaa.ui.MainActivity;
import io.github.snumcaa.R;

public class ProfileFragment extends Fragment {
    private SharedPreferences userInfo;
    private View root;

    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        root = inflater.inflate(R.layout.fragment_profile, container, false);

        MainActivity main = (MainActivity) getContext();
        userInfo = main.getUserInfo();
        updateAvatar();
        TextView username = root.findViewById(R.id.user_name);
        username.setText(userInfo.getString("user name_show", getString(R.string.blank)));
        return root;
    }

    private void updateAvatar() {
        String imageUriString = userInfo.getString(getResources().getString(R.string.user_avatar_image), null);

        if (imageUriString == null)
            return;

        Uri imageUri = Uri.parse(imageUriString);
        Bitmap avatar;

        try {
            avatar = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (avatar == null)
            return;

        CircularImageView avatarView = root.findViewById(R.id.profile_image);
        avatarView.setImageBitmap(avatar);
    }

    private void upDateUserName(){
        TextView usernameView = getActivity().findViewById(R.id.user_name);
        usernameView.setText(userInfo.getString("user name_show", getResources().getString(R.string.profile_default_user_name)));
    }

    @Override
    public void onResume(){
        super.onResume();
        updateAvatar();
        upDateUserName();
    }
}
