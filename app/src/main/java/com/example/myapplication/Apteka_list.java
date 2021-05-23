package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;

public class Apteka_list extends AppCompatActivity {
    private Button button;
    private Button back_button;
    private TextView name_form;
    private String name;
    private String sname;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apteka_list);
        this.sname = (String) getIntent().getSerializableExtra("sname");
        this.name = (String) getIntent().getSerializableExtra("name");
        this.name_form = findViewById(R.id.textApteka);
        this.name_form.setText(name);
        this.id = (int) getIntent().getSerializableExtra("id");
        this.button = findViewById(R.id.domoy);
        this.back_button = findViewById(R.id.back_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Apteka_list.this, MainActivity.class);
                startActivity(intent);
            }
        });
        back_button(this.sname);
        dataBase();
    }

    public void back_button(String sname){
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Apteka_list.this, ProductActivity.class);
                intent.putExtra("sname", sname);
                startActivity(intent);
            }
        });
    }


    private void dataBase() {
        DatabaseHelper mDBHelper = new DatabaseHelper(this);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
        SQLiteDatabase mDb;
        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }


        String query = "SELECT d.Telephone, printf( \"%.2f\", d.BaseCost), d.Address, d.PharmacyName FROM drugs d WHERE catalogid = " + this.id + " ORDER by d.BaseCost";

        Cursor cursor = mDb.rawQuery(query, null);
        Apteka[] AptekaArr = new Apteka[cursor.getCount()];
        int index = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Apteka apteka = new Apteka();
            apteka.telefon = cursor.getString(0);
            apteka.price = cursor.getString(1);
            apteka.address = cursor.getString(2);
            apteka.name_apteka = cursor.getString(3);
            AptekaArr[index] = apteka;
            index++;
            cursor.moveToNext();
        }
        cursor.close();
        Adapter(AptekaArr);
    }


    public void Adapter(Apteka[] AptekaArr) {
        MyAptekaAdapter adapter = new MyAptekaAdapter(this, AptekaArr);
        ListView lv = (ListView) findViewById(R.id.aptekaList);
        lv.setAdapter(adapter);
    }
}