package com.programm.ge.saphire2d.ui.elements;

import com.programm.ge.saphire2d.core.IMouse;
import com.programm.ge.saphire2d.core.bounds.IBounds;
import com.programm.ge.saphire2d.core.bounds.IEditableBounds;
import com.programm.ge.saphire2d.core.bounds.VarBounds;
import com.programm.ge.saphire2d.reactivevalues.core.*;
import com.programm.ge.saphire2d.ui.IPencil;
import com.programm.ge.saphire2d.ui.elements.layout.VerticalLayout;
import com.programm.ge.saphire2d.ui.utils.Colors;
import com.programm.ge.saphire2d.ui.utils.GFXUtils;
import lombok.RequiredArgsConstructor;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class SUICombobox<T> extends AbstractTextComponent {

    private static class DefaultItemRenderer extends SUILabel {
        {
            primary = null;
            secondary = null;
        }
    }

    @RequiredArgsConstructor
    private static class ItemRenderProxy extends SUIButton {
        private final ITextComponent itemRenderer;

        @Override
        public void render(IBounds bounds, IPencil pen) {
            super.render(bounds, pen);
            itemRenderer.render(bounds, pen);
        }

        @Override
        public Float minWidth(IPencil pen) {
            return itemRenderer.minWidth(pen);
        }

        @Override
        public Float minHeight(IPencil pen) {
            return itemRenderer.minHeight(pen);
        }

        @Override
        public StringProperty text() {
            return itemRenderer.text();
        }

        @Override
        public ObjectProperty<Vector4f> textColor() {
            return itemRenderer.textColor();
        }

        @Override
        public FloatProperty fontSize() {
            return itemRenderer.fontSize();
        }

        @Override
        public IntProperty textAlign() {
            return itemRenderer.textAlign();
        }
    }



    protected final List<T> items = new ArrayList<>();
    private final SUIView selectionView;
    private final IEditableBounds selectionBounds;

    protected Supplier<ITextComponent> itemRenderer;
    private final BoolProperty itemsExpanded = new BoolPropertyValue();
    private final FloatProperty selectionMaxHeight = new FloatPropertyValue(100f);



    private final IntProperty selectedIndex = new IntPropertyValue();
    {
        selectedIndex.listenChange(i -> {
            if(i >= 0 && i < items.size()){
                T item = items.get(i);
                String text = Objects.toString(item);
                text().set(text);
            }
        });
    }


    public SUICombobox(List<T> items, int selectedIndex) {
        primary = Colors.BLACK;

        this.items.addAll(items);
        initRenderBounds(items);
        this.selectedIndex.set(selectedIndex);
        itemRenderer = DefaultItemRenderer::new;

        selectionView = new SUIScrollView();
        selectionView.setLayout(new VerticalLayout(VerticalLayout.POLICY_STRETCH, VerticalLayout.POLICY_INITIAL));

        selectionBounds = new VarBounds();
    }

    public SUICombobox(T[] items, int selectedIndex) {
        this(new ArrayList<>(List.of(items)), selectedIndex);
    }

    public SUICombobox(T[] items) {
        this(items, -1);
    }

    public SUICombobox() {
        this(new ArrayList<>(), -1);
    }

    @Override
    public void render(IBounds bounds, IPencil pen) {
        if(secondary != null){
            pen.fillRectangle(bounds, secondary);
        }

        if(primary != null){
            Vector4f color = GFXUtils.primaryOrDisabled(this);
            pen.drawRectangle(bounds, color);
        }

        SUILabel.renderText(this, bounds, pen);

        if(itemsExpanded.get()){
            float minHeight = Math.min(selectionMaxHeight.get(), selectionView.minHeight(pen));
            selectionBounds.bounds(bounds.x(), bounds.y() + bounds.height(), bounds.width(), minHeight);
            selectionView.render(selectionBounds, pen);
        }
    }

    private void initRenderBounds(List<T> items){
        for (T item : items) {
            addItem(item);
        }
    }

    @Override
    public Float minWidth(IPencil pen) {
        return selectionView.minWidth(pen);
    }

    @Override
    public Float minHeight(IPencil pen) {
        if(minHeight == null){
            minHeight = pen.stringHeight(fontSize().get());
        }

        return minHeight;
    }

    @Override
    public void onMousePressed(IBounds bounds, IMouse mouse, int button) {
        if(itemsExpanded.get()){
            selectionView.onMousePressed(selectionBounds, mouse, button);
        }

        if(button == IMouse.BUTTON_LEFT){
            if(mouse.inside(bounds)){
                itemsExpanded.invert();
            }
            else {
                itemsExpanded.set(false);
            }
        }
    }

    @Override
    public void onMouseReleased(IBounds bounds, IMouse mouse, int button) {
        selectionView.onMouseReleased(selectionBounds, mouse, button);
    }

    @Override
    public void onMouseMoved(IBounds bounds, IMouse mouse) {
        if(itemsExpanded.get()){
            selectionView.onMouseMoved(selectionBounds, mouse);
        }
    }

    @Override
    public void onMouseScrolled(IBounds bounds, IMouse mouse, float scrollX, float scrollY) {
        if(itemsExpanded.get()){
            selectionView.onMouseScrolled(selectionBounds, mouse, scrollX, scrollY);
        }
    }

    public void addItem(T item){
        items.add(item);
        ItemRenderProxy itemRenderer = new ItemRenderProxy(this.itemRenderer.get());
        String _item = Objects.toString(item);
        itemRenderer.text().set(_item);

        final int index = selectionView.getChildrenSize();
        itemRenderer.pressed().listenChange(p -> selectedIndex.set(index));

        selectionView.add(itemRenderer);
        minWidth = null;
        minHeight = null;
    }

    public void removeItem(T item){
        //TODO
    }

    public List<T> items(){
        return items;
    }

    public IntProperty selectedIndex(){
        return selectedIndex;
    }



    public T selectedItem(){
        return selectedIndex.get() == -1 ? null : items.get(selectedIndex.get());
    }

    public BoolProperty expanded(){
        return itemsExpanded;
    }

    public void itemRenderer(Supplier<ITextComponent> itemRenderer){
        this.itemRenderer = itemRenderer;
    }

    public Supplier<ITextComponent> itemRenderer(){
        return itemRenderer;
    }

    public FloatProperty selectionMaxHeight(){
        return selectionMaxHeight;
    }

}
