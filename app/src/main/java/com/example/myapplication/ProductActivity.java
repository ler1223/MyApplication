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

import java.io.IOException;

public class ProductActivity extends AppCompatActivity {

    private String sname;
    private int id_layout;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_layout);
        this.sname = (String) getIntent().getSerializableExtra("sname");
        dataBase(this.sname);
        this.button = findViewById(R.id.back_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    private void dataBase(String sname) {
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

        if (sname.equals("")) {
            //textView.setText("");
        } else {
            String query = "SELECT c.id, c.name, c.form, min(d.BaseCost), MAX(d.BaseCost)" +
                    "FROM catalog c left JOIN drugs d  on c.id=d.catalogid" +
                    " WHERE c.id in (SELECT c1.id FROM catalog c1 WHERE c1.name like '" + "%" + sname + "%')" +
                    "AND d.catalogid is not NULL GROUP by c.id";

            Cursor cursor = mDb.rawQuery(query, null);
            MyProduct[] ProductArr = new MyProduct[cursor.getCount()];
            int index = 0;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                MyProduct product = new MyProduct();
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String form = cursor.getString(2);
                String price = cursor.getInt(3) + " - " + cursor.getInt(4);
                product.name = name + "\t" + form;
                product.price = price;
                product.id = id;
                ProductArr[index] = product;
                index++;
                cursor.moveToNext();
            }
            cursor.close();
            newAdapter(ProductArr, this.sname);
        }
    }

    public void newAdapter(MyProduct[] ProductArr, String sname) {
        MyProductAdapter adapter = new MyProductAdapter(this, ProductArr);
        ListView lv = (ListView) findViewById(R.id.productList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProductActivity.this, Apteka_list.class);
                intent.putExtra("name",ProductArr[position].name);
                intent.putExtra("id", ProductArr[position].id);
                intent.putExtra("sname", sname);
                startActivity(intent);
            }
        });
    }

}