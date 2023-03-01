package com.programm.saphire2d.ui.elements;

import com.programm.saphire2d.ui.IPencil;
import com.programm.saphire2d.ui.SUIComponent;
import com.programm.saphire2d.ui.elements.layout.ILayout;
import com.programm.saphire2d.ui.utils.Colors;
import org.joml.Vector4f;

public abstract class AbstractTextComponent extends SUIComponent implements ITextComponent {

    protected String text;
    protected Vector4f textColor;
    protected int textAlign;
    private float fontSize;

    protected Float minWidth, minHeight;

    public AbstractTextComponent(String text) {
        this();
        this.text = text;
    }

    public AbstractTextComponent() {
        textColor = Colors.BLACK;
        textAlign = ILayout.ALIGN_CENTER;
        fontSize = 21;
    }

    @Override
    public Float minWidth(IPencil pen) {
        if(minWidth == null){
            minWidth = pen.stringWidth(text, fontSize) + 4;
        }

        return minWidth;
    }

    @Override
    public Float minHeight(IPencil pen) {
        if(minHeight == null){
            minHeight = pen.stringHeight(fontSize) + 4;
        }

        return minHeight;
    }

    @Override
    public void text(String text){
        this.text = text;
        this.minWidth = null;
    }

    @Override
    public String text(){
        return text;
    }

    @Override
    public void textColor(Vector4f textColor){
        this.textColor = textColor;
    }

    @Override
    public Vector4f textColor(){
        return textColor;
    }

    @Override
    public void fontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    @Override
    public float fontSize() {
        return fontSize;
    }

    @Override
    public void textAlign(int textAlign){
        this.textAlign = textAlign;
    }

    @Override
    public int textAlign(){
        return textAlign;
    }

}
