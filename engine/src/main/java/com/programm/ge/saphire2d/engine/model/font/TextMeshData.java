package com.programm.ge.saphire2d.engine.model.font;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TextMeshData {
	
	public final float[] vertexPositions;
	public final float[] textureCoords;

	public int getVertexCount() {
		return vertexPositions.length/2;
	}

}
