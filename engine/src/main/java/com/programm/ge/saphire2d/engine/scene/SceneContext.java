package com.programm.ge.saphire2d.engine.scene;

import com.programm.ge.saphire2d.core.IKeyboard;
import com.programm.ge.saphire2d.core.IMouse;

public interface SceneContext {

    IMouse mouse();
    IKeyboard keyboard();

    double delta();

}
