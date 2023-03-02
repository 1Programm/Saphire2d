package com.programm.ge.saphire2d.ui.elements;

import com.programm.ge.saphire2d.core.IMouse;
import com.programm.ge.saphire2d.core.bounds.IBounds;
import com.programm.ge.saphire2d.core.bounds.VarBounds;
import com.programm.ge.saphire2d.reactivevalues.constraint.number.FloatClampConstraint;
import com.programm.ge.saphire2d.reactivevalues.core.FloatProperty;
import com.programm.ge.saphire2d.reactivevalues.core.FloatPropertyValue;
import com.programm.ge.saphire2d.ui.utils.Colors;
import com.programm.ge.saphire2d.ui.IPencil;
import com.programm.ge.saphire2d.ui.SUIComponent;

public class SUISlider extends SUIComponent {

    private final FloatProperty delta = new FloatPropertyValue();
    {
        delta.constrain(new FloatClampConstraint(0, 1));
    }

    private boolean selectedHandle;

    private final FloatProperty minValue = new FloatPropertyValue(null);
    private final FloatProperty maxValue = new FloatPropertyValue(null);

    private final VarBounds handleBounds = new VarBounds();
    private SUILabel label;

    @Override
    public void render(IBounds bounds, IPencil pen) {
        float handleSize = handleSize(bounds);
        float hs2 = handleSize/2f;
        float x = bounds.x() + hs2;
        float y = bounds.y() + hs2;
        float endX = bounds.x() + bounds.width() - hs2;
        float endY = bounds.y() + bounds.height() - hs2;

        pen.drawLine(x, y, endX, endY, Colors.BLACK);

        float handleX = handleX(bounds);
        float handleY = handleY(bounds);
        handleBounds.bounds(handleX, handleY, handleSize, handleSize);


        pen.fillRectangle(handleX, handleY, handleSize, handleSize, Colors.BLACK, 0);

        if(label != null) {
            handleBounds.y(handleY + handleSize);
            label.render(handleBounds, pen);
            handleBounds.y(handleY);
        }
    }

    @Override
    public void onMousePressed(IBounds bounds, IMouse mouse, int button) {
        if(button == IMouse.BUTTON_LEFT && mouse.inside(handleBounds)){
            selectedHandle = true;
        }
    }

    @Override
    public void onMouseReleased(IBounds bounds, IMouse mouse, int button) {
        selectedHandle = false;
    }

    @Override
    public void onMouseDragged(IBounds bounds, IMouse mouse, int button) {
        if(selectedHandle){
            float handleSize2 = handleSize(bounds) / 2f;
            float ax = bounds.x() + handleSize2;
            float ay = bounds.y() + handleSize2;
            float bx = bounds.x() + bounds.width() - handleSize2;
            float by = bounds.y() + bounds.height() - handleSize2;
            float px = mouse.dragX();
            float py = mouse.dragY();

            float proj = projectionLength(ax, ay, bx, by, px, py);
//            delta.set(clamp(proj));
            delta.set(proj);
        }
    }

//    private float clamp(float d){
//        return Math.max(0, Math.min(1, d));
//    }

    private float handleSize(IBounds bounds){
        return Math.min(bounds.width(), bounds.height());
    }

    private float handleX(IBounds bounds){
        float s = handleSize(bounds);
        return bounds.x() + (bounds.width() - s) * delta.get();
    }

    private float handleY(IBounds bounds){
        float s = handleSize(bounds);
        return bounds.y() + (bounds.height() - s) * delta.get();
    }

    private static float projectionLength(float ax, float ay, float bx, float by, float px, float py)
    {
        float abx = bx - ax;
        float aby = by - ay;

        float apx = px - ax;
        float apy = py - ay;

        float abLength = (float) Math.sqrt(abx * abx + aby * aby);
        float dot = apx * abx + apy * aby;
        return dot / (abLength * abLength);
    }

//    public Float delta(){
//        return delta;
//    }
//
//    public void delta(float delta){
//        this.delta = clamp(delta);
//    }

    public FloatProperty delta(){
        return delta;
    }

    public float value(){
        if(minValue.get() == null || maxValue.get() == null) return delta.get();
        float dist = maxValue.get() - minValue.get();
        return minValue.get() + dist * delta.get();
    }

    public void value(float value){
        if(minValue.get() == null || maxValue.get() == null) {
            delta.set(value);
            return;
        }

        value = Math.max(minValue.get(), Math.min(maxValue.get(), value));
        float dist = maxValue.get() - minValue.get();
        delta.set((value - minValue.get()) / dist);
    }

    public void setValueRange(float min, float max){
        this.minValue.set(min);
        this.maxValue.set(max);
    }

    public void label(SUILabel label){
        this.label = label;
        delta.set(delta.get());
    }

}
