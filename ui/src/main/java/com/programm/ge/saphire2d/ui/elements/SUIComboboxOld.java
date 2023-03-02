package com.programm.ge.saphire2d.ui.elements;

import com.programm.ge.saphire2d.core.IMouse;
import com.programm.ge.saphire2d.core.bounds.IBounds;
import com.programm.ge.saphire2d.core.bounds.IEditableBounds;
import com.programm.ge.saphire2d.core.bounds.VarBounds;
import com.programm.ge.saphire2d.ui.IPencil;
import com.programm.ge.saphire2d.ui.subscription.RunnableSubscriptionManager;
import com.programm.ge.saphire2d.ui.subscription.Subscription;
import com.programm.ge.saphire2d.ui.utils.Colors;
import com.programm.ge.saphire2d.ui.utils.GFXUtils;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SUIComboboxOld<T> extends AbstractTextComponent {

    private static class DefaultItemRenderer extends SUILabel {
        {
//            primary = Colors.BLACK;
            secondary = null;
        }
    }

//    static {
//        GlobalWaveDefaults.setBaseDefault(WaveCombobox.class, "renderer", new DefaultComboboxRenderer());
//        GlobalWaveDefaults.setBaseDefault(WaveCombobox.class, "primary", Color.BLACK);
//        GlobalWaveDefaults.setBaseDefault(WaveCombobox.class, "secondary", Color.WHITE);
//        GlobalWaveDefaults.setBaseDefault(WaveCombobox.class, "disabledColor", Color.LIGHT_GRAY);
//        GlobalWaveDefaults.setBaseDefault(WaveCombobox.class, "itemRenderer", new DefaultItemRenderer());
//    }

    protected final RunnableSubscriptionManager slectionChangeManager = new RunnableSubscriptionManager();

    protected final List<T> items;
    protected List<IEditableBounds> itemRenderBounds;

    protected int selectedIndex;
    protected int hoveredIndex;
    protected ITextComponent itemRenderer;
    protected boolean itemsExpanded;

    public SUIComboboxOld(List<T> items, int selectedIndex) {
        this.items = items;
        this.itemRenderBounds = initRenderBounds(items.size());
        this.selectedIndex = selectedIndex;

        primary = Colors.BLACK;
        itemRenderer = new DefaultItemRenderer();
    }

    public SUIComboboxOld(T[] items, int selectedIndex) {
        this(new ArrayList<>(List.of(items)), selectedIndex);
    }

    public SUIComboboxOld(T[] items) {
        this(items, -1);
    }

    public SUIComboboxOld() {
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

        if(itemsExpanded){
//            float curY = bounds.y() + bounds.height();
            float itemHeight = minHeight(pen);
            float itemX = bounds.x();
            float itemWidth = bounds.width();
            float allItemsHeight = itemHeight * (items.size() - 1) + bounds.height();

            float startY = bounds.y();
            if(selectedIndex != -1){
                startY -= itemHeight * selectedIndex;
            }
            else {
                startY += bounds.height();
            }

            float curY = startY;

            if(secondary != null) {
                pen.fillRectangle(itemX, startY, itemWidth, allItemsHeight, secondary);
            }

            for(int i=0;i<items.size();i++){
                Object item = items.get(i);
                String _item = Objects.toString(item);
                IEditableBounds renderBounds = itemRenderBounds.get(i);

                renderBounds.bounds(itemX, curY, itemWidth, itemHeight);

                if(hoveredIndex == i) {
//                    pen.setColor(Color.LIGHT_GRAY);
                    pen.fillRectangle(renderBounds, Colors.LIGHT_GRAY);
                }

                itemRenderer.text().set(_item);
                itemRenderer.render(renderBounds, pen);

                curY += itemHeight;
            }
        }
    }

    private List<IEditableBounds> initRenderBounds(int size){
        List<IEditableBounds> renderBounds = new ArrayList<>();

        for(int i=0;i<size;i++){
            renderBounds.add(new VarBounds());
        }

        return renderBounds;
    }

    @Override
    public Float minWidth(IPencil pen) {
        if(minWidth == null){
            for(int i=0;i<items.size();i++) {
                String _item = items.get(i).toString();
                float itemMinWidth = pen.stringWidth(_item, fontSize().get());
                if(minWidth == null){
                    minWidth = itemMinWidth;
                }
                else {
                    minWidth = Math.max(minWidth, itemMinWidth);
                }
            }

            if(minWidth != null){
                minWidth += 4;
            }
        }

        return minWidth;
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
        if(itemsExpanded){
            for(int i=0;i<items.size();i++){
                IBounds renderBounds = itemRenderBounds.get(i);

                if(mouse.inside(renderBounds)){
                    selectedIndex(i);
                    itemsExpanded = false;
                    return;
                }
            }
        }

        if(button == IMouse.BUTTON_LEFT) {
            if(itemsExpanded){
                itemsExpanded = false;
                hoveredIndex = -1;
            }
            else {
                if(mouse.inside(bounds)) {
                    itemsExpanded = true;
                    hoveredIndex = selectedIndex;
                }
            }
        }
    }

    @Override
    public void onMouseMoved(IBounds bounds, IMouse mouse) {
        if(itemsExpanded){
            hoveredIndex = -1;
            for(int i=0;i<items.size();i++){
                IBounds renderBounds = itemRenderBounds.get(i);
                if(mouse.inside(renderBounds)){
                    hoveredIndex = i;
                }
            }
        }
    }

//    @Override
//    public void text(String text) {
//        super.text(text);
//
//        selectedIndex = -1;
//        for(int i=0;i<items.size();i++){
//            String _item = Objects.toString(items.get(i));
//            if(text.equals(_item)){
//                selectedIndex = i;
//                break;
//            }
//        }
//    }

    public void addItem(T item){
        items.add(item);
        itemRenderBounds.add(new VarBounds());
        minWidth = null;
        minHeight = null;
    }

    public List<T> items(){
        return items;
    }

    public List<IEditableBounds> itemRenderBounds(){
        return itemRenderBounds;
    }

    public void selectedIndex(int selectedIndex){
        this.selectedIndex = selectedIndex;
        if(selectedIndex != -1){
            String text = Objects.toString(selectedItem());
            text().set(text);
        }

        slectionChangeManager.notifyChange();
    }

    public int selectedIndex(){
        return selectedIndex;
    }

    public T selectedItem(){
        return selectedIndex == -1 ? null : items.get(selectedIndex);
    }

    public int hoveredIndex(){
        return hoveredIndex;
    }

    public Subscription listenSeletionChange(Runnable listener){
        return slectionChangeManager.subscribe(listener);
    }

    public void itemsExpanded(boolean itemsExpanded){
        this.itemsExpanded = itemsExpanded;
    }

    public boolean itemsExpanded(){
        return itemsExpanded;
    }

    public void itemRenderer(AbstractTextComponent itemRenderer){
        this.itemRenderer = itemRenderer;
    }

    public ITextComponent itemRenderer(){
        return itemRenderer;
    }

}
