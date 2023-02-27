package com.programm.ge.saphire2d.core.bounds;

public interface IEditableBounds extends IBounds {

    void x(float x);

    void y(float y);

    void width(float width);

    void height(float height);


    default void position(float x, float y){
        x(x);
        y(y);
    }

    default void size(float width, float height){
        width(width);
        height(height);
    }

    default void bounds(float x, float y, float width, float height){
        x(x);
        y(y);
        width(width);
        height(height);
    }

    default void bounds(IBounds bounds){
        bounds(bounds.x(), bounds.y(), bounds.width(), bounds.height());
    }

}
