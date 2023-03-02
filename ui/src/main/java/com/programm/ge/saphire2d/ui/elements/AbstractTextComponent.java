package com.programm.ge.saphire2d.ui.elements;

import com.programm.ge.saphire2d.reactivevalues.core.*;
import com.programm.ge.saphire2d.ui.GlobalComponentUtils;
import com.programm.ge.saphire2d.ui.IPencil;
import com.programm.ge.saphire2d.ui.SUIComponent;
import com.programm.ge.saphire2d.ui.elements.layout.ILayout;
import com.programm.ge.saphire2d.ui.utils.Colors;
import org.joml.Vector4f;

public abstract class AbstractTextComponent extends SUIComponent implements ITextComponent {

    private final StringProperty text = new StringPropertyValue();
    {
        text.listenChange(t -> minWidth = null);//reset minWidth on text change
    }

    private final ObjectProperty<Vector4f> textColor = new ObjectPropertyValue<>();
    private final FloatProperty fontSize = new FloatPropertyValue();
    private final IntProperty textAlign = new IntPropertyValue();

    protected Float minWidth, minHeight;

    public AbstractTextComponent(String text) {
        this();
        this.text.set(text);
    }

    public AbstractTextComponent() {
        primary = null;
        secondary = null;
        textColor.set(Colors.BLACK);
        fontSize.set(21f);
        textAlign.set(ILayout.ALIGN_CENTER);
    }

    @Override
    public Float minWidth(IPencil pen) {
        if(!GlobalComponentUtils.widthUntouched(this)) return width();
        if(text.get() != null && minWidth == null){
            minWidth = pen.stringWidth(text.get(), fontSize.get()) + 4;
        }

        return minWidth;
    }

    @Override
    public Float minHeight(IPencil pen) {
        if(!GlobalComponentUtils.heightUntouched(this)) return height();
        if(minHeight == null){
            minHeight = pen.stringHeight(fontSize.get()) + 4;
        }

        return minHeight;
    }

    @Override
    public StringProperty text() {
        return text;
    }

    @Override
    public ObjectProperty<Vector4f> textColor() {
        return textColor;
    }

    @Override
    public FloatProperty fontSize() {
        return fontSize;
    }

    @Override
    public IntProperty textAlign() {
        return textAlign;
    }

}
