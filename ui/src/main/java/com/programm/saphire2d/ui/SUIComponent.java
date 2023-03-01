package com.programm.saphire2d.ui;

import com.programm.ge.saphire2d.core.IKeyboard;
import com.programm.ge.saphire2d.core.IMouse;
import com.programm.ge.saphire2d.core.bounds.IBounds;
import com.programm.ge.saphire2d.core.bounds.IEditableBounds;
import com.programm.saphire2d.ui.utils.Colors;
import org.joml.Vector4f;

public abstract class SUIComponent implements IComponent, IEditableBounds, SUIRenderable {

    private float x, y, width, height;
    boolean xUntouched = true, yUntouched = true, widthUntouched = true, heightUntouched = true;
    protected Vector4f primary, secondary, disabledColor;
    protected boolean visible, disabled;

    public SUIComponent() {
        primary = Colors.BLACK;
        secondary = Colors.WHITE;
        disabledColor = Colors.LIGHT_GRAY;
    }

    //Listeners
    @Override
    public void onMousePressed(IBounds bounds, IMouse mouse, int button) {}
    @Override
    public void onMouseReleased(IBounds bounds, IMouse mouse, int button) {}
    @Override
    public void onMouseMoved(IBounds bounds, IMouse mouse) {}
    @Override
    public void onMouseDragged(IBounds bounds, IMouse mouse, int button) {}
    @Override
    public void onMouseScrolled(IBounds bounds, IMouse mouse, float scrollX, float scrollY) {}
    @Override
    public void onKeyPressed(IBounds bounds, IKeyboard keyboard, int key) {}
    @Override
    public void onKeyReleased(IBounds bounds, IKeyboard keyboard, int key) {}


    @Override
    public Float minWidth(IPencil pen) { return null; }

    @Override
    public Float minHeight(IPencil pen) { return null; }

    @Override
    public float x() {
        return x;
    }

    @Override
    public void x(float x){
        this.x = x;
        this.xUntouched = false;
    }

    @Override
    public float y() {
        return y;
    }

    @Override
    public void y(float y){
        this.y = y;
        this.yUntouched = false;
    }

    @Override
    public void position(float x, float y){
        this.x = x;
        this.y = y;
        this.xUntouched = false;
        this.yUntouched = false;
    }

    @Override
    public float width() {
        return width;
    }

    @Override
    public void width(float width){
        this.width = width;
        this.widthUntouched = false;
    }

    @Override
    public float height() {
        return height;
    }

    @Override
    public void height(float height){
        this.height = height;
        this.heightUntouched = false;
    }

    @Override
    public void size(float width, float height){
        this.width = width;
        this.height = height;
        this.widthUntouched = false;
        this.heightUntouched = false;
    }

    @Override
    public void bounds(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.xUntouched = false;
        this.yUntouched = false;
        this.widthUntouched = false;
        this.heightUntouched = false;
    }

//    @Override
//    public void bounds(IBounds bounds) {
//        bounds(bounds.x(), bounds.y(), bounds.width(), bounds.height());
//    }

    public void primary(Vector4f primary){
        this.primary = primary;
    }

    public Vector4f primary(){
        return primary;
    }

    public void secondary(Vector4f secondary){
        this.secondary = secondary;
    }

    public Vector4f secondary(){
        return secondary;
    }

    public void disabledColor(Vector4f disabledColor){
        this.disabledColor = disabledColor;
    }

    public Vector4f disabledColor(){
        return disabledColor;
    }

    public void visible(boolean visible){
        this.visible = visible;
    }

    public boolean visible(){
        return visible;
    }

    public void disabled(boolean disabled){
        this.disabled = disabled;
    }

    public boolean disabled(){
        return disabled;
    }

}
