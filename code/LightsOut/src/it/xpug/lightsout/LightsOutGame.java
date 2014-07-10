package it.xpug.lightsout;

public class LightsOutGame {

	private LightsOutView view;
	private boolean buttonIsOn;

	public LightsOutGame(LightsOutView view) {
		this.view = view;
	}

	public void onGameStart(int size) {
		int buttonID = view.createButton();
		view.turnButtonOn(buttonID);
		buttonIsOn = true;
	}

	public void onClick() {
		if (this.buttonIsOn)
			view.turnButtonOff(0);
		else
			view.turnButtonOn(0);
		buttonIsOn = !buttonIsOn;
	}

}
