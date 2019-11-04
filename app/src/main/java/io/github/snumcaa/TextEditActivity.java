package io.github.snumcaa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.r0adkll.slidr.Slidr;

public class TextEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_edit);
        Slidr.attach(this);
        Intent intent = getIntent();
    }
}
