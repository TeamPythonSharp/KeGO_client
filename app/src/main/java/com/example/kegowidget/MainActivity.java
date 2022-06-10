package com.example.kegowidget;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    Button btnSave;
    EditText edtTitle, edtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(this);

        btnSave = (Button) findViewById(R.id.btn_save);
        edtTitle = (EditText) findViewById(R.id.edt_tittle);
        edtContent = (EditText) findViewById(R.id.edt_content);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().write("Title",edtTitle.getText().toString());
                Paper.book().write("Content",edtContent.getText().toString());

                Toast.makeText(MainActivity.this, "Save!!!",Toast.LENGTH_SHORT).show();

            }
        });
    }
}