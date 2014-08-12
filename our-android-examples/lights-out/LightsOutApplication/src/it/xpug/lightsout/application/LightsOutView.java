package it.xpug.lightsout.application;

public interface LightsOutView {

	void setLightOnAt(int position);

	void setLightOffAt(int position);

	void showVictory();

	void updateScore(int newScore);

}
