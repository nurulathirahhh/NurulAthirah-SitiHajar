package com.example.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText goldWeight, CGoldValue;
    RadioGroup radioGroup;
    Button btnCalc, btnClr;
    TextView outputTGV, outputZP, outputTotal;
    String Shared_pref = "sharedPrefs";
    String gold_weight = "goldWeight";
    String gold_value = "CGoldValue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goldWeight = (EditText) findViewById(R.id.goldWeight);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        CGoldValue = (EditText) findViewById(R.id.CGoldValue);
        btnCalc = (Button) findViewById(R.id.btnCalc);
        btnClr = (Button) findViewById(R.id.btnClr);
        outputTGV = (TextView) findViewById(R.id.outputTGV);
        outputZP = (TextView) findViewById(R.id.outputZP);
        outputTotal = (TextView) findViewById(R.id.outputTotal);

        btnCalc.setOnClickListener(this);
        btnClr.setOnClickListener(this);
        SharedPreferences sharedPreferences = getSharedPreferences(Shared_pref, MODE_PRIVATE);
        String weightgold = sharedPreferences.getString(gold_weight, "");
        goldWeight.setText(weightgold);
        String goldvalue = sharedPreferences.getString(gold_value, "");
        CGoldValue.setText(goldvalue);
    }

    @Override
    public void onClick(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences(Shared_pref, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(gold_weight, goldWeight.getText().toString());
        editor.putString(gold_value, CGoldValue.getText().toString());

        editor.apply();
        int checkedId = radioGroup.getCheckedRadioButtonId();
        if(view.getId() == btnCalc.getId()){
            try {

                switch (view.getId()){
                    case R.id.btnCalc:

                        int value = 0;
                        if(checkedId==-1){
                            //no radio buttons are selected
                            Toast.makeText(MainActivity.this, "Please select type of gold!", Toast.LENGTH_SHORT).show();
                        }else {


                            switch (checkedId) {
                                case R.id.keepRB:
                                    value = 85;

                                    break;

                                case R.id.wearRB:
                                    value = 200;

                                    break;
                            }
                        }

                        double weight = Double.parseDouble(goldWeight.getText().toString());
                        double gValue = Double.parseDouble(CGoldValue.getText().toString());
                        double totalGV = weight * gValue;
                        outputTGV.setText(String.format( "RM %.2f", totalGV));

                        double uruf = weight - value;
                        double totalZP = 0;
                        if(uruf < 0){
                            totalZP = 0;
                        }else{
                            totalZP = uruf * gValue;
                        }
                        outputZP.setText(String.format( "RM %.2f", totalZP));

                        double finalTotal = 0.025 * totalZP;
                        outputTotal.setText(String.format( "RM %.2f", finalTotal));

                        break;
                }

            }catch(NumberFormatException nfe) {
                Toast.makeText(this, "Please enter the value!", Toast.LENGTH_SHORT).show();
            }
        }
        else if(view.getId() == btnClr.getId()){
            goldWeight.setText(" ");
            CGoldValue.setText(" ");
            outputTGV.setText(" ");
            outputZP.setText(" ");
            outputTotal.setText(" ");
            radioGroup.clearCheck();
        }
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate (R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Toast.makeText(this, "About", Toast.LENGTH_LONG).show();
                Intent intent = new Intent (this, AboutActivity.class);
                startActivity(intent);
                break;

            case R.id.home:
                Toast.makeText(this, "Home!", Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent (this, MainActivity.class);
                startActivity(intent1);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}