package it.xpug.life;

import static com.thoughtworks.game_of_life.core.Location.*;
import android.annotation.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import com.thoughtworks.game_of_life.core.*;

public class LifeView extends View {

	private Paint paint = new Paint();
	private RectF rect = new RectF();
	private World world;
	
	private RefreshHandler handler = new RefreshHandler();
	private int width;
	private int height;

	@SuppressLint("HandlerLeak")
	class RefreshHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			world.advance();
			invalidate();
		}
		
        public void sleep(long delayMillis) {
        	removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
	}

	public LifeView(Context context, World world) {
		super(context);
		this.world = world;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		width = canvas.getWidth();
		height = world.getHeight();
		int side = Math.min(canvas.getHeight(), width) / height;
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < world.getWidth(); c++) {
				int color;
				if (world.isAlive(at(c, r))) {
					color = Color.DKGRAY;
				} else {
					color = Color.WHITE;
				}
				rect.left = c * side;
				rect.top = r * side;
				rect.right = (c+1) * side;
				rect.bottom = (r+1) * side;
				paint.setColor(color);
				canvas.drawRect(rect, paint);
			}
		}
		
		handler.sleep(100);
	}

}
