package it.xpug.life;

import static com.thoughtworks.game_of_life.core.Location.*;

import java.util.*;

import android.app.*;
import android.os.*;
import android.view.*;

import com.thoughtworks.game_of_life.core.*;

public class LifeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		int side = 20;
		final World world = new World(side, side);
		Random random = new Random();
		for (int x = 0; x < side; x++) {
			for (int y = 0; y < side; y++) {
				if (random.nextFloat() > .7) {
			        world.setLiving(at(x,y));
				}
			}
		}

		LifeView view = new LifeView(this, world);
		setContentView(view);		
	}
	
	
}
