package name.vaccari.matteo.unitdoctor;

import android.app.Activity;
import android.os.Bundle;
import android.text.*;
import android.view.*;
import android.widget.TextView;

import name.vaccari.matteo.unitdoctor.core.UnitDoctor;


public class MainActivity extends Activity implements TextWatcher {
    private UnitDoctor doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView inputNumberField = (TextView) findViewById(R.id.inputNumber);
        TextView fromUnitField = (TextView) findViewById(R.id.fromUnit);
        TextView toUnitField = (TextView) findViewById(R.id.toUnit);
        TextView resultField = (TextView) findViewById(R.id.result);
        AndroidUnitDoctorView view = new AndroidUnitDoctorView(inputNumberField, fromUnitField, toUnitField, resultField);

        doctor = new UnitDoctor(view);

        inputNumberField.addTextChangedListener(this);
        fromUnitField.addTextChangedListener(this);
        toUnitField.addTextChangedListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        doctor.onChange();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
