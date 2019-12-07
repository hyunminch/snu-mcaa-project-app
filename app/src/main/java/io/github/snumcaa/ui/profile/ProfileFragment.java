package io.github.snumcaa.ui.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import io.github.snumcaa.R;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {
    private ProfileViewModel viewModel;
    private TextView usernameTextView;
    private TextView profileBioTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this, new ProfileViewModelFactory(getContext())).get(ProfileViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        usernameTextView = view.findViewById(R.id.profile_username_top);
        initializeUsernameTextView();

        profileBioTextView = view.findViewById(R.id.profile_bio_show);
        profileBioTextView.setOnClickListener(v -> onBioClick());

        viewModel.getProfile()
                .observe(this, profile -> {
                    if (profile == null) {

                    } else {
                        Log.i("ProfileFragment", "Setting profile text.");
                        profileBioTextView.setText(profile.getProfile());
                    }
                });

        return view;
    }

    private void initializeUsernameTextView() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("VIDEOSHAREX_PREFS", MODE_PRIVATE);

        String username = sharedPreferences.getString("username", "");
        usernameTextView.setText(username);
    }

    private void updateProfile(String profile) {
        viewModel.updateProfile(profile)
                .observe(this, _profile -> {
                    if (_profile == null) {
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                        profileBioTextView.setText(_profile.getProfile());
                    }
                });
    }

    private void onBioClick() {
        Context context = getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Input new bio!");

        final EditText editText = new EditText(context);
        builder.setView(editText);

        builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String newProfile = editText.getText().toString();
                updateProfile(newProfile);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Canceled", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private boolean verifyChange(String type, String value) {
        // TODO: to verify changes wih the server
        return true;
    }
}
