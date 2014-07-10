package it.xpug.lightsout;

public interface LightsOutView {

	void turnButtonOn(int buttonId);

	void turnButtonOff(int buttonId);

	int createButton();

	int createButtonRightOf(int buttonId);

	int createButtonBelow(int buttonId);

}
