package it.xpug.lightsout;

import android.content.*;
import android.test.*;
import junit.framework.*;

public class VictoryScreenAcceptanceTest extends ActivityInstrumentationTestCase2<VictoryScreenActivity> {

	public VictoryScreenAcceptanceTest() {
		super(VictoryScreenActivity.class);
	}
	
	public void setUp() throws Exception {
		super.setUp();
		givenBestScores(200, 300, 400);
	}
	
	public void testBestScore() {
		whenIWinWithScore(199);
		theMessageIs("Congratulations! Your score is 199\nThis is your best score!");
		andTheHighScoreTableContains(199, 200, 300, 400);
	}
	
	private void givenBestScores(int ... scores) {
	}

	private void andTheHighScoreTableContains(int ...scores) {
	}

	private void theMessageIs(String message) {
	}

	private void whenIWinWithScore(int score) {
	}
	
	public void testProva() throws Exception {
		Intent intent = new Intent("it.xpug.lightsout.victory");
		setActivityIntent(intent);
		
		
	}

//	private void whenIWinWithScore(int i) {
//	}
//
//	private void testNoHighScore() {
//		whenIWinWithScore(200);
//		theMessageIs("Congratulations! Your score is 200");
//		andTheHighScoreTableContains(200, 300, 400);
//	}

	
}
