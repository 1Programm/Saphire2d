package com.programm.ge.saphire2d.engine.model.font;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class Word {
	
	public final List<Character> characters = new ArrayList<>();
	private final double fontSize;
	public double width = 0;

	protected void addCharacter(Character character){
		characters.add(character);
		width += character.xAdvance * fontSize;
	}

}
