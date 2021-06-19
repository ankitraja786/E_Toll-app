package com.example.nitish235.newsignin;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class Main3Activity extends AppCompatActivity {

    private static final String TAG = Main3Activity.class.getSimpleName();
    EditText vehicle_text;
    DatabaseReference databaseReference;
    String num;
    //TextView printingTV;
    Button enterwalabtn;
    TextView paisa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Intent intent=getIntent();
        String var=intent.getStringExtra("Extra");
        final Double amount=Double.parseDouble(var) ;

        Checkout.preload(getApplicationContext());

        // Payment button created by you in XML layout
        Button button = (Button) findViewById(R.id.btn_pay);
        vehicle_text=(EditText)findViewById(R.id.vehicleid);
        //printingTV=(TextView)findViewById(R.id.print);
        enterwalabtn=(Button)findViewById(R.id.enterbtn);
        paisa=(TextView)findViewById(R.id.inr);

        paisa.setText("Inr: "+String.valueOf(amount));

        enterwalabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vechicle_number=vehicle_text.getText().toString();
                num=vechicle_number;
                if(!vechicle_number.matches(""))
                {
                    Toast.makeText(Main3Activity.this,"Entered",Toast.LENGTH_LONG).show();
                    vehicle_text.setText("");
                    //paisa.setText("Inr: "+String.valueOf(amount));
                }
                else
                    {
                        Toast.makeText(Main3Activity.this,"Enter vehicle number",Toast.LENGTH_LONG).show();
                    }

                //printingTV.setText("L= "+vechicle_number);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(num.matches(""))
                {
                    Toast.makeText(Main3Activity.this,"Please Enter vechicle number",Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    startPayment();
                }

            }
        });
    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Razorpay Corp");
            options.put("description", "Demoing Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", "100");

            JSONObject preFill = new JSONObject();
            preFill.put("email", "test@razorpay.com");
            preFill.put("contact", "9876543210");

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }


    @SuppressWarnings("unused")
    //@Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {

            Toast.makeText(this, "Database Updated with vehicle no: "+num+" and Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
            vehicle_text.setText("");
            Intent intent=new Intent(Main3Activity.this,Main5Activity.class);
            startActivity(intent);

              //String vechicle_number= vehicle_text.getText().toString(); 8*c
                String id=databaseReference.push().getKey();
                vehicle vehicle_details=new vehicle(id,num);

                databaseReference.child(id).push().getKey();



        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    @SuppressWarnings("unused")
    //@Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }
}
