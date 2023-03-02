package com.programm.ge.saphire2d.ui.elements.layout;

import com.programm.ge.saphire2d.core.bounds.IBounds;
import com.programm.ge.saphire2d.core.bounds.IEditableBounds;
import com.programm.ge.saphire2d.ui.GlobalComponentUtils;
import com.programm.ge.saphire2d.ui.IPencil;
import com.programm.ge.saphire2d.ui.SUIComponent;

import java.util.List;

public class InheritLayout implements ILayout {

    @Override
    public void updateBoundsForChildren(IPencil pen, IBounds parent, List<SUIComponent> children, List<Object> childArgs, List<IEditableBounds> bounds) {
        float midX = parent.x() + parent.width() / 2f;
        float midY = parent.y() + parent.height() / 2f;

        for(int i=0;i<children.size();i++){
            SUIComponent child = children.get(i);
            IEditableBounds childBounds = bounds.get(i);

            float width = GlobalComponentUtils.getWidthConstrainedByMinMax(pen, child);
            float height = GlobalComponentUtils.getHeightConstrainedByMinMax(pen, child);
            float x = GlobalComponentUtils.xUntouched(child) ? (midX - width / 2f) : parent.x() + child.x();
            float y = GlobalComponentUtils.yUntouched(child) ? (midY - height / 2f) : parent.y() + child.y();

            childBounds.bounds(x, y, width, height);
        }
    }

    @Override
    public float minWidth(IPencil pen, List<SUIComponent> children, List<Object> childArgs) {
        Float maxMinWidth = null;

        for(int i=0;i<children.size();i++){
            Float minWidth = children.get(i).minWidth(pen);
            if(maxMinWidth == null || (minWidth != null && maxMinWidth < minWidth)){
                maxMinWidth = minWidth;
            }
        }

        return maxMinWidth == null ? 0 : maxMinWidth;
    }

    @Override
    public float minHeight(IPencil pen, List<SUIComponent> children, List<Object> childArgs) {
        Float maxMinHeight = null;

        for(int i=0;i<children.size();i++){
            Float minHeight = children.get(i).minHeight(pen);
            if(maxMinHeight == null || (minHeight != null && maxMinHeight < minHeight)){
                maxMinHeight = minHeight;
            }
        }

        return maxMinHeight == null ? 0 : maxMinHeight;
    }
}
