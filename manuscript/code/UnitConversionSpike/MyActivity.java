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

        getEditText(R.id.inputNumber).setOnKeyListener(this);
        getEditText(R.id.fromUnit).setOnKeyListener(this);
        getEditText(R.id.toUnit).setOnKeyListener(this);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        String input = getEditText(R.id.inputNumber).getText();
        String fromUnit = getEditText(R.id.fromUnit).getText();
        String toUnit = getEditText(R.id.toUnit).getText();
        getEditText(R.id.result).setText(" Hello! " + input + fromUnit + toUnit);
        return false;
    }

    private EditText getEditText(int id) {
        return (EditText) findViewById(id);
    }

    private String getEditTextContent(int id) {
        return getEditText(id).getText().toString();
    }
}
