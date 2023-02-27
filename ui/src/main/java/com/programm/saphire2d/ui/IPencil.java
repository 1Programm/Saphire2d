package com.programm.saphire2d.ui;

import com.programm.ge.saphire2d.core.bounds.IBounds;
import org.joml.Vector4f;

public interface IPencil {

    float stringWidth(String s);

    float stringHeight();





    void drawLine(float x1, float y1, float x2, float y2, Vector4f color, float lineSize);

    default void drawLine(float x1, float y1, float x2, float y2, Vector4f color) {
        drawLine(x1, y1, x2, y2, color, 1);
    }





    void drawRectangle(float x, float y, float width, float height, Vector4f color, float edge, float lineSize);

    default void drawRectangle(float x, float y, float width, float height, Vector4f color) {
        drawRectangle(x, y, width, height, color, 0, 1);
    }





    void fillRectangle(float x, float y, float width, float height, Vector4f color, float edge);

    default void fillRectangle(float x, float y, float width, float height, Vector4f color) {
        fillRectangle(x, y, width, height, color, 0);
    }





    void drawString(String s, float x, float y, Vector4f color);

    void drawStringCentered(String s, float x, float y, Vector4f color);

    void drawStringVCentered(String s, float x, float y, Vector4f color);

    void drawStringVCenteredRightAligned(String s, float x, float y, float width, Vector4f color);

    void drawStringHCentered(String s, float x, float y, Vector4f color);






    default void drawRectangle(IBounds bounds, Vector4f color, float edge, float lineSize){
        drawRectangle(bounds.x(), bounds.y(), bounds.width(), bounds.height(), color, edge, lineSize);
    }

    default void drawRectangle(IBounds bounds, Vector4f color){
        drawRectangle(bounds.x(), bounds.y(), bounds.width(), bounds.height(), color, 0, 1);
    }

    default void fillRectangle(IBounds bounds, Vector4f color, float edge){
        fillRectangle(bounds.x(), bounds.y(), bounds.width(), bounds.height(), color, edge);
    }

    default void fillRectangle(IBounds bounds, Vector4f color){
        fillRectangle(bounds.x(), bounds.y(), bounds.width(), bounds.height(), color, 0);
    }

}
