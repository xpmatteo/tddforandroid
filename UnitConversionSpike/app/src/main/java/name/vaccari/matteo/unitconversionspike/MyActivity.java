package name.vaccari.matteo.unitconversionspike;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MyActivity extends Activity implements View.OnKeyListener {

    private EditText inputNumber;
    private TextView outputNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        inputNumber = (EditText) findViewById(R.id.inputNumber);
        inputNumber.setOnKeyListener(this);

        outputNumber = (TextView) findViewById(R.id.result);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
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
