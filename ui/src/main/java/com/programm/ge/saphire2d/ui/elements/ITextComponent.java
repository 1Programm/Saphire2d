package com.programm.ge.saphire2d.ui.elements;

import com.programm.ge.saphire2d.reactivevalues.core.FloatProperty;
import com.programm.ge.saphire2d.reactivevalues.core.IntProperty;
import com.programm.ge.saphire2d.reactivevalues.core.ObjectProperty;
import com.programm.ge.saphire2d.reactivevalues.core.StringProperty;
import com.programm.ge.saphire2d.ui.SUIRenderable;
import org.joml.Vector4f;

public interface ITextComponent extends SUIRenderable {

    StringProperty text();

    ObjectProperty<Vector4f> textColor();
//    void textColor(Vector4f textColor);
//    Vector4f textColor();

    FloatProperty fontSize();
//    void fontSize(float fontSize);
//    float fontSize();

    IntProperty textAlign();
//    void textAlign(int textAlign);
//    int textAlign();

}
