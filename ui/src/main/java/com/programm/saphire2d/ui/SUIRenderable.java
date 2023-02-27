package com.programm.saphire2d.ui;

import com.programm.ge.saphire2d.core.bounds.IBounds;

public interface SUIRenderable {

    void render(IBounds bounds, IPencil pen);

    Float minWidth(IPencil pen);
    Float minHeight(IPencil pen);

}
