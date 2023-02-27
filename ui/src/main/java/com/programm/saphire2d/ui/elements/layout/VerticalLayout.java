package com.programm.saphire2d.ui.elements.layout;

import com.programm.ge.saphire2d.core.bounds.IBounds;
import com.programm.ge.saphire2d.core.bounds.IEditableBounds;
import com.programm.saphire2d.ui.GlobalComponentUtils;
import com.programm.saphire2d.ui.IPencil;
import com.programm.saphire2d.ui.SUIComponent;

import java.util.List;

public class VerticalLayout extends AbstractPolicyLayout {

    private float padding;

    public VerticalLayout() {
        super();
        this.padding = 0;
    }

    public VerticalLayout(int horizontalPolicy, int verticalPolicy) {
        super(horizontalPolicy, verticalPolicy, ALIGN_LEFT, ALIGN_TOP);
        this.padding = 0;
    }

    public VerticalLayout(int horizontalPolicy, int verticalPolicy, int horizontalAlign, int verticalAlign, int padding) {
        super(horizontalPolicy, verticalPolicy, horizontalAlign, verticalAlign);
        this.padding = padding;
    }

    @Override
    public void updateBoundsForChildren(IPencil pen, IBounds parent, List<SUIComponent> children, List<Object> childArgs, List<IEditableBounds> bounds) {
        switch (verticalPolicy){
            case POLICY_INITIAL:
                updateInitial(pen, parent, children, childArgs, bounds);
                break;
            case POLICY_STRETCH:
                updateStretch(pen, parent, children, childArgs, bounds, false);
                break;
            case POLICY_STRETCH_FORCE:
                updateStretch(pen, parent, children, childArgs, bounds, true);
                break;
            case POLICY_SPREAD:
                updateSpread(pen, parent, children, childArgs, bounds);
                break;
        }
    }

    private void updateInitial(IPencil pen, IBounds parent, List<SUIComponent> children, List<Object> childArgs, List<IEditableBounds> bounds) {
        Float[] childHeights = new Float[children.size()];

        float allChildHeight = 0;
        int elementsWithForceStretch = 0;

        for(int i=0;i<children.size();i++) {
            SUIComponent child = children.get(i);
            Object args = childArgs.get(i);

            if(args instanceof Integer && (((int)args) == POLICY_STRETCH || ((int)args) == POLICY_STRETCH_FORCE)){
                elementsWithForceStretch++;
                if(i != 0) allChildHeight += padding;
                continue;
            }

            if(i != 0) allChildHeight += padding;
            float childHeight = GlobalComponentUtils.getHeightConstrainedByMinMax(pen, child);
            childHeights[i] = childHeight;
            allChildHeight += childHeight;
        }

        float stretchHeight;

        if(elementsWithForceStretch == 0){
            stretchHeight = 0;
        }
        else {
            float parentHeightWithoutPadding = parent.height() - allChildHeight;
            stretchHeight = parentHeightWithoutPadding / elementsWithForceStretch;
        }

        if(elementsWithForceStretch != 0){
            allChildHeight = parent.height();
        }

        float curY = 0;
        switch(verticalAlign){
            case ALIGN_TOP:
                curY = parent.y();
                break;
            case ALIGN_BOTTOM:
                curY = parent.y() + parent.height() - allChildHeight;
                break;
            case ALIGN_CENTER:
                curY = parent.y() + parent.height() / 2f - allChildHeight / 2f;
                break;
        }

        for(int i=0;i<children.size();i++){
            SUIComponent child = children.get(i);
            Object args = childArgs.get(i);
            IEditableBounds childBounds = bounds.get(i);
            Float childHeight = childHeights[i];

            if(childHeight == null || (args instanceof Integer && (((int)args) == POLICY_STRETCH || ((int)args) == POLICY_STRETCH_FORCE))){
                childHeight = GlobalComponentUtils.getHeightConstrainedByMinMax(pen, child, stretchHeight);
            }

            updateBounds(pen, parent, child, childBounds, curY, childHeight);
            curY += childHeight + padding;
        }
    }

