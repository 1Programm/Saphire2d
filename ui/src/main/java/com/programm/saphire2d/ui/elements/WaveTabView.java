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
        super(new VerticalLayout(VerticalLayout.POLICY_STRETCH_FORCE, VerticalLayout.POLICY_INITIAL));

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
        if(tab < 0 || tab >= tabViews.size()) return;

        if(selectedTab != -1 && selectedTab != tab) {
            SUIButtonToggle btn = tabButtons.get(selectedTab);
            btn.toggle(false);
//            btn.secondary(GlobalWaveDefaults.getDefault(SUIButton.class, "secondary"));
        }

//        tabButtons.get(tab).secondary(GlobalWaveDefaults.getDefault(SUIButton.class, "hoverColor"));
        tabButtons.get(tab).toggle(true);
        selectedTab = tab;

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
    }

    @Override
    public void add(SUIComponent child, Object args) {
        String name = Objects.toString(args);

        SUIButtonToggle button = new SUIButtonToggle(name);
        final int index = tabButtons.size();
        button.listenPressed(() -> selectTab(index));
        button.size(120, 0);

        tabButtons.add(button);
        tabLabelView.add(button);
        tabViews.add(child);

//        if(selectedTab == -1) selectedTab = 0;
        if(selectedTab == -1) selectTab(0);
    }

    public void horizontalTabNamePolicy(int horizontalPolicy){
        if(horizontalPolicy == HorizontalLayout.POLICY_STRETCH) horizontalPolicy = HorizontalLayout.POLICY_STRETCH_FORCE;
        tablLabelViewLayout.horizontalPolicy(horizontalPolicy);
    }
}
