package com.programm.saphire2d.ui.elements;

import com.programm.saphire2d.ui.SUIRenderable;
import org.joml.Vector4f;

public interface ITextComponent extends SUIRenderable {

    void text(String text);
    String text();

    void textColor(Vector4f textColor);
    Vector4f textColor();

    void textAlign(int textAlign);
    int textAlign();

}
