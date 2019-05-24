package com.itbp.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    DBHelper db;
    ListView lView;

    /**
     *
     * Activity class for history.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        /* Create Db instance */
        db = new DBHelper(this);
        lView = (ListView)findViewById(R.id.listHistory);

        /* Get All rows from the db as ArrayList of String array */
        ArrayList<String[]> list = db.rawQuery("SELECT * FROM results");

        /* Setting the adapter of the List View */
        ListViewAdapter adapter = new ListViewAdapter(this,list);
        lView.setAdapter(adapter);

    }
}
