package it.xpug.lightsout.application;


public class LightsOutApplication {

	private LightsOutView view;
	private LightsOutModel model;
	private int score = 0;

	public LightsOutApplication(LightsOutModel model, LightsOutView view) {
		this.model = model;
		this.view = view;
	}

	public void onClickOn(int position) {
		toggleCross(position);
		if (won())
			view.showVictory();
		score++;
		view.updateScore(score);
	}

	private void toggleCross(int position) {
		if (!model.onTopRow(position))
			toggle(position-model.side());
		if (!model.onLeftSide(position))
			toggle(position-1);
		toggle(position);
		if (!model.onTheRightSide(position))
			toggle(position+1);
		if (!model.onTheBottomSide(position))
			toggle(position+model.side());
	}

	private boolean won() {
		return model.isAllOff();
	}

	private void toggle(int position) {
		model.toggle(position);
		if (model.isOnAt(position))
			view.setLightOnAt(position);
		else
			view.setLightOffAt(position);
	}

	public boolean isOnAt(int position) {
		return model.isOnAt(position);
	}

	public void restoreStatus(String status) {
		model.setStatus(status);
	}

	public String saveStatus() {
		return model.toString();
	}

	public int score() {
		return score;
	}

}
