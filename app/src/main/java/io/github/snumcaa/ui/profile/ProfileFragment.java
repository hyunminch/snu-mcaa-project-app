package io.github.snumcaa.ui.profile;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        root = inflater.inflate(R.layout.fragment_profile, container, false);

        MainActivity main = (MainActivity) getContext();
        userInfo = main.getUserInfo();
        updateAvatar();
        TextView username = root.findViewById(R.id.user_name);
        username.setText(userInfo.user_name);
        return root;
    }

    private void updateAvatar() {
        Bitmap avatar = userInfo.user_avatar;

        CircularImageView avatarView = root.findViewById(R.id.profile_image);
        avatarView.setImageBitmap(avatar);
    }

    private void upDateUserName(){
        TextView usernameView = getActivity().findViewById(R.id.user_name);
        usernameView.setText(userInfo.user_name);
    }

    @Override
    public void onResume(){
        super.onResume();
        updateAvatar();
        upDateUserName();
    }
}
