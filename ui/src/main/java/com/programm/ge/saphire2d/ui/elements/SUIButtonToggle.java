package com.programm.ge.saphire2d.ui.elements;

import com.programm.ge.saphire2d.core.IMouse;
import com.programm.ge.saphire2d.core.bounds.IBounds;
import com.programm.ge.saphire2d.reactivevalues.core.BoolProperty;
import com.programm.ge.saphire2d.reactivevalues.core.BoolPropertyValue;
import com.programm.ge.saphire2d.reactivevalues.core.ObjectProperty;
import com.programm.ge.saphire2d.reactivevalues.core.ObjectPropertyValue;
import com.programm.ge.saphire2d.ui.IPencil;
import com.programm.ge.saphire2d.ui.utils.Colors;
import com.programm.ge.saphire2d.ui.utils.GFXUtils;
import org.joml.Vector4f;

public class SUIButtonToggle extends SUILabel {

    private final ObjectProperty<Vector4f> pressedColor = new ObjectPropertyValue<>();
    private final BoolProperty pressed = new BoolPropertyValue();

    private final ObjectProperty<Vector4f> hoveredColor = new ObjectPropertyValue<>();
    private final BoolProperty hovered = new BoolPropertyValue();

    public SUIButtonToggle(String text) {
        super(text);
        initDefaults();
    }

    public SUIButtonToggle() {
        initDefaults();
    }

    private void initDefaults(){
        primary = Colors.BLACK;
        secondary = Colors.WHITE;
        pressedColor.set(Colors.LIGHT_GRAY);
        hoveredColor.set(Colors.LIGHTER_GRAY);
    }

    @Override
    public void render(IBounds bounds, IPencil pen) {
        if(secondary != null){
            Vector4f color = GFXUtils.mixColor(secondary, pressedColor.get(), pressed.get(), hoveredColor.get(), hovered.get());
            pen.fillRectangle(bounds, color);
        }

        if(primary != null){
            Vector4f color = GFXUtils.primaryOrDisabled(this);
            pen.drawRectangle(bounds, color);
        }

        SUILabel.renderText(this, bounds, pen);
    }

    @Override
    public void onMouseReleased(IBounds bounds, IMouse mouse, int button) {
        if(button == IMouse.BUTTON_LEFT) {
            if(mouse.inside(bounds)) {
                toggle();
            }
        }
    }

    @Override
    public void onMouseMoved(IBounds bounds, IMouse mouse) {
        hovered.set(mouse.inside(bounds));
    }

    public void toggle(){
        pressed.invert();
    }

    public void toggle(boolean pressed){
        this.pressed.set(pressed);
    }

    public ObjectProperty<Vector4f> pressedColor() {
        return pressedColor;
    }

    public BoolProperty pressed(){
        return pressed;
    }

    public ObjectProperty<Vector4f> hoveredColor(){
        return hoveredColor;
    }

    public BoolProperty hovered(){
        return hovered;
    }

}
