package com.programm.ge.saphire2d.engine.shader;

import org.joml.Matrix4f;
import org.joml.Vector4f;

public class TestShader2 extends Shader {

    private int projection_loc;
    private int transform_loc;

    public TestShader2() {
        super("/shaders/ui/testShader");
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "pos");
        bindAttribute(1, "texCoord");
    }

    @Override
    protected void loadUniformLocations() {
        projection_loc = getUniformLocation("projection");
        transform_loc = getUniformLocation("transform");
    }

    public void loadProjectionMatrix(Matrix4f m){
        loadMatrix(projection_loc, m);
    }

    public void loadTransformationMatrix(Matrix4f m){
        loadMatrix(transform_loc, m);
    }

}
