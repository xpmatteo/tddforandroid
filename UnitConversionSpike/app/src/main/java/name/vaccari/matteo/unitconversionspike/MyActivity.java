package name.vaccari.matteo.unitconversionspike;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;


public class MyActivity extends Activity implements View.OnKeyListener {

    private EditText inputNumber;
    private TextView outputNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        inputNumber = (EditText) findViewById(R.id.inputNumber);
        outputNumber = (TextView) findViewById(R.id.result);

        // whenever any of these fields changes, our onKey() will be called
        setThisAsOnKeyListener(R.id.inputNumber);
        setThisAsOnKeyListener(R.id.fromUnit);
        setThisAsOnKeyListener(R.id.toUnit);
    }

    private void setThisAsOnKeyListener(int id) {
        getEditText(id).setOnKeyListener(this);
    }

    private EditText getEditText(int id) {
        return (EditText) findViewById(id);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        String text = inputNumber.getText().toString();
        if (text.trim().isEmpty())
            return false;

        if (getEditTextContent(R.id.fromUnit).equals("in")
                && getEditTextContent(R.id.toUnit).equals("cm")) {
            double n = Double.valueOf(text);
            outputNumber.setText("" + n * 2.54);
        } else {
            outputNumber.setText("I don't know how to convert this");
        }
        return false;
    }

    private String getEditTextContent(int id) {
        return getEditText(id).getText().toString();
    }
}
