package com.programm.saphire2d.ui.elements;

import com.programm.saphire2d.ui.IPencil;
import com.programm.saphire2d.ui.SUIComponent;
import com.programm.saphire2d.ui.elements.layout.ILayout;
import com.programm.saphire2d.ui.utils.Colors;
import org.joml.Vector4f;

public abstract class AbstractTextComponent extends SUIComponent implements ITextComponent {

    protected String text;
    protected Vector4f textColor = Colors.BLACK;
    protected int textAlign = ILayout.ALIGN_CENTER;

    protected Float minWidth, minHeight;

    public AbstractTextComponent(String text) {
        this.text = text;
    }

    public AbstractTextComponent() {}

    @Override
    public Float minWidth(IPencil pen) {
        if(minWidth == null){
            minWidth = pen.stringWidth(text) + 4;
        }

        return minWidth;
    }

    @Override
    public Float minHeight(IPencil pen) {
        if(minHeight == null){
            minHeight = pen.stringHeight() + 4;
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
    public void textAlign(int textAlign){
        this.textAlign = textAlign;
    }

    @Override
    public int textAlign(){
        return textAlign;
    }

}
