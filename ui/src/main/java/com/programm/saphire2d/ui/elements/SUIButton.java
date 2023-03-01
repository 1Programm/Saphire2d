package com.programm.saphire2d.ui.elements;

import com.programm.ge.saphire2d.core.IMouse;
import com.programm.ge.saphire2d.core.bounds.IBounds;
import com.programm.saphire2d.ui.IPencil;
import com.programm.saphire2d.ui.subscription.RunnableSubscriptionManager;
import com.programm.saphire2d.ui.subscription.Subscription;
import com.programm.saphire2d.ui.utils.Colors;
import com.programm.saphire2d.ui.utils.GFXUtils;
import org.joml.Vector4f;

public class SUIButton extends WaveLabel {

    protected final RunnableSubscriptionManager pressedManager = new RunnableSubscriptionManager();

    protected Vector4f pressedColor;
    protected boolean pressed;

    protected Vector4f hoverColor;
    protected boolean hovered;

    public SUIButton(String text) {
        super(text);
        initDefaults();
    }

    public SUIButton() {
        initDefaults();
    }

    private void initDefaults(){
        pressedColor = Colors.LIGHT_GRAY;
        hoverColor = Colors.LIGHTER_GRAY;
    }

    @Override
    public void render(IBounds bounds, IPencil pen) {
        if(secondary != null){
            Vector4f color = GFXUtils.mixColor(secondary, pressedColor, pressed, hoverColor, hovered);
            pen.fillRectangle(bounds, color);
        }

        if(primary != null){
            Vector4f color = GFXUtils.primaryOrDisabled(this);
            pen.drawRectangle(bounds, color);
        }

        WaveLabel.renderText(this, bounds, pen);
    }

    @Override
    public void onMousePressed(IBounds bounds, IMouse mouse, int button) {
        if(button == IMouse.BUTTON_LEFT) {
            if(mouse.inside(bounds)) {
                pressed = true;
                pressedManager.notifyChange();
            }
        }
    }

    @Override
    public void onMouseReleased(IBounds bounds, IMouse mouse, int button) {
        if(button == IMouse.BUTTON_LEFT) {
            if(pressed) {
                pressed = false;
            }
        }
    }

    @Override
    public void onMouseMoved(IBounds bounds, IMouse mouse) {
        hovered = false;
        if(mouse.inside(bounds)){
            hovered = true;
        }
    }

    public void pressedColor(Vector4f pressedColor){
        this.pressedColor = pressedColor;
    }

    public Vector4f pressedColor(){
        return pressedColor;
    }

    public void hoverColor(Vector4f hoverColor){
        this.hoverColor = hoverColor;
    }

    public Vector4f hoverColor(){
        return hoverColor;
    }

    public boolean pressed(){
        return pressed;
    }

    public boolean hovered() {
        return hovered;
    }

    public Subscription listenPressed(Runnable listener){
        return pressedManager.subscribe(listener);
    }
}
