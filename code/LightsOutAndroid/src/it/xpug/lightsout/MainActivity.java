package it.xpug.lightsout;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements LightsOutView {

	private Drawable colorOff = new ColorDrawable(Color.DKGRAY);
	private Drawable colorOn = new ColorDrawable(Color.YELLOW);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		
		final LightsOutGame game = new LightsOutGame(this);
		game.onGameStart(1);
		getButton().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				game.onClick();
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

	@Override
	public int createButton() {
		Button button = new Button(this);
		button.setId(1000);
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative_layout);
		layout.addView(button);
		return 1000;
	}

	@Override
	public int createButtonRightOf(int buttonId) {
		return 0;
	}

	@Override
	public int createButtonBelow(int buttonId) {
		return 0;
	}

	private Button getButton() {
		return (Button) findViewById(1000);
	}

}
