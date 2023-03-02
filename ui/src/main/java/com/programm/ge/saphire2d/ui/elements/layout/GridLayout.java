package com.programm.ge.saphire2d.ui.elements.layout;

import com.programm.ge.saphire2d.core.bounds.IBounds;
import com.programm.ge.saphire2d.core.bounds.IEditableBounds;
import com.programm.ge.saphire2d.core.bounds.VarBounds;
import com.programm.ge.saphire2d.ui.IPencil;
import com.programm.ge.saphire2d.ui.SUIComponent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GridLayout implements ILayout {

    public static final int OVERFLOW_GROW_HORIZONTAL = 0;
    public static final int OVERFLOW_GROW_VERTICAL = 1;
    public static final int OVERFLOW_HIDE = 2;

    private int gridWidth, gridHeight;
    private int overflowPolicy;

    private final Map<Integer, Float> rowWeights = new HashMap<>();
    private final Map<Integer, Float> colWeights = new HashMap<>();

    public GridLayout(int gridWidth, int gridHeight, int overflowPolicy) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.overflowPolicy = overflowPolicy;
    }

    public GridLayout(int gridWidth, int gridHeight) {
        this(gridWidth, gridHeight, OVERFLOW_GROW_VERTICAL);
    }

    @Override
    public void updateBoundsForChildren(IPencil pen, IBounds parent, List<SUIComponent> children, List<Object> childArgs, List<IEditableBounds> bounds) {
        int numChildren = children.size();
        int gridWidth = this.gridWidth;
        int gridHeight = this.gridHeight;

        if(numChildren > gridWidth * gridHeight){
            int numMissing = numChildren - gridWidth * gridHeight;

            switch (overflowPolicy){
                case OVERFLOW_GROW_HORIZONTAL:
                    int missingColumns = (int) Math.ceil(((float)numMissing) / gridHeight);
                    gridWidth += missingColumns;
                    break;
                case OVERFLOW_GROW_VERTICAL:
                    int missingRows = (int) Math.ceil(((float)numMissing) / gridWidth);
                    gridHeight += missingRows;
                    break;
                case OVERFLOW_HIDE:
                    break;
            }
        }

        Float[] actualWidths = calculateWidthsForRows(pen, children, childArgs, gridWidth, gridHeight, parent.width());
        Float[] actualHeights = calculateHeightsForCols(pen, children, childArgs, gridWidth, gridHeight, parent.height());


        Integer[][] idGrid = new Integer[gridHeight][gridWidth];
        boolean[] skipChilds = new boolean[numChildren];

        for(int i=0;i<children.size();i++){
            Object args = childArgs.get(i);
            if(skipChilds[i]) continue;

            int pos = i;
            if(args instanceof Integer){
                int _pos = (int) args;
                if(_pos != pos) {
                    pos = _pos;

                    if(pos >= gridWidth * gridHeight){
                        //override cur child bounds
                        bounds.set(i, null);
                        continue;
                    }

                    if(pos < numChildren) {
                        //override future child
                        bounds.set(pos, null);
                        skipChilds[pos] = true;
                    }
                }
            }
            else if(pos >= gridWidth * gridHeight){
                bounds.set(i, null);
                continue;
            }

            int x = pos % gridWidth;
            int y = pos / gridWidth;

            idGrid[y][x] = i;
        }

        float curY = parent.y();
        for(int y=0;y<gridHeight;y++){
            float cellHeight = actualHeights[y];
            float curX = parent.x();

            for(int x=0;x<gridWidth;x++){
                Integer id = idGrid[y][x];
                float cellWidth = actualWidths[x];

                if(id != null) {
                    IEditableBounds childBounds = bounds.get(id);

                    if(childBounds == null){
                        bounds.set(id, new VarBounds(curX, curY, cellWidth, cellHeight));
                    }
                    else {
                        childBounds.bounds(curX, curY, cellWidth, cellHeight);
                    }
                }

                curX += cellWidth;
            }

            curY += cellHeight;
        }
    }

    private Float[] calculateWidthsForRows(IPencil pen, List<SUIComponent> children, List<Object> childArgs, int gridWidth, int gridHeight, float parentWidth){
        float[] minWidths = new float[gridWidth];
        for(int i=0;i<gridWidth;i++){
            minWidths[i] = maxMinCellWidthForRow(pen, children, childArgs, i, gridWidth, gridHeight);
        }

        Float[] rowWidths = new Float[gridWidth];

        float remainingWidth = parentWidth;
        int remainingChildren = gridWidth;
        float remainigCellWidth = remainingWidth / remainingChildren;

        for(int x=0;x<gridWidth;x++){
            if(rowWidths[x] != null) continue;

            float minWidth = minWidths[x];
            Float rowWeight = rowWeights.get(x);
            if(rowWeight != null){
                float weightedWidth = rowWeight * parentWidth;
                minWidth = Math.max(minWidth, weightedWidth);
            }

            if(minWidth > remainigCellWidth){
                rowWidths[x] = minWidth;
                remainingWidth -= minWidth;
                remainingChildren--;
                remainigCellWidth = remainingWidth / remainingChildren;
                x = -1;
            }
        }

        for(int i=0;i<gridWidth;i++){
            if(rowWidths[i] == null) {
                rowWidths[i] = remainigCellWidth;
            }
        }

        return rowWidths;
    }

    private Float[] calculateHeightsForCols(IPencil pen, List<SUIComponent> children, List<Object> childArgs, int gridWidth, int gridHeight, float parentHeight){
        float[] minHeights = new float[gridHeight];
        for(int i=0;i<gridHeight;i++){
            minHeights[i] = maxMinCellHeightForColumn(pen, children, childArgs, i, gridWidth, gridHeight);
        }

        Float[] rowHeights = new Float[gridHeight];

        float remainingHeight = parentHeight;
        int remainingChildren = gridHeight;
        float remainigCellHeight = remainingHeight / remainingChildren;

        for(int y=0;y<gridHeight;y++){
            if(rowHeights[y] != null) continue;

            float minHeight = minHeights[y];
            Float colWeight = colWeights.get(y);
            if(colWeight != null){
                float weightedHeight = colWeight * parentHeight;
                minHeight = Math.max(minHeight, weightedHeight);
            }

            if(minHeight > remainigCellHeight){
                rowHeights[y] = minHeight;
                remainingHeight -= minHeight;
                remainingChildren--;
                remainigCellHeight = remainingHeight / remainingChildren;
                y = -1;
            }
        }

        for(int i=0;i<gridHeight;i++){
            if(rowHeights[i] == null) {
                rowHeights[i] = remainigCellHeight;
            }
        }

        return rowHeights;
    }

    private float maxMinCellHeightForColumn(IPencil pen, List<SUIComponent> children, List<Object> childArgs, int col, int gridWidth, int gridHeight){
        float maxMinHeight = 0;

        int numChildren = children.size();
        boolean[] skipChilds = new boolean[numChildren];

        for(int i=0;i<numChildren;i++){
            Object args = childArgs.get(i);
            if(skipChilds[i]) continue;

            int pos = i;
            if(args instanceof Integer){
                int _pos = (int) args;
                if(_pos != pos) {
                    pos = _pos;

                    if(pos >= gridWidth * gridHeight){
                        continue;
                    }

                    if(pos < numChildren) {
                        skipChilds[pos] = true;
                    }
                }
            }
            else if(pos >= gridWidth * gridHeight){
                continue;
            }

            int y = pos / gridWidth;

            if(y == col){
                SUIComponent child = children.get(i);
                Float childMinHeight = child.minHeight(pen);
                if(childMinHeight != null) {
                    maxMinHeight = Math.max(maxMinHeight, childMinHeight);
                }
            }
        }

        return maxMinHeight;
    }

    private float maxMinCellWidthForRow(IPencil pen, List<SUIComponent> children, List<Object> childArgs, int row, int gridWidth, int gridHeight){
        float maxMinWidth = 0;

        int numChildren = children.size();
        boolean[] skipChilds = new boolean[numChildren];

        for(int i=0;i<numChildren;i++){
            Object args = childArgs.get(i);
            if(skipChilds[i]) continue;

            int pos = i;
            if(args instanceof Integer){
                int _pos = (int) args;
                if(_pos != pos) {
                    pos = _pos;

                    if(pos >= gridWidth * gridHeight){
                        continue;
                    }

                    if(pos < numChildren) {
                        skipChilds[pos] = true;
                    }
                }
            }
            else if(pos >= gridWidth * gridHeight){
                continue;
            }

            int x = pos % gridWidth;

            if(x == row){
                SUIComponent child = children.get(i);
                Float childMinWidth = child.minWidth(pen);
                if(childMinWidth != null){
                    maxMinWidth = Math.max(maxMinWidth, childMinWidth);
                }
            }
        }

        return maxMinWidth;
    }

    @Override
    public float minWidth(IPencil pen, List<SUIComponent> children, List<Object> childArgs) {
        int numChildren = children.size();
        int gridWidth = this.gridWidth;
        int gridHeight = this.gridHeight;

        if(numChildren > gridWidth * gridHeight){
            int numMissing = numChildren - gridWidth * gridHeight;

            switch (overflowPolicy){
                case OVERFLOW_GROW_HORIZONTAL:
                    int missingColumns = (int) Math.ceil(((float)numMissing) / gridHeight);
                    gridWidth += missingColumns;
                    break;
                case OVERFLOW_GROW_VERTICAL:
                    int missingRows = (int) Math.ceil(((float)numMissing) / gridWidth);
                    gridHeight += missingRows;
                    break;
                case OVERFLOW_HIDE:
                    break;
            }
        }

        float[] allMinWidthPerRow = new float[gridHeight];

        boolean[] skipChilds = new boolean[numChildren];

        for(int i=0;i<numChildren;i++){
            Object args = childArgs.get(i);
            if(skipChilds[i]) continue;

            int pos = i;
            if(args instanceof Integer){
                int _pos = (int) args;
                if(_pos != pos) {
                    pos = _pos;

                    if(pos >= gridWidth * gridHeight){
                        continue;
                    }

                    if(pos < numChildren) {
                        skipChilds[pos] = true;
                    }
                }
            }
            else if(pos >= gridWidth * gridHeight){
                continue;
            }

            int y = pos / gridWidth;

            SUIComponent child = children.get(i);
            Float childMinWidth = child.minWidth(pen);
            if(childMinWidth != null) {
                allMinWidthPerRow[y] += childMinWidth;
            }
        }

        float allMinWidth = 0;
        for(int i=0;i<gridHeight;i++){
            allMinWidth = Math.max(allMinWidth, allMinWidthPerRow[i]);
        }

        return allMinWidth;
    }

    @Override
    public float minHeight(IPencil pen, List<SUIComponent> children, List<Object> childArgs) {
        int numChildren = children.size();
        int gridWidth = this.gridWidth;
        int gridHeight = this.gridHeight;

        if(numChildren > gridWidth * gridHeight){
            int numMissing = numChildren - gridWidth * gridHeight;

            switch (overflowPolicy){
                case OVERFLOW_GROW_HORIZONTAL:
                    int missingColumns = (int) Math.ceil(((float)numMissing) / gridHeight);
                    gridWidth += missingColumns;
                    break;
                case OVERFLOW_GROW_VERTICAL:
                    int missingRows = (int) Math.ceil(((float)numMissing) / gridWidth);
                    gridHeight += missingRows;
                    break;
                case OVERFLOW_HIDE:
                    break;
            }
        }

        float[] allMinHeightPerColumn = new float[gridWidth];

        boolean[] skipChilds = new boolean[numChildren];

        for(int i=0;i<numChildren;i++){
            Object args = childArgs.get(i);
            if(skipChilds[i]) continue;

            int pos = i;
            if(args instanceof Integer){
                int _pos = (int) args;
                if(_pos != pos) {
                    pos = _pos;

                    if(pos >= gridWidth * gridHeight){
                        continue;
                    }

                    if(pos < numChildren) {
                        skipChilds[pos] = true;
                    }
                }
            }
            else if(pos >= gridWidth * gridHeight){
                continue;
            }

            int x = pos % gridWidth;

            SUIComponent child = children.get(i);
            Float childMinHeight = child.minHeight(pen);
            if(childMinHeight != null) {
                allMinHeightPerColumn[x] += childMinHeight;
            }
        }

        float allMinHeight = 0;
        for(int x=0;x<gridWidth;x++){
            allMinHeight = Math.max(allMinHeight, allMinHeightPerColumn[x]);
        }

        return allMinHeight;
    }

    public void gridWidth(int gridWidth){
        this.gridWidth = gridWidth;
    }

    public int gridWidth(){
        return gridWidth;
    }

    public void gridHeight(int gridHeight){
        this.gridHeight = gridHeight;
    }

    public int gridHeight(){
        return gridHeight;
    }

    public void overflowPolicy(int overflowPolicy){
        this.overflowPolicy = overflowPolicy;
    }

    public int overflowPolicy(){
        return overflowPolicy;
    }

    public void rowWeight(int row, double weight){
        rowWeights.put(row, (float)weight);
    }

    public void rowWeights(double... weights){
        for(int x=0;x<weights.length;x++){
            rowWeights.put(x, (float)weights[x]);
        }
    }

    public Float rowWeight(int row){
        return rowWeights.get(row);
    }

    public void colWeight(int col, double weight){
        colWeights.put(col, (float)weight);
    }

    public void colWeights(double... weights){
        for(int y=0;y<weights.length;y++){
            colWeights.put(y, (float)weights[y]);
        }
    }

    public Float colWeight(int col){
        return colWeights.get(col);
    }
}
