package com.programm.ge.saphire2d.engine.model.font;

import java.io.File;

public class SaphFont {

	public final int textureAtlas;
	private final TextMeshCreator loader;

	public SaphFont(int textureAtlas, File fontFile, double aspectRatio) {
		this.textureAtlas = textureAtlas;
		this.loader = new TextMeshCreator(fontFile, aspectRatio);
	}

	public TextMeshData loadText(GUIText text) {
		return loader.createTextMesh(text);
	}

}
