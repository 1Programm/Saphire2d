package com.programm.ge.saphire2d.engine.model.font;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TextMeshCreator {

	protected static final double LINE_HEIGHT = 0.03f;
	protected static final int SPACE_ASCII = 32;

	private final MetaFile metaData;

	protected TextMeshCreator(File metaFile, double aspectRatio) {
//		metaData = new MetaFile(metaFile);
		metaData = null;//MetaFile.load(metaFile, aspectRatio);
	}

	protected TextMeshData createTextMesh(GUIText text) {
		List<Line> lines = createStructure(text);
		return createQuadVertices(text, lines);
	}

	private List<Line> createStructure(GUIText text) {
		char[] chars = text.textString.toCharArray();
		List<Line> lines = new ArrayList<>();
		Line currentLine = new Line(metaData.spaceWidth, text.fontSize, text.lineMaxSize);
		Word currentWord = new Word(text.fontSize);
		for (char c : chars) {
			if (c == SPACE_ASCII) {
				boolean added = currentLine.attemptToAddWord(currentWord);
				if (!added) {
					lines.add(currentLine);
					currentLine = new Line(metaData.spaceWidth, text.fontSize, text.lineMaxSize);
					currentLine.attemptToAddWord(currentWord);
				}
				currentWord = new Word(text.fontSize);
				continue;
			}
			Character character = metaData.getCharacter(c);
			currentWord.addCharacter(character);
		}
		completeStructure(lines, currentLine, currentWord, text);
		return lines;
	}

	private void completeStructure(List<Line> lines, Line currentLine, Word currentWord, GUIText text) {
		boolean added = currentLine.attemptToAddWord(currentWord);
		if (!added) {
			lines.add(currentLine);
			currentLine = new Line(metaData.spaceWidth, text.fontSize, text.lineMaxSize);
			currentLine.attemptToAddWord(currentWord);
		}
		lines.add(currentLine);
	}

	private TextMeshData createQuadVertices(GUIText text, List<Line> lines) {
		text.numberOfLines = lines.size();
		double curserX = 0f;
		double curserY = 0f;
		List<Float> vertices = new ArrayList<>();
		List<Float> textureCoords = new ArrayList<>();
		for(Line line : lines) {
			if(text.centerText) {
				curserX = (line.maxLength - line.lineLength) / 2;
			}

			for(Word word : line.words) {
				for(Character c : word.characters) {
					addVerticesForCharacter(curserX, curserY, c, text.fontSize, vertices);
					addVertices(textureCoords, c.texCoordX, c.texCoordY, c.texCoordMaxX, c.texCoordMaxY);
					curserX += c.xAdvance * text.fontSize;
				}
				curserX += metaData.spaceWidth * text.fontSize;
			}

			curserX = 0;
			curserY += LINE_HEIGHT * text.fontSize;
		}

		return new TextMeshData(listToArray(vertices), listToArray(textureCoords));
	}

	private void addVerticesForCharacter(double curserX, double curserY, Character character, double fontSize, List<Float> vertices) {
		double x = curserX + (character.offX * fontSize);
		double y = curserY + (character.offY * fontSize);
		double maxX = x + (character.width * fontSize);
		double maxY = y + (character.height * fontSize);
		double properX = (2 * x) - 1;
		double properY = (-2 * y) + 1;
		double properMaxX = (2 * maxX) - 1;
		double properMaxY = (-2 * maxY) + 1;
		addVertices(vertices, properX, properY, properMaxX, properMaxY);
	}

	private static void addVertices(List<Float> vertices, double x, double y, double maxX, double maxY) {
		vertices.add((float) x);
		vertices.add((float) y);
		vertices.add((float) x);
		vertices.add((float) maxY);
		vertices.add((float) maxX);
		vertices.add((float) maxY);
		vertices.add((float) maxX);
		vertices.add((float) maxY);
		vertices.add((float) maxX);
		vertices.add((float) y);
		vertices.add((float) x);
		vertices.add((float) y);
	}

//	private static void addTexCoords(List<Float> texCoords, double x, double y, double maxX, double maxY) {
//		texCoords.add((float) x);
//		texCoords.add((float) y);
//		texCoords.add((float) x);
//		texCoords.add((float) maxY);
//		texCoords.add((float) maxX);
//		texCoords.add((float) maxY);
//		texCoords.add((float) maxX);
//		texCoords.add((float) maxY);
//		texCoords.add((float) maxX);
//		texCoords.add((float) y);
//		texCoords.add((float) x);
//		texCoords.add((float) y);
//	}

	
	private static float[] listToArray(List<Float> listOfFloats) {
		float[] array = new float[listOfFloats.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = listOfFloats.get(i);
		}
		return array;
	}

}
