package com.itbp.calculator;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    EditText A,B,C;
    TextView result;
    Button calculate;

    DBHelper db;
    Button history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* instances of the EditText fields */
        A = (EditText) findViewById(R.id.A);
        B = (EditText) findViewById(R.id.B);
        C = (EditText) findViewById(R.id.C);
        result = (TextView) findViewById(R.id.result);
        calculate = (Button) findViewById(R.id.calculate);
        history = (Button) findViewById(R.id.history);
        db = new DBHelper(this);
        final Context context = this;
        /* Click listener for the calculate button */
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{

                    /* Getting the entered values */
                    String text_a=A.getText().toString();
                    String text_b=B.getText().toString();
                    String text_c=C.getText().toString();

                    /** Validation if all fields has value */
                    if(text_a.equals("") || text_b.equals("") || text_c.equals("") ) {
                        result.setText("Не сте въвели необходимите данни! ");
                        return;
                    }

                    /** Parsing the values as floats */
                    Float fl_a=Float.parseFloat(text_a);
                    Float fl_b=Float.parseFloat(text_b);
                    Float fl_c=Float.parseFloat(text_c);

                    /* calculating the D */
                    Float D=fl_b*fl_b-4*fl_a*fl_c;
                    String res = "";
                    /* Showing the result depending on the D and then save the data in the local DB*/
                    if(D<0){
                        res = "Няма реални корени";
                        result.setText(res);
                        A.setText("");
                        B.setText("");
                        C.setText("");
                        db.executeSQL("INSERT INTO results (a,b,c,result) VALUES ('"+String.valueOf(text_a)+"','"+String.valueOf(text_b)+"','"+String.valueOf(text_c)+"','"+String.valueOf(res)+"') ");
                        return;
                    }
                    if(D==0){
                        Float x=-fl_b/(2*fl_a);

                        res = "Един корен: "+x.toString();
                        result.setText(res);
                        A.setText("");
                        B.setText("");
                        C.setText("");
                        db.executeSQL("INSERT INTO results (a,b,c,result) VALUES ('"+String.valueOf(text_a)+"','"+String.valueOf(text_b)+"','"+String.valueOf(text_c)+"','"+String.valueOf(res)+"') ");
                        return;

                    }

                    Float x1=(-fl_b+((float)Math.sqrt(D)))/(2*fl_a);
                    Float x2=(-fl_b-((float)Math.sqrt(D)))/(2*fl_a);

                    res = "Има 2 реални корена: \nΧ1 = "+String.format("%.2f", x1)+" \nX2 = "+String.format("%.2f", x2);
                    result.setText(res );

                    db.executeSQL("INSERT INTO results (a,b,c,result) VALUES ('"+String.valueOf(text_a)+"','"+String.valueOf(text_b)+"','"+String.valueOf(text_c)+"','"+String.valueOf(res)+"') ");
                    A.setText("");
                    B.setText("");
                    C.setText("");
                }catch (Exception e) {
                    result.setText(e.getLocalizedMessage());
                }
            }
        });

        /* Click listener for tge history button */
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent histIntent = new Intent(context,HistoryActivity.class);
                context.startActivity(histIntent);
            }
        });
    }
}