    private void updateStretch(IPencil pen, IBounds parent, List<SUIComponent> children, List<Object> childArgs, List<IEditableBounds> bounds, boolean force) {
        Float[] childHeights = new Float[children.size()];

        int elementsToStretch = 0;
        float fixedElementsHeight = 0;
        for(int i=0;i<children.size();i++){
            SUIComponent child = children.get(i);
            Object args = childArgs.get(i);
            if(force || GlobalComponentUtils.heightUntouched(child) || args instanceof Integer && (((int)args) == POLICY_STRETCH || ((int)args) == POLICY_STRETCH_FORCE)){
                elementsToStretch++;
            }
            else {
                float childHeight = GlobalComponentUtils.getHeightConstrainedByMinMax(pen, child);
                childHeights[i] = childHeight;
                fixedElementsHeight += childHeight;
            }
        }

        float remainingHeightForStretch = parent.height() - fixedElementsHeight - padding * (children.size() - 1);
        float stretchHeight;
        if(elementsToStretch == 0){
            stretchHeight = 0;
        }
        else {
            stretchHeight = remainingHeightForStretch / elementsToStretch;
        }

        float curY = parent.y();

        if(elementsToStretch == 0){
            switch(verticalAlign){
                case ALIGN_TOP:
                    curY = parent.y();
                    break;
                case ALIGN_BOTTOM:
                    curY = parent.y() + parent.height() - fixedElementsHeight;
                    break;
                case ALIGN_CENTER:
                    curY = parent.y() + parent.height() / 2f - fixedElementsHeight / 2f;
                    break;
            }
        }

        for(int i=0;i<children.size();i++){
            SUIComponent child = children.get(i);
            Object args = childArgs.get(i);
            IEditableBounds childBounds = bounds.get(i);
            Float childHeight = childHeights[i];

            if(childHeight == null || force || GlobalComponentUtils.heightUntouched(child) || args instanceof Integer && (((int)args) == POLICY_STRETCH || ((int)args) == POLICY_STRETCH_FORCE)){
                childHeight = GlobalComponentUtils.getHeightConstrainedByMinMax(pen, child, stretchHeight);
            }

            updateBounds(pen, parent, child, childBounds, curY, childHeight);
            curY += childHeight + padding;
        }
    }

    private void updateSpread(IPencil pen, IBounds parent, List<SUIComponent> children, List<Object> childArgs, List<IEditableBounds> bounds) {
        float[] childHeights = new float[children.size()];

        float allChildHeight = 0;
        for(int i=0;i<children.size();i++){
            SUIComponent child = children.get(i);
            Object args = childArgs.get(i);
            if(args instanceof Integer){
                if(((int)args) == POLICY_STRETCH || ((int)args) == POLICY_STRETCH_FORCE) {
                    updateStretch(pen, parent, children, childArgs, bounds, false);
                    return;
                }
            }

            float childHeight = GlobalComponentUtils.getHeightConstrainedByMinMax(pen, child);
            childHeights[i] = childHeight;
            allChildHeight += childHeight;
        }

        float curY = parent.y();
        float stepSize = (parent.height() - allChildHeight) / (children.size() - 1);

        for(int i=0;i<children.size();i++) {
            SUIComponent child = children.get(i);
            IEditableBounds childBounds = bounds.get(i);
            float childHeight = childHeights[i];

            updateBounds(pen, parent, child, childBounds, curY, childHeight);
            curY += childHeight + stepSize;
        }
    }

    private void updateBounds(IPencil pen, IBounds parent, SUIComponent child, IEditableBounds childBounds, float y, float height){
        float width = 0;
        switch(horizontalPolicy){
            case POLICY_INITIAL:
                width = GlobalComponentUtils.getWidthConstrainedByMinMax(pen, child);//child.width();
                break;
            case POLICY_STRETCH:
                if(GlobalComponentUtils.widthUntouched(child)){
                    width = GlobalComponentUtils.getWidthConstrainedByMinMax(pen, child, parent.width());//parent.width();
                }
                else {
                    width = GlobalComponentUtils.getWidthConstrainedByMinMax(pen, child);//child.width();
                }
                break;
            case POLICY_STRETCH_FORCE:
                width = GlobalComponentUtils.getWidthConstrainedByMinMax(pen, child, parent.width());//parent.width();
                break;
        }

        float x = 0;
        switch(horizontalAlign){
            case ALIGN_LEFT:
                x = parent.x();
                break;
            case ALIGN_RIGHT:
                x = parent.x() + parent.width() - width;
                break;
            case ALIGN_CENTER:
                x = parent.x() + parent.width() / 2f - width / 2f;
                break;
        }

        childBounds.bounds(x, y, width, height);
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
        float allMinHeight = 0;

        for(int i=0;i<children.size();i++){
            Float minHeight = children.get(i).minHeight(pen);
            if(minHeight != null) {
                allMinHeight += minHeight;
            }
        }

        return allMinHeight;
    }

    public void padding(float padding){
        this.padding = padding;
    }

    public float padding(){
        return padding;
    }
}
