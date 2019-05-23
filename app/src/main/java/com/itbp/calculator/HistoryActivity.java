package com.itbp.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    DBHelper db;
    ListView lView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        db = new DBHelper(this);
        lView = (ListView)findViewById(R.id.listHistory);

        ArrayList<String[]> list = db.rawQuery("SELECT * FROM results");

        ListViewAdapter adapter = new ListViewAdapter(this,list);
        lView.setAdapter(adapter);

    }
}
