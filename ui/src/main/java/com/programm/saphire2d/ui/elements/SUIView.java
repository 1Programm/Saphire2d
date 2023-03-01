package com.programm.saphire2d.ui.elements;

import com.programm.ge.saphire2d.core.IKeyboard;
import com.programm.ge.saphire2d.core.IMouse;
import com.programm.ge.saphire2d.core.bounds.IBounds;
import com.programm.ge.saphire2d.core.bounds.IEditableBounds;
import com.programm.ge.saphire2d.core.bounds.VarBounds;
import com.programm.saphire2d.ui.IPencil;
import com.programm.saphire2d.ui.SUIComponent;
import com.programm.saphire2d.ui.elements.layout.ILayout;
import com.programm.saphire2d.ui.utils.GFXUtils;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class SUIView extends SUIComponent {

    protected final List<SUIComponent> children = new ArrayList<>();
    protected final List<Object> childArgsList = new ArrayList<>();
    protected final List<IEditableBounds> childBoundsList = new ArrayList<>();
    protected ILayout layout;

    private Float minWidth, minHeight;

    public SUIView(ILayout layout) {
        this();
        this.layout = layout;
    }

    public SUIView() {
        primary = null;
        secondary = null;
        disabledColor = null;
    }

    @Override
    public void render(IBounds bounds, IPencil pen) {
        renderSelf(bounds, pen);
        if(layout != null) {
            layout.updateBoundsForChildren(pen, bounds, children, childArgsList, childBoundsList);
        }
        else {
            if(children.size() == 1){
                childBoundsList.get(0).bounds(bounds);
            }
        }

        for(int i=0;i<children.size();i++){
            SUIComponent child = children.get(i);
            IEditableBounds childBounds = childBoundsList.get(i);
            if(childBounds != null) {
                child.render(childBounds, pen);
            }
        }
    }

    protected void renderSelf(IBounds bounds, IPencil pen){
            if (secondary != null) {
                pen.fillRectangle(bounds, secondary);
            }

            if (primary != null) {
                Vector4f color = GFXUtils.primaryOrDisabled(this);
                pen.drawRectangle(bounds, color);
            }
    }

    @Override
    public Float minWidth(IPencil pen) {
        if(layout != null && minWidth == null){
            minWidth = layout.minWidth(pen, children, childArgsList);
        }

        return minWidth;
    }

    @Override
    public Float minHeight(IPencil pen) {
        if(layout != null && minHeight == null){
            minHeight = layout.minHeight(pen, children, childArgsList);
        }

        return minHeight;
    }

    @Override
    public void onMousePressed(IBounds bounds, IMouse mouse, int button) {
        callForAll((child, childBounds) -> child.onMousePressed(childBounds, mouse, button));
    }

    @Override
    public void onMouseReleased(IBounds bounds, IMouse mouse, int button) {
        callForAll((child, childBounds) -> child.onMouseReleased(childBounds, mouse, button));
    }

    @Override
    public void onMouseMoved(IBounds bounds, IMouse mouse) {
        callForAll((child, childBounds) -> child.onMouseMoved(childBounds, mouse));
    }

    @Override
    public void onMouseDragged(IBounds bounds, IMouse mouse, int button) {
        callForAll((child, childBounds) -> child.onMouseDragged(childBounds, mouse, button));
    }

    @Override
    public void onMouseScrolled(IBounds bounds, IMouse mouse, float scrollX, float scrollY) {
        callForAll((child, childBounds) -> child.onMouseScrolled(childBounds, mouse, scrollX, scrollY));
    }

    @Override
    public void onKeyPressed(IBounds bounds, IKeyboard keyboard, int key) {
        callForAll((child, childBounds) -> child.onKeyPressed(childBounds, keyboard, key));
    }

    @Override
    public void onKeyReleased(IBounds bounds, IKeyboard keyboard, int key) {
        callForAll((child, childBounds) -> child.onKeyReleased(childBounds, keyboard, key));
    }

    private void callForAll(BiConsumer<SUIComponent, IBounds> childConsumer){
        for(int i=0;i<children.size();i++){
            SUIComponent child = children.get(i);
            IBounds childBounds = childBoundsList.get(i);
            if(childBounds == null) continue;
            childConsumer.accept(child, childBounds);
        }
    }

    public void add(SUIComponent child, Object args){
        children.add(child);
        childArgsList.add(args);
        childBoundsList.add(new VarBounds(child));
        minWidth = null;
        minHeight = null;
    }

    public void add(SUIComponent child){
        add(child, null);
    }

    public boolean remove(SUIComponent child){
        for(int i=0;i<children.size();i++){
            if(children.get(i) == child){
                childArgsList.remove(i);
                childBoundsList.remove(i);
                minWidth = null;
                minHeight = null;
                return true;
            }
        }

        return false;
    }

    public void setLayout(ILayout layout){
        this.layout = layout;
        minWidth = null;
        minHeight = null;
    }
    
}
