package com.programm.saphire2d.ui.elements.layout;

import com.programm.ge.saphire2d.core.bounds.IBounds;
import com.programm.ge.saphire2d.core.bounds.IEditableBounds;
import com.programm.saphire2d.ui.GlobalComponentUtils;
import com.programm.saphire2d.ui.IPencil;
import com.programm.saphire2d.ui.SUIComponent;

import java.util.List;

public class HorizontalLayout extends AbstractPolicyLayout {

    private float padding;

    public HorizontalLayout() {
        super();
        this.padding = 2;
    }

    public HorizontalLayout(int horizontalPolicy, int verticalPolicy) {
        super(horizontalPolicy, verticalPolicy, ALIGN_LEFT, ALIGN_TOP);
        this.padding = 0;
    }

    public HorizontalLayout(int horizontalPolicy, int verticalPolicy, int horizontalAlign, int verticalAlign, int padding) {
        super(horizontalPolicy, verticalPolicy, horizontalAlign, verticalAlign);
        this.padding = padding;
    }

    @Override
    public void updateBoundsForChildren(IPencil pen, IBounds parent, List<SUIComponent> children, List<Object> childArgs, List<IEditableBounds> bounds) {
        switch (horizontalPolicy){
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
        Float[] childWidths = new Float[children.size()];

        float allChildWidth = 0;
        int elementsWithForceStretch = 0;

        for(int i=0;i<children.size();i++) {
            SUIComponent child = children.get(i);
            Object args = childArgs.get(i);

            if(args instanceof Integer && (((int)args) == POLICY_STRETCH || ((int)args) == POLICY_STRETCH_FORCE)){
                elementsWithForceStretch++;
                if(i != 0) allChildWidth += padding;
                continue;
            }

            if(i != 0) allChildWidth += padding;
            float childWidth = GlobalComponentUtils.getWidthConstrainedByMinMax(pen, child);
            childWidths[i] = childWidth;
            allChildWidth += childWidth;
        }

        float stretchWidth;
        if(elementsWithForceStretch == 0){
            stretchWidth = 0;
        }
        else {
            float parentWidthWithoutPadding = parent.width() - allChildWidth;
            stretchWidth = parentWidthWithoutPadding / elementsWithForceStretch;
        }

        if(elementsWithForceStretch != 0){
            allChildWidth = parent.width();
        }

        float curX = 0;
        switch(horizontalAlign){
            case ALIGN_LEFT:
                curX = parent.x();
                break;
            case ALIGN_RIGHT:
                curX = parent.x() + parent.width() - allChildWidth;
                break;
            case ALIGN_CENTER:
                curX = parent.x() + parent.width() / 2f - allChildWidth / 2f;
                break;
        }

        for(int i=0;i<children.size();i++){
            SUIComponent child = children.get(i);
            Object args = childArgs.get(i);
            IEditableBounds childBounds = bounds.get(i);
            Float childWidth = childWidths[i];

            if(childWidth == null || (args instanceof Integer && (((int)args) == POLICY_STRETCH || ((int)args) == POLICY_STRETCH_FORCE))){
                childWidth = GlobalComponentUtils.getWidthConstrainedByMinMax(pen, child, stretchWidth);
            }

            updateBounds(pen, parent, child, childBounds, curX, childWidth);
            curX += childWidth + padding;
        }
    }

    private void updateStretch(IPencil pen, IBounds parent, List<SUIComponent> children, List<Object> childArgs, List<IEditableBounds> bounds, boolean force) {
        Float[] childWidths = new Float[children.size()];

        int elementsToStretch = 0;
        float fixedElementsWidth = 0;
        for(int i=0;i<children.size();i++){
            SUIComponent child = children.get(i);
            Object args = childArgs.get(i);
            if(force || GlobalComponentUtils.widthUntouched(child) || args instanceof Integer && (((int)args) == POLICY_STRETCH || ((int)args) == POLICY_STRETCH_FORCE)){
                elementsToStretch++;
            }
            else {
                float childWidth = GlobalComponentUtils.getWidthConstrainedByMinMax(pen, child);
                childWidths[i] = childWidth;
                fixedElementsWidth += childWidth;
            }
        }

        float stretchWidth;
        if(elementsToStretch == 0){
            stretchWidth = 0;
        }
        else {
            float remainingWidthForStretch = parent.width() - fixedElementsWidth - padding * (children.size() - 1);
            stretchWidth = remainingWidthForStretch / elementsToStretch;
        }

        float curX = parent.x();
        if(elementsToStretch == 0){
            switch(horizontalAlign){
                case ALIGN_LEFT:
                    curX = parent.x();
                    break;
                case ALIGN_RIGHT:
                    curX = parent.x() + parent.width() - fixedElementsWidth;
                    break;
                case ALIGN_CENTER:
                    curX = parent.x() + parent.width() / 2f - fixedElementsWidth / 2f;
                    break;
            }
        }

        for(int i=0;i<children.size();i++){
            SUIComponent child = children.get(i);
            Object args = childArgs.get(i);
            IEditableBounds childBounds = bounds.get(i);
            Float childWidth = childWidths[i];

            if(childWidth == null || force || GlobalComponentUtils.widthUntouched(child) || args instanceof Integer && (((int)args) == POLICY_STRETCH || ((int)args) == POLICY_STRETCH_FORCE)){
                childWidth = GlobalComponentUtils.getWidthConstrainedByMinMax(pen, child, stretchWidth);
            }

            updateBounds(pen, parent, child, childBounds, curX, childWidth);
            curX += childWidth + padding;
        }
    }

    private void updateSpread(IPencil pen, IBounds parent, List<SUIComponent> children, List<Object> childArgs, List<IEditableBounds> bounds) {
        float[] childWidths = new float[children.size()];

        float allChildWidth = 0;
        for(int i=0;i<children.size();i++){
            SUIComponent child = children.get(i);
            Object args = childArgs.get(i);
            if(args instanceof Integer){
                if(((int)args) == POLICY_STRETCH || ((int)args) == POLICY_STRETCH_FORCE) {
                    updateStretch(pen, parent, children, childArgs, bounds, false);
                    return;
                }
            }

            float childWidth = GlobalComponentUtils.getWidthConstrainedByMinMax(pen, child);
            childWidths[i] = childWidth;
            allChildWidth += childWidth;
        }



        float curX = parent.x();
        float stepSize = (parent.width() - allChildWidth) / (children.size() - 1);

        for(int i=0;i<children.size();i++) {
            SUIComponent child = children.get(i);
            IEditableBounds childBounds = bounds.get(i);
            float childWidth = childWidths[i];

            updateBounds(pen, parent, child, childBounds, curX, childWidth);
            curX += childWidth + stepSize;
        }
    }

    private void updateBounds(IPencil pen, IBounds parent, SUIComponent child, IEditableBounds childBounds, float x, float width){
        float height = 0;
        switch(verticalPolicy){
            case POLICY_INITIAL:
                height = GlobalComponentUtils.getHeightConstrainedByMinMax(pen, child);
                break;
            case POLICY_STRETCH:
                if(GlobalComponentUtils.heightUntouched(child)){
                    height = GlobalComponentUtils.getHeightConstrainedByMinMax(pen, child, parent.height());
                }
                else {
                    height = GlobalComponentUtils.getHeightConstrainedByMinMax(pen, child);
                }
                break;
            case POLICY_STRETCH_FORCE:
                height = GlobalComponentUtils.getHeightConstrainedByMinMax(pen, child, parent.height());
                break;
        }

        float y = 0;
        switch(verticalAlign){
            case ALIGN_TOP:
                y = parent.y();
                break;
            case ALIGN_BOTTOM:
                y = parent.y() + parent.height() - height;
                break;
            case ALIGN_CENTER:
                y = parent.y() + parent.height() / 2f - height / 2f;
                break;
        }

        childBounds.bounds(x, y, width, height);
    }

    @Override
    public float minWidth(IPencil pen, List<SUIComponent> children, List<Object> childArgs) {
        float allMinWidth = 0;

        for(int i=0;i<children.size();i++){
            Float minWidth = children.get(i).minWidth(pen);
            if(minWidth != null) {
                allMinWidth += minWidth;
            }
        }

        return allMinWidth;
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

    public void padding(float padding){
        this.padding = padding;
    }

    public float padding(){
        return padding;
    }
}
