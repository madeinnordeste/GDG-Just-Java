package com.github.madeinnordeste.justjava;

        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.ActionBarActivity;
        import android.support.v7.app.AppCompatActivity;
        import android.text.Editable;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display(quantity);
    }

    public void show_toast(String text){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void increment(View view){
        int limit = 100;
        quantity++;
        if( quantity > limit ){
            quantity = limit;
            show_toast( getString(R.string.max_quantity)+" "+Integer.toString(limit) );
        }



        display(quantity);

    }

    public void decrement(View view){
        int limit = 1;
        quantity--;
        if( quantity < limit ){
            quantity = limit;
            show_toast( getString(R.string.min_quantity)+" "+Integer.toString(limit) );
        }
        display(quantity);

    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        createOrderSumary();

        //String valor = (String)view.getTag();
        //displayPrice(quantity * 5);
        //String priceMessage = "Free";
        //displayMessage(priceMessage);

    }

    private void createOrderSumary(){

        EditText name_edit_text = (EditText)findViewById(R.id.name_editText);
        String name = name_edit_text.getText().toString();

        CheckBox whipped_cream = (CheckBox)findViewById(R.id.whipped_cream_checkbox);
        String has_whipped_cream = getString( whipped_cream.isChecked() ? R.string.yes : R.string.no );

        CheckBox chocolate = (CheckBox)findViewById(R.id.chocolate_checkbox);
        String has_chocolate = getString( chocolate.isChecked() ? R.string.yes : R.string.no );

        String priceMessage = getString(R.string.name)+": "+name+"\n"+
                getString(R.string.add_whipped_cream)+"? "+has_whipped_cream+"\n"+
                getString(R.string.add_chocolate)+"? "+has_chocolate+"\n"+
                getString(R.string.quantity)+": "+quantity+"\n"+
                getString(R.string.total)+": "+calculatePrice(quantity)+"\n"+
                getString(R.string.thank_you);


        //displayMessage(priceMessage);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject) );
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }



    }

    private int calculatePrice(int quantity){

        int basePrice = 5;

        //whipped cream
        CheckBox whipped_cream = (CheckBox)findViewById(R.id.whipped_cream_checkbox);
        if( whipped_cream.isChecked() ){
            basePrice+=1;
        }

        //chocolate
        CheckBox chocolate = (CheckBox)findViewById(R.id.chocolate_checkbox);
        if( chocolate.isChecked() ){
            basePrice+=2;
        }


        return quantity * basePrice;
    }



    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);

    }



    /*
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summaty_text_view);
        priceTextView.setText("R$ "+NumberFormat.getCurrencyInstance().format(number));

    }
    */

    /*
    private void displayMessage(String priceMessage) {
        TextView ordeSummaryTextView = (TextView) findViewById(R.id.order_summaty_text_view);
        ordeSummaryTextView.setText(priceMessage);
        Log.v("MainActivity", "displayMessage() method");
    }
    */

}