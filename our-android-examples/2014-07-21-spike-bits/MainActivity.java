package it.xpug.lightsoutspike;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		System.out.println(" *** ON CREATE *** ");

		GridView grid = (GridView) findViewById(R.id.grid_view);
		LightsOutGridAdapter adapter = new LightsOutGridAdapter(this);
		final LightsOutController controller = new LightsOutController(adapter);
		adapter.setController(controller);
		grid.setAdapter(adapter);				
		grid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				controller.onClick(position);
			}
		});
	}
}
