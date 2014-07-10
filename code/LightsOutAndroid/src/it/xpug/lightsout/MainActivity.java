package it.xpug.lightsout;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private Drawable colorOff = new ColorDrawable(Color.DKGRAY);
	private Drawable colorOn = new ColorDrawable(Color.YELLOW);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final Button button = (Button) findViewById(R.id.button1);
		button.setBackground(colorOff);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (button.getBackground().equals(colorOff))
					button.setBackground(colorOn);
				else
					button.setBackground(colorOff);
			}
		});
	}
}
