package com.programm.ge.saphire2d.engine.model.font;

import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class MetaFile {

	public static MetaFile load(File file, double aspectRatio) {
		Map<Integer, Character> metaData = new HashMap<>();
		double spaceWidth = 0;


		double verticalPerPixelSize;
		double horizontalPerPixelSize;
		int[] padding;
		int paddingWidth;
		int paddingHeight;
		BufferedReader reader;
		final Map<String, String> values = new HashMap<>();



		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (IOException e) {
			throw new RuntimeException("Could not read font-meta-file [" + file.getPath() + "]!", e);
		}



		processNextLine(reader, values);
		padding = getValuesOfVariable(values, "padding");
		paddingWidth = padding[PAD_LEFT] + padding[PAD_RIGHT];
		paddingHeight = padding[PAD_TOP] + padding[PAD_BOTTOM];


		processNextLine(reader, values);
		int lineHeightPixels = getValueOfVariable(values, "lineHeight") - paddingHeight;
		verticalPerPixelSize = TextMeshCreator.LINE_HEIGHT / (double) lineHeightPixels;
		horizontalPerPixelSize = verticalPerPixelSize / aspectRatio;


		int imageWidth = getValueOfVariable(values, "scaleW");
		processNextLine(reader, values);
		processNextLine(reader, values);
		while (processNextLine(reader, values)) {
			int id = getValueOfVariable(values, "id");
			if (id == TextMeshCreator.SPACE_ASCII) {
				spaceWidth = (getValueOfVariable(values, "xadvance") - paddingWidth) * horizontalPerPixelSize;
				continue;
			}

			double xTex = ((double) getValueOfVariable(values, "x") + (padding[PAD_LEFT] - DESIRED_PADDING)) / imageWidth;
			double yTex = ((double) getValueOfVariable(values, "y") + (padding[PAD_TOP] - DESIRED_PADDING)) / imageWidth;
			int width = getValueOfVariable(values, "width") - (paddingWidth - (2 * DESIRED_PADDING));
			int height = getValueOfVariable(values, "height") - ((paddingHeight) - (2 * DESIRED_PADDING));
			double quadWidth = width * horizontalPerPixelSize;
			double quadHeight = height * verticalPerPixelSize;
			double xTexSize = (double) width / imageWidth;
			double yTexSize = (double) height / imageWidth;
			double xOff = (getValueOfVariable(values, "xoffset") + padding[PAD_LEFT] - DESIRED_PADDING) * horizontalPerPixelSize;
			double yOff = (getValueOfVariable(values, "yoffset") + (padding[PAD_TOP] - DESIRED_PADDING)) * verticalPerPixelSize;
			double xAdvance = (getValueOfVariable(values, "xadvance") - paddingWidth) * horizontalPerPixelSize;
			Character c = new Character(id, xTex, yTex, xTexSize, yTexSize, xOff, yOff, quadWidth, quadHeight, xAdvance);
			metaData.put(c.id, c);
		}

		try {
			reader.close();
		} catch (IOException e) {
			throw new RuntimeException("ERROR CLOSING READER!", e);
		}

		return new MetaFile(metaData, spaceWidth);
	}

	private static boolean processNextLine(BufferedReader reader, Map<String, String> values) {
		values.clear();
		String line;
		try {
			line = reader.readLine();
		} catch (IOException e) {
			throw new RuntimeException("ERROR READING LINE", e);
		}

		if (line == null) return false;

		for (String part : line.split(SPLITTER)) {
			String[] valuePairs = part.split("=");
			if (valuePairs.length == 2) {
				values.put(valuePairs[0], valuePairs[1]);
			}
		}

		return true;
	}

	private static int getValueOfVariable(Map<String, String> values, String variable) {
		return Integer.parseInt(values.get(variable));
	}

	private static int[] getValuesOfVariable(Map<String, String> values, String variable) {
		String[] numbers = values.get(variable).split(NUMBER_SEPARATOR);
		int[] actualValues = new int[numbers.length];
		for (int i = 0; i < actualValues.length; i++) {
			actualValues[i] = Integer.parseInt(numbers[i]);
		}
		return actualValues;
	}











	private static final int PAD_TOP = 0;
	private static final int PAD_LEFT = 1;
	private static final int PAD_BOTTOM = 2;
	private static final int PAD_RIGHT = 3;

	private static final int DESIRED_PADDING = 3;

	private static final String SPLITTER = " ";
	private static final String NUMBER_SEPARATOR = ",";

	private final Map<Integer, Character> metaData;
	public final double spaceWidth;

	protected Character getCharacter(int ascii) {
		return metaData.get(ascii);
	}

}
