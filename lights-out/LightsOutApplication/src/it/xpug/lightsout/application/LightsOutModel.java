package it.xpug.lightsout.application;

public class LightsOutModel {

	private int side;
	private boolean[] lights;

	public LightsOutModel(int side) {
		this.side = side;
		this.lights = new boolean[side*side];
		for (int i = 0; i < lights.length; i++) {
			lights[i] = true;
		}
	}
	
	public LightsOutModel(String status) {
		this((int) Math.sqrt(status.length()));
		setStatus(status);
	}

	@Override
	public String toString() {
		String result = "";
		for (boolean b : lights) {
			result += b ? "O" : "."; 
		}
		return result ;
	}

	public void toggle(int position) {
		if (position < 0 || position >= lights.length)
			throw new IllegalArgumentException("Try to toggle position " + position);
		lights[position] = !lights[position]; 
	}

	public int side() {
		return side;
	}

	public boolean isAllOff() {
		for (int i = 0; i < lights.length; i++) {
			if (lights[i]) return false;
		}
		return true;
	}

	public boolean isAllOn() {
		for (int i = 0; i < lights.length; i++) {
			if (!lights[i]) return false;
		}
		return true;
	}

	public boolean isOnAt(int position) {
		return lights[position];
	}
	
	public boolean onTheBottomSide(int position) {
		return position >= (side-1)*side;
	}

	public boolean onTheRightSide(int position) {
		return (position+1) % side == 0;
	}

	public boolean onTopRow(int position) {
		return position < side;
	}

	public boolean onLeftSide(int position) {
		return position % side == 0;
	}

	public void setStatus(String status) {
		for (int i = 0; i < status.length(); i++) {
			lights[i] = status.charAt(i) == 'O';
		}
	}

}
