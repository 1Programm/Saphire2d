package com.programm.ge.saphire2d.engine.model.font;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class GUIText {

	public final String textString;
	public final float fontSize;

	public int textMeshVao;
	public int vertexCount;
	public final Vector3f color = new Vector3f(0f, 0f, 0f);

	public final Vector2f position;
	public final float lineMaxSize;
	public int numberOfLines;

	public final SaphFont font;

	public boolean centerText;

	public GUIText(String text, float fontSize, SaphFont font, Vector2f position, float maxLineLength, boolean centered) {
		this.textString = text;
		this.fontSize = fontSize;
		this.font = font;
		this.position = position;
		this.lineMaxSize = maxLineLength;
		this.centerText = centered;
	}

	public void remove() {
		// remove text
	}

}
