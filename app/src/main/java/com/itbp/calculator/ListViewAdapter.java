package com.itbp.calculator;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListViewAdapter  extends ArrayAdapter<String[]> {

    private ArrayList<String[]> list;
    private DBHelper db;
    private ListViewAdapter adapter;
    private Context context;
    public ListViewAdapter(Context context, ArrayList<String[]> lv) {
        super(context,0,lv);
        list = lv;
        db = new DBHelper(context);
        adapter = this;
        this.context = context;
    }

    @Override
    public String[] getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final String[] item = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.history_list,parent,false);
        }

        TextView A = (TextView) convertView.findViewById(R.id.A);
        TextView B = (TextView) convertView.findViewById(R.id.B);
        TextView C = (TextView) convertView.findViewById(R.id.C);
        TextView res = (TextView) convertView.findViewById(R.id.result);
        A.setText("A: " + item[1] + " ");
        B.setText("B: " + item[2] + " " );
        C.setText("C: " +item[3]);
        res.setText("Резултат: " + item[4]);
        Button delete = (Button) convertView.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.executeSQL("DELETE FROM results WHERE id = " + item[0]);
                list.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(context,"Записът изтрит успешно!",Toast.LENGTH_LONG).show();
            }
        });
        return convertView;
    }
}

