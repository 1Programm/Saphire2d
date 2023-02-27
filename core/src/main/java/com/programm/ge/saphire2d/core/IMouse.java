package com.programm.ge.saphire2d.core;

import com.programm.ge.saphire2d.core.bounds.IBounds;

public interface IMouse {

    int BUTTON_LEFT = 0;
    int BUTTON_MID = 1;
    int BUTTON_RIGHT = 2;

    float x();
    float y();

    float dragX();
    float dragY();

    boolean isButtonDown(int button);

    default boolean buttonLeft() {
        return isButtonDown(BUTTON_LEFT);
    }

    default boolean buttonMid() {
        return isButtonDown(BUTTON_MID);
    }

    default boolean buttonRight() {
        return isButtonDown(BUTTON_RIGHT);
    }

    default boolean inside(IBounds bounds){
        return bounds.inside(x(), y());
    }

}
