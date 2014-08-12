package it.xpug.lightsoutspike;

import static java.lang.String.*;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.view.*;
import android.widget.*;

public class LightsOutGridAdapter extends BaseAdapter implements LightsOutView {
	final Drawable colorOn = new ColorDrawable(Color.YELLOW);
	final Drawable colorOff = new ColorDrawable(Color.DKGRAY);
	
	private int count = 16;
	private Context context;
	private ViewGroup grid;
	private LightsOutController controller;
	
	public LightsOutGridAdapter(Context context) {
		this.context = context;
	}

	public void setController(LightsOutController controller) {
		this.controller = controller;
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public Object getItem(int position) {
		return Integer.valueOf(position);
	}

	@Override
	public long getItemId(int position) {
		return Integer.valueOf(position);
	}
	
	@Override
	public void toggle(int position) {
		View view = grid.findViewById(position);
		if (null != view)
			toggle(view);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		this.grid = parent;
		return new TextView(context) {{ 
			setHeight(80);
			setId(position);
			setText("" + position);
			if (controller.isButtonOn(position))
				setBackground(colorOn);
			else 
				setBackground(colorOff);
		}};
	}

	private void toggle(View v) {
		if (v.getBackground().equals(colorOn))
			v.setBackground(colorOff);
		else
			v.setBackground(colorOn);
	}
}
