package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubmitModel {
	private static int currentIndex = 0;
	private static boolean isClear = false;
	
	public boolean isValid(String answer) {
		if(answer.length() == 5) {
			return true;
		} else {
			return false;
		}
	}

	public boolean[] isCollectPosition(String answer, String collect) {
		boolean[] isCollect = new boolean[answer.length()];
		for (int i = 0; i < isCollect.length; i++) {
			if(answer.charAt(i) == collect.charAt(i)) {
				isCollect[i] = true;
			}
		}
		return isCollect;
	}

	public boolean[] isContain(String answer, String collect) {
		boolean[] isContain = new boolean[answer.length()];
		
		Set<Character> collection = new HashSet<Character>();
		for (int i = 0; i < collect.length(); i++) {
			collection.add(collect.charAt(i));
		}
		for (int i = 0; i < isContain.length; i++) {
			if(collection.contains(answer.charAt(i))) {
				isContain[i] = true;
			}
		}
		return isContain;
	}
	
	public boolean isClear(List<Color> colors) {
		for (Color c: colors) {
			if(c != Color.GREEN)
				return false;
		}
		isClear = true;
		return true;
	}
	
	public boolean isGameOver(int maxRow) {
		if(currentIndex >= maxRow) {
			return true;
		}
		return false;
	}
	
	public List<Color> getColors(boolean[] isCollectPosition, boolean[] isContain) {
		List<Color> fillColors = new ArrayList<Color>();
		
		for (int i = 0; i < isContain.length; i++) {
			if(isCollectPosition[i]) {
				fillColors.add(Color.GREEN);
			} else if(isContain[i]) {
				fillColors.add(Color.ORANGE);
			} else {
				fillColors.add(Color.GRAY);
			}
		}
		return fillColors;
	}
	
	public int getIndex() {
		return currentIndex;
	}
	
	public boolean getClearState() {
		return isClear;
	}
	
	public void setNextIndex() {
		currentIndex++;
	}
	
	public void resetIndex() {
		currentIndex = 0;
	}
	
	public void resetIsClear() {
		isClear = false;
	}
}
