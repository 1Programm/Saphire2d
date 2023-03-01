package com.programm.saphire2d.ui.elements;

import com.programm.ge.saphire2d.core.IKeyboard;
import com.programm.ge.saphire2d.core.IMouse;
import com.programm.ge.saphire2d.core.bounds.IBounds;
import com.programm.ge.saphire2d.core.bounds.IEditableBounds;
import com.programm.ge.saphire2d.core.bounds.VarBounds;
import com.programm.saphire2d.ui.IPencil;
import com.programm.saphire2d.ui.SUIComponent;

import java.util.function.BiConsumer;

public class SUIScrollView extends SUIView {

    private static final float SCROLL_SPEED = -1f;

    private float scroll;

    private final IEditableBounds scrolledBounds = new VarBounds(){
        @Override
        public float y() {
            return super.y() + scroll;
        }
    };


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

        pen.pushClipping(bounds);
        for(int i=0;i<children.size();i++){
            SUIComponent child = children.get(i);
            IEditableBounds childBounds = childBoundsList.get(i);
            if(childBounds != null) {
                child.render(childBounds, pen);
            }
        }
        pen.popClipping();
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
        scroll += scrollY * SCROLL_SPEED;

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
}
