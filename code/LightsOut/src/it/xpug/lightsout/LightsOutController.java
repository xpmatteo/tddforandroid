package it.xpug.lightsout;

import java.util.List;

public class LightsOutController {

	private LightsOutView view;
	private List<Boolean> status;

	public LightsOutController(List<Boolean> status, LightsOutView view) {
		this.status = status;
		this.view = view;
	}

	public void onGameStart() {
		for (int i = 0; i < status.size(); i++) {
			if (status.get(i))
				view.turnButtonOn(i);
			else
				view.turnButtonOff(i);
		}
	}

	public void onClick(int buttonId) {
		for (int i=buttonId-1; i<= buttonId+1; i++) {
			view.turnButtonOff(i);
			status.set(i, false);
		}
	}

}
