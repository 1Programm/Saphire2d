package com.programm.ge.saphire2d.engine.utils;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Transformation {

    private final Matrix4f transformation = new Matrix4f();

    public final Vector3f position = new Vector3f();
    public final Vector3f rotation = new Vector3f();
    public final Vector2f size = new Vector2f();

    public Matrix4f asMatrix(){
        MathUtils.getTransformation(transformation, this);
        return transformation;
    }

}
