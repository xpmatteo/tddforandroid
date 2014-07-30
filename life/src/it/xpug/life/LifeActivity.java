package it.xpug.life;

import static com.thoughtworks.game_of_life.core.Location.*;

import java.util.*;

import android.app.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import android.view.*;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;

import com.thoughtworks.game_of_life.core.*;

public class LifeActivity extends Activity {

	private World world;
	private LifeView lifeView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_life);
		
		world = new World(20, 20);
		world.randomize();		
		lifeView = (LifeView) findViewById(R.id.life);
		lifeView.setWorld(world);
		enableRandomizeButton();
		enableStopButton();
		enableStartButton();
	}


	private void enableStartButton() {
		View button = findViewById(R.id.restart);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				lifeView.restart();
			}
		});
	}


	private void enableStopButton() {
		View button = findViewById(R.id.stop);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				lifeView.stop();
			}
		});
	}


	private void enableRandomizeButton() {
		View button = findViewById(R.id.randomize);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				world.randomize();
				lifeView.invalidate();
			}
		});
	}
	
	
}
