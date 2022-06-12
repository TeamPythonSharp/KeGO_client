package com.example.kego_client;


import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import io.paperdb.Paper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimerConfigActivity extends AppCompatActivity {

    Button btnSave, btnChange;
    EditText  edtTarget, edtTime, edtDate;
    private final String DATE_FORMAT = "HH:mm dd.MM.yyyy";
    long diff_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_config);

        Paper.init(this);

        btnSave = findViewById(R.id.btn_save);
        edtTarget = findViewById(R.id.edt_target);
        edtTime = findViewById(R.id.edt_time);
        edtDate = findViewById(R.id.edt_date);
        btnChange = findViewById(R.id.change_btn);

        btnSave.setOnClickListener(view -> {
            Paper.book().write("target","чтобы " + edtTarget.getText().toString());
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            try {
                Date event_date = dateFormat.parse(edtTime.getText().toString() + " " + edtDate.getText().toString());
                Date current_date = new Date();
                diff_date = event_date.getTime() - current_date.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Paper.book().write("data_and_time", diff_date);
            Toast.makeText(TimerConfigActivity.this, "Поехали!",Toast.LENGTH_SHORT).show();
            finishAfterTransition();
        });
    }
}