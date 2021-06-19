package com.example.nitish235.newsignin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Intent intent=getIntent();
        String var=intent.getStringExtra("Extra");
        Double distance=Double.parseDouble(var) ;

        int totalbooths=0;
        totalbooths= (int) (distance/80000);

        Double payamount=0.00;
        payamount=totalbooths*30.00;

        TextView booth;
        TextView pay;
        Button proceed;
        booth=findViewById(R.id.tollTV);
        pay=findViewById(R.id.totalpayTV);
        proceed=findViewById(R.id.proceddbtn);

        booth.setText("Total Booth in the journey: "+String.valueOf(totalbooths));
        pay.setText("Total billed amount: "+String.valueOf(payamount));

        final Double finalPayamount = payamount;
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pay_intent=new Intent(Main4Activity.this,Main3Activity.class);
                pay_intent.putExtra("Extra",String.valueOf(finalPayamount));
                startActivity(pay_intent);
            }
        });

    }
}
