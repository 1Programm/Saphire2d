package com.programm.ge.saphire2d.engine.model.font;

import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class FontMetadata {

    private static final int PAD_TOP = 0;
    private static final int PAD_LEFT = 1;
    private static final int PAD_BOTTOM = 2;
    private static final int PAD_RIGHT = 3;

    private static final int DESIRED_PADDING = 3;

    private static final String SPLITTER = " ";
    private static final String NUMBER_SEPARATOR = ",";

    public static FontMetadata load(String path, double aspectRatio) {
        Map<Integer, Character> metaData = new HashMap<>();
        float spaceWidth = 0;
        int[] padding;
        int paddingWidth;
        int paddingHeight;
        final Map<String, String> values = new HashMap<>();



        InputStream is = FontMetadata.class.getResourceAsStream(path);
        if(is == null) throw new RuntimeException("Could not read font-meta-file [" + path + "]!");

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));



        processNextLine(reader, values);
        padding = getValuesOfVariable(values, "padding");
        paddingWidth = padding[PAD_LEFT] + padding[PAD_RIGHT];
        paddingHeight = padding[PAD_TOP] + padding[PAD_BOTTOM];

        float fontSize = getValueOfVariable(values, "size");


        processNextLine(reader, values);

        float lineHeight = (getValueOfVariable(values, "lineHeight") - paddingHeight) / fontSize;


        int imageWidth = getValueOfVariable(values, "scaleW");
        processNextLine(reader, values);
        processNextLine(reader, values);
        while (processNextLine(reader, values)) {
            int id = getValueOfVariable(values, "id");
            if (id == ' ') {
                spaceWidth = (getValueOfVariable(values, "xadvance") - paddingWidth) / fontSize;
                continue;
            }

            float xTex = ((float) getValueOfVariable(values, "x") + (padding[PAD_LEFT] - DESIRED_PADDING)) / imageWidth;
            float yTex = ((float) getValueOfVariable(values, "y") + (padding[PAD_TOP] - DESIRED_PADDING)) / imageWidth;
            int width = getValueOfVariable(values, "width") - (paddingWidth - (2 * DESIRED_PADDING));
            int height = getValueOfVariable(values, "height") - ((paddingHeight) - (2 * DESIRED_PADDING));
            float xTexSize = (float) width / imageWidth;
            float yTexSize = (float) height / imageWidth;
            float xOff = (getValueOfVariable(values, "xoffset") + padding[PAD_LEFT] - DESIRED_PADDING);
            float yOff = (getValueOfVariable(values, "yoffset") + (padding[PAD_TOP] - DESIRED_PADDING));
            float xAdvance = (getValueOfVariable(values, "xadvance") - paddingWidth);
            Character c = new Character(id, xTex, yTex, xTexSize, yTexSize, xOff / fontSize, yOff / fontSize, width / fontSize, height / fontSize, xAdvance / fontSize);
            metaData.put(c.id, c);
        }

        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException("ERROR CLOSING READER!", e);
        }

        return new FontMetadata(metaData, spaceWidth, lineHeight);
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












    private final Map<Integer, Character> metaData;
    public final float spaceWidth;
    public final float lineHeight;

    public Character getCharacter(int ascii) {
        return metaData.get(ascii);
    }

}
