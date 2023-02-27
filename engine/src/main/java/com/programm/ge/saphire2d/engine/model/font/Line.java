package com.programm.ge.saphire2d.engine.model.font;

import java.util.ArrayList;
import java.util.List;

public class Line {

	public final double maxLength;
	public final double spaceSize;

	public final List<Word> words = new ArrayList<Word>();
	public double lineLength = 0;

	protected Line(double maxLength, double spaceWidth, double fontSize) {
		this.spaceSize = spaceWidth * fontSize;
		this.maxLength = maxLength;
	}

	protected boolean attemptToAddWord(Word word) {
		double additionalLength = word.width;
		additionalLength += words.isEmpty() ? 0 : spaceSize;
		if (lineLength + additionalLength <= maxLength) {
			words.add(word);
			lineLength += additionalLength;
			return true;
		} else {
			return false;
		}
	}

}
