package it.xpug.lightsout;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements LightsOutView {

	private Drawable colorOff = new ColorDrawable(Color.DKGRAY);
	private Drawable colorOn = new ColorDrawable(Color.YELLOW);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		
		List<Boolean> status = Arrays.asList(true);
		final LightsOutController game = new LightsOutController(status, this);
		game.onGameStart();
		getButton().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				game.onClick(v.getId());
			}
		});
	}

	@Override
	public void turnButtonOn(int buttonId) {
		getButton().setBackground(colorOn);
	}

	@Override
	public void turnButtonOff(int buttonId) {
		getButton().setBackground(colorOff);
	}

	private Button getButton() {
		return (Button) findViewById(R.id.button1);
	}

}
