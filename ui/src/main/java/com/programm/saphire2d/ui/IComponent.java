package com.programm.saphire2d.ui;

import com.programm.ge.saphire2d.core.IKeyboard;
import com.programm.ge.saphire2d.core.IMouse;
import com.programm.ge.saphire2d.core.bounds.IBounds;

public interface IComponent {

    void onMousePressed(IBounds bounds, IMouse mouse, int button);
    void onMouseReleased(IBounds bounds, IMouse mouse, int button);
    void onMouseMoved(IBounds bounds, IMouse mouse);
    void onMouseDragged(IBounds bounds, IMouse mouse, int button);
    void onKeyPressed(IBounds bounds, IKeyboard keyboard, int key);
    void onKeyReleased(IBounds bounds, IKeyboard keyboard, int key);

}
