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

//    private static class DefaultViewRenderer implements ISUIComponentRenderer<SUIView> {
//
//        @Override
//        public void render(SUIBounds bounds, IPencil pen, SUIView c) {
//            if (c.secondary != null) {
//                pen.setColor(c.secondary);
//                pen.fillRectangle(bounds);
//            }
//
//            if (c.primary != null) {
//                pen.setColor(GFXUtils.primaryOrDisabled(c));
//                pen.drawRectangle(bounds);
//            }
//        }
//    }

//    static {
//        GlobalSUIDefaults.setBaseDefault(SUIView.class, "renderer", new DefaultViewRenderer());
//        GlobalSUIDefaults.setBaseDefault(SUIView.class, "primary", null);
//        GlobalSUIDefaults.setBaseDefault(SUIView.class, "secondary", null);
//        GlobalSUIDefaults.setBaseDefault(SUIView.class, "disabledColor", null);
//        GlobalSUIDefaults.setBaseDefault(SUIView.class, "layout", new InheritLayout());
//    }

//    public static SUIView containing(SUIComponent child){
//        SUIView view = new SUIView(new InheritLayout());
//        view.add(child);
//        return view;
//    }

    protected final List<SUIComponent> children = new ArrayList<>();
    protected final List<Object> childArgsList = new ArrayList<>();
    protected final List<IEditableBounds> childBoundsList = new ArrayList<>();
    protected ILayout layout;

    private Float minWidth, minHeight;

    public SUIView(ILayout layout) {
        this.layout = layout;
    }

    public SUIView() {}

//    @Override
//    public void init(int windowID){
//        super.init(windowID);
//
//        for(int i=0;i<children.size();i++){
//            children.get(i).init(windowID);
//        }
//    }

    @Override
    public void render(IBounds bounds, IPencil pen) {
//        if(forceRedraw || GlobalComponentUtils.isDirty(this)) {
//            GlobalComponentUtils.setDirty(this, false);
//            renderSelf(bounds, pen);
//            if(layout != null) {
//                layout.updateBoundsForChildren(pen, bounds, children, childArgsList, childBoundsList);
//            }
//            forceRedraw = true;
//        }
//        else {
//            for(int i=0;i<children.size();i++){
//                if(GlobalComponentUtils.isDirty(children.get(i))){
//                    renderSelf(bounds, pen);
//                    forceRedraw = true;
//                    break;
//                }
//            }
//        }

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

//    @SuppressWarnings({"unchecked", "rawtypes"})
    protected void renderSelf(IBounds bounds, IPencil pen){
//        ISUIComponentRenderer renderer = GlobalWindowRegister.getRendererForComponent(this);
//        renderer.render(bounds, pen, this);

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
        if(minWidth == null){
            minWidth = layout.minWidth(pen, children, childArgsList);
        }

        return minWidth;
    }

    @Override
    public Float minHeight(IPencil pen) {
        if(minHeight == null){
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
    public void onKeyPressed(IBounds bounds, IKeyboard keyboard, int key) {
        callForAll((child, childBounds) -> child.onKeyPressed(childBounds, keyboard, key));
    }

    @Override
    public void onKeyReleased(IBounds bounds, IKeyboard keyboard, int key) {
        callForAll((child, childBounds) -> child.onKeyReleased(childBounds, keyboard, key));
    }

//    @Override
//    public void onWindowClosed() {
//        for(int i=0;i<children.size();i++){
//            children.get(i).onWindowClosed();
//        }
//    }

    private void callForAll(BiConsumer<SUIComponent, IBounds> childConsumer){
        for(int i=0;i<children.size();i++){
            SUIComponent child = children.get(i);
            IBounds childBounds = childBoundsList.get(i);
            if(childBounds == null) continue;
            childConsumer.accept(child, childBounds);
        }
    }

    public void add(SUIComponent child, Object args){
//        GlobalWindowRegister.initRegisterID(child, this);

        children.add(child);
        childArgsList.add(args);
        childBoundsList.add(new VarBounds(child));
        minWidth = null;
        minHeight = null;
//        requestRedraw();
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
//                requestRedraw();
                return true;
            }
        }

        return false;
    }

    public void setLayout(ILayout layout){
        this.layout = layout;
        minWidth = null;
        minHeight = null;
//        requestRedraw();
    }
    
}
