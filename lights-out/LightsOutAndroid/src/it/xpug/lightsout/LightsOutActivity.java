package it.xpug.lightsout;

import it.xpug.lightsout.application.*;
import android.app.Activity;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class LightsOutActivity extends Activity implements LightsOutView {

	private static final ColorDrawable COLOR_OFF = new ColorDrawable(Color.DKGRAY);
	private static final ColorDrawable COLOR_ON = new ColorDrawable(Color.YELLOW);
	private LightsOutApplication application;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lights_out);
		GridView grid = (GridView) findViewById(R.id.grid);
		grid.setAdapter(new LightsOutAdapter(this));
		
		application = new LightsOutApplication(new LightsOutModel(5), this);
		grid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				application.onClickOn(position);
			}
		});
		
		System.out.println("===== ON CREATE");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.out.println("===== ON DESTROY");
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		System.out.println("===== ON SAVE INSTANCE STATE");
		//throw new RuntimeException("I did it on purpose");
	}
	
	@Override
	public void setLightOnAt(int position) {
		View cell = findViewById(R.id.grid).findViewById(position);
		cell.setBackground(COLOR_ON);
	}

	@Override
	public void setLightOffAt(int position) {
		View cell = findViewById(R.id.grid).findViewById(position);
		cell.setBackground(COLOR_OFF);
	}

	class LightsOutAdapter extends BaseAdapter {

		private Context context;
		
		public LightsOutAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			return 25;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			return new TextView(context) {{
				setId(position);
				setHeight(90);
				setBackground(COLOR_ON);
			}};
		}
	}

	@Override
	public void showVictory() {
		Toast toast = Toast.makeText(this, R.string.you_have_won, Toast.LENGTH_LONG);
		toast.show();
	}
}
