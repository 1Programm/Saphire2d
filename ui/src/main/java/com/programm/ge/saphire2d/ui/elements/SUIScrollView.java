package com.programm.ge.saphire2d.ui.elements;

import com.programm.ge.saphire2d.core.IKeyboard;
import com.programm.ge.saphire2d.core.IMouse;
import com.programm.ge.saphire2d.core.bounds.IBounds;
import com.programm.ge.saphire2d.core.bounds.IEditableBounds;
import com.programm.ge.saphire2d.core.bounds.VarBounds;
import com.programm.ge.saphire2d.reactivevalues.core.FloatProperty;
import com.programm.ge.saphire2d.reactivevalues.core.FloatPropertyValue;
import com.programm.ge.saphire2d.ui.IPencil;
import com.programm.ge.saphire2d.ui.SUIComponent;

import java.util.function.BiConsumer;

public class SUIScrollView extends SUIView {

    private final FloatProperty scroll = new FloatPropertyValue();
    private final FloatProperty scrollSpeed = new FloatPropertyValue(1f);

    private final SUISlider scrollSlider = new SUISlider();
    private final IEditableBounds scrolledBounds = new VarBounds(){
        @Override
        public float y() {
            return super.y() + scroll.get();
        }
    };
    private final IEditableBounds sliderBounds = new VarBounds();

    private Float minChildrenHeight;

    @Override
    public void render(IBounds bounds, IPencil pen) {
        renderSelf(bounds, pen);

        scrolledBounds.bounds(bounds);
        if(layout != null) {
            layout.updateBoundsForChildren(pen, scrolledBounds, children, childArgsList, childBoundsList);
        }
        else {
            if(children.size() == 1){
                childBoundsList.get(0).bounds(scrolledBounds);
            }
        }

        if(minChildrenHeight == null){
            minChildrenHeight = childrenHeight(pen);
            //TODO update when updating (adding / removing) children
        }


        pen.pushClipping(bounds);
        for(int i=0;i<children.size();i++){
            SUIComponent child = children.get(i);
            IEditableBounds childBounds = childBoundsList.get(i);
            if(childBounds != null) {
                child.render(childBounds, pen);
            }
        }
        pen.popClipping();

        sliderBounds.bounds(bounds.x() + bounds.width() - 5, bounds.y(), 5, bounds.height());
        scrollSlider.render(sliderBounds, pen);
    }

    @Override
    public void onMousePressed(IBounds bounds, IMouse mouse, int button) {
        callForAll(bounds, (child, childBounds) -> child.onMousePressed(childBounds, mouse, button));
    }

    @Override
    public void onMouseReleased(IBounds bounds, IMouse mouse, int button) {
        callForAll(bounds, (child, childBounds) -> child.onMouseReleased(childBounds, mouse, button));
    }

    @Override
    public void onMouseMoved(IBounds bounds, IMouse mouse) {
        callForAll(bounds, (child, childBounds) -> child.onMouseMoved(childBounds, mouse));
    }

    @Override
    public void onMouseDragged(IBounds bounds, IMouse mouse, int button) {
        callForAll(bounds, (child, childBounds) -> child.onMouseDragged(childBounds, mouse, button));
    }

    @Override
    public void onMouseScrolled(IBounds bounds, IMouse mouse, float scrollX, float scrollY) {
        scroll.add(scrollY * scrollSpeed.get());

        if(scroll.get() > 0) scroll.set(0f);

        if(minChildrenHeight != null){
            float max = minChildrenHeight - bounds.height();
            if(scroll.get() < -max) scroll.set(-max);
            scrollSlider.delta().set(scroll.get() / (-max));
        }

        callForAll(bounds, (child, childBounds) -> child.onMouseScrolled(childBounds, mouse, scrollX, scrollY));
    }

    @Override
    public void onKeyPressed(IBounds bounds, IKeyboard keyboard, int key) {
        callForAll(bounds, (child, childBounds) -> child.onKeyPressed(childBounds, keyboard, key));
    }

    @Override
    public void onKeyReleased(IBounds bounds, IKeyboard keyboard, int key) {
        callForAll(bounds, (child, childBounds) -> child.onKeyReleased(childBounds, keyboard, key));
    }

    private void callForAll(IBounds bounds, BiConsumer<SUIComponent, IBounds> childConsumer){
        for(int i=0;i<children.size();i++){
            SUIComponent child = children.get(i);
            IBounds childBounds = childBoundsList.get(i);
            if(childBounds == null) continue;
            if(!bounds.intersects(childBounds)) continue;
            childConsumer.accept(child, childBounds);
        }
    }

    private float childrenHeight(IPencil pen){
        if(layout != null){
            return layout.minHeight(pen, children, childArgsList);
        }
        return 0;
    }

    public FloatProperty scroll(){
        return scroll;
    }

    public FloatProperty scrollSpeed(){
        return scrollSpeed;
    }
}
