package com.programm.ge.saphire2d.engine.model;

import com.programm.ge.saphire2d.engine.utils.MathUtils;
import lombok.RequiredArgsConstructor;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

@RequiredArgsConstructor
public class GObject {

    public final Vector3f position = new Vector3f();
    public final Vector3f rotation = new Vector3f();
    public final Vector2f size = new Vector2f();

    public int textureIndex = 0;

    private final Matrix4f transformation = new Matrix4f();

    public Matrix4f getTransformation(){
        MathUtils.getTransformation(transformation, this);
        return transformation;
    }

}
