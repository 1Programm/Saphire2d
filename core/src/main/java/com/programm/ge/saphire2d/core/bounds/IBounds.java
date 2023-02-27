package com.programm.ge.saphire2d.core.bounds;

public interface IBounds {

    static boolean inside(float x, float y, float w, float h, float px, float py){
        return px >= x && py >= y
            && px < x + w && py < y + h;
    }

    static boolean intersects(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2){
        if(x1 >= x2 + w2 || x1 + w1 < x2) return false;
        return y1 < y2 + h2 && y1 + h1 >= y2;
    }

    static IBounds join(IBounds b1, IBounds b2){
        float x1 = b1.x();
        float y1 = b1.y();
        float w1 = b1.width();
        float h1 = b1.height();
        float xe1 = x1 + w1;
        float ye1 = y1 + h1;

        float x2 = b1.x();
        float y2 = b1.y();
        float w2 = b1.width();
        float h2 = b1.height();
        float xe2 = x2 + w2;
        float ye2 = y2 + h2;

        float x, y, w, h;

        boolean isAllLeft;
        boolean isOne = true;

        if(x1 <= x2){
            x = x1;
            isAllLeft = true;
        }
        else {
            x = x2;
            isAllLeft = false;
        }

        if(y1 <= y2){
            y = y1;
            if(!isAllLeft) isOne = false;
        }
        else {
            y = y2;
            if(isAllLeft) isOne = false;
        }

        if(xe1 >= xe2){
            w = xe1 - x;
            if(!isAllLeft) isOne = false;
        }
        else {
            w = xe2 - x;
            if(isAllLeft) isOne = false;
        }

        if(ye1 >= ye2){
            h = ye1 - y;
            if(!isAllLeft) isOne = false;
        }
        else {
            h = ye2 - y;
            if(isAllLeft) isOne = false;
        }

        if(isOne){
            if(isAllLeft){
                return b1;
            }
            else {
                return b2;
            }
        }

        return new ConstantBounds(x, y, w, h);
    }




    float x();
    float y();
    float width();
    float height();

    default boolean inside(float px, float py){
        return inside(x(), y(), width(), height(), px, py);
    }

    default boolean intersects(IBounds other){
        return intersects(x(), y(), width(), height(), other.x(), other.y(), other.width(), other.height());
    }

}
