package sg.edu.rp.c346.id19028654.mybmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText weight, height ;
    TextView date, bmi,outcome;
    Button calc, reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weight = findViewById(R.id.editTextweight);
        height = findViewById(R.id.editTextHeight);
        date = findViewById(R.id.tvdate);
        bmi = findViewById(R.id.tvbmi);
        calc = findViewById(R.id.buttonCalculate);
        reset = findViewById(R.id.buttonReset);
        outcome=findViewById(R.id.textViewResult);

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float W = Float.parseFloat(weight.getText().toString());
                float H = Float.parseFloat(height.getText().toString());
                float BMI = W/(H*H);
                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);
                date.setText(datetime);


                if (BMI<18.5){
                    outcome.setText("your are underweight");
                }else if (BMI >=18.5 && BMI<=24.9){
                    outcome.setText("your are normal");
                }else if (BMI>=25 && BMI <=29.9){
                    outcome.setText("your are overweight");
                }else{
                    outcome.setText("your are obese");
                }

                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor pre = pref.edit();

                pre.putString("Last calculated date",datetime);
                pre.putFloat("Last calculated BMI",BMI);

                pre.commit();
                date.setText(datetime);
                bmi.setText(BMI+"");


            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date.setText("Last Calculated Date: ");
                bmi.setText("Last Calculated BMI: ");
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor pre = pref.edit();
                pre.clear();
                pre.commit();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        String datetimeString =date.getText().toString();
        float LBmi =Float.parseFloat(date.getText().toString());
        String LOutcome=outcome.getText().toString();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor pre =prefs.edit();
        pre.putString("Datetime",datetimeString);
        pre.putFloat("LastBMIValue",LBmi);
        pre.putString("outcome",LOutcome);
        pre.commit();



    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String strDate = pref.getString("Date","");
        float flBmi = pref.getFloat("bmi", (float) 0.0);
        date.setText(strDate);
        bmi.setText(flBmi+"");


    }
}
