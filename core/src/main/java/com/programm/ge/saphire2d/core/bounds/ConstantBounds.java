package com.programm.ge.saphire2d.core.bounds;

public class ConstantBounds implements IBounds {

    private final float x, y, width, height;

    public ConstantBounds(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public ConstantBounds(IBounds bounds){
        this(bounds.x(), bounds.y(), bounds.width(), bounds.height());
    }

    @Override
    public float x() {
        return x;
    }

    @Override
    public float y() {
        return y;
    }

    @Override
    public float width() {
        return width;
    }

    @Override
    public float height() {
        return height;
    }

    @Override
    public String toString() {
        return "Const(" + x + ", " + y + ", " + width + ", " + height + ")";
    }
}
