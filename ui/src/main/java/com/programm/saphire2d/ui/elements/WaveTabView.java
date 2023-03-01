package com.programm.saphire2d.ui.elements;

import com.programm.ge.saphire2d.core.bounds.IBounds;
import com.programm.ge.saphire2d.core.bounds.IEditableBounds;
import com.programm.ge.saphire2d.core.bounds.VarBounds;
import com.programm.saphire2d.ui.IPencil;
import com.programm.saphire2d.ui.SUIComponent;
import com.programm.saphire2d.ui.elements.layout.HorizontalLayout;
import com.programm.saphire2d.ui.elements.layout.VerticalLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WaveTabView extends SUIView {

    protected final HorizontalLayout tablLabelViewLayout;
    protected final SUIView tabLabelView;
    protected final List<SUIButtonToggle> tabButtons = new ArrayList<>();
    protected final List<SUIComponent> tabViews = new ArrayList<>();

    protected int selectedTab = -1;

    public WaveTabView() {
        super(new VerticalLayout(VerticalLayout.POLICY_STRETCH_FORCE, VerticalLayout.POLICY_INITIAL, VerticalLayout.ALIGN_LEFT, VerticalLayout.ALIGN_TOP, 0));

        tablLabelViewLayout = new HorizontalLayout(HorizontalLayout.POLICY_INITIAL, HorizontalLayout.POLICY_STRETCH_FORCE);
        tabLabelView = new SUIView(tablLabelViewLayout);
        tabLabelView.size(0, 30);

        children.add(tabLabelView);

        childArgsList.add(null);
        childArgsList.add(VerticalLayout.POLICY_STRETCH_FORCE);

        childBoundsList.add(new VarBounds());
        childBoundsList.add(new VarBounds());
    }

    public void selectTab(int tab){
        selectTab(tab, true);
    }

    public void selectTab(int tab, boolean state){
        if(tab < 0 || tab >= tabViews.size()) return;

        if(!state) {
            if(tab == selectedTab) {
                SUIButtonToggle btn = tabButtons.get(selectedTab);
                btn.toggle(true);
            }
            return;
        }
        else if(tab == selectedTab) {
            return;
        }


        if(selectedTab != -1) {
            SUIButtonToggle btn = tabButtons.get(selectedTab);

            selectedTab = tab;
            btn.toggle(false);
        }
        selectedTab = tab;


        tabButtons.get(tab).toggle(true);
//        selectedTab = tab;

        SUIComponent selectedView = tabViews.get(tab);
        if(children.size() == 1){
            children.add(selectedView);
        }
        else {
            children.set(1, selectedView);
        }
    }

    @Override
    public void render(IBounds bounds, IPencil pen) {
        renderSelf(bounds, pen);
        if(layout != null) {
            layout.updateBoundsForChildren(pen, bounds, children, childArgsList, childBoundsList);
        }

        for(int i=0;i<children.size();i++){
            SUIComponent child = children.get(i);
            IEditableBounds childBounds = childBoundsList.get(i);
            if(childBounds != null) {
                child.render(childBounds, pen);
            }
        }

//        SUIComponent child = children.get(0);
//        IEditableBounds childBounds = childBoundsList.get(0);
//        if(childBounds != null) {
//            child.render(childBounds, pen);
//
//            if (primary != null) {
//                float x = childBounds.x();
//                float y = childBounds.y() + childBounds.height() + 1;
//                float w = childBounds.width();
//                Vector4f color = GFXUtils.primaryOrDisabled(this);
//                pen.drawLine(x, y, x + w, y, color);
//            }
//        }
//
//        if(children.size() == 2){
//            child = children.get(1);
//            childBounds = childBoundsList.get(1);
//            if(childBounds != null) {
//                child.render(childBounds, pen);
//            }
//        }
    }

    @Override
    public void add(SUIComponent child, Object args) {
        String name = Objects.toString(args);

        SUIButtonToggle button = new SUIButtonToggle(name);
        final int index = tabButtons.size();
        button.listenToggle((t) -> selectTab(index, t));
        button.size(100, 0);

        tabButtons.add(button);
        tabLabelView.add(button);
        tabViews.add(child);

        if(selectedTab == -1) selectTab(0);
    }

    public void horizontalTabNamePolicy(int horizontalPolicy){
        if(horizontalPolicy == HorizontalLayout.POLICY_STRETCH) horizontalPolicy = HorizontalLayout.POLICY_STRETCH_FORCE;
        tablLabelViewLayout.horizontalPolicy(horizontalPolicy);
    }
}
