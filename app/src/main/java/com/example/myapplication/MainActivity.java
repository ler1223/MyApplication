package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public Button button;
    public EditText edit;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    protected TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.button = findViewById(R.id.button);
        this.edit = findViewById(R.id.edit);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sname = edit.getText().toString().toUpperCase();
                newActivity(sname);
            }
        });
    }

    private void newActivity(String name){
        Intent intent = new Intent(MainActivity.this, ProductActivity.class);
        intent.putExtra("sname", name);
        intent.putExtra("id_layout", 1);
        startActivity(intent);
    }
}
