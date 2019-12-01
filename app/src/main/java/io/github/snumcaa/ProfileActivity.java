package io.github.snumcaa;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.r0adkll.slidr.Slidr;

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

        TextView user = findViewById(R.id.profile_username_show);
        upDateText(user);
        TextView whatsUp = findViewById(R.id.profile_whatsUp_show);
        upDateText(whatsUp);
        TextView bio = findViewById(R.id.profile_bio_show);
        upDateText(bio);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.exit_right);
    }

    public void onClick(View v){
        final String tag_show = v.getTag() + "_show";
        final SharedPreferences.Editor editor = user_info.edit();
        final TextView showView = v.findViewWithTag(tag_show);
        final Context context = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("input user name");
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

    public void upDateText(TextView text){
        text.setText(user_info.getString((String)text.getTag(), "nothing"));
    }
}
