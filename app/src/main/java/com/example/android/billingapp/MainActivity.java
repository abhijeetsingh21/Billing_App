package com.example.android.billingapp;


import android.content.Intent;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    int quantity=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void increment(View view) {
        if (quantity ==100) {
            Toast.makeText(this,"You Cannot have more than 100 coffees",Toast.LENGTH_LONG).show();
            return;
        }
        quantity=quantity+1;
        display( quantity);
    }
    public void decrement(View view) {
        if (quantity ==0) {
            Toast.makeText(this,"You Cannot have less than 1 coffees",Toast.LENGTH_LONG).show();
            return;
        }
        quantity=quantity-1;
        display(quantity);
    }
    public void submitOrder(View view) {
        EditText nameField =(EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox cappuccinoCheckBox = (CheckBox) findViewById(R.id.cappuccino_checkbox);
        boolean hasCappuccino = cappuccinoCheckBox.isChecked();
        int price = calculateprice(hasWhippedCream,hasCappuccino);
        String priceMessage = createOrderSummary(name,price,hasWhippedCream,hasCappuccino);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,"Just Java order for "+ name);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if(intent.resolveActivity(getPackageManager())!=null)
        {
            startActivity(intent);
        }
    }

   private int calculateprice(boolean addWhippedCream,boolean addCappuccino)
    {
        int basePrice=5;

        if (addWhippedCream)
        {
            basePrice+=1;
        }
        if (addCappuccino)
        {
            basePrice+=2;
        }
        return quantity*basePrice ;

    }
    private String createOrderSummary(String name,int price,boolean add_whipped_cream,boolean add_cappuccino)
    {
        String priceMessage= getString(R.string.order_summary_name,name);
        priceMessage+="\nAdd whipped cream? "+getString(R.string.order_summary_whipped_cream,add_whipped_cream);
        priceMessage+="\nAdd cappuccino? "+getString(R.string.order_summary_chocolate,add_cappuccino);
        priceMessage+="\nQuantity: "+ getString(R.string.order_summary_quantity,quantity);
        priceMessage+="\nTotal: $" + getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));
        priceMessage+="\n "+ getString(R.string.thank_you);
        return priceMessage;

    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}