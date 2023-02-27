package com.programm.saphire2d.ui.elements.layout;

import com.programm.ge.saphire2d.core.bounds.IBounds;
import com.programm.ge.saphire2d.core.bounds.IEditableBounds;
import com.programm.saphire2d.ui.IPencil;
import com.programm.saphire2d.ui.SUIComponent;

import java.util.List;

public interface ILayout {

    int ALIGN_TOP           = 0;
    int ALIGN_LEFT          = 1;
    int ALIGN_BOTTOM        = 2;
    int ALIGN_RIGHT         = 3;
    int ALIGN_CENTER        = 4;

    void updateBoundsForChildren(IPencil pen, IBounds parent, List<SUIComponent> children, List<Object> childArgs, List<IEditableBounds> bounds);

    float minWidth(IPencil pen, List<SUIComponent> children, List<Object> childArgs);
    float minHeight(IPencil pen, List<SUIComponent> children, List<Object> childArgs);

}
